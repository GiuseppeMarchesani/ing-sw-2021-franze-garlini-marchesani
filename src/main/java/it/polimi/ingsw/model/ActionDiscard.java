package it.polimi.ingsw.model;

/**
 * This class represent the Action Token that will discard a DevCard.
 */
public class ActionDiscard implements ActionToken {
    private Color color;

    public ActionDiscard(Color color) {
        this.color = color;
    }

    public void doOperation() {

    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Action Discard";
    }
}
