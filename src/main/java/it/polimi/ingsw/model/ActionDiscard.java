package it.polimi.ingsw.model;

/**
 * This class represent the Action Token that will discard a DevCard.
 */
public class ActionDiscard implements ActionToken {
    private Color color;

    public ActionDiscard(Color color) {
        this.color = color;
    }
    public void doOperation(SinglePlayerGame game) {
        int endGame = game.getCardMarket().discardDevCard(color);
        if(endGame == -1) {
            game.endGame(0);
        }
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Action Discard " + color;
    }
}
