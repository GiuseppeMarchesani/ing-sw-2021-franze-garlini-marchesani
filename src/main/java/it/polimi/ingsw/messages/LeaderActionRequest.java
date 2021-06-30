package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.LeaderCard;

/**
 * This message contains the leader card chosen for a leader action and
 * if it was chosen to play or discard.
 */
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
