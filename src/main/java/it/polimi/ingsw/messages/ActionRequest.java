package it.polimi.ingsw.messages;

/**
 * This message identifies a request for a basic turn action
 */
public class ActionRequest extends ClientMessage{

    public ActionRequest(String username, MessageType messageType){
        super(username, messageType);
    }
}
