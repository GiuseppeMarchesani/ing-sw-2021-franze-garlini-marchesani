package it.polimi.ingsw.messages;

public class LoginMsg extends GeneralMessage{
    public LoginMsg(int playerID, MessageType messageType) {
        super(playerID, messageType);
    }
}
