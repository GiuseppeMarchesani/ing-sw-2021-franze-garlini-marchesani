package it.polimi.ingsw.messages;

public class ShowPlayerTurnMsg extends ServerMessage {
    private String activePlayer;
    public ShowPlayerTurnMsg(String activePlayer) {
        super(MessageType.OTHER_PLAYER_TURN);
        this.activePlayer=activePlayer;
    }

    public String getActivePlayer() {
        return activePlayer;
    }
}
