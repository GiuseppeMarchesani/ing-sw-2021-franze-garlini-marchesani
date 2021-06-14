package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class ShowStrongboxMsg extends ServerMessage {
    private HashMap<ResourceType, Integer> strongbox;
    private String username;
    public ShowStrongboxMsg(HashMap<ResourceType, Integer> strongbox, String username) {
        super(MessageType.SHOW_STRONGBOX);
        this.strongbox=strongbox;
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public HashMap<ResourceType, Integer> getStrongbox() {
        return strongbox;
    }
}
