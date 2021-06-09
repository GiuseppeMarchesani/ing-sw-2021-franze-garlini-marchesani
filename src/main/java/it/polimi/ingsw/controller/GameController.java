package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Action.ActionToken;
import it.polimi.ingsw.model.Card.*;
import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.network.ResourceToWarehouseRequestMsg;
import it.polimi.ingsw.view.*;

import java.security.InvalidParameterException;
import java.util.*;

import static it.polimi.ingsw.messages.MessageType.*;

public class GameController {
    private int maxPlayers;
    private Game gameSession;
    private final HashMap<String, VirtualView> allVirtualView;
    private TurnController turnController;
    private GameState gameState;
    private ArrayList<DevCard> tempCards;

    public GameController(){
        this.allVirtualView= new HashMap<String, VirtualView>();
        this.gameState= GameState.INIT;
        maxPlayers=0;
        tempCards=new ArrayList<DevCard>();

    }

    public HashMap<String, VirtualView> getAllVirtualView() {
        return allVirtualView;
    }

    public void newPlayer(String username, String gameId, VirtualView virtualView) {
        if(allVirtualView.isEmpty()){
            allVirtualView.put(username, virtualView);
            virtualView.showLoginResult(username,gameId, true);
            virtualView.askPlayersNumber();
        }
        else if(allVirtualView.size()<maxPlayers){
            this.gameSession.addPlayer(new Player(username));
            allVirtualView.put(username, virtualView);
            virtualView.showLoginResult(username, gameId,true);
            if(allVirtualView.size()==maxPlayers){
                startGame();
            }
        }
        else virtualView.showLoginResult(username, gameId,false);

    }

    /** Game state.
     *
     */
    public void getMessage(ClientMessage receivedMessage)throws InvalidParameterException {
            switch (gameState) {
                case INIT:
                    VirtualView virtualView = allVirtualView.get(receivedMessage.getUsername());
                    if(receivedMessage.getMessageType() == PLAYER_NUMBER){

                        PlayersNumberRequest pmsg =(PlayersNumberRequest) receivedMessage;

                        if(pmsg.getPlayersNumber()==1){gameSession=new SinglePlayerGame();
                        gameSession.addPlayer(new Player(pmsg.getUsername()));
                        virtualView.showMessage("Hosting SinglePlayer Game");
                        startGame();
                        }
                        else{
                            gameSession=new Game();
                            gameSession.addPlayer(new Player(pmsg.getUsername()));
                            virtualView.showMessage("Hosting MultiPlayer ("+pmsg.getPlayersNumber()+") Game. \nWaiting for other players...");
                        }
                        maxPlayers=pmsg.getPlayersNumber();
                    }
                case DRAWLEADER:
                case GIVERES:
                    setupGame(receivedMessage);
                    break;
                case IN_GAME:
                case END_GAME:
                    inGame(receivedMessage);
                    break;
                default:
                    for (VirtualView vv : allVirtualView.values()) {
                        vv.showErrorMsg("Error!");
                    }
                    break;
            }

    }

    /**
     * initial phase.
     * @param msg
     */
    public void setupGame(ClientMessage msg){
        Player player=gameSession.getPlayer(msg.getUsername());
         if(msg.getMessageType() == STARTING_LEADERS){
            choseLeader((StartingLeadersRequestMsg) msg,player);

                if(turnController.proxPlayer().equals( turnController.firstPlayer())){
                    if(maxPlayers>2) {
                        setGameState(GameState.GIVERES);
                    //The First two players don't gain any resources
                    turnController.proxPlayer();
                    turnController.proxPlayer();
                    }
                    else{
                        setGameState(GameState.IN_GAME);
                    }
                }

        }
        else if(msg.getMessageType()== RESOURCE_TO_WAREHOUSE){
            ResourceToWarehouseRequestMsg message= ((ResourceToWarehouseRequestMsg) msg);
            placeResWarehouse(player, message.getDepotToResource(), message.getDepotToQuantity(), new ArrayList<Integer>(), 0);
             if(turnController.proxPlayer().equals( turnController.firstPlayer())) {
                 setGameState(GameState.IN_GAME);
             }
        }
        startTurn();
    }


