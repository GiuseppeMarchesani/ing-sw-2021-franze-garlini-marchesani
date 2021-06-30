package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.LeaderCard;

import java.util.HashMap;

/**
 * This message contains the data of the player's leader Cards.
 */
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
