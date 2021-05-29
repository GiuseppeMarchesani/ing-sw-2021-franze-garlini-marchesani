package it.polimi.ingsw.messages;

public class LoginReplyMsg extends GeneralMessage {
     private boolean joined;
    public LoginReplyMsg(String username, String gameID,boolean joined) {
        super(username, gameID, MessageType.LOGIN_REPLY);
        this.joined=joined;
    }

    public boolean wasJoined(){
        return joined;
    }
}
