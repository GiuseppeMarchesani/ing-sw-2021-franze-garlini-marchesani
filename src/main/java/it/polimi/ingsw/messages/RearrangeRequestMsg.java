package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class RearrangeRequestMsg extends GeneralMessage{
    private Warehouse warehouse;
    private HashMap<ResourceType, Integer> resources;
    public RearrangeRequestMsg(String username, String gameId, Warehouse warehouse, HashMap<ResourceType, Integer> resources) {
        super(username, gameId, MessageType.REARRANGE_REQUEST);
        this.warehouse=warehouse;
        this.resources=resources;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
