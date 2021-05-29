package it.polimi.ingsw.messages;

public class LoginMsg extends GeneralMessage{
    private String username;
    private String gameId;
    public LoginMsg(String username, String gameId) {
        super(MessageType.LOGIN);
        this.username=username;
        this.gameId=gameId;
    }

    public String getGameId() {
        return gameId;
    }

    public String getUsername() {
        return username;
    }
}
