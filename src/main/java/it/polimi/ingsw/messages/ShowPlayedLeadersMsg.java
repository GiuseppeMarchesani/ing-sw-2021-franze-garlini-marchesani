package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.LeaderCard;

import java.util.ArrayList;

public class ShowPlayedLeadersMsg extends ServerMessage {
    private ArrayList<LeaderCard> playedLeaderCards;
    private String activePlayer;
    public ShowPlayedLeadersMsg(ArrayList<LeaderCard> playedLeaderCards, String activePlayer) {
        super(MessageType.SHOW_PLAYED_LEADERS);
        this.activePlayer=activePlayer;
        this.playedLeaderCards=playedLeaderCards;
    }

    public ArrayList<LeaderCard> getPlayedLeaderCards() {
        return playedLeaderCards;
    }

    public String getActivePlayer() {
        return activePlayer;
    }
}
