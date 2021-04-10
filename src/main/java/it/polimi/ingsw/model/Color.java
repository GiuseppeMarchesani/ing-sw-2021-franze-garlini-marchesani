package it.polimi.ingsw.model;

public enum Color {
    GREEN(0),
    PURPLE(1),
    YELLOW(2),
    BLUE(3);
    private int val;
    Color(int v){
        val=v;
    }
    public int getVal(){
        return val;
    }
}
