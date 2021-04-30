package it.polimi.ingsw.model;

/**
 * This class represent the Action Token which increase the Black Cross position by 2.
 */
public class ActionCross implements ActionToken {
    private int spaces;

    public ActionCross(int spaces) {
        this.spaces = spaces;
    }

    public void doOperation(SinglePlayerGame game) {
        int blackCrossPosition = game.getBlackCross().increaseBlackCross(spaces);
        if(game.getFaithTrack().isOnFinalPosition(blackCrossPosition)) {
            game.endGame(1);
        }
    }

    public int getSpaces() {
        return spaces;
    }

    @Override
    public String toString() {
        return "Action Cross " + "+2";
    }
}
