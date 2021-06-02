package it.polimi.ingsw.messages;

public class EndTurnRequest extends ClientMessage{
    public EndTurnRequest(String username) {
        super(username, MessageType.END_TURN);
    }
}
