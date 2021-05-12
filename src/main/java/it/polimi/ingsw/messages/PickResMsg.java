package it.polimi.ingsw.messages;

public class PickResMsg extends CommandMsg{
    public PickResMsg(int playerID, MessageType messageType) {
        super(playerID, MessageType.PICK_MARKETRES);
    }
}
