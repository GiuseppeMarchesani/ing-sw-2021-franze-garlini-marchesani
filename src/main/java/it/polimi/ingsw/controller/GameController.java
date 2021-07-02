package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Action.ActionToken;
import it.polimi.ingsw.model.Card.*;
import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.messages.ResourceToWarehouseRequestMsg;
import it.polimi.ingsw.view.*;

import java.security.InvalidParameterException;
import java.util.*;

import static it.polimi.ingsw.messages.MessageType.*;

/**
 * This class manage the game evolution.
 * Sends messages through the Virtual Views.
 * Reads messages coming from the Client.
 */
public class GameController {
    private int maxPlayers;
    private Game gameSession;
    private final HashMap<String, VirtualView> allVirtualView;
    private TurnController turnController;
    private GameState gameState;
    private ArrayList<DevCard> tempCards;

    /**
     * Default constructor.
     */
    public GameController(){
        this.allVirtualView= new HashMap<>();
        this.gameState= GameState.INIT;
        maxPlayers=0;
        tempCards=new ArrayList<>();

    }


    /**
     * Adds a new Virtual View.
     * @param username username of the Player's Virtual View.
     * @param gameId id belonging to the game the Player just joined.
     * @param virtualView Player's Virtual View.
     * @return if the player joined succesfully
     */
    public boolean newPlayer(String username, String gameId, VirtualView virtualView) {
        if(allVirtualView.isEmpty()){
            allVirtualView.put(username, virtualView);
            virtualView.showLoginResult(username,gameId, true);
            virtualView.askPlayersNumber();
            return true;
        }
        else if(allVirtualView.size()<maxPlayers){
            this.gameSession.addPlayer(new Player(username));
            allVirtualView.put(username, virtualView);
            virtualView.showLoginResult(username, gameId,true);
            if(allVirtualView.size()==maxPlayers){
                startGame();
            }
            return true;
        }
        else virtualView.showLoginResult(username, gameId,false);
        return false;

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
                        maxPlayers=pmsg.getPlayersNumber();
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

                    }
                    break;
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
                    if(maxPlayers>=2) {
                        setGameState(GameState.GIVERES);
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
        if(gameState==GameState.IN_GAME){
            for(VirtualView vv: allVirtualView.values()){
                vv.showDevMarket(gameSession.getCardMarket().availableCards(), gameSession.getCardMarket().remainingCards());
                vv.showMarket(gameSession.getMarket().getMarketTray(), gameSession.getMarket().getCornerMarble());
                for(String s: allVirtualView.keySet()){
                    if (!s.equals(getActivePlayer())){
                        allVirtualView.get(s).showPlayerTurn(getActivePlayer());
                    }
                }
            }
        }
        startTurn();
    }

    /**
     * Gives starting faith to players, sets the game state to DRAWLEADER to distribute leader cards.
     */
    private void startGame() {
        setGameState(GameState.DRAWLEADER);
        turnController=new TurnController(this);
        switch(maxPlayers){
            case 4:
                gameSession.getPlayersList().get(3).increaseFaith(2);
                allVirtualView.get(gameSession.getPlayerListByUsername().get(3)).showPlayerFaith(gameSession.getFaith(gameSession.getPlayerListByUsername().get(3)));
            case 3:
                gameSession.getPlayersList().get(2).increaseFaith(1);
                allVirtualView.get(gameSession.getPlayerListByUsername().get(2)).showPlayerFaith(gameSession.getFaith(gameSession.getPlayerListByUsername().get(2)));
        }
        broadcastMessage("Everyone joined the game!");
        startTurn();
    }

