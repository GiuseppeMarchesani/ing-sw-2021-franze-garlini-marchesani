package it.polimi.ingsw.model;

/**
 * This class represent the Action Token which increase the Black Cross position by 2.
 */
public class ActionCross implements ActionToken {
    private int spaces;

    public ActionCross(int spaces) {
        this.spaces = spaces;
    }

    public void doOperation(SinglePlayerGame singlePlayerGame) {

    }

    public int getSpaces() {
        return spaces;
    }

    @Override
    public String toString() {
        return "Action Cross";
    }
}
