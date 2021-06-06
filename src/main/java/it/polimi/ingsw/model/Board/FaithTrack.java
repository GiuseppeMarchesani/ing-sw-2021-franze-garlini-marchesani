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
     * Class constructor.
     */
    public FaithTrack(ArrayList<FaithZone> faithZones, LinkedHashMap<Integer, Integer> VPspaces) {
        this.faithZones = faithZones;
        this.VPspaces = VPspaces;

    }

    public Boolean isOnFinalPosition(int position) {
        return (position >= (faithZones.get(faithZones.size()-1).getEnd()));
    }

    public Boolean isInFaithZone(int position, int whichFaithZone) {
        return (position >= faithZones.get(whichFaithZone).getStart() && position <= faithZones.get(whichFaithZone).getEnd());
    }

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
        for(int position: VPspaces.keySet()) {
            if(playerPosition >= position) {
                VPForPosition = VPspaces.get(position);
            }
        }
        return VPForPosition;
    }
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


}