    /**
     * Starts the turn, the action depends on the state of the game.
     * Always clears the array of temporary cards.
     */
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
            case END_GAME:
                allVirtualView.get(getActivePlayer()).askAction();
                break;

        }
    }
    /**
     * to discard leader cards that the player chose.
     * @param msg
     * @param player (who sent the message)
     */
    private void choseLeader(StartingLeadersRequestMsg msg, Player player){
        for(int i=0; i<2;i++){
            player.getLeaderCards().put(msg.getLeaderCard().get(i), false);
        }
        broadcastMessage(player.getUsername()+" has chosen his leader cards. ");

    }
    /**
     *Gives 4 leader cards to a player for him to discard.
     */
    private void drawLeaderCards(){
        for(String s: allVirtualView.keySet()){
            if (!s.equals(getActivePlayer())){
                allVirtualView.get(s).showPlayerTurn(getActivePlayer());
            }
        }
        ArrayList<LeaderCard> leaderCards= new ArrayList<>();
        for(int j=0; j<4; j++){
            leaderCards.add(gameSession.drawCard());
        }
        allVirtualView.get(getActivePlayer()).askLeaderCardToKeep(leaderCards);

    }

    /**
     *Asks which starting resources a player wants.
     *
     */
    private boolean choseInitialRes(){
        String activePlayer= getActivePlayer();
        for(String s: allVirtualView.keySet()){
            if (!s.equals(getActivePlayer())){
                allVirtualView.get(s).showPlayerTurn(getActivePlayer());
            }
        }
        try {
            if (activePlayer.equals(turnController.getPlayerOrder().get(1))) {
                allVirtualView.get(activePlayer).askInitialRes(1);

                return true;
            }
            else if (activePlayer.equals(turnController.getPlayerOrder().get(2))) {
                allVirtualView.get(activePlayer).askInitialRes(1);
                return true;
            } else if(activePlayer.equals(turnController.getPlayerOrder().get(3))) {
                allVirtualView.get(activePlayer).askInitialRes(2);
                return true;
            }
            else{
                turnController.proxPlayer();
                return false;
            }
        }
        catch (IndexOutOfBoundsException e){
            gameState=GameState.IN_GAME;

            turnController.proxPlayer();
            for(String s: allVirtualView.keySet()){
                if (!s.equals(getActivePlayer())){
                    allVirtualView.get(s).showPlayerTurn(getActivePlayer());
                }
            }
            return true;
        }
    }
    /**
     * Adds resources to a player's warehouse.
     * @param player the player to give resources to.
     * @param depotToResource map from depot floor to resource.
     * @param depotToQuantity map from depot floor to quantity.
     * @param resourceToLeader quantity of resources for each leader depot.
     * @param discard how many resources were discarded
     */
    public void placeResWarehouse(Player player, HashMap<Integer,ResourceType> depotToResource, HashMap<Integer,Integer> depotToQuantity, ArrayList<Integer> resourceToLeader, int discard){
        player.getWarehouse().replaceResources(depotToResource, depotToQuantity, resourceToLeader);
        allVirtualView.get(getActivePlayer()).showWarehouse(player.getWarehouse().getDepotToQuantity(), player.getWarehouse().getDepotToResource(), getActivePlayer());
    if (discard!=0)
        increaseFaith(discard, false);
    }


    /**
     *@return false if game hasn't started yet.
     */
    public boolean isGameStarted(){
        return gameState!=GameState.INIT;
    }
    /**
     *@return the game object.
     */
    public Game getGameSession(){
        return gameSession;
    }

    /**
     *Sets the game state to a new one.
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    /**
     *Returns the gamestate
     * @return returns the current gamestate
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     *Removes the player from the game and checks if he was the active player, in which case starts a new turn.
     *@param username the username who disconnected
     */
    public void disconnect(String username){
        if(username.equals(getActivePlayer()))
        {

            if(turnController.proxPlayer().equals(turnController.firstPlayer())&&turnController.getActivePlayers().size()!=0){
                switch(gameState){
                    case DRAWLEADER:
                        if(maxPlayers>=2){
                            setGameState(GameState.GIVERES);}
                        break;
                    case GIVERES:
                        setGameState(GameState.IN_GAME);
                        for(String s: allVirtualView.keySet()){
                            if (!s.equals(getActivePlayer())){
                                allVirtualView.get(s).showPlayerTurn(getActivePlayer());
                            }
                        }
                        break;
                    default:
                        //None
                }
            }
            turnController.disconnect(username);
            startTurn();
        }
        turnController.disconnect(username);
    }
    /**
     *@return A List of usernames of disconnected players from an ongoing game
     */
    public List<String> getInactivePlayers(){
        return turnController.getInactivePlayers();
    }
    /**
     *@return if the game is ongoing and has disconnected players.
     */
    public boolean hasInactivePlayers(){
        return turnController.hasInactivePlayers();
    }

    /**Reconnects a player who previously disconnected to an ongoing game.
     * @param username of the player reconnecting.
     * @param virtualView the view of the player reconnecting.
     */
    public void reconnect(String username, VirtualView virtualView){
        allVirtualView.put(username, virtualView);
        turnController.reconnect(username);
        broadcastMessage(username + " has reconnected.");
        showPlayer(gameSession.getPlayer(username),username);
        allVirtualView.get(username).showMarket(gameSession.getMarket().getMarketTray(), gameSession.getMarket().getCornerMarble());
        allVirtualView.get(username).showDevMarket(gameSession.getCardMarket().availableCards(), gameSession.getCardMarket().remainingCards());
        allVirtualView.get(username).showPlayerTurn(getActivePlayer());

    }
    /**Sends a string message to every player in the game
     * @param  message the String to send.
     */
    public void broadcastMessage(String message) {
        for (VirtualView vv : allVirtualView.values()) {
            vv.showMessage(message);
        }
    }

    /**
     * Handles messages when gameState==GameState.IN_GAME
     * @param msg message sent from the client.
     */
    public void inGame(ClientMessage msg){
        if (msg.getUsername().equals(getActivePlayer())){
           Player player =gameSession.getPlayer(msg.getUsername());
            switch(msg.getMessageType()){
                case LEADER_ACTION:
                    leaderAction(((LeaderActionRequest) msg).getCard(), ((LeaderActionRequest) msg).isChoseToPlay(), player );
                    startTurn();
                    break;
                case SIDE_LEADER:
                    sendLeaderCards((player.getLeaderCardList()));
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
                    if(!isMainActionDone()){
                        allVirtualView.get(msg.getUsername()).askCardsToActivateProd(player.getDevCardSlot().getCardsAvailable());
                    }
                    else {
                        allVirtualView.get(getActivePlayer()).showMessage("You can't do a Main Action now");

                        startTurn();
                    }
                    break;
                case PLACE_CARD:

                    placeCard((PlaceDevCardRequest) msg, tempCards.get(0), player);
                    startTurn();
                    break;
                case PICK_DEVCARD:
                    getMarketDevCard((BuyDevCardRequest) msg, player);
                    break;
                case MAIN_CARD:
                    if(!isMainActionDone()){
                        allVirtualView.get(msg.getUsername()).askDevCardToBuy();
                    }
                    else {
                        allVirtualView.get(getActivePlayer()).showMessage("You can't do a Main Action now");

                        startTurn();
                    }
                    break;
                case RESOURCE_TO_WAREHOUSE:
                    ResourceToWarehouseRequestMsg message= (ResourceToWarehouseRequestMsg) msg;
                    placeResWarehouse(player, message.getDepotToResource(), message.getDepotToQuantity(), message.getLeaderToDepot(), message.getDiscard());
                    startTurn();
                    break;
                case MAIN_MARBLE:
                    if(!isMainActionDone()){
                    allVirtualView.get(msg.getUsername()).askMarketLineToGet(player.getMarbleConversion());
                    turnController.setMainAction(true);
                    }
                    else {
                        allVirtualView.get(getActivePlayer()).showMessage("You can't do a Main Action now");

                        startTurn();
                    }
                    break;
                case PICK_MARKETRES:
                        getMarketResources((GetMarketResRequest) msg,player);
                    break;
                case END_TURN:
                    if(isMainActionDone()) endTurn();
                    else {allVirtualView.get(getActivePlayer()).showMessage("You Need to do a main action before ending your turn!");
                    startTurn();}

                    break;
                case SHOW_FAITH_TRACK:
                    allVirtualView.get(getActivePlayer()).showFaithTrack(false,0);
                    startTurn();
                    break;
                case SHOW_VICTORY_POINTS:
                    allVirtualView.get(getActivePlayer()).showCurrentVP(gameSession.getVictoryPoints());
                    startTurn();
                    break;
                case SHOW_SLOT:
                    allVirtualView.get(getActivePlayer()).showSlots(player.getDevCardSlot(), getActivePlayer());
                    startTurn();
                    break;
                case SHOW_LEADER:
                    allVirtualView.get(getActivePlayer()).showLeaderCards(player.getLeaderCards());
                    startTurn();
                    break;
                case SHOW_DEV_MARKET:
                    allVirtualView.get(getActivePlayer()).showDevMarket(gameSession.getCardMarket().availableCards(), gameSession.getCardMarket().remainingCards());
                    startTurn();
                    break;
                case SHOW_MARKET:
                    allVirtualView.get(getActivePlayer()).showMarket(gameSession.getMarket().getMarketTray(), gameSession.getMarket().getCornerMarble());
                    startTurn();
                    break;
                case SHOW_STRONGBOX:
                    allVirtualView.get(getActivePlayer()).showStrongbox(player.getStrongbox(), getActivePlayer());
                    startTurn();
                    break;
                case SHOW_WAREHOUSE:
                    allVirtualView.get(getActivePlayer()).showWarehouse(player.getWarehouse().getDepotToQuantity(), player.getWarehouse().getDepotToResource(), getActivePlayer());
                    startTurn();
                    break;
                case ASK_PLAYER:
                    allVirtualView.get(getActivePlayer()).showPlayerList(turnController.getPlayerOrder());
                    break;
                case SHOW_PLAYER:

                    player=gameSession.getPlayer((((ShowPlayerRequest) msg).getPlayer()));
                        if((((ShowPlayerRequest) msg).getPlayer()).equals(getActivePlayer())) {
                            showPlayer(player, getActivePlayer());
                            startTurn();
                        }
                        else showPlayer(player, getActivePlayer());
                    break;
                case SHOW_PLAYER_FAITH:
                    allVirtualView.get(getActivePlayer()).showPlayerFaith(gameSession.getFaith(getActivePlayer()));
                    startTurn();
            }
        }
        else{
            allVirtualView.get(msg.getUsername()).showErrorMsg("Not your turn!");
        }
    }

    /**Asks the player in which depots he would like to store the resources obtained with a market action.
     *
     * @param msg the message containing the row or column sent from the active player
     * @param player the player object associated to the active player
     */

    public void getMarketResources(GetMarketResRequest msg,Player player){

            HashMap<ResourceType,Integer> resource= gameSession.pickMarketRes( msg.getRowOrCol(), msg.getNum(),msg.getConversion());
             for (VirtualView vv : allVirtualView.values()) {
                vv.showMarket(gameSession.getMarket().getMarketTray(), gameSession.getMarket().getCornerMarble());

            }
             if(resource.containsKey(ResourceType.FAITH)){
                increaseFaith(resource.get(ResourceType.FAITH), true);
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

            allVirtualView.get(getActivePlayer()).askResourceToWarehouse(resource,any,player.getWarehouse().getLeaderDepot());

    }

    /**Asks the player in which slot he would like to store the card, if no card is available sends an error message.
     *
     * @param msg the message containing the color and the level of the card requested.
     * @param player the player object associated to the active player
     */
    public void getMarketDevCard(BuyDevCardRequest msg, Player player) {

        ArrayList<Integer> slots = player.getDevCardSlot().getAvailableSlots(msg.getLevel());
        if (slots.size() == 0) {
            allVirtualView.get(msg.getUsername()).showErrorMsg("No slot to place the card!");
            startTurn();
            return;
        }
        DevCard card = gameSession.pickDevCard(msg.getColor(), msg.getLevel()-1);
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
            allVirtualView.get(msg.getUsername()).askSlot(player.getStrongbox(), player.getWarehouse().getAllResources(), discountedPrice, any, slots);
            turnController.setMainAction(true);
        }
        else{
            allVirtualView.get(msg.getUsername()).showErrorMsg("Not enough resources!");
            gameSession.returnDevCard(card);
            startTurn();
        }



    }

    /**Places the card in the requested slot and triggers the endgame turns if the player has 7 cards.
     *
     * @param msg the message containing the requested slot.
     * @param player the player object associated to the active player
     */
    public void placeCard(PlaceDevCardRequest msg, DevCard card, Player player){
        player.setStrongbox(msg.getNewStrongbox());
        player.getDevCardSlot().getSlotDev().get(msg.getSlotToPlace()).add(card);
        player.getWarehouse().spendResources(msg.getExpenseDepot());
        for (VirtualView vv : allVirtualView.values()) {

            vv.showDevMarket(gameSession.getCardMarket().availableCards(), gameSession.getCardMarket().remainingCards());
        }
        allVirtualView.get(getActivePlayer()).showSlots(player.getDevCardSlot(), getActivePlayer());
        allVirtualView.get(getActivePlayer()).showStrongbox(msg.getNewStrongbox(), getActivePlayer());
        allVirtualView.get(getActivePlayer()).showWarehouse(player.getWarehouse().getDepotToQuantity(), player.getWarehouse().getDepotToResource(), getActivePlayer());
        if(player.getDevCardSlot().getCardQuantity()==7){
            broadcastMessage("A player has bought 7 development cards.");
            broadcastMessage("We're in the endgame now.");
            setGameState(GameState.END_GAME);
        }

    }

    /**
     * Increases faith of active player, or every other player (Moves the black cross in case of SinglePlayer Game
     * @param faithQuantity the quantity to add to the current faith space
     * @param activateOnYourself if true, increases faith of active player, if false, increase of every other player.
     */
    public void increaseFaith(int faithQuantity, boolean activateOnYourself){
        boolean trigger =gameSession.increaseFaith(faithQuantity, activateOnYourself, getActivePlayer());

        for(String s: allVirtualView.keySet()){
            allVirtualView.get(s).showFaithTrack(trigger,gameSession.lastActivatedFaithZone() );
            if(!activateOnYourself&&!(s.equals(getActivePlayer()))){
                allVirtualView.get(s).showPlayerFaith(gameSession.getFaith(s));
            }
        }
        if(activateOnYourself|| maxPlayers==1) allVirtualView.get(getActivePlayer()).showPlayerFaith(gameSession.getFaith(getActivePlayer()));

        if (trigger&& gameSession.lastActivatedFaithZone()==2){
            broadcastMessage("The last faith zone has been activated");
            broadcastMessage("We're in the endgame now.");
                setGameState(GameState.END_GAME);

            }

    }

    /**
     * Checks if the player can pay the production cost of the chosen cards and, if so, asks the player
     * how he would like to pay
     * @param cards the chosen dev cards
     * @param player the player object associated to the active player
     */
    public void produceResources(ArrayList<DevCard> cards, Player player){
        HashMap<ResourceType, Integer> price= new HashMap<>();
        int anyProduce=0;
        for(DevCard card : cards){
            HashMap<ResourceType, Integer> cost=card.getProductionCost();
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
            allVirtualView.get(getActivePlayer()).askProduction(player.getStrongbox(), player.getWarehouse().getAllResources(), price, anyPayment, anyProduce);
        }
        else {
            allVirtualView.get(getActivePlayer()).showErrorMsg("Not enough resources!");
            startTurn();
        }

    }
    /**
     * Sets the player's strongbox to a new one after resources have been spent and produced. Removes resources paid from depot
     * @param depotExpense payment of a resource from the warehouse
     * @param strongbox  new strongbox
     * @param player the player object associated to the active player
     */
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
            increaseFaith(strongbox.get(ResourceType.FAITH), true);
            strongbox.remove(ResourceType.FAITH);
        }
        player.setStrongbox(strongbox);
            allVirtualView.get(getActivePlayer()).showStrongbox(strongbox, getActivePlayer());
            allVirtualView.get(getActivePlayer()).showWarehouse(player.getWarehouse().getDepotToQuantity(), player.getWarehouse().getDepotToResource(), getActivePlayer());
    }

    /**
     * Sends the leader cards in the hand to a player
     * @param leader the cards
     */
    public void sendLeaderCards(ArrayList<LeaderCard> leader){
        if(leader.size()>0) {
            allVirtualView.get(getActivePlayer()).askLeaderCardToPlay(leader);
        }
        else{
            allVirtualView.get(getActivePlayer()).showErrorMsg("All leader cards are activated or discarded");
            startTurn();
        }
    }

    /**
     * Executes a leader action
     * @param card the card to play or discard
     * @param choseToPlay true if the player wants to play it, false if the player wants to discard it.
     * @param player the player object associated to the active player
     */
    private void leaderAction(LeaderCard card, boolean choseToPlay, Player player) {
        if(!choseToPlay){
            increaseFaith(1, true);

            player.discardLeader(card.getId());
            broadcastMessage(player.getUsername() + " has discarded a leader card.");
            allVirtualView.get(getActivePlayer()).showLeaderCards(player.getLeaderCards());
        }
        else{
            switch(card.getCostType()){
                case RESOURCES:
                    if(!player.checkPriceCanBePaid((card).getResourceCost())){
                        allVirtualView.get(getActivePlayer()).showErrorMsg("Not enough resources!");
                        return;
                    }
                    player.playLeader(card);
                        allVirtualView.get(getActivePlayer()).showPlayedLeaderCards(player.getPlayedLeaderCards(), getActivePlayer());

                    break;
                case LEVEL_TWO:
                    if(checkLevelTwoColor(((LeaderProduction) card).getColorCost(), player)){
                        player.playLeader(card);
                        allVirtualView.get(getActivePlayer()).showPlayedLeaderCards(player.getPlayedLeaderCards(), getActivePlayer());

                     }
                    else  {
                        allVirtualView.get(getActivePlayer()).showErrorMsg("You don't have the right cards!");
                    return;
                    }

                    break;
                case DEV_CARD_SINGLE:
                case DEV_CARD_DOUBLE:
                    if(checkCardColorRequirements(player, card.getCardCost())){
                        player.playLeader(card);
                        allVirtualView.get(getActivePlayer()).showPlayedLeaderCards(player.getPlayedLeaderCards(), getActivePlayer());
                    }
                    else return;
                    break;

            }
            broadcastMessage(player.getUsername()+ " has played a Leader Power: " + card.toString());
        }
    }

    /**
     * checks if the player has enough cards of certain colors
     * @param player the player object associated to the active player
     * @param requirement the color requirement
     * @return true if the player has the required cards
     */
    public boolean checkCardColorRequirements(Player player, HashMap<Color, Integer> requirement){
        for(Color c: requirement.keySet()){
            if(! player.checkHasEnoughCardOfColor(c, requirement.get(c))){
                allVirtualView.get(getActivePlayer()).showErrorMsg("You don't have the right cards!");
                return false;
            }
        }
        return true;
    }
    /**
     * checks if the player has a level two card of a certain color
     * @param player the player object associated to the active player
     * @param color the color requirement
     * @return true if the player has the required card
     */
    public boolean checkLevelTwoColor(Color color , Player player){
        return player.getDevCardSlot().hasLevelTwoOfColor(color);
    }

    /**
     * Counts all victory points at the end of the game and sends a message to all players.
     */
    public void countFinalVictoryPoints(){
        for(VirtualView vv: allVirtualView.values()){
            vv.showWinMessage(gameSession.endGame());
        }

    }

    /**
     * Ends the turn by drawing a token and moves the turn to the next player.
     */
    public void endTurn(){
        ActionToken token = gameSession.drawToken();
        if(token!=null){
            switch (token.getTokenType()){
                case FAITH:
                    boolean trigger=gameSession.updateFaithTrack();
                    if(trigger)  allVirtualView.get(getActivePlayer()).showFaithTrack( trigger, gameSession.lastActivatedFaithZone());
                    allVirtualView.get(getActivePlayer()).showPlayerFaith(gameSession.getFaith(getActivePlayer()));
                    break;
                case DISCARD:
                    allVirtualView.get(getActivePlayer()).showDevMarket(gameSession.getCardMarket().availableCards(), gameSession.getCardMarket().remainingCards());
                    break;
            }
            broadcastMessage("You drew a "+ token.toString()+". "+token.getEffect());
        }


        if(gameSession.checkLoss()){
            allVirtualView.get(getActivePlayer()).showLoseMessage();
            return;
        }
        if(turnController.proxPlayer().equals(turnController.firstPlayer())&&gameState==GameState.END_GAME){
            countFinalVictoryPoints();
        }
        else {
            for(String s: allVirtualView.keySet()){
                if (!s.equals(getActivePlayer())){
                    allVirtualView.get(s).showPlayerTurn(getActivePlayer());
                }
            }
            startTurn();
        }
    }

    /**
     * Returns the variable allVirtualView
     * @return the views of every connected player
     */
    public HashMap<String, VirtualView> getAllVirtualView() {
        return allVirtualView;
    }

    /**
     * Gets the current active player
     * @return THe active player's username
     */
    public String getActivePlayer(){
        return turnController.getActivePlayer();
    }

    /**
     * Checks if the main action has been done
     * @return true if a main action has been done.
     */
    public boolean isMainActionDone(){
        return turnController.getMainAction();
    }

    /**
     * Shows a player
     * @param player The player to show
     * @param username the username of the player who requested to show.
     */
    public void showPlayer(Player player, String username){
        allVirtualView.get(username).showPlayer(player.getUsername(),player.getFaithSpace(),player.getWarehouse().getDepotToResource(),player.getWarehouse().getDepotToQuantity(),player.getStrongbox(),player.getDevCardSlot(),player.getPlayedLeaderCards(), player.remainingLeaderCards(), username);

    }

}
