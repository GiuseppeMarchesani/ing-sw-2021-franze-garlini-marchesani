package it.polimi.ingsw.messages;

public class EndGameMsg extends GeneralMessage{
    private int gameID;
    public EndGameMsg(String username, MessageType messageType, int gameID) {
        super(username, messageType);
        this.gameID = gameID;
    }

}
