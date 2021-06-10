package it.polimi.ingsw.messages;

public class LoginRequest extends ClientMessage{
    private String gameId;
    public LoginRequest(String username, String gameId) {
        super(username, MessageType.LOGIN);
        this.gameId=gameId;
    }
    public String getGameId() {
        return gameId;
    }
}
