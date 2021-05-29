package it.polimi.ingsw.messages;

public abstract class GeneralMessage {

    private final MessageType msg;
    public GeneralMessage(MessageType msg){

        this.msg=msg;
    }
    public MessageType getMessageType(){
        return msg;
    }
}
