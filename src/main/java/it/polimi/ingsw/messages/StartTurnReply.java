package it.polimi.ingsw.messages;
/**
 * This message notifies a player that it's their turn
 */
public class StartTurnReply  extends ServerMessage{

    public StartTurnReply(){
        super(MessageType.START_TURN);
    }
}
