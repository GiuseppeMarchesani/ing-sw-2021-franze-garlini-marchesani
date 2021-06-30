package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.LeaderCard;

import java.util.ArrayList;
import java.util.List;
/**
 * This message contains the player's leader cards that can be played or discarded
 */
public class SideLeaderReply extends ServerMessage {
    private ArrayList<LeaderCard> leaderCards;
    public SideLeaderReply(ArrayList<LeaderCard> leaderCards) {
        super(MessageType.SIDE_LEADER);
        this.leaderCards=leaderCards;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }
}
