package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.LeaderCard;

public class PlayLeaderMsg extends GeneralMessage{
    private LeaderCard leaderCard;
    public PlayLeaderMsg(String username, LeaderCard leaderCard, String gameID) {
        super(username, MessageType.PLAYLEADER, gameID);
        this.leaderCard = leaderCard;
    }

    public LeaderCard getLeaderCard(){
        return leaderCard;
    }
}
