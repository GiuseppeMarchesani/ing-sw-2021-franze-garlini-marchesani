package it.polimi.ingsw.model.enumeration;

public enum Color {
    GREEN(0, "GREEN"),
    PURPLE(1, "PURPLE"),
    YELLOW(2, "YELLOW"),
    BLUE(3, "BLUE");

    private final int color;
    private final String string;
     Color(int color, String string){
        this.color=color;
            this.string=string;
    }

    public int getVal(){
        return color;
    }
    @Override
    public String toString(){ return string;}
}