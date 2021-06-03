package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class PlaceDevCardRequest extends ClientMessage {
    private HashMap<ResourceType, Integer> expenseDepot;
    private HashMap<ResourceType, Integer> newStrongbox;
    private int slotToPlace;
    public PlaceDevCardRequest(String username,HashMap<ResourceType, Integer> expenseDepot, HashMap<ResourceType, Integer> newStrongbox, int slotToPlace) {
        super(username, MessageType.PLACE_CARD);
        this.slotToPlace=slotToPlace;
        this.expenseDepot=expenseDepot;
        this.newStrongbox=newStrongbox;
    }

    public HashMap<ResourceType, Integer> getExpenseDepot() {
        return expenseDepot;
    }

    public HashMap<ResourceType, Integer> getNewStrongbox() {
        return newStrongbox;
    }

    public int getSlotToPlace() {
        return slotToPlace;
    }
}
