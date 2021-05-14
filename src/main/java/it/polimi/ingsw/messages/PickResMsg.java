package it.polimi.ingsw.messages;

public class PickResMsg extends GeneralMessage{
    public PickResMsg(String username, MessageType messageType) {
        super(username, MessageType.PICK_MARKETRES);
    }
}
