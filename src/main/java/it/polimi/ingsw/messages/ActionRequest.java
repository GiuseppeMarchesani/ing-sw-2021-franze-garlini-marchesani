package it.polimi.ingsw.messages;

public class ActionRequest extends ClientMessage{

    public ActionRequest(String username, MessageType messageType){
        super(username, messageType);
    }
}
