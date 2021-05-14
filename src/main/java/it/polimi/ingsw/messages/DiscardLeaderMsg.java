package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.LeaderCard;

public class DiscardLeaderMsg extends GeneralMessage{

    public LeaderCard leaderCard;
    public DiscardLeaderMsg(String username, LeaderCard leaderCard) {
        super(username, MessageType.DISCARDLEADER);
        this.leaderCard=leaderCard;
    }

    public LeaderCard getLeaderCard(){
        return leaderCard;
    }
}
