package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.LeaderCard;

public class PlayLeaderMsg extends GeneralMessage{
    private LeaderCard leaderCard;
    public PlayLeaderMsg(int playerID, LeaderCard leaderCard) {
        super(playerID, MessageType.PLAYLEADER);
        this.leaderCard = leaderCard;
    }

    public LeaderCard getLeaderCard(){
        return leaderCard;
    }
}
