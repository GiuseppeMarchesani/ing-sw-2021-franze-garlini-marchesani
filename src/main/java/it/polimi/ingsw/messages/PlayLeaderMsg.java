package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.LeaderCard;

public class PlayLeaderMsg extends GeneralMessage{
    private LeaderCard leaderCard;
    public PlayLeaderMsg(String username, LeaderCard leaderCard) {
        super(MessageType.PLAYLEADER);
        this.leaderCard = leaderCard;
    }

    public LeaderCard getLeaderCard(){
        return leaderCard;
    }
}
