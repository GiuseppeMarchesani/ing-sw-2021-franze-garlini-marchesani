package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.DevCardSlot;

public class ShowSlotsMsg extends ServerMessage {
    private String username;
    private DevCardSlot devCardSlot;
    public ShowSlotsMsg(DevCardSlot devCardSlot, String username) {
        super(MessageType.SHOW_SLOT);
        this.devCardSlot=devCardSlot;
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public DevCardSlot getDevCardSlot() {
        return devCardSlot;
    }
}
