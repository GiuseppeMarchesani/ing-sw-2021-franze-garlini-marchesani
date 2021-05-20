package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.enumeration.Color;

public class PlaceCardMsg extends GeneralMessage{
    int slot;
    DevCard devCard;
    public PlaceCardMsg(String username, MessageType messageType, String gameID, int slot, DevCard devCard) {
        super(username, messageType, gameID);
        this.slot=slot;
        this.devCard=devCard;
    }
    public int getSlot(){
        return slot;
    }

    public DevCard getDevCard() {
        return devCard;
    }
}
