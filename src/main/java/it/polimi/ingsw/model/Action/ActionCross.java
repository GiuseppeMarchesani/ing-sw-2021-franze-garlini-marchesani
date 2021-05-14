package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;

/**
 * This class represent the Action Token which increase the Black Cross position by 2.
 */
public class ActionCross implements ActionToken {
    private int spaces;

    public ActionCross(int spaces) {
        this.spaces = spaces;
    }

    public int doOperation(SinglePlayerGame game) {
        int blackCrossPosition = game.getBlackCross().increaseBlackCross(spaces);
        if(game.getFaithTrack().isOnFinalPosition(blackCrossPosition)) {
            return 1; //endGameCode = 1 -> Lorenzo has passed the final space
        }
        else return -1;
    }

    public int getSpaces() {
        return spaces;
    }

    @Override
    public String toString() {
        return "Action Cross " + "+2";
    }
}
