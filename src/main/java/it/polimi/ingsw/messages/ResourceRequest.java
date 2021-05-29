package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class ResourceRequest extends GeneralMessage{
    HashMap <ResourceType, Integer> res;
    public ResourceRequest(HashMap<ResourceType, Integer> res) {
        super( MessageType.RESOURCE_TO_STRONGBOX);
        this.res=res;
    }

    public HashMap<ResourceType, Integer> getRes() {
        return res;
    }
}
