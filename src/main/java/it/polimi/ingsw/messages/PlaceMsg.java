package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

public class PlaceMsg extends GeneralMessage{
    private ResourceType res;
    private int quantity;
    private int floor;

    public PlaceMsg(String username, MessageType messageType, ResourceType res, int quantity, int floor) {
        super(username, messageType);
        this.res=res;
        this.quantity=quantity;
        this.floor=floor;
    }

    public ResourceType getRes(){
        return res;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getFloor() {
        return floor;
    }
}
