package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;

public class ResourceToWarehouseReplyMsg extends ServerMessage{
    private HashMap<ResourceType, Integer> resourceToPlace;
    private int any;
    private ArrayList<ResourceType> leaderDepots;
    public ResourceToWarehouseReplyMsg(HashMap <ResourceType,Integer> resourceToPlace, int any, ArrayList<ResourceType> leaderDepots){
        super(MessageType.RESOURCE_TO_WAREHOUSE);
        this.resourceToPlace=resourceToPlace;
        this.any=any;
        this.leaderDepots=leaderDepots;
    }

    public int getAny() {
        return any;
    }

    public HashMap<ResourceType, Integer> getResourceToPlace() {
        return resourceToPlace;
    }

    public ArrayList<ResourceType> getLeaderDepots() {
        return leaderDepots;
    }
}
