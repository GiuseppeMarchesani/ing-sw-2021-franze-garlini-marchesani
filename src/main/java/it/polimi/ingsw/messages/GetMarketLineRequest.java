package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Board.Market;

public class GetMarketLineRequest extends GeneralMessage{
    private Market market;
    public GetMarketLineRequest(String username, MessageType messageType, String gameID, Market market) {
        super(username, messageType, gameID);
        this.market=market;
    }

    public Market getMarket() {
        return market;
    }
}
