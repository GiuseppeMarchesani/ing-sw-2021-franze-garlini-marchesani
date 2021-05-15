package it.polimi.ingsw.messages;

public abstract class GeneralMessage {
    private final String username;
    private final String gameID;
    private final MessageType messageType;

    public GeneralMessage(String username, MessageType messageType, String gameID){
        this.username= username;
        this.messageType=messageType;
        this.gameID=gameID;
    }

    public String getUsername(){ return username;}
    public MessageType getMessageType(){
        return messageType;
    }
    public String getGameID(){
        return gameID;
    }
}
