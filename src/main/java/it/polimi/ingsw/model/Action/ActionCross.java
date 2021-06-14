package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;

/**
 * This class represent the Action Token which increase the Black Cross position by 2.
 */
public class ActionCross implements ActionToken {
    private int spaces;
    private ActionTokenType tokenType;

    public ActionCross(int spaces, ActionTokenType actionTokenType) {
        this.spaces = spaces;
        this.tokenType = actionTokenType;
    }

    public void doOperation(SinglePlayerGame game) {
        game.getBlackCross().increaseBlackCross(spaces);
    }

    public ActionTokenType getTokenType() {
        return tokenType;
    }
    public String toString() {
        return "Lorenzo Il Magnifico Token";
    }

    public String getEffect(){
        return "Increase Lorenzo's position on the faith track by two spaces." ;
    }
}
