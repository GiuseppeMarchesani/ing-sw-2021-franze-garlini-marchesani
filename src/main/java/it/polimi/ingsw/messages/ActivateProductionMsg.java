package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.DevCard;

import java.util.ArrayList;

public class ActivateProductionMsg extends GeneralMessage{

    public ActivateProductionMsg(String username, MessageType messageType, String gameID) {
        super(username, messageType, gameID);
    }


}
