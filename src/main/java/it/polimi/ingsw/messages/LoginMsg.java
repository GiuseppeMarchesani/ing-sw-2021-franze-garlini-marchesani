package it.polimi.ingsw.messages;

public class LoginMsg extends GeneralMessage{
    int numPlayers;
    public LoginMsg(String username, String gameID, int numPlayers) {
        super(username, gameID, MessageType.LOGIN);
        this.numPlayers=numPlayers;
    }
    public int getNumPlayers(){
        return numPlayers;
    }
}
