package it.polimi.ingsw.model.enumeration;

public enum Color {
    GREEN(0, "Green"),
    PURPLE(1, "Purple"),
    YELLOW(2, "Yellow"),
    BLUE(3, "Blue");

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