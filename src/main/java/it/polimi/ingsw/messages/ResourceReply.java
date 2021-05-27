package it.polimi.ingsw.messages;

import java.util.ArrayList;

public class ResourceReply extends GeneralMessage{
    ArrayList<String> resources;
    String typeRequest;
    public ResourceReply(String username, String gameID, ArrayList<String> resources, String typeRequest) {
        super(username, gameID, Mess);
        this.resources=resources;
        this.typeRequest=typeRequest;
    }

    public ArrayList<String> getResources() {
        return resources;
    }
}
