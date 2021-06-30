package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.DevCard;

import java.util.ArrayList;
import java.util.List;

/**
 * This message contains the data of the devcardMarket
 */
public class ShowDevMarketMsg extends ServerMessage {
    ArrayList<DevCard> availableCards;
    ArrayList<Integer> remainingCards;
    public ShowDevMarketMsg(ArrayList<DevCard> availableCards, ArrayList<Integer> remainingCards) {
        super(MessageType.SHOW_DEV_MARKET);
        this.availableCards=availableCards;
        this.remainingCards=remainingCards;
    }

    public ArrayList<DevCard> getAvailableCards() {
        return availableCards;
    }

    public ArrayList<Integer> getRemainingCards() {
        return remainingCards;
    }
}
