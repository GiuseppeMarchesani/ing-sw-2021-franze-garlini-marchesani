package it.polimi.ingsw.messages;

import java.io.Serializable;

public abstract class GeneralMessage implements Serializable {

    private final MessageType msg;
    public GeneralMessage(MessageType msg){

        this.msg=msg;
    }
    public MessageType getMessageType(){
        return msg;
    }
}
