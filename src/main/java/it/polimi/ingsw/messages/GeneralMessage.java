package it.polimi.ingsw.messages;

public abstract class GeneralMessage {
    private final String username;
    private final MessageType messageType;

    public GeneralMessage(String username, MessageType messageType){
        this.username= username;
        this.messageType=messageType;
    }

    public String getUsername(){ return username;}
    public MessageType getMessageType(){
        return messageType;
    }
}
