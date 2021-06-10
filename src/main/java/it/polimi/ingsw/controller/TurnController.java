package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Card.*;
import it.polimi.ingsw.model.enumeration.*;

import it.polimi.ingsw.view.VirtualView;

import java.util.*;
import java.util.stream.Collectors;


public class TurnController {

    private HashMap<String, Boolean> activePlayer;
    private String playingPlayer;
    private boolean mainAction;
    private ArrayList<String> playerOrder;

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
    public String firstPlayer(){
        return getActivePlayers().get(0);

    }


    public String  proxPlayer(){
        int player = getActivePlayers().indexOf(playingPlayer)+1;
        if(player>=getActivePlayers().size()){
            player=0;
        }
        playingPlayer = getActivePlayers().get(player);
        setMainAction(false);
        return playingPlayer;

    }


    public String getActivePlayer() {
        return playingPlayer;
    }

    public boolean disconnect(String username){
        activePlayer.put(username,false);
        return(username==getActivePlayer());
    }

    public boolean hasInactivePlayers(){
        return(getInactivePlayers()!=null);
    }

    public List<String> getInactivePlayers(){

        return activePlayer.entrySet().stream().filter(entry -> (!entry.getValue()))
                .map(entry-> entry.getKey()).collect(Collectors.toList());
    }
    public List<String> getActivePlayers(){
        ArrayList<String> activePlayers=new ArrayList<String>();
        for(int i=0;i<playerOrder.size(); i++) {
            if (activePlayer.get(playerOrder.get(i))) {
                activePlayers.add(playerOrder.get(i));
            }
        }
        return activePlayers;
    }
    public List<String> getPlayerOrder(){
        return playerOrder;
    }
    public void reconnect(String username){
        activePlayer.put(username, true);
    }
      public void setMainAction(boolean state){
        mainAction=state;
    }
    public boolean getMainAction(){return mainAction;}
}

