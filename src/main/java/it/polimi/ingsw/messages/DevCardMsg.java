package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.DevCard;

public class DevCardMsg extends GeneralMessage{
    DevCard devCard;
    public DevCardMsg(int playerID, MessageType messageType, DevCard devCard) {
        super(playerID, MessageType.PICK_DEVCARD);
        this.devCard= devCard;

    }

    public DevCard getDevCard(){
        return devCard;
    }
}
