package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class ShowResourcesRequest extends GeneralMessage{
    private HashMap<ResourceType, Integer> strongbox;
    private Warehouse warehouse;
    public ShowResourcesRequest(String username, MessageType messageType, String gameID,
                                Warehouse warehouse, HashMap<ResourceType, Integer> strongbox) {
        super(username, messageType, gameID);
        this.warehouse=warehouse;
        this.strongbox=strongbox;
    }

    public HashMap<ResourceType, Integer> getStrongbox() {
        return strongbox;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }
}
