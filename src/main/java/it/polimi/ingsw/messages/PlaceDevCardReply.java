package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;

public class PlaceDevCardReply extends GeneralMessage {
    private HashMap<ResourceType, Integer> warehouse;
    private HashMap<ResourceType, Integer> strongbox;
    private int any;
    private HashMap<ResourceType, Integer> cardCost;
    private ArrayList<Integer> availableSlots;
    public PlaceDevCardReply(HashMap<ResourceType, Integer> warehouse, HashMap<ResourceType, Integer> strongbox, HashMap<ResourceType, Integer> cardCost,int any, ArrayList<Integer> availableSlots) {
        super(MessageType.PLACE_CARD);
        this.warehouse=warehouse;
        this.strongbox=strongbox;
        this.any=any;
        this.availableSlots=availableSlots;
        this.cardCost=cardCost;
    }

    public int getAny() {
        return any;
    }

    public HashMap<ResourceType, Integer> getCardCost() {
        return cardCost;
    }

    public HashMap<ResourceType, Integer> getStrongbox() {
        return strongbox;
    }

    public ArrayList<Integer> getAvailableSlots() {
        return availableSlots;
    }

    public HashMap<ResourceType, Integer> getWarehouse() {
        return warehouse;
    }
}
