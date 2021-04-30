package it.polimi.ingsw.model;

public class Card {
    private int id;
    private int victoryPoints;

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
