package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.ArrayList;

public class ProductionMsg extends GeneralMessage{
    ArrayList<DevCard> devCards;
    ResourceType res;
    public ProductionMsg(String username, MessageType messageType, String gameID, ArrayList<DevCard> devCards,
                         ResourceType res) {

        super(username, MessageType.ACTIVATE_PRODUCTION, gameID);
        this.devCards= devCards;
        this.res=res;
    }

    public ArrayList<DevCard> getDevCards() {
        return devCards;
    }

    public ResourceType getRes() {
        return res;
    }
}

