package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;
import javafx.fxml.FXML;

/**
 * This class represent the Action Token which increase the Black Cross position by 2.
 */
public class ActionCross implements ActionToken {
    private int spaces;
    private ActionTokenType tokenType;

    /**
     * Default constructor
     * @param spaces spaces it must cross
     * @param actionTokenType the type of the action token
     */
    public ActionCross(int spaces, ActionTokenType actionTokenType) {
        this.spaces = spaces;
        this.tokenType = actionTokenType;
    }

    /**
     * Increases the faith points of the black cross
     * @param game game to which it refers
     */
    public void doOperation(SinglePlayerGame game) {
        game.getBlackCross().increaseBlackCross(spaces);
    }

    /**
     * Returns the type associated to this token
     * @return type of the token
     */
    public ActionTokenType getTokenType() {
        return tokenType;
    }

    @Override
    public String toString() {
        return "Lorenzo Il Magnifico Token";
    }

    public String getEffect(){
        return "Increase Lorenzo's position on the faith track by two spaces." ;
    }
}
