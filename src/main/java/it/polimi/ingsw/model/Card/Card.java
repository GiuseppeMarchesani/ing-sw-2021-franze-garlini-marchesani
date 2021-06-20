package it.polimi.ingsw.model.Card;

import java.io.Serializable;

/**
 * This class is used to represents the game cards.
 */
public class Card implements Serializable {
    private int id;
    private int victoryPoints;

    /**
     * Default constructor.
     * @param id id associated with the card.
     * @param victoryPoints victory points the player can receive from this card.
     */
    public Card(int id, int victoryPoints){
        this.id = id;
        this.victoryPoints = victoryPoints;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getVP(){
        return victoryPoints;
    }

    public void setVP(int victoryPoints){
        this.victoryPoints = victoryPoints;
    }
}
