package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

public class ResourceReply extends GeneralMessage{
    ResourceType res;
    public ResourceReply(String username, MessageType messageType, String gameID, ResourceType res) {
        super(username, messageType, gameID);
        this.res=res;
    }

    public ResourceType getRes() {
        return res;
    }
}
