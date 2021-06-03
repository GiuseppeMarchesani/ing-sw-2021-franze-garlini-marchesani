package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.model.enumeration.PhaseTurn;
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
    private VirtualView virtualView;
    private TurnController turnController;
    private GameState gameState;
    private String gameId;
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

    public void newPlayer(String username, VirtualView virtualView) {
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
                    inGame(receivedMessage);
                    break;
                case END_GAME:
                    endGame(receivedMessage);
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
        int indexPlayer = gameSession.getPlayerListByUsername().indexOf(msg.getUsername());
        Player player=gameSession.getPlayersList().get(indexPlayer);
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

    /**
     * end game phase
     * @param msg
     */
    public void endGame(GeneralMessage msg){
        //TODO: viene fatto fare l'ultimo giro
        //TODO: viene preso il vincitore


        //TODO: chiude la partita
    }

    private void startGame() {
        setGameState(GameState.DRAWLEADER);
        turnController=new TurnController(this);
        switch(maxPlayers){
            case 3:
                gameSession.getPlayersList().get(2).increaseFaith(1);
            case 4:
                gameSession.getPlayersList().get(3).increaseFaith(2);
                break;
            default:
                //Don't increase faith
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
            player.getLeaderCardList().put(msg.getLeaderCard().get(i), false);
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
            int indexPlayer = gameSession.getPlayerListByUsername().indexOf(msg.getUsername());
            Player player=gameSession.getPlayersList().get(indexPlayer);
            switch(msg.getMessageType()){
                case SHOW_LEADER:
                    turnController.setPhaseTurn(PhaseTurn.START_TURN);
                    turnController.getMessage(msg);
                case SHOW_MARKET:
                    turnController.setPhaseTurn(PhaseTurn.START_TURN);
                    turnController.getMessage(msg);
                case  SHOW_DEV_MARKET:
                    turnController.setPhaseTurn(PhaseTurn.START_TURN);
                    turnController.getMessage(msg);
                case SHOW_DEV_CARDS:
                    turnController.setPhaseTurn(PhaseTurn.START_TURN);
                    turnController.getMessage(msg);
                case SHOW_RES:
                    turnController.setPhaseTurn(PhaseTurn.START_TURN);
                    turnController.getMessage(msg);
                case SHOW_FAITH_TRACK:
                    turnController.setPhaseTurn(PhaseTurn.START_TURN);
                    turnController.getMessage(msg);
                case SHOW_VICTORY_POINTS:
                    //
                case PLAYLEADER:
                    turnController.setPhaseTurn(PhaseTurn.ACTION);
                    turnController.getMessage(msg);

                case DEVCARD_REPLY:
                    turnController.getMessage(msg);
                case ACTIVATE_PRODUCTION:
                    turnController.setPhaseTurn(PhaseTurn.ACTION);
                    turnController.getMessage(msg);
                case PRODUCTION_RES:
                    turnController.getMessage(msg);
                case PAY_RES:
                    turnController.getMessage(msg);

                case PLACE_CARD:
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
                    turnController.proxPlayer();

                    startTurn();
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
    public void getMarketDevCard(BuyDevCardRequest msg, Player player){

        ArrayList<Integer> slots=player.getDevCardSlot().getAvailableSlots(msg.getLevel());
        if(slots.size()==0)
        {
            allVirtualView.get(msg.getUsername()).showErrorMsg("No slot to place the card!");
            startTurn();
            return;
        }
        DevCard card=gameSession.pickDevCard(msg.getColor(), msg.getLevel());
        if(card==null){
            allVirtualView.get(msg.getUsername()).showErrorMsg("The pile is empty!");
            startTurn();
            return;
        }
        tempCards.add(card);
        HashMap<ResourceType,Integer> resource=player.getAllResources();
        Integer any=card.getCardCost().get(ResourceType.ANY);
        if(any==null){
            any=0;
        }
        int grandTotalCost=0;
        for(ResourceType r: resource.keySet()){
            grandTotalCost+=resource.get(r);
        }

        int discount;
        for(ResourceType r: card.getCardCost().keySet()) {
            discount=0;
            if(player.getResourceDiscount().containsKey(r)){
                discount=player.getResourceDiscount().get(r);

            }
            grandTotalCost -= card.getCardCost().get(r)-discount;
            if (r != ResourceType.ANY) {
                if (card.getCardCost().get(r)-discount > resource.get(r)) {
                    allVirtualView.get(msg.getUsername()).showErrorMsg("Not enough resources!");
                    gameSession.returnDevCard(card);
                    tempCards.remove(card);
                    startTurn();
                    return;
                }
            }
        }
        if(grandTotalCost>=0){
            for(ResourceType r: player.getResourceDiscount().keySet()){
                if( card.getCardCost().containsKey(r)){
                    card.getCardCost().put(r, card.getCardCost().get(r)-player.getResourceDiscount().get(r));
                        if(card.getCardCost().get(r)<=0){
                            card.getCardCost().remove(r);
                    }
                }
            }
            allVirtualView.get(msg.getUsername()).askSlot(player.getWarehouse().getAllResources(),player.getStrongbox(), card.getCardCost(), any.intValue(),slots);
        }
        else{
            allVirtualView.get(msg.getUsername()).showErrorMsg("Not enough resources!");
            gameSession.returnDevCard(card);
            tempCards.remove(card);
            startTurn();
        }

    }
    public void placeCard(PlaceDevCardRequest msg, DevCard card, Player player){
        player.setStrongbox(msg.getNewStrongbox());
        player.getDevCardSlot().getSlotDev().get(msg.getSlotToPlace()).add(card);
        player.getWarehouse().spendResources(msg.getExpenseDepot());

        //TODO:AGGIORNAMENTI

    }
}
