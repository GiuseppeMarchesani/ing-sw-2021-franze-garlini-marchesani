package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.LeaderCard;

public class ChoseLeadersMsg extends GeneralMessage{
    private char disOrPlay;
    private LeaderCard leaderCard;
    public ChoseLeadersMsg(String username, MessageType messageType, String gameID,LeaderCard leaderCard, char disOrPlay) {
        super(username, messageType, gameID);
        this.leaderCard=leaderCard;
        this.disOrPlay=disOrPlay;
    }

    public char getDisOrPlay() {
        return disOrPlay;
    }

    public LeaderCard getLeaderCard() {
        return leaderCard;
    }
}
