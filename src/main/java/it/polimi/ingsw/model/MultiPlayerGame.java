package it.polimi.ingsw.model;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used for MultiPlayer mode and it's the intermediary between all the game's components.
 */

public class MultiPlayerGame extends Game{

    private List<Player> playersList = new ArrayList<Player>();

    /**
     * MultiPlayerGame class constructor.
     * @param playersList The Array containing all the players.
     * @throws IOException
     */
    public MultiPlayerGame(ArrayList<Player> playersList) throws IOException {
        super(playersList);
    }

    /**
     * Sets all the components up, gives the Leader Cards to the players and starts the game.
     */
    public void start() {
        List<LeaderCard> leadCardDeck = new ArrayList<LeaderCard>();
        //TODO: LeaderCard generation
        //Cambia qualcosa da SinglePlayer?
    }

}