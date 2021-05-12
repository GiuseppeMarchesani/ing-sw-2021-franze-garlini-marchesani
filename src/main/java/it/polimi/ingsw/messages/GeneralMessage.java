package it.polimi.ingsw.messages;

public abstract class GeneralMessage {
    private final int playerID;
    private final MessageType messageType;

    public GeneralMessage(int playerID, MessageType messageType){
        this.playerID= playerID;
        this.messageType=messageType;
    }

    public int getPlayerID(){ return playerID;}
    public MessageType getMessageType(){
        return messageType;
    }
}
