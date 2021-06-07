package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class GetProductionRequest extends ClientMessage {
    private HashMap<ResourceType, Integer> expenseDepot;
    private HashMap<ResourceType, Integer> newStrongbox;
    public GetProductionRequest(String username, HashMap<ResourceType, Integer> expenseDepot, HashMap<ResourceType, Integer> newStrongbox) {
        super(username, MessageType.ACTIVATE_PRODUCTION);
        this.expenseDepot=expenseDepot;
        this.newStrongbox=newStrongbox;

    }

    public HashMap<ResourceType, Integer> getExpenseDepot() {
        return expenseDepot;
    }

    public HashMap<ResourceType, Integer> getNewStrongbox() {
        return newStrongbox;
    }
}
