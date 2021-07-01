package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.LeaderCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * This message contains the player's leader cards that can be played or discarded
 */
public class SideLeaderReply extends ServerMessage {
    private ArrayList<LeaderCard> allLeaderCards;
    public HashMap<LeaderCard, Boolean> leaderCards;
    public SideLeaderReply(HashMap<LeaderCard, Boolean> leaderCards, ArrayList<LeaderCard> allLeaderCards) {
        super(MessageType.SIDE_LEADER);
        this.allLeaderCards= allLeaderCards;
        this.leaderCards = leaderCards;
    }

    public ArrayList<LeaderCard> getAllLeaderCards() {
        return allLeaderCards;
    }
    public HashMap<LeaderCard, Boolean> getLeaderCards(){
        return leaderCards;
    }
}
