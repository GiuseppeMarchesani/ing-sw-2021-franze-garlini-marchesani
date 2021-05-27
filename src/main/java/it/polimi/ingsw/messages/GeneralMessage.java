package it.polimi.ingsw.messages;

public abstract class GeneralMessage {
    private final String username;
    private final String gameID;
    private final MessageType msg;
    public GeneralMessage(String username, String gameID, MessageType msg){
        this.username= username;
        this.gameID=gameID;
        this.msg=msg;
    }
    public String getUsername(){ return username;}
    public String getGameID(){
        return gameID;
    }
    public MessageType getMessageType(){
        return msg;
    }
}
