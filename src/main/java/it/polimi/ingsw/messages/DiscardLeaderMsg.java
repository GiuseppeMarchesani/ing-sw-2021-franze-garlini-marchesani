package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.LeaderCard;

public class DiscardLeaderMsg extends GeneralMessage{

    public LeaderCard leaderCard;
    public DiscardLeaderMsg(int playerID, LeaderCard leaderCard) {
        super(playerID, MessageType.DISCARDLEADER);
        this.leaderCard=leaderCard;
    }

    public LeaderCard getLeaderCard(){
        return leaderCard;
    }
}