    private void startGame() {
        setGameState(GameState.DRAWLEADER);
        turnController=new TurnController(this);
        switch(maxPlayers){
            case 4:
                gameSession.getPlayersList().get(3).increaseFaith(2);
            case 3:
                gameSession.getPlayersList().get(2).increaseFaith(1);
            default:
                //Don't increase faith but update track
                HashMap<String, Integer> faith=new HashMap<>();
                for(String user:  gameSession.getPlayerListByUsername()){
                    faith.put(user, gameSession.getPlayer(user).getFaithSpace());
                }
                for(VirtualView vv: allVirtualView.values()){

                    vv.showFaithTrack(faith ,false,0);
                }
        }
        broadcastMessage("Everyone joined the game!");
        startTurn();
    }

    public void startTurn(){
        tempCards.clear();
        switch(gameState){
            case DRAWLEADER:
                drawLeaderCards();
                break;
            case GIVERES:
                choseInitialRes();
                break;
            case IN_GAME:
                allVirtualView.get(turnController.getActivePlayer()).askAction();
                break;

        }
    }
    /**
     * to discard leader's card that the player chose.
     * @param msg
     * @param player (who send message)
     */
    private void choseLeader(StartingLeadersRequestMsg msg, Player player){
        for(int i=0; i<2;i++){
            player.getLeaderCards().put(msg.getLeaderCard().get(i), false);
        }
        for(VirtualView vv: allVirtualView.values()){
            vv.showRemainingLeaderCards(msg.getUsername(), 2);
        }

    }
    private void drawLeaderCards(){
        broadcastMessage("It's "+ turnController.getActivePlayer()+"'s turn to discard leader cards.");
        ArrayList<LeaderCard> leaderCards= new ArrayList<>();
        for(int j=0; j<4; j++){
            leaderCards.add(gameSession.drawCard());
        }
        allVirtualView.get(turnController.getActivePlayer()).askLeaderCardToKeep(leaderCards);

    }

    /**
     * to pick resource that the player chose.
     */
    private void choseInitialRes(){
        String activePlayer= turnController.getActivePlayer();

        if(activePlayer.equals(turnController.getPlayerOrder().get(2))){
            allVirtualView.get(activePlayer).askInitialRes(1);
        }
        else{
            allVirtualView.get(activePlayer).askInitialRes(2);
        }
    }
    public void placeResWarehouse(Player player, HashMap<Integer,ResourceType> depotToResource, HashMap<Integer,Integer> depotToQuantity, ArrayList<Integer> resourceToLeader, int discard){
        player.getWarehouse().replaceResources(depotToResource, depotToQuantity, resourceToLeader);
        for (VirtualView vv : allVirtualView.values()) {
            vv.showWarehouse(player.getWarehouse().getDepotToQuantity(), player.getWarehouse().getDepotToResource(), turnController.getActivePlayer());
        }
        increaseFaith(discard, 2);
    }



