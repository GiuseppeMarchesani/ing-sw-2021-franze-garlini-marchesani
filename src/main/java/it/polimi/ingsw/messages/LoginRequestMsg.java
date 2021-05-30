package it.polimi.ingsw.messages;

public class LoginRequestMsg extends ClientMessage{
    private String gameId;
    public LoginRequestMsg(String username, String gameId) {
        super(username, MessageType.LOGIN);
        this.gameId=gameId;
    }
    public String getGameId() {
        return gameId;
    }
}
