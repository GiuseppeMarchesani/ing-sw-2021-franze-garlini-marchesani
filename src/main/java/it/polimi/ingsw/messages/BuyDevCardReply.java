package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

/**
 * This message is sent if the player is allowed to buy a devcard.
 */
public class BuyDevCardReply extends ServerMessage{
    public BuyDevCardReply(){
        super(MessageType.MAIN_CARD);
    }

}
