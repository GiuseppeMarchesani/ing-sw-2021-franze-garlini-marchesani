package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Board.Market;

public class GetMarketResRequest extends ClientMessage{
    public GetMarketResRequest(String username) {
        super(username, MessageType.PICK_MARKETRES);
    }

}
