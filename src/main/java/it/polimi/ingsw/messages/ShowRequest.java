package it.polimi.ingsw.messages;

public class ShowRequest extends ClientMessage{

    public ShowRequest(String username, MessageType messageType){
        super(username, messageType);
    }
}
