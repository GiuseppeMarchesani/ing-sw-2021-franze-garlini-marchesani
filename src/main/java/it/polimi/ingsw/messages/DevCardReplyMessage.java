package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.DevCard;

import java.util.ArrayList;

public class DevCardReplyMessage extends GeneralMessage{
    private ArrayList<DevCard> devCard;
    public DevCardReplyMessage(String username,  String gameID, ArrayList<DevCard> devCard) {
        super(username,gameID, MessageType.SHOW_DEV_MARKET);
        this.devCard=devCard;
    }

    public ArrayList<DevCard> getDevCard(){
        return devCard;
    }
}
