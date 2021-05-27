package it.polimi.ingsw.messages;

public class LoginReplyMsg extends GeneralMessage {
    boolean created;
    boolean joined;
    public LoginReplyMsg(String username, String gameID, boolean created, boolean joined) {
        super(username, gameID, MessageType.LOGIN_REPLY);
        this.created=created;
        this.joined=joined;
    }

    public boolean wasCreated(){
        return created;
    }
    public boolean wasJoined(){
        return joined;
    }
}
