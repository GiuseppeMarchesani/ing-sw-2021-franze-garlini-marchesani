package it.polimi.ingsw.model.Card;

import it.polimi.ingsw.model.enumeration.Color;

import java.io.Serializable;

public class DevCardType implements Serializable {
    private Color color;
    private int level;

    /**
     *
     * Default constructor
     * @param color color associated with the card
     * @param level level associated with the card
     */
    public DevCardType(Color color, int level){
        checkLevelIsOk(level);
        this.color = color;
        this.level = level;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public int getLevel(){
        return level;
    }

    public void setLevel(int level){
        checkLevelIsOk(level);
        this.level = level;
    }

    /**
     * Checks that the level is between 1 and 3 otherwise it throws an exception
     * @param level
     */
    private void checkLevelIsOk(int level){
        if (level<0 || level>3)  throw new ArithmeticException("An incorrect level was passed as input! Make sure level is between 0 and 3.");
    }
}
