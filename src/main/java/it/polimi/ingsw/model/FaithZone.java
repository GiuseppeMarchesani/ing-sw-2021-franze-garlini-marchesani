package it.polimi.ingsw.model;

/**
 * This class represents the Vatican Report section.
 */
public class FaithZone {
    private int start;
    private int end;


    /**
     * Class Constructor.
     * @param start The starting space of the section.
     * @param end The ending space of the section.
     */
    public FaithZone(int start, int end) {
        this.start = start;
        this.end = end;
    }


    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
