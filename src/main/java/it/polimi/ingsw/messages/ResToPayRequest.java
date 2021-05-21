package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class ResToPayRequest extends ShowResourcesRequest{
    private ResourceType res;
    public ResToPayRequest(String username, MessageType messageType, String gameID, Warehouse warehouse, HashMap<ResourceType, Integer> strongbox,
                           ResourceType res) {
        super(username, messageType, gameID, warehouse, strongbox);
        this.res=res;
    }

    public ResourceType getRes() {
        return res;
    }
}
