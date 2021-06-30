package it.polimi.ingsw.messages;

import it.polimi.ingsw.messages.ClientMessage;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This message contains the new warehouse state of a player.
 */
public class ResourceToWarehouseRequestMsg extends ClientMessage {
    private HashMap<Integer, ResourceType> depotToResource;
    private HashMap<Integer, Integer> depotToQuantity;
    private ArrayList<Integer> leaderToDepot;
    private int discard;
    public ResourceToWarehouseRequestMsg(String username, HashMap<Integer,ResourceType>depotToResource, HashMap<Integer, Integer> depotToQuantity, ArrayList<Integer> leaderToDepot, int discard) {
        super(username, MessageType.RESOURCE_TO_WAREHOUSE);
        this.depotToResource=depotToResource;
        this.depotToQuantity=depotToQuantity;
        this.leaderToDepot=leaderToDepot;
        this.discard=discard;
    }

    public ArrayList<Integer> getLeaderToDepot() {
        return leaderToDepot;
    }

    public HashMap<Integer, Integer> getDepotToQuantity() {
        return depotToQuantity;
    }

    public HashMap<Integer, ResourceType> getDepotToResource() {
        return depotToResource;
    }

    public int getDiscard() {
        return discard;
    }
}
