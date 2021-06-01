package it.polimi.ingsw.messages;

import com.sun.deploy.util.SessionState;
import it.polimi.ingsw.model.enumeration.ResourceType;

public class ResourceToStrongboxReplyMsg extends ServerMessage{
    private int any;

    public ResourceToStrongboxReplyMsg(int any){
        super(MessageType.RESOURCE_TO_STRONGBOX);
        this.any=any;
    }

}
