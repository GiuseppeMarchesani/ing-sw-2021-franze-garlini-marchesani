package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class BuyDevCardReply extends ServerMessage{
    private HashMap<ResourceType, Integer> discount;
    public BuyDevCardReply(HashMap<ResourceType, Integer> discount){
        super(MessageType.MAIN_CARD);
        this.discount=discount;
    }
    public HashMap<ResourceType, Integer> getDiscount() {
        return discount;
    }

}
