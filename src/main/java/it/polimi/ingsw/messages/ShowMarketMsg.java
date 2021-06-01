package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Board.Market;
import it.polimi.ingsw.model.enumeration.ResourceType;

public class ShowMarketMsg extends GeneralMessage{
    private ResourceType[][] market;
    private ResourceType cornerMarble;
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
