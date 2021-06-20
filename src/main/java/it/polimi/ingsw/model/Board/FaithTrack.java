package it.polimi.ingsw.model.Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * This class represents the FaithTrack.
 */
public class FaithTrack {
    private ArrayList<FaithZone> faithZones;
    private LinkedHashMap<Integer, Integer> VPSpaces;

    /**
     * Class constructor.
     * @param faithZones all the FaithZones on the FaithTrack.
     * @param VPSpaces all the VPSpaces on the FaithTrack.
     */
    public FaithTrack(ArrayList<FaithZone> faithZones, LinkedHashMap<Integer, Integer> VPSpaces) {
        this.faithZones = faithZones;
        this.VPSpaces = VPSpaces;
    }

    /**
     * Returns true if the given int is equivalent to the final position or bigger.
     * @param position the given position of a player.
     * @return true if the given position is equivalent (or bigger) than the final space, false otherwise.
     */
    public boolean isOnFinalPosition(int position) {
        return (position >= (faithZones.get(faithZones.size()-1).getEnd()));
    }

    /**
     * Returns true if a player is on a given FaithZone.
     * @param position the given position.
     * @param whichFaithZone the FaithZone to check.
     * @return true if the position is between the FaithZone beginning and end the FaithZone end.
     */
    public boolean isInFaithZone(int position, int whichFaithZone) {
        return (position >= faithZones.get(whichFaithZone).getStart() && position <= faithZones.get(whichFaithZone).getEnd());
    }

    /**
     * Checks if the given position matches a FaithTrack popeSpace.
     * @param position the given position.
     * @return returns an int representing the activated FaithZone, -1 if no FaithZone has been activated.
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

    /**
     * Returns the amount of Victory Points associated to a position.
     * @param playerPosition the given position.
     * @return the amount of Victory Points associated to the given position.
     */
    public int getAssociatedVP(int playerPosition) {
        playerPosition=playerPosition-playerPosition%3;
        return VPSpaces.get(playerPosition);
    }

    /**
     * Returns the next un-activated FaithZone.
     * @return the next un-activated FaithZone. If all the FaithZones have been activated returns a special one used for checks.
     */
    public FaithZone getNextFaithZone(){
        for(FaithZone fz: faithZones){
            if (!fz.getActivated()){
                return fz;
            }
        }
        return new FaithZone(100,100,0,false);
    }

    /**
     * Returns the index of the next FaithZone.
     * @return an int representing the id of the next FaithZone.
     */
    public int indexOfNextFaithZone(){
       return getFaithZones().indexOf( getNextFaithZone() );
    }

    public ArrayList<FaithZone> getFaithZones() {
        return faithZones;
    }
}