    public boolean isGameStarted(){
        return gameState!=GameState.INIT;
    }
    public Game getGameSession(){
        return gameSession;
    }
    public TurnController getTurnController() {
        return turnController;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void disconnect(String username){
        if(turnController.disconnect(username))
        {

            if(turnController.proxPlayer().equals(turnController.firstPlayer())){
                switch(gameState){
                    case DRAWLEADER:
                            setGameState(GameState.GIVERES);
                        break;
                    case GIVERES:
                        setGameState(GameState.IN_GAME);
                        break;
                    default:
                        //None
                }
            }
            startTurn();
        }
    }

    public List<String> getInactivePlayers(){
        return turnController.getInactivePlayers();
    }
    public boolean hasInactivePlayers(){
        return turnController.hasInactivePlayers();
    }

    public void reconnect(String username, VirtualView virtualView){
        allVirtualView.put(username, virtualView);
        turnController.reconnect(username);
        broadcastMessage(username + "has reconnected.");
    }
    public ArrayList<ResourceType> availableRes(){
        ArrayList<ResourceType> resource= new ArrayList<>();
        for(ResourceType resourceType: ResourceType.values()){
            if(!resourceType.equals(ResourceType.ANY) && !resourceType.equals(ResourceType.EMPTY) &&
                    !resourceType.equals(ResourceType.FAITH))
                resource.add(resourceType);
        }
        return resource;
    }
    public ArrayList<String> availableResToString() {
        ArrayList<String> resource = new ArrayList<>();
        for (ResourceType resourceType : ResourceType.values()) {
            if (!resourceType.equals(ResourceType.ANY) && !resourceType.equals(ResourceType.EMPTY) &&
                    !resourceType.equals(ResourceType.FAITH))
                resource.add(resourceType.toString());
        }
        return resource;
    }

    public void broadcastMessage(String message) {
        for (VirtualView vv : allVirtualView.values()) {
            vv.showMessage(message);
        }
    }

    public GameState getGameState() {
        return gameState;
    }
    /**
     * in game phase
     * @param msg
     */
    public void inGame(ClientMessage msg){
        if (msg.getUsername()== turnController.getActivePlayer()){
           Player player =gameSession.getPlayer(msg.getUsername());
            switch(msg.getMessageType()){
                case LEADER_ACTION:
                    leaderAction(((LeaderActionRequest) msg).getCard(), ((LeaderActionRequest) msg).isChoseToPlay(), player );
                    startTurn();
                    break;
                case SIDE_LEADER:
                    sendLeaderCards(player.getLeaderCardList());
                    break;
                case ACTIVATE_PRODUCTION:
                    turnController.setMainAction(true);
                    confirmProduction(((GetProductionRequest) msg).getExpenseDepot(), ((GetProductionRequest) msg).getNewStrongbox(), player);
                    startTurn();
                    break;
                case CHECK_PRODUCTION:
                    produceResources(((AskProductionRequest) msg).getChosen(), player);
                    break;
                case MAIN_PRODUCTION:
                    if(!turnController.getMainAction()){
                        allVirtualView.get(msg.getUsername()).askCardsToActivateProd(player.getDevCardSlot().getCardsAvailable());
                    }
                    else {
                        allVirtualView.get(turnController.getActivePlayer()).showMessage("You can't do a Main Action now");

                        startTurn();
                    }
                    break;
                case PLACE_CARD:
                    turnController.setMainAction(true);
                    placeCard((PlaceDevCardRequest) msg, tempCards.get(0), player);
                    startTurn();
                    break;
                case PICK_DEVCARD:
                    getMarketDevCard((BuyDevCardRequest) msg, player);
                    break;
                case MAIN_CARD:
                    if(!turnController.getMainAction()){
                        allVirtualView.get(msg.getUsername()).askDevCardToBuy();
                    }
                    else {
                        allVirtualView.get(turnController.getActivePlayer()).showMessage("You can't do a Main Action now");

                        startTurn();
                    }
                    break;
                case RESOURCE_TO_WAREHOUSE:
                    ResourceToWarehouseRequestMsg message= (ResourceToWarehouseRequestMsg) msg;
                    placeResWarehouse(player, message.getDepotToResource(), message.getDepotToQuantity(), message.getLeaderToDepot(), message.getDiscard());
                    startTurn();
                    break;
                case MAIN_MARBLE:
                    if(!turnController.getMainAction()){
                    allVirtualView.get(msg.getUsername()).askMarketLineToGet(player.getMarbleConversion());
                    turnController.setMainAction(true);
                    }
                    else {
                        allVirtualView.get(turnController.getActivePlayer()).showMessage("You can't do a Main Action now");

                        startTurn();
                    }
                    break;
                case PICK_MARKETRES:
                        getMarketResources((GetMarketResRequest) msg,player);
                    break;
                case END_TURN:
                    endTurn();

                    break;
                case SHOW_FAITH_TRACK:
                    HashMap<String, Integer> faith= new HashMap<>();
                    for(String user: gameSession.getPlayerListByUsername()){
                        faith.put(user, gameSession.getPlayer(user).getFaithSpace());
                    }
                    allVirtualView.get(turnController.getActivePlayer()).showFaithTrack(faith, false,0);
                    break;
                case SHOW_VICTORY_POINTS:
                    HashMap<String, Integer> vp= new HashMap<>();
                    for(String user: gameSession.getPlayerListByUsername()){
                        vp.put(user, gameSession.getPlayer(user).getVictoryPoint());
                    }
                    allVirtualView.get(turnController.getActivePlayer()).showCurrentVP(vp);
                    break;
            }
        }
        else{
            allVirtualView.get(msg.getUsername()).showErrorMsg("Not your turn!");
        }
    }



    public void getMarketResources(GetMarketResRequest msg,Player player){

            HashMap<ResourceType,Integer> resource= gameSession.pickMarketRes(((GetMarketResRequest) msg).getRowOrCol(),((GetMarketResRequest) msg).getNum(),((GetMarketResRequest) msg).getConversion());
             for (VirtualView vv : allVirtualView.values()) {
                vv.showMarket(gameSession.getMarket().getMarketTray(), gameSession.getMarket().getCornerMarble());

            }
             if(resource.containsKey(ResourceType.FAITH)){
                increaseFaith(resource.get(ResourceType.FAITH), 0);
                resource.remove(ResourceType.FAITH);
            }
            int any=0;
            if( resource.containsKey(ResourceType.ANY)){
                any=resource.get(ResourceType.ANY);
                resource.remove(ResourceType.ANY);
            }
            HashMap<ResourceType,Integer> resourceW=player.getWarehouse().getAllResources();
            for(ResourceType r : resourceW.keySet()){
                if(resource.containsKey(r)){
                    resource.put(r, resource.get(r)+resourceW.get(r));
                }
                else   resource.put(r, resourceW.get(r));
            }

            allVirtualView.get(turnController.getActivePlayer()).askResourceToWarehouse(resource,any,player.getWarehouse().getLeaderDepot());

    }
    public void getMarketDevCard(BuyDevCardRequest msg, Player player) {

        ArrayList<Integer> slots = player.getDevCardSlot().getAvailableSlots(msg.getLevel());
        if (slots.size() == 0) {
            allVirtualView.get(msg.getUsername()).showErrorMsg("No slot to place the card!");
            startTurn();
            return;
        }
        DevCard card = gameSession.pickDevCard(msg.getColor(), msg.getLevel());
        if (card == null) {
            allVirtualView.get(msg.getUsername()).showErrorMsg("The pile is empty!");
            startTurn();
            return;
        }
        tempCards.add(card);

        HashMap<ResourceType, Integer> discountedPrice = new HashMap<>();
        for (ResourceType r : card.getCardCost().keySet()) {
            if (player.getResourceDiscount().containsKey(r)) {
                int updated = card.getCardCost().get(r) - player.getResourceDiscount().get(r);
                if (updated < 0) {
                    updated = 0;
                }
                discountedPrice.put(r, updated);
            } else discountedPrice.put(r, card.getCardCost().get(r));
        }
        int any=0;
        if(card.getCardCost().containsKey(ResourceType.ANY)){
            any=card.getCardCost().get(ResourceType.ANY);
        }
        if (player.checkPriceCanBePaid(discountedPrice)){
            allVirtualView.get(msg.getUsername()).askSlot(player.getStrongbox(), card.getCardCost(), any, slots);
        }
        else{
            allVirtualView.get(msg.getUsername()).showErrorMsg("Not enough resources!");
            gameSession.returnDevCard(card);
            startTurn();
        }



    }
    public void placeCard(PlaceDevCardRequest msg, DevCard card, Player player){
        player.setStrongbox(msg.getNewStrongbox());
        player.getDevCardSlot().getSlotDev().get(msg.getSlotToPlace()).add(card);
        player.getWarehouse().spendResources(msg.getExpenseDepot());
        for (VirtualView vv : allVirtualView.values()) {
            vv.showSlots(player.getDevCardSlot(), turnController.getActivePlayer());
            vv.showStrongbox(msg.getNewStrongbox(), turnController.getActivePlayer());
            vv.showWarehouse(player.getWarehouse().getDepotToQuantity(), player.getWarehouse().getDepotToResource(), turnController.getActivePlayer());
            vv.showDevMarket(gameSession.getCardMarket().availableCards(), gameSession.getCardMarket().remainingCards());
        }
        if(player.getDevCardSlot().getCardQuantity()==7){
            setGameState(GameState.END_GAME);
            broadcastMessage("We're in the endgame now. Every player until the first gets one last turn!");
        }

    }
    public void increaseFaith(int faithQuantity, int who){
        ArrayList<Player> vpWinners=new ArrayList<Player>();
        int thresholdL=gameSession.getFaithTrack().getNextFaithZone().getStart();
        boolean trigger=false;
        int thresholdH=gameSession.getFaithTrack().getNextFaithZone().getEnd();
        HashMap<String, Integer> faith= new HashMap<String, Integer>();
        //0 = Only activePlayer
        //1 = everyone
        //2 = everyone except activePlayer
            if(who==0||who==1){
               if(gameSession.getPlayer(turnController.getActivePlayer()).increaseFaith(faithQuantity)>thresholdL){
                   vpWinners.add(gameSession.getPlayer(turnController.getActivePlayer()));
               }
               if(gameSession.getPlayer(turnController.getActivePlayer()).getFaithSpace()>thresholdH){
                   trigger=true;
               }

            }

            for(String user:  gameSession.getPlayerListByUsername()){
                faith.put(user, gameSession.getPlayer(user).getFaithSpace());
                if((who==1||who==2)&&!(user.equals(turnController.getActivePlayer()))){
                     if(gameSession.getPlayer(user).increaseFaith(faithQuantity)>thresholdL){
                         vpWinners.add(gameSession.getPlayer(user));
                     }
                     if(gameSession.getPlayer(user).getFaithSpace()>thresholdH){
                         trigger=true;
                     }

                }
            }
            for(VirtualView vv: allVirtualView.values()){
                vv.showFaithTrack(faith, trigger,gameSession.getFaithTrack().indexOfNextFaithZone() );
            }

            if (trigger){
               int vp=gameSession.getFaithTrack().getNextFaithZone().getFaithZoneVP();
               for(Player p: vpWinners){
                   faith.put(p.getUsername(), p.increaseVP(vp));
               }
                for(VirtualView vv: allVirtualView.values()){
                    vv.showCurrentVP(faith);
                }
                if(gameSession.getFaithTrack().indexOfNextFaithZone()==2){
                    setGameState(GameState.END_GAME);
                    broadcastMessage("We're in the endgame now. Every player until the first gets one last turn!");
                }
                gameSession.getFaithTrack().getNextFaithZone().setActivated();
            }
        }
    public void produceResources(ArrayList<DevCard> cards, Player player){
        HashMap<ResourceType, Integer> price= new HashMap<>();
        int anyProduce=0;
        for(DevCard card : cards){
            HashMap<ResourceType, Integer> cost=card.getCardCost();
            for(ResourceType r: cost.keySet()){
                if(price.containsKey(r)){
                    price.put(r, price.get(r)+cost.get(r));
                }
                else price.put(r, cost.get(r));
            }
            if(card.getProductionIncome().containsKey(ResourceType.ANY)){
                anyProduce+=card.getProductionIncome().get(ResourceType.ANY);
            }
            tempCards.add(card);
        }
        int anyPayment=0;
        if(price.containsKey(ResourceType.ANY)){
            anyPayment=price.get(ResourceType.ANY);
        }
        if(player.checkPriceCanBePaid(price)){
            allVirtualView.get(turnController.getActivePlayer()).askProduction(player.getStrongbox(), price, anyPayment, anyProduce);

        }
        else {
            allVirtualView.get(turnController.getActivePlayer()).showErrorMsg("Not enough resources!");
            startTurn();
        }

    }
    public void confirmProduction(HashMap<ResourceType, Integer> depotExpense, HashMap<ResourceType, Integer> strongbox, Player player){
        player.getWarehouse().spendResources(depotExpense);
        for(DevCard card :tempCards){
            for (ResourceType r: card.getProductionIncome().keySet()){
                if (r!=ResourceType.ANY){
                    if(strongbox.containsKey(r)){
                        strongbox.put(r, strongbox.get(r)+card.getProductionIncome().get(r));
                    }
                    else  strongbox.put(r, card.getProductionIncome().get(r));
                }
            }
        }
        if(strongbox.containsKey(ResourceType.FAITH)){
            increaseFaith(strongbox.get(ResourceType.FAITH), 0);
            strongbox.remove(ResourceType.FAITH);
        }
        player.setStrongbox(strongbox);
        for (VirtualView vv : allVirtualView.values()) {
            vv.showStrongbox(strongbox, turnController.getActivePlayer());
            vv.showWarehouse(player.getWarehouse().getDepotToQuantity(), player.getWarehouse().getDepotToResource(), turnController.getActivePlayer());
        }
    }
    public void sendLeaderCards(ArrayList<LeaderCard> cards){
        allVirtualView.get(turnController.getActivePlayer()).askLeaderCardToPlay(cards);
    }
    private void leaderAction(LeaderCard card, boolean choseToPlay, Player player) {
        if(!choseToPlay){
            increaseFaith(1, 0);
            player.discardLeader(card);
            broadcastMessage(player.getUsername() + " has discarded a leader card.");
        }
        else{
            switch(card.getCostType()){
                case RESOURCES:
                    if(!player.checkPriceCanBePaid(((LeaderDepot) card).getCost())){
                        allVirtualView.get(turnController.getActivePlayer()).showErrorMsg("Not enough resources!");
                        return;
                    }
                    player.playLeader(card);
                    for(VirtualView vv: allVirtualView.values()) {
                        vv.showWarehouse(player.getWarehouse().getDepotToQuantity(), player.getWarehouse().getDepotToResource(), turnController.getActivePlayer());
                    }
                    break;
                case LEVEL_TWO:
                    if(checkLevelTwoColor(((LeaderProduction) card).getColorCost(), player)){
                        player.playLeader(card);
                        for(VirtualView vv: allVirtualView.values()) {
                            vv.showSlots(player.getDevCardSlot(), turnController.getActivePlayer());
                        }
                     }
                    else  {
                        allVirtualView.get(turnController.getActivePlayer()).showErrorMsg("You don't have the right cards!");
                    return;
                    }

                    break;
                case DEV_CARD_SINGLE:
                    if(checkCardColorRequirements(player, ((LeaderDiscount) card).getCost())){
                        player.playLeader(card);
                    }
                    else return;
                    break;
                case DEV_CARD_DOUBLE:
                    if(checkCardColorRequirements(player, ((LeaderMarble) card).getCost())){
                        player.playLeader(card);
                    }
                    else return;
                    break;
            }
            broadcastMessage(player.getUsername()+ " has played a Leader Power: " + card.toString());
        }
    }
    public boolean checkCardColorRequirements(Player player, HashMap<Color, Integer> requirement){
        for(Color c: requirement.keySet()){
            if(! player.checkHasEnoughCardOfColor(c, requirement.get(c))){
                allVirtualView.get(turnController.getActivePlayer()).showErrorMsg("You don't have the right cards!");
                return false;
            }
        }
        return true;
    }
    public boolean checkLevelTwoColor(Color color , Player player){
        return player.getDevCardSlot().hasLevelTwoOfColor(color);
    }
    public void countFinalVictoryPoints(){
        for(VirtualView vv: allVirtualView.values()){
            vv.showWinMessage(gameSession.endGame());
        }

    }
    public void endTurn(){
        ActionToken token = gameSession.drawToken();
        if(token!=null){
            switch (token.getType()){
                case FAITH:
                    allVirtualView.get(turnController.getActivePlayer()).showFaithTrack(gameSession.getFaithMap(), gameSession.updateFaithTrack(), gameSession.lastActivatedFaithZone());
                    break;
                case DISCARD:
                    allVirtualView.get(turnController.getActivePlayer()).showDevMarket(gameSession.getCardMarket().availableCards(), gameSession.getCardMarket().remainingCards());
                    break;
            }
        }

        if(gameSession.checkLoss()){
            allVirtualView.get(turnController.getActivePlayer()).showLoseMessage();
            return;
        }
        if(gameState==GameState.END_GAME&&turnController.proxPlayer()== turnController.firstPlayer()){
            countFinalVictoryPoints();
        }
        else startTurn();
    }
}
