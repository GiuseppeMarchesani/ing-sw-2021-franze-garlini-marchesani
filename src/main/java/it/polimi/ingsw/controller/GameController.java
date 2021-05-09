package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.SinglePlayerGame;
import it.polimi.ingsw.model.enumeration.GameState;

import static it.polimi.ingsw.messages.MessageType.*;

public class GameController {
    private Game gameSession;
    private Turn turnController;
    private GameState gameState;

    public GameController(char gameType) throws IllegalArgumentException {

        if(gameType == 's' || gameType=='S'){
            gameSession=new SinglePlayerGame();
        }
        else if(gameType=='m' || gameType=='M'){
            gameSession= new Game();
        }
        else throw new IllegalArgumentException();
    }


    /** Turn state.
     *
     */

    public void getMessage (GeneralMessage receivedMessage){
        switch (gameState){
            case MAIN_ACTION:
                mainAction(receivedMessage);
                break;
            case LEADER_ACTION:
                leaderAction(receivedMessage);
                break;
            default:
                //non deve mai succedere, da scrivere
                break;

        }
    }

    private void mainAction(GeneralMessage msg){
        if(msg.getMessageType() == PICK_DEVCARD){
            pickDevCard(/*(DevCardMsg) msg*/);
        }
        else if(msg.getMessageType() == ACTIVATE_PRODUCTION){
            activateProduction(/*(ProductionMsg) msg*/);
        }
        else if(msg.getMessageType() == PICK_MARKETRES){
            pickMarketRes(/*(PickResMsg) msg*/);
        }
        else{
            //mandare messaggio di errore
        }

    }

    private void leaderAction(GeneralMessage msg){
        if(msg.getMessageType() == PLAYLEADER){
            playLeader(/*(PlayLeaderMsg) msg*/);
        }
        else if(msg.getMessageType() == DISCARDLEADER){
            discardLeader(/*(DiscardLeaderMsg) msg*/);
        }
        else{
            //messaggio di errore
        }
    }



    private void discardLeader(){

    }

    private void playLeader(){

    }

    private void pickDevCard(){

    }

    private void activateProduction(){

    }

    public void pickMarketRes(){

    }

}
