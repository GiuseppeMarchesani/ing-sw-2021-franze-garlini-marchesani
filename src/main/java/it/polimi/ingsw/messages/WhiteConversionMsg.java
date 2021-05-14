package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

public class WhiteConversionMsg extends GeneralMessage{
    private ResourceType res;
    private int quantity;
    private char wOrS;
    public WhiteConversionMsg(String username, MessageType messageType, ResourceType res, int quantity, char wOrS) {
        super(username, messageType);
        this.res=res;
        this.quantity=quantity;
        this.wOrS=wOrS;
    }

    public ResourceType getRes(){
        return res;
    }

    public int getQuantity() {
        return quantity;
    }

    public char getWOrS() {
        return wOrS;
    }
}
