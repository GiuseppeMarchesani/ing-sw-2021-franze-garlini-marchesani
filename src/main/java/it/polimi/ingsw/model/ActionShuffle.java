package it.polimi.ingsw.model;

/**
 * This class represent the Action Token which shuffle the TokenBag.
 */
public class ActionShuffle implements ActionToken{
    private int spaces;

    public ActionShuffle(int spaces) {
        this.spaces = spaces;
    }

    public void doOperation(SinglePlayerGame singlePlayerGame) {

    }

    @Override
    public String toString() {
        return "Action Shuffle";
    }

    public int getSpaces() {
        return spaces;
    }
}
