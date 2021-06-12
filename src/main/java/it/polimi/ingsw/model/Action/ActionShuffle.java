package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;

/**
 * This class represent the Action Token which shuffle the TokenBag.
 */
public class ActionShuffle implements ActionToken{
    private int spaces;
    private ActionTokenType tokenType;

    public ActionShuffle(int spaces, ActionTokenType actionTokenType) {
        this.spaces = spaces;
        this.tokenType = actionTokenType;
    }

    public void doOperation(SinglePlayerGame game) {
        game.getTokenBag().shuffle();
        game.getBlackCross().increaseBlackCross(spaces);
    }

    public ActionTokenType getTokenType() {
        return tokenType;
    }

}
