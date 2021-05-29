package it.polimi.ingsw.messages;

public class PlayersNumberReply extends GeneralMessage {
    int playersNumber;
    public PlayersNumberReply(int playersNumber) {
        super(MessageType.PLAYER_NUMBER);
        this.playersNumber=playersNumber;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }
}
