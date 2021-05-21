package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.model.enumeration.PhaseTurn;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.view.*;

import java.lang.reflect.Array;
import java.net.Socket;
import java.security.InvalidParameterException;
import java.util.*;

import static it.polimi.ingsw.messages.MessageType.*;

public class GameController {
    private ArrayList<String> players;
    private boolean ongoing;
    private final Game gameSession;
    private final String gameID;
    private final HashMap<String, VirtualView> allVirtualView;
    private VirtualView virtualView;
    private final TurnController turnController;
    private GameState gameState;

    public GameController(String gameID, int maxPlayers, HashMap<String, VirtualView> virtualViews){
        this.allVirtualView= virtualViews;
        ongoing = false;
        this.gameID=gameID;
        this.gameState= GameState.START;
        this.turnController= new TurnController(this);
        if(maxPlayers == 1){
            this.gameSession= new SinglePlayerGame();
        }
        else this.gameSession= new Game();
    }

    public HashMap<String, VirtualView> getAllVirtualView() {
        return allVirtualView;
    }

    public void newPlayer(String username) throws Exception {
        players.add(username);
        this.gameSession.addPlayer(new Player(username));
    }

    /** Game state.
     *
     */

    public void getMessage(GeneralMessage receivedMessage)throws InvalidParameterException {
        if(receivedMessage.getGameID().equals(gameID)) {
            VirtualView virtualView = allVirtualView.get(receivedMessage.getUsername());
            switch (gameState) {
                case START:
                    startGame((StartGameMsg) receivedMessage);
                    break;
                case IN_GAME:
                    inGame(receivedMessage);
                case END_GAME:
                    endGame(receivedMessage);
                default:
                    for (VirtualView vv : allVirtualView.values()) {
                        vv.showErrorMsg("Error!");
                    }
                    break;

            }
        }
        else throw new InvalidParameterException();
    }
/*
    public void startGame(GeneralMessage msg){

    }

 */

    //SBAGLIATO
    public void startGame(StartGameMsg msg) {
        ongoing=true;
        ArrayList<LeaderCard> leaderCards= new ArrayList<>();
        for(int i=0; i<getPlayers().size(); i++){
            for(int j=0; j<4; j++){
                leaderCards.add(gameSession.drawCard());
            }
            for(int x=0; x<2; x++){
                allVirtualView.get(players.get(i)).askLeaderCardToDiscard(leaderCards);
            }
            gameSession.getPlayersList().get(i).getLeaderCardList().put(msg.getLeader(), false);
        }
        ArrayList<Player> tmp = new ArrayList<>();
        int indexFirst= pickFirstPlayer();
        for(int i= 0; i<getPlayers().size(); i++){
            if(indexFirst > getPlayers().size()){
                indexFirst=0;
            }
            tmp.add(i, gameSession.getPlayersList().get(indexFirst));
        }
        for(int i=0; i< players.size(); i++){
            gameSession.getPlayersList().add(i, tmp.get(i));
        }
        ArrayList<String> resource= this.availableRes();
        if(gameSession.getPlayersList().size()==4){
            gameSession.getPlayersList().get(2).increaseFaith(1);
            gameSession.getPlayersList().get(3).increaseFaith(2);
            allVirtualView.get(players.get(1)).askChooseOneRes(resource,
                    "Choose initial resource by typing COIN, SHIELD, SERVANT or STONE");
            allVirtualView.get(players.get(2)).askChooseOneRes(resource,
                    "Choose initial resource by typing COIN, SHIELD, SERVANT or STONE");
            for(int i=0; i<2; i++){
                allVirtualView.get(players.get(3)).askChooseOneRes(resource,
                        "Choose initial resource by typing COIN, SHIELD, SERVANT or STONE");
            }
        }
        else if(gameSession.getPlayersList().size()==3){
            gameSession.getPlayersList().get(2).increaseFaith(1);
            allVirtualView.get(players.get(1)).askChooseOneRes(resource,
                    "Choose initial resource by typing COIN, SHIELD, SERVANT or STONE");
            allVirtualView.get(players.get(2)).askChooseOneRes(resource,
                    "Choose initial resource by typing COIN, SHIELD, SERVANT or STONE");
        }
        else if(gameSession.getPlayersList().size()==2){
            allVirtualView.get(players.get(1)).askChooseOneRes(resource,
                    "Choose initial resource by typing COIN, SHIELD, SERVANT or STONE");
        }
        setGameState(GameState.IN_GAME);
        turnController.newTurn();
    }

    private int pickFirstPlayer(){
        Random rand = new Random();
        int firstPlayer;
        firstPlayer = rand.nextInt(gameSession.getPlayersList().size());
        return firstPlayer;
    }

