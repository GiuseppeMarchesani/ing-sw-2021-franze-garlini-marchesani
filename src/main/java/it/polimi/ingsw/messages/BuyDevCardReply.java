package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class BuyDevCardReply extends ServerMessage{
    public BuyDevCardReply(){
        super(MessageType.MAIN_CARD);
    }

}
