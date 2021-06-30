package it.polimi.ingsw.messages;

/**
 * This message identifies a request from a client to join a game.
 */
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
