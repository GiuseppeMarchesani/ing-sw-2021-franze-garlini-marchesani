package it.polimi.ingsw.messages;

import java.util.ArrayList;

public class ResourceRequest extends GeneralMessage{
    ArrayList<String> resources;
    String typeRequest;
    public ResourceRequest(String username, MessageType messageType, String gameID, ArrayList<String> resources, String typeRequest) {
        super(username, messageType, gameID);
        this.resources=resources;
        this.typeRequest=typeRequest;
    }

    public ArrayList<String> getResources() {
        return resources;
    }
}
