package it.polimi.ingsw.messages;

import java.io.Serializable;

/**
 * This class is used for all messages to allow serialization
 */
public abstract class GeneralMessage implements Serializable {

    private final MessageType msg;
    public GeneralMessage(MessageType msg){

        this.msg=msg;
    }
    public MessageType getMessageType(){
        return msg;
    }
}
