package it.polimi.ingsw.model;

public class Color {
    private int color;
    public Color(int x){
        color=x;
    }
    public String toString(int res) {
        String s = null;
        switch (res){
            case 0:
                s="GREEN";
                break;
            case 1:
                s= "PURPLE";
                break;
            case 2:
                s="YELLOW";
                break;
            case 3:
                s="BLUE";
        }
        return s;
    }


    public void setColor(int res) {
        color = res;
    }
    public int getVal(){
        return color;
    }
}
