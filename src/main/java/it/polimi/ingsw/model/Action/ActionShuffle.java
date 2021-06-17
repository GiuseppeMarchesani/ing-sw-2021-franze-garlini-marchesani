package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;

/**
 * This class represents the Action Token which shuffle the TokenBag and increase Black Cross position by one.
 */
public class ActionShuffle implements ActionToken{
    private int spaces;
    private ActionTokenType tokenType;

    /**
     * Default constructor.
     * @param spaces spaces to cross.
     * @param actionTokenType type of the action token.
     */
    public ActionShuffle(int spaces, ActionTokenType actionTokenType) {
        this.spaces = spaces;
        this.tokenType = actionTokenType;
    }

    /**
     * Increases the faith points of the black cross by one and shuffles all the tokens.
     * @param game game to which it refers.
     */
    public void doOperation(SinglePlayerGame game) {
        game.getTokenBag().shuffle();
        game.getBlackCross().increaseBlackCross(spaces);
    }

    public ActionTokenType getTokenType() {
        return tokenType;
    }

    @Override
    public String toString() {
        return "Shuffle Token";
    }

    /**
     * Returns a String representing the Action Token effect.
     * @return a String that specifies the effect.
     */
    public String getEffect(){
        return "Shuffle the Token Bag and increase Lorenzo's spaces on the faith track by one." ;
    }
}
