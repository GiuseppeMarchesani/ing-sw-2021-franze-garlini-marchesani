package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.LeaderCard;

import java.util.ArrayList;
import java.util.List;

public class ChoseLeadersMsg extends GeneralMessage{

    private List<LeaderCard> leaderCard;
    public ChoseLeadersMsg(List<LeaderCard> leaderCard) {
        super(MessageType.KEEP_LEADER);
        this.leaderCard=leaderCard;
    }

    public List<LeaderCard> getLeaderCard() {
        return leaderCard;
    }
}
