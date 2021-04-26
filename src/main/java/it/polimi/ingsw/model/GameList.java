package it.polimi.ingsw.model;

import java.util.HashMap;

public class GameList {
    private HashMap<Integer, Turn> activeGames;

    private static GameList instance=new GameList();
    public static GameList getInstance(){
        return instance;
    }
    public HashMap<Integer,Turn> getActiveGames(){return activeGames;}
}
