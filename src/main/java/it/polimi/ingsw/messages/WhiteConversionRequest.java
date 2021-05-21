package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.ArrayList;

public class WhiteConversionRequest extends GeneralMessage{
    private ArrayList<ResourceType> resourceTypes;
    public WhiteConversionRequest(String username, MessageType messageType, String gameID, ArrayList<ResourceType> resourceTypes) {
        super(username, messageType, gameID);
        this.resourceTypes=resourceTypes;
    }
    public ArrayList<ResourceType> getResourceTypes() {
        return resourceTypes;
    }
}
