package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Card.*;
import it.polimi.ingsw.model.enumeration.*;

import it.polimi.ingsw.view.VirtualView;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class controls the flow of the turns during the game.
 */

public class TurnController {

    private HashMap<String, Boolean> activePlayer;
    private String playingPlayer;
    private boolean mainAction;
    private ArrayList<String> playerOrder;

    /**
     * Constructor
     * @param gameController the gamecontroller associated to this turn controller.
     */
    public TurnController(GameController gameController){
        mainAction=false;
        playingPlayer =  gameController.getGameSession().getPlayersList().get(0).getUsername();
        playerOrder=new ArrayList<String>();
        activePlayer=new HashMap<String, Boolean>();
        for(int i=0; i<gameController.getAllVirtualView().size(); i++){
            String name=gameController.getGameSession().getPlayersList().get(i).getUsername();
            activePlayer.put(name, true);
            playerOrder.add(name);
        }
    }

    /**
     * Gets the first player in the turn order
     * @return the first player's username
     */
    public String firstPlayer(){
        return getActivePlayers().get(0);

    }

    /**
     * Resets the main action and changes the active player
     * @return the new active player's username
     */
    public String  proxPlayer(){
        int player = getActivePlayers().indexOf(playingPlayer)+1;
        if(player>=getActivePlayers().size()){
            player=0;
        }
        playingPlayer = getActivePlayers().get(player);
        setMainAction(false);
        return playingPlayer;

    }

    /**
     * Returns the current active player's username
     * @return the current active player's username
     */
    public String getActivePlayer() {
        return playingPlayer;
    }

    /**
     * Sets an user as inactive to allow reconnection at a later time
     * @param username the username that disconnected
     * @return if the active player was the player who disconnected.
     */
    public boolean disconnect(String username){
        activePlayer.put(username,false);
        return(username.equals(getActivePlayer()));
    }

    /**
     * Checks if the game has players who previously disconnected
     * @return if there are disconnected players.
     */
    public boolean hasInactivePlayers(){
        return(getInactivePlayers().size()!=0);
    }

    /**
     * Returns a list of players who previously disconnected
     * @return the list of usernames of players who previously disconnected
     */
    public List<String> getInactivePlayers(){

        return activePlayer.entrySet().stream().filter(entry -> (!entry.getValue()))
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    /**
     * Returns a list of players who are still connected ordered by turn order.
     * @return the list of connected players' usernames
     */
    public List<String> getActivePlayers(){
        ArrayList<String> activePlayers=new ArrayList<String>();
        for(int i=0;i<playerOrder.size(); i++) {
            if (activePlayer.get(playerOrder.get(i))) {
                activePlayers.add(playerOrder.get(i));
            }
        }
        return activePlayers;
    }

    /**
     * Returns a list containing the usernames in player order
     * @returna the list containing the usernames in player order
     */
    public List<String> getPlayerOrder(){
        return playerOrder;
    }

    /**
     * Sets a username as active, allowing him to play.
     * @param username the user that reconnected.
     */
    public void reconnect(String username){
        activePlayer.put(username, true);
    }

    /**
     * Sets a new main action state either a main action was done or not
     * @param state
     */
      public void setMainAction(boolean state){
        mainAction=state;
    }

    /**
     * Returns if a main action was done this turn or not
     * @return if a main action was done this turn or not
     */
    public boolean getMainAction(){return mainAction;}
}

