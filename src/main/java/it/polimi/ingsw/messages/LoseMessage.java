package it.polimi.ingsw.messages;

/**
 * This message notifies a loss in a single player game
 */
public class LoseMessage extends ServerMessage {
    public LoseMessage() {
        super(MessageType.LOSE);
    }
}
