package it.polimi.ingsw.messages;

public class LoginMsg extends CommandMsg{
    public LoginMsg(int playerID, MessageType messageType) {
        super(playerID, messageType);
    }
}
