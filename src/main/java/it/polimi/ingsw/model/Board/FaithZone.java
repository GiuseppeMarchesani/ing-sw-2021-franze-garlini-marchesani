package it.polimi.ingsw.model.Board;

/**
 * This class represents the Vatican Report section.
 */
public class FaithZone {
    private int start;
    private int end;
    private int faithZoneVP;
    private Boolean activated;


    /**
     * Default constructor.
     * @param start the starting space of the section.
     * @param end the ending space of the section.
     * @param faithZoneVP amount of Victory Points associated to the FaithZone.
     * @param activated will become true when a player pass the end of the faith zone.
     */
    public FaithZone(int start, int end, int faithZoneVP, Boolean activated) {
        this.start = start;
        this.end = end;
        this.faithZoneVP = faithZoneVP;
        this.activated = activated;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getFaithZoneVP() {
        return faithZoneVP;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated() {
        this.activated = true;
    }
}
