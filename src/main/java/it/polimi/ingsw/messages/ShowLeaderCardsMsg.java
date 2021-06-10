package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.LeaderCard;

import java.util.HashMap;

public class ShowLeaderCardsMsg extends ServerMessage{
    private HashMap<LeaderCard, Boolean> cards;
     public ShowLeaderCardsMsg(HashMap<LeaderCard, Boolean> cards){
         super(MessageType.SHOW_LEADER);
         this.cards=cards;

     }

    public HashMap<LeaderCard, Boolean> getCards() {
        return cards;
    }
}
