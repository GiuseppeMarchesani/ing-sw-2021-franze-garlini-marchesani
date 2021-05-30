package it.polimi.ingsw.messages;

public class HostGameReply extends ServerMessage{
    public HostGameReply(){
        super(MessageType.SUCCESSFUL_HOST);
    }
}
