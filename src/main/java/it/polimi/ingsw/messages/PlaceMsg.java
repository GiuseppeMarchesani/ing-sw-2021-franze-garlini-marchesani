package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

public class PlaceMsg extends GeneralMessage{
    private ResourceType res;
    private int floor;
    private boolean rearrange;
    private int depot1;
    private int depot2;

    public PlaceMsg(String username, MessageType messageType, String gameID, ResourceType res, int floor,
                    boolean rearrange, int depot1, int depot2) {
        super(username, messageType, gameID);
        this.res=res;
        this.floor=floor;
        this.rearrange= rearrange;
        this.depot1=depot1;
        this.depot2=depot2;
    }

    public ResourceType getRes(){
        return res;
    }

    public int getFloor() {
        return floor;
    }

    public boolean isRearrange() {
        return rearrange;
    }

    public int getDepot1() {
        return depot1;
    }

    public int getDepot2() {
        return depot2;
    }
}
