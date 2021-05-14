package it.polimi.ingsw.messages;

public class LoginMsg extends GeneralMessage{
    private int gameID;
    public LoginMsg(String username, MessageType messageType, int gameID) {
        super(username, messageType);
        this.gameID=gameID;
    }
    public int getGameID(){
        return gameID;
    }
}
