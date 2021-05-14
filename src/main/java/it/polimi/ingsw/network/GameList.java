package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Turn;
import it.polimi.ingsw.messages.GeneralMessage;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;
import java.util.HashMap;

public class GameList {
    private ArrayList<GameController> activeGames;

    private static GameList instance=new GameList();
    public static GameList getInstance(){
        return instance;
    }
    public ArrayList<GameController> getActiveGames() {
        return activeGames;
    }

}
