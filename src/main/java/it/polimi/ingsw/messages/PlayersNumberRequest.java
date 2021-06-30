package it.polimi.ingsw.messages;

/**
 * This message contains the number of players requested for a hosted game
 */
public class PlayersNumberRequest extends ClientMessage {
    private int playersNumber;
    public PlayersNumberRequest(String username, int playersNumber) {
        super(username,MessageType.PLAYER_NUMBER);
        this.playersNumber=playersNumber;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }


}
