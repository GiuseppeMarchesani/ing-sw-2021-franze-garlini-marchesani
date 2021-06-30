package it.polimi.ingsw.messages;

/**
 * This message identifies a request from the server to chose the number of
 * players.
 */
public class HostGameReply extends ServerMessage{
    public HostGameReply(){
        super(MessageType.SUCCESSFUL_HOST);
    }
}
