package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.ArrayList;

public class GetMarketResReply extends ServerMessage{
    ResourceType[][] market;
    ArrayList<ResourceType> conversion;
    public GetMarketResReply(ResourceType[][] market, ArrayList<ResourceType> conversion){
        this.market=market;
        this.conversion=conversion;
    }

    public ArrayList<ResourceType> getConversion() {
        return conversion;
    }

    public ResourceType[][] getMarket() {
        return market;
    }
}
