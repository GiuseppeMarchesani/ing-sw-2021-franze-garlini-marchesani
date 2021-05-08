package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Turn;

import java.util.HashMap;

public class GameList {
    private HashMap<String, Turn> activeGames;

    private static GameList instance=new GameList();
    public static GameList getInstance(){
        return instance;
    }
    public HashMap<String,Turn> getActiveGames(){return activeGames;}
}
