package it.polimi.ingsw.model;

/**
 * This class represents the Lorenzo Il Magnifico marker in the solo-mode.
 */
public class BlackCross {
    private int faithSpace;

    /**
     * Default constructor.
     */
    public BlackCross() {
        faithSpace = 0;
    }

    /**
     * Increase the BlackCross position.
     * @param spaces the number of spaces the black cross is going to move forward.
     */
    public int increaseBlackCross(int spaces) {
        faithSpace = faithSpace + spaces;
        if(faithSpace>24) faithSpace=24;
        return faithSpace;
    }

    public int getFaithSpace() {
        return faithSpace;
    }
}
