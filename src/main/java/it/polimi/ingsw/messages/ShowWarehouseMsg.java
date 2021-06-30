package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;
/**
 * This message contains the data of the Player's warehouse
 */
public class ShowWarehouseMsg extends ServerMessage {
    private HashMap<Integer, ResourceType> depotToResource;
    private HashMap<Integer, Integer> depotToQuantity;
    private String username;
    public ShowWarehouseMsg(HashMap<Integer, ResourceType> depotToResource, HashMap<Integer, Integer> depotToQuantity, String username) {
        super(MessageType.SHOW_WAREHOUSE);
        this.depotToQuantity=depotToQuantity;
        this.depotToResource=depotToResource;
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public HashMap<Integer, Integer> getDepotToQuantity() {
        return depotToQuantity;
    }

    public HashMap<Integer, ResourceType> getDepotToResource() {
        return depotToResource;
    }
}
