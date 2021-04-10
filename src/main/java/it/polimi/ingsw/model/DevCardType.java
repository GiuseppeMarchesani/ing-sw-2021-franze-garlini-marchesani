package it.polimi.ingsw.model;

public class DevCardType{
    private Color color;
    private int level;
    public DevCardType(Color c, int x){
        checkLevelIsOk(x);
        color=c;
        level=x;
    }
    public Color getColor(){
        return color;
    }
    public void setColor(Color x){
        color=x;
    }
    public int getLevel(){
        return level;
    }
    public void setLevel(int x){
        checkLevelIsOk(x);
        level=x;
    }
    private void checkLevelIsOk(int x){
        if (x<0||x>3)  throw new ArithmeticException("An incorrect level was passed as input! Make sure level is between 0 and 3.");
    }
}
