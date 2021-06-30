package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.LeaderCard;

import java.util.ArrayList;
/**
 * This message contains the chosen leader cards to keep at the start.
 */
public class StartingLeadersRequestMsg extends ClientMessage{

        private ArrayList<LeaderCard> leaderCard;
        public StartingLeadersRequestMsg(String username, ArrayList<LeaderCard> leaderCard) {
            super(username,MessageType.STARTING_LEADERS);
            this.leaderCard=leaderCard;

        }

        public ArrayList<LeaderCard> getLeaderCard() {
            return leaderCard;
        }
    }


