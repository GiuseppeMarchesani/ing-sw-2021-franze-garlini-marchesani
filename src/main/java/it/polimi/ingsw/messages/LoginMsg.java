package it.polimi.ingsw.messages;

public class LoginMsg extends GeneralMessage{
    private String gameID;
    public LoginMsg(String username, String gameID, int numPlayers) {
        super(username, gameID, MessageType.LOGIN);
    }

}
