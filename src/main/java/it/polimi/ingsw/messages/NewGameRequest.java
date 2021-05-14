package it.polimi.ingsw.messages;

public class NewGameRequest extends GeneralMessage {
    private int gameID;
    private int numOfPlayers;
    public NewGameRequest(String username, MessageType messageType, int gameID, int numOfPlayers) {
        super(username, messageType);
        this.gameID= gameID;
        this.numOfPlayers= numOfPlayers;
    }
    public int getGameID(){
        return gameID;
    }

    public int getNumOfPlayers(){
        return numOfPlayers;
    }
}
