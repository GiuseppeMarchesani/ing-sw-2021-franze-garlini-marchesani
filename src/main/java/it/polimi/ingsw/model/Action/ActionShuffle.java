package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;

/**
 * This class represent the Action Token which shuffle the TokenBag.
 */
public class ActionShuffle implements ActionToken{
    private int spaces;
    private ActionTokenType tokenType;

    /**
     * Default constructor
     * @param spaces spaces it must cross
     * @param actionTokenType type of the action token
     */
    public ActionShuffle(int spaces, ActionTokenType actionTokenType) {
        this.spaces = spaces;
        this.tokenType = actionTokenType;
    }

    /**
     * Increases the faith points of the black cross and shuffles all the token
     * @param game game to which it refers
     */
    public void doOperation(SinglePlayerGame game) {
        game.getTokenBag().shuffle();
        game.getBlackCross().increaseBlackCross(spaces);
    }

    /**
     *  Returns the type associated to this token
     * @return type of the token
     */
    public ActionTokenType getTokenType() {
        return tokenType;
    }
    @Override
    public String toString() {
        return "Shuffle Token";
    }

    public String getEffect(){
        return "Shuffle the Token Bag and increase Lorenzo's spaces on the faith track by one." ;
    }

}
