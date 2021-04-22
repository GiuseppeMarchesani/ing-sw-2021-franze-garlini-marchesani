package it.polimi.ingsw.model;

/**
 * This class represent the Action Token that will discard a DevCard.
 */
public class ActionDiscard implements ActionToken {
    private Color color;

    public ActionDiscard(int color) {
        this.color = new Color(color);
    }
    public void doOperation(SinglePlayerGame game) {
        game.getCardMarket().discardDevCard(color);
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Action Discard";
    }
}
