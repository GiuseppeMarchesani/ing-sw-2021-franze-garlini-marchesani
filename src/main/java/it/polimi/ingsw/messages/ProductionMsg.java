package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.ArrayList;

public class ProductionMsg extends GeneralMessage{
    ArrayList<DevCard> devCards;
    public ProductionMsg(ArrayList<DevCard> devCards,
                         ) {

        super(MessageType.ACTIVATE_PRODUCTION);
        this.devCards= devCards;
    }

    public ArrayList<DevCard> getDevCards() {
        return devCards;
    }

}

