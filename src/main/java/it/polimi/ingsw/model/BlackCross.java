package it.polimi.ingsw.model;

/**
 * This class represents the Lorenzo Il Magnifico marker in the solo-mode.
 */
public class BlackCross {
    private int faithSpace;

    /**
     * Class constructor.
     */
    public BlackCross() {
        faithSpace = 0;
    }

    /**
     * Increase the BlackCross position.
     * @param spaces The number of spaces the black cross is going to move forward.
     */
    public int increaseBlackCross(int spaces) {
        faithSpace = faithSpace + spaces;
        return faithSpace;
    }

    public int getFaithSpace() {
        return faithSpace;
    }
}
