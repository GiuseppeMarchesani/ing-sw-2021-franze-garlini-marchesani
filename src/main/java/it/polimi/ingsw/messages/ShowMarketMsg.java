package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

/**
 * This message contains the data of the Marble Market
 */
public class ShowMarketMsg extends ServerMessage{
    private final ResourceType[][] market;
    private final ResourceType cornerMarble;
    public ShowMarketMsg( ResourceType[][] market, ResourceType cornerMarble) {
        super(MessageType.SHOW_MARKET);
        this.market=market;
        this.cornerMarble=cornerMarble;
    }
    public ResourceType[][] getMarket() {
        return market;
    }
    public ResourceType getCornerMarble() {
        return cornerMarble;
    }


}
