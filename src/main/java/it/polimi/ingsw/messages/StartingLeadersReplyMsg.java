package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.LeaderCard;

import java.util.ArrayList;

public class StartingLeadersReplyMsg extends ServerMessage{

    private ArrayList<LeaderCard> leaderCard;
    public StartingLeadersReplyMsg(ArrayList<LeaderCard> leaderCard) {
        super(MessageType.STARTING_LEADERS);
        this.leaderCard=leaderCard;

    }

    public ArrayList<LeaderCard> getLeaderCard() {
        return leaderCard;
    }
}
