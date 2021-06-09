package it.polimi.ingsw.model.enumeration;

public enum Color {
    GREEN(0),
    PURPLE(1),
    YELLOW(2),
    BLUE(3);

    private int color;

     Color(int color){
        this.color=color;
    }

    public int getVal(){
        return color;
    }
}