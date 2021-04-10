package it.polimi.ingsw.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is used for solo-mode and it's the intermediary between all the game's components.
 */
public class SinglePlayerGame extends Game {
    private BlackCross blackCross;
    private TokenBag tokenBag;

    /**
     * SinglePlayerGame class constructor.
     * @param playersList The Array containing all the players, in this case contains just the solo-mode player.
     * @throws IOException
     */
    public SinglePlayerGame(ArrayList<Player> playersList) throws IOException {
        super(playersList);
        blackCross = new BlackCross();
        tokenBag = new TokenBag();
    }

    /**
     * Sets all the components up, gives the Leader Cards to the player and starts the game.
     */
    public void start() {
        List<LeaderCard> leadCardDeck = new ArrayList<LeaderCard>();
        //TODO: LeaderCard generation
        //Cambia qualcosa dallo start del MultiPlayerGame?
    }

    /**
     * This method activates an ActionToken each turn.
     */
    public void turnAction() {
        ActionToken actionToken = tokenBag.drawToken();

        //actionToken.doOperation();

        if(actionToken.toString() == "Action Discard") {
            getCardMarket().discardDevCard(((ActionDiscard)actionToken).getColor());

        }
        else if(actionToken.toString()=="Action Shuffle") {
            blackCross.increaseBlackCross(((ActionShuffle)actionToken).getSpaces());
            tokenBag.shuffle();
        }
        else if(actionToken.toString()=="Action Cross") {
            blackCross.increaseBlackCross(((ActionCross)actionToken).getSpaces());
        }
    }

    /**
     * Ends the game and communicate the result.
     *
     */
    public void endGame() {
            //TODO
        }

    public BlackCross getBlackCross() {
        return blackCross;
    }

    public TokenBag getTokenBag() {
        return tokenBag;
    }
}
