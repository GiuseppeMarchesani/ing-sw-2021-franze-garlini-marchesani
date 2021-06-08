package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.LeaderCard;

import java.util.ArrayList;
import java.util.List;

public class SideLeaderReply extends GeneralMessage {
    private ArrayList<LeaderCard> leaderCards;
    public SideLeaderReply(ArrayList<LeaderCard> leaderCards) {
        super(MessageType.SIDE_LEADER);
        this.leaderCards=leaderCards;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }
}
