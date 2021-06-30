package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.ArrayList;

/**
 * This message sends the player's available empty marble conversions
 * and allows to buy from marble market.
 */
public class GetMarketResReply extends ServerMessage{

    ArrayList<ResourceType> conversion;
    public GetMarketResReply( ArrayList<ResourceType> conversion){
        super(MessageType.MAIN_MARBLE);
        this.conversion=conversion;
    }

    public ArrayList<ResourceType> getConversion() {
        return conversion;
    }

}
