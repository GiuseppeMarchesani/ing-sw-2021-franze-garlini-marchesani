package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.DevCard;

import java.util.ArrayList;
import java.util.List;

public class ShowDevMarketMsg extends GeneralMessage {
    List<DevCard> availableCards;
    List<Integer> remainingCards;
    public ShowDevMarketMsg(List<DevCard> availableCards, List<Integer> remainingCards) {
        super(MessageType.SHOW_DEV_MARKET);
        this.availableCards=availableCards;
        this.remainingCards=remainingCards;
    }

    public List<DevCard> getAvailableCards() {
        return availableCards;
    }

    public List<Integer> getRemainingCards() {
        return remainingCards;
    }
}
