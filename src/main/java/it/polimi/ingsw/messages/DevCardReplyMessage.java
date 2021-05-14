package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.DevCard;

public class DevCardReplyMessage extends GeneralMessage{
    private DevCard devCard;
    public DevCardReplyMessage(String username, MessageType messageType, DevCard devCard) {
        super(username, messageType);
        this.devCard=devCard;
    }

    public DevCard getDevCard(){
        return devCard;
    }
}
