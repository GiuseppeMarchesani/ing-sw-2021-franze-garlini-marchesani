package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;
import it.polimi.ingsw.model.enumeration.Color;

/**
 * This class represent the Action Token that will discard a DevCard.
 */
public class ActionDiscard implements ActionToken {
    private Color color;

    /**
     * ActionDiscard class constructor.
     * @param color The color of the Development Card that must be discarded.
     */
    public ActionDiscard(Color color) {
        this.color = color;
    }

    /**
     * Discards a Development Card from the Card Market and calls the endgame if there are no more available cards in a deck in the Card Market.
     * @param game The ongoing Game.
     */
    public int doOperation(SinglePlayerGame game) {
        game.getCardMarket().discardDevCard(color);
        return game.getCardMarket().discardDevCard(color);
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Action Discard " + color.toString();
    }
}
