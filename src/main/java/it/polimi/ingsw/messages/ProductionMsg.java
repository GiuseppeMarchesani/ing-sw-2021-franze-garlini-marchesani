package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.DevCard;

public class ProductionMsg extends GeneralMessage{
    DevCard devCard;
    public ProductionMsg(String username, MessageType messageType, DevCard devCard) {
        super(username, MessageType.ACTIVATE_PRODUCTION);
        this.devCard=devCard;
    }

    public DevCard getDevCard(){
        return devCard;
    }
}
