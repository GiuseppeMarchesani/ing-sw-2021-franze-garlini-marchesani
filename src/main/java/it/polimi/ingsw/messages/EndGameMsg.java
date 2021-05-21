package it.polimi.ingsw.messages;

public class EndGameMsg extends GeneralMessage{
    public EndGameMsg(String username, MessageType messageType, String gameID) {
        super(username, messageType, gameID);
    }

}
