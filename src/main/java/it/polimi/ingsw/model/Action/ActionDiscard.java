package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;
import it.polimi.ingsw.model.enumeration.Color;

/**
 * This class represents the Action Token that will discard DevCards from the DevMarket.
 */
public class ActionDiscard implements ActionToken {
    private Color color;
    private ActionTokenType tokenType;

    /**
     * Default constructor.
     * @param color color of the Development Card that must be discarded.
     * @param actionTokenType the type of the action token.
     */
    public ActionDiscard(Color color, ActionTokenType actionTokenType) {
        this.color = color;
        this.tokenType = actionTokenType;
    }

    /**
     * Discards a Development Card from the Card Market and calls the endgame
     * if there are no more available cards in a deck in the Card Market.
     * @param game The ongoing game.
     */
    public void doOperation(SinglePlayerGame game) {
         game.getCardMarket().discardDevCard(color);
    }


    @Override
    public String toString() {
        return "Discard Card Token";
    }

    /**
     * Returns a String representing the Action Token effect.
     * @return a String that specifies the effect.
     */
    public String getEffect(){
      return "Discard 2 "+ color.toString() + " cards." ;
    }

    public Color getColor() {
        return color;
    }

    public ActionTokenType getTokenType() {
        return tokenType;
    }
}