    public void inGame(GeneralMessage msg){
        if(msg.getMessageType() == SHOW_LEADER){
            turnController.setPhaseTurn(PhaseTurn.START_TURN);
            turnController.getMessage(msg);
        }
        else if(msg.getMessageType()== SHOW_MARKET){
            turnController.setPhaseTurn(PhaseTurn.START_TURN);
            turnController.getMessage(msg);
        }
        else if(msg.getMessageType()== SHOW_DEV_MARKET){
            turnController.setPhaseTurn(PhaseTurn.START_TURN);
            turnController.getMessage(msg);
        }
        else if(msg.getMessageType()== SHOW_SLOT){
            turnController.setPhaseTurn(PhaseTurn.START_TURN);
            turnController.getMessage(msg);
        }
        else if(msg.getMessageType() == SHOW_ALL_SLOT){
            turnController.setPhaseTurn(PhaseTurn.START_TURN);
            turnController.getMessage(msg);
        }
        else if(msg.getMessageType() == SHOW_RES){
            turnController.setPhaseTurn(PhaseTurn.START_TURN);
            turnController.getMessage(msg);
        }
        else if(msg.getMessageType()== SHOW_ALL_RES){
            turnController.setPhaseTurn(PhaseTurn.START_TURN);
            turnController.getMessage(msg);
        }
        else if(msg.getMessageType()== SHOW_FAITH_TRACK){
            turnController.setPhaseTurn(PhaseTurn.START_TURN);
            turnController.getMessage(msg);
        }
        else if(msg.getMessageType()== SHOW_INFO){
            turnController.setPhaseTurn(PhaseTurn.START_TURN);
            turnController.getMessage(msg);
        }
        else if(msg.getMessageType() == PLAYLEADER){
            turnController.setPhaseTurn(PhaseTurn.ACTION);
            turnController.getMessage(msg);
        }
        else if(msg.getMessageType() == LEADER_REPLY){
            turnController.getMessage(msg);
        }
        else if(msg.getMessageType() == PICK_DEVCARD){
            turnController.setPhaseTurn(PhaseTurn.ACTION);
            turnController.getMessage(msg);
        }
        else if(msg.getMessageType() == DEVCARD_REPLY){
            turnController.getMessage(msg);
        }
        else if(msg.getMessageType()== PLACE_CARD){
            turnController.getMessage(msg);
        }
        else if(msg.getMessageType() == ACTIVATE_PRODUCTION){
            turnController.setPhaseTurn(PhaseTurn.ACTION);
            turnController.getMessage(msg);
        }
        else if(msg.getMessageType() == PRODUCTION_RES){
            turnController.getMessage(msg);
        }
        else if(msg.getMessageType()== PAY_RES){
            turnController.getMessage(msg);
        }
        else if(msg.getMessageType() == PICK_MARKETRES){
            pickMarketRes((PickResMsg) msg);
        }
        else if(msg.getMessageType()== ROW_OR_COL){
            rowOrCol((GetMarketLineReply) msg);
        }
        else if(msg.getMessageType()== WHITE_CONVERSION){
            chosenMarbleConversion((WhiteConversionMsg) msg);
        }
        else if(msg.getMessageType()==PLACE_RES) {
            placeRes((PlaceMsg) msg);
        }
        else if(msg.getMessageType()== END_TURN){
            turnController.setPhaseTurn(PhaseTurn.NEXT_TURN);
            turnController.getMessage(msg);
        }
        else{
            allVirtualView.get(turnController.getActivePlayer()).showErrorMsg("Invalid action. Try again!");
        }
    }


    public void  activateProduction(ActivateProductionMsg msg){
        turnController.setPhaseTurn(PhaseTurn.ACTION);
        turnController.getMessage(msg);
    }
    public void productionRes(ProductionMsg msg){
        turnController.setPhaseTurn(PhaseTurn.ACTION);
        turnController.getMessage(msg);
    }
    public void resToPay(ResToPayMsg msg){
        turnController.setPhaseTurn(PhaseTurn.ACTION);
        turnController.getMessage(msg);
    }
    public void  pickMarketRes(PickResMsg msg){
        turnController.setPhaseTurn(PhaseTurn.ACTION);
        turnController.getMessage(msg);
    }
    public void rowOrCol(GetMarketLineReply msg){
        turnController.setPhaseTurn(PhaseTurn.ACTION);
        turnController.getMessage(msg);
    }
    public void chosenMarbleConversion(WhiteConversionMsg msg){
        turnController.setPhaseTurn(PhaseTurn.ACTION);
        turnController.getMessage(msg);
    }
    public void placeRes(PlaceMsg msg){
        turnController.setPhaseTurn(PhaseTurn.ACTION);
        turnController.getMessage(msg);
    }



    public void endGame(GeneralMessage msg){
       //chiudere la partita
    }
    public boolean getStatus(){
        return ongoing;
    }
    public Game getGameSession(){
        return gameSession;
    }
    public ArrayList<String> getPlayers() {
        return players;
    }
    public TurnController getTurnController() {
        return turnController;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void disconnect(String username){
        turnController.disconnect(username);
    }
    public boolean status(){
        return turnController.status();
    }
    public List<String> getInactivePlayers(){
        return turnController.getInactivePlayers();
    }
    public boolean hasInactivePlayers(){
        return turnController.hasInactivePlayers();
    }
    public void reconnect(String username){
        turnController.reconnect(username);
    }
    public ArrayList<String> availableRes(){
        ArrayList<String> resource= new ArrayList<>();
        for(ResourceType resourceType: ResourceType.values()){
            if(!resourceType.equals(ResourceType.ANY) && !resourceType.equals(ResourceType.EMPTY) &&
                    !resourceType.equals(ResourceType.FAITH))
                resource.add(resourceType.toString());
        }
        return resource;
    }
}
