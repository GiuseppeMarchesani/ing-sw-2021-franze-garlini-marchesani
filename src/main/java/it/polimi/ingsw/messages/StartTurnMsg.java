package it.polimi.ingsw.messages;

public class StartTurnMsg extends GeneralMessage{
    public StartTurnMsg(String username, MessageType messageType, String gameID) {
        super(username, messageType, gameID);
    }
}
