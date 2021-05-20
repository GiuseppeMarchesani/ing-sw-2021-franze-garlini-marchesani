package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.view.*;

import java.lang.reflect.Array;
import java.net.Socket;
import java.util.*;

import static it.polimi.ingsw.messages.MessageType.*;

public class GameController {
    private ArrayList<String> players;
    private boolean ongoing;
    private Game gameSession;
    private String gameID;
    private HashMap<String, VirtualView> allVirtualView;
    private VirtualView virtualView;
    private Turn turnController;
    private GameState gameState;

    public GameController(String gameID, int maxPlayers, HashMap<String, VirtualView> virtualViews){
        this.allVirtualView= virtualViews;
        ongoing = false;
        this.gameID=gameID;
        this.turnController= new Turn(this);
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

    public void getMessage (GeneralMessage receivedMessage){
        VirtualView virtualView = allVirtualView.get(receivedMessage.getUsername());
        switch (gameState){
            case START:
                startGame((StartGameMsg) receivedMessage);
                break;
            case IN_GAME:
            default:
                for(VirtualView vv: allVirtualView.values()){
                    vv.showErrorMsg("Error!");
                }
                break;

        }
    }

    public void startGame(StartGameMsg msg) {
        ongoing=true;
        ArrayList<LeaderCard> leaderCards= new ArrayList<>();
        for(int i=0; i<getPlayers().size(); i++){
            for(int j=0; j<4; j++){
                leaderCards.add(gameSession.drawCard());
            }
            for(int x=0; x<2; x++){
                allVirtualView.get(players.get(i)).askChooseLeaderCard(leaderCards);
            }
            gameSession.getPlayersList().get(i).getLeaderCardList().add(msg.getLeader());
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
        if(gameSession.getPlayersList().size()==4){
            gameSession.getPlayersList().get(2).increaseFaith(1);
            gameSession.getPlayersList().get(3).increaseFaith(2);
            allVirtualView.get(players.get(1)).askChooseRes();
            allVirtualView.get(players.get(2)).askChooseRes();
            for(int i=0; i<2; i++){
                allVirtualView.get(players.get(3)).askChooseRes();
            }
        }
        else if(gameSession.getPlayersList().size()==3){
            gameSession.getPlayersList().get(2).increaseFaith(1);
            allVirtualView.get(players.get(1)).askChooseRes();
            allVirtualView.get(players.get(2)).askChooseRes();
        }
        else if(gameSession.getPlayersList().size()==2){
            allVirtualView.get(players.get(1)).askChooseRes();
        }
    }

    private int pickFirstPlayer(){
        Random rand = new Random();
        int firstPlayer;
        firstPlayer = rand.nextInt(gameSession.getPlayersList().size());
        return firstPlayer;
    }

    private void endGameMethod(){
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
    public Turn getTurnController() {
        return turnController;
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

}
