package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;

/**
 * This class represent the Action Token which increase the Black Cross position by 2.
 */
public class ActionCross extends ActionToken {
    private int spaces;

    public ActionCross(int spaces) {
        super(ActionTokenType.FAITH);
        this.spaces = spaces;
    }

    public void doOperation(SinglePlayerGame game) {
        game.getBlackCross().increaseBlackCross(spaces);
    }
}
