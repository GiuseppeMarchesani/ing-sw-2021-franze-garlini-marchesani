package it.polimi.ingsw.messages;

public class LoginMsg extends GeneralMessage{
    private String gameID;
    public LoginMsg(String username, MessageType messageType, String gameID) {
        super(username, messageType, gameID);
    }

}
