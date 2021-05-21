package it.polimi.ingsw.messages;

public class NewGameRequest extends GeneralMessage {
    private int numOfPlayers;
    public NewGameRequest(String username, MessageType messageType, String gameID, int numOfPlayers) {
        super(username, messageType, gameID);
        this.numOfPlayers= numOfPlayers;
    }

    public int getNumOfPlayers(){
        return numOfPlayers;
    }
}
