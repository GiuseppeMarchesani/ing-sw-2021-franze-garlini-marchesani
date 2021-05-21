package it.polimi.ingsw.messages;

public class EndTurnMsg extends GeneralMessage{
    public EndTurnMsg(String username, MessageType messageType, String gameID) {
        super(username, messageType, gameID);
    }
}
