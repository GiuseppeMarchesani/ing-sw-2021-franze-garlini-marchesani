package it.polimi.ingsw.model.Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * This Class represents the Faith Track
 */
public class FaithTrack {
    private List<FaithZone> faithZones = new ArrayList<>();
    private LinkedHashMap<Integer, Integer> VPspaces = new LinkedHashMap<>();

     /**
     * Default constructor
     * @param faithZones the faith zones present in the faith track
     * @param VPspaces the victory points of the pope spaces
     */
    public FaithTrack(ArrayList<FaithZone> faithZones, LinkedHashMap<Integer, Integer> VPspaces) {
        this.faithZones = faithZones;
        this.VPspaces = VPspaces;

    }

    /**
     * Check if the player is on a final position.
     * @param position position of the player
     * @return return true if the player is on otherwise return false
     */
    public Boolean isOnFinalPosition(int position) {
        return (position >= (faithZones.get(faithZones.size()-1).getEnd()));
    }

    /**
     * Check if the player if in a faith zone
     * @param position position of the player to check
     * @param whichFaithZone TODO
     * @return returns true if the player is in a faith zone otherwise returns false
     */
    public Boolean isInFaithZone(int position, int whichFaithZone) {
        return (position >= faithZones.get(whichFaithZone).getStart() && position <= faithZones.get(whichFaithZone).getEnd());
    }

    /**
     * Check if the player is on a Pope space
     * @param position position of the player to check
     * @return return true if the player is on otherwise returns false
     */
    public int isOnPopeSpace(int position) {
        for(FaithZone faithZone: faithZones) {
            if(position>=faithZone.getEnd() && !faithZone.getActivated()) {
                int whichFaithZone = faithZones.indexOf(faithZone);
                faithZone.setActivated();
                return whichFaithZone;
            }
        }
        return -1;
    }

    public int getAssociatedVP(int playerPosition) {
        int VPForPosition = 0;
        playerPosition=playerPosition-playerPosition%3;
        return VPspaces.get(playerPosition);
    }

    /**
     * Gets the next faith zones
     * @return returns the faith zones
     */
    public FaithZone getNextFaithZone(){
        for(FaithZone fz: faithZones){
            if (!fz.getActivated()){
                return fz;
            }
        }
        return new FaithZone(100,100,0,false);
    }

    public List<FaithZone> getFaithZones() {
        return faithZones;
    }

    public HashMap<Integer, Integer> getVPspaces() {
        return VPspaces;
    }

    public void setFaithZones(List<FaithZone> faithZones) {
        this.faithZones = faithZones;
    }

    public void setVPspaces(LinkedHashMap<Integer, Integer> VPspaces) {
        this.VPspaces = VPspaces;
    }

    /**
     * Gets the next index of the faith zones
     * @return returns the numbers of the faith zones
     */
    public int indexOfNextFaithZone(){
       return getFaithZones().indexOf( getNextFaithZone() );
    }

}

