package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.DevCard;

public class DevCardMsg extends GeneralMessage{
    DevCard devCard;
    public DevCardMsg(String username, MessageType messageType, String gameID) {
        super(username, MessageType.PICK_DEVCARD, gameID);

    }
}
