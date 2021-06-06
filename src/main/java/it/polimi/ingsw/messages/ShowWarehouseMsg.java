package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class ShowWarehouseMsg extends GeneralMessage {
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
