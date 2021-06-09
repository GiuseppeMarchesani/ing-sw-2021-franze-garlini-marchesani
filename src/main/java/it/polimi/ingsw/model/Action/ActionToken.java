package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;

public abstract class ActionToken {
     private ActionTokenType type;
     public ActionToken(ActionTokenType type){
         this.type=type;
     }
    public abstract void doOperation(SinglePlayerGame game);

    public ActionTokenType getType() {
        return type;
    }
}
