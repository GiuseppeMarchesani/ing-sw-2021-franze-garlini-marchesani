package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.enumeration.ResourceType;

public class PlaceRequestMsg extends GeneralMessage{
    private Warehouse warehouse;
    private ResourceType res;
    public PlaceRequestMsg(String username, MessageType messageType, String gameID, Warehouse warehouse, ResourceType res) {
        super(username, messageType, gameID);
        this.warehouse=warehouse;
        this.res=res;
    }

    public ResourceType getRes() {
        return res;
    }
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
