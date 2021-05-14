package it.polimi.ingsw.messages;

public class ErrorMsg extends GeneralMessage{
    public ErrorMsg(String username, MessageType messageType) {
        super(username, messageType);
    }
}
