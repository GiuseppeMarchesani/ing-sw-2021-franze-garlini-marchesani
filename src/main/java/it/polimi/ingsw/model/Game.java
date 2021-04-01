package it.polimi.ingsw.model;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is the intermediary between all the game's components.
 */
public abstract class Game {
    private Market market ;
    private FaithTrack faithTrack ;
    private CardMarket cardMarket;

    public Game() {
        market = new Market();
        faithTrack = new FaithTrack();
        cardMarket = new CardMarket();
    }

}
