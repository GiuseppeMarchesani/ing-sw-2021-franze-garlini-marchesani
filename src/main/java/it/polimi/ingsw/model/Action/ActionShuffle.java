package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;

/**
 * This class represent the Action Token which shuffle the TokenBag.
 */
public class ActionShuffle extends ActionToken{
    private int spaces;

    public ActionShuffle(int spaces) {
        super(ActionTokenType.FAITH);
        this.spaces = spaces;
    }

    public void doOperation(SinglePlayerGame game) {
        game.getTokenBag().shuffle();
        game.getBlackCross().increaseBlackCross(spaces);
    }

}
