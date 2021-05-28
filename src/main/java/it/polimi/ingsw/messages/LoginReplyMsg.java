package it.polimi.ingsw.messages;

public class LoginReplyMsg extends GeneralMessage {
     private boolean joined;
    private int remainingPlayers;
    public LoginReplyMsg(String username, String gameID,boolean joined, int remainingPlayers) {
        super(username, gameID, MessageType.LOGIN_REPLY);
       this.remainingPlayers=remainingPlayers;
        this.joined=joined;
    }

    public boolean wasJoined(){
        return joined;
    }
    public int getRemainingPlayers(){
        return remainingPlayers;
    }
}
