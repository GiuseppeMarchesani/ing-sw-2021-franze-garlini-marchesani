package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;

public interface ActionToken {

    public abstract void doOperation(SinglePlayerGame game);

    public abstract ActionTokenType getTokenType();
    public String getEffect();
}
