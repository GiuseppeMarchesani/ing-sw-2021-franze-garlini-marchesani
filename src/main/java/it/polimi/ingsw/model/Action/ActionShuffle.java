package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;

/**
 * This class represent the Action Token which shuffle the TokenBag.
 */
public class ActionShuffle implements ActionToken{
    private int spaces;

    public ActionShuffle(int spaces) {
        this.spaces = spaces;
    }

    public int doOperation(SinglePlayerGame game) {
        game.getTokenBag().shuffle();

        int blackCrossPosition = game.getBlackCross().increaseBlackCross(spaces);
        if(game.getFaithTrack().isOnFinalPosition(blackCrossPosition)) {
            return 1; //endGameCode = 1 -> Lorenzo has passed the final space
        }
        else return -1;
    }

    @Override
    public String toString() {
        return "Action Shuffle";
    }

    public int getSpaces() {
        return spaces;
    }
}
