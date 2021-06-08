package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.LeaderCard;

public class LeaderActionRequest extends ClientMessage {
    private LeaderCard card;
    private boolean choseToPlay;
    public LeaderActionRequest(String username, LeaderCard card, boolean choseToPlay) {
        super(username, MessageType.LEADER_ACTION);
        this.card=card;
        this.choseToPlay=choseToPlay;
    }

    public LeaderCard getCard() {
        return card;
    }

    public boolean isChoseToPlay() {
        return choseToPlay;
    }
}
