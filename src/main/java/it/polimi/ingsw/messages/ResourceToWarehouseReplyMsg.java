package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class ResourceToWarehouseReplyMsg extends ServerMessage{
    private HashMap<ResourceType, Integer> resourceToPlace;
    private int any;
    public ResourceToWarehouseReplyMsg(HashMap <ResourceType,Integer> resourceToPlace, int any){
        super(MessageType.RESOURCE_TO_WAREHOUSE);
        this.resourceToPlace=resourceToPlace;
        this.any=any;
    }

    public int getAny() {
        return any;
    }

    public HashMap<ResourceType, Integer> getResourceToPlace() {
        return resourceToPlace;
    }
}
