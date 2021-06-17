package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;

/**
 * Interface that represents a generic Action Token.
 */
public interface ActionToken {

    /**
     * Method that will implements the main effect of the Action Token.
     * @param game the ongoing game.
     */
    public abstract void doOperation(SinglePlayerGame game);

    public abstract ActionTokenType getTokenType();

    /**
     * Returns a String representing the Action Token effect.
     * @return a String that specifies the effect.
     */
    public String getEffect();
}
