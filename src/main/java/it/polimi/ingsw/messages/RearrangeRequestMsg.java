package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Board.Warehouse;

public class RearrangeRequestMsg extends GeneralMessage{
    private Warehouse warehouse;
    public RearrangeRequestMsg(String username, MessageType messageType, String gameID, Warehouse warehouse) {
        super(username, messageType, gameID);
        this.warehouse=warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
