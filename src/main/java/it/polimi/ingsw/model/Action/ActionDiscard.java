package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;
import it.polimi.ingsw.model.enumeration.Color;

/**
 * This class represent the Action Token that will discard a DevCard.
 */
public class ActionDiscard extends ActionToken {
    private Color color;

    /**
     * ActionDiscard class constructor.
     * @param color The color of the Development Card that must be discarded.
     */
    public ActionDiscard(Color color) {
        super(ActionTokenType.DISCARD);
        this.color = color;
    }

    /**
     * Discards a Development Card from the Card Market and calls the endgame if there are no more available cards in a deck in the Card Market.
     * @param game The ongoing Game.
     */
    public void doOperation(SinglePlayerGame game) {
         game.getCardMarket().discardDevCard(color);
    }

    public Color getColor() {
        return color;
    }

}
