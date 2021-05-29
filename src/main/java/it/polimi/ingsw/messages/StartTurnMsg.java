package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;

public class StartTurnMsg extends GeneralMessage{
    public StartTurnMsg() {
        super(MessageType.START_TURN);
    }

}
