package it.polimi.ingsw.model;

public class Card {
    private int id;
    private int victoryPoints;
    public Card(int i, int v){
        id=i;
        victoryPoints=v;
    }
    public int getId(){
        return id;
    }
    public void setId(int x){
        id=x;
    }
    public int getVictoryPoints(){
        return victoryPoints;
    }
    public void setVictoryPoints(int x){
        victoryPoints=x;
    }

}
