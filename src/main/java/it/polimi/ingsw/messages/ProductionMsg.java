package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.DevCard;

public class ProductionMsg extends GeneralMessage{
    DevCard devCard;
    public ProductionMsg(int playerID, MessageType messageType, DevCard devCard) {
        super(playerID, MessageType.ACTIVATE_PRODUCTION);
        this.devCard=devCard;
    }

    public DevCard getDevCard(){
        return devCard;
    }
}
