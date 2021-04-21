package it.polimi.ingsw.model;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * This Class represents the Faith Track
 */
public class FaithTrack {
    private List<FaithZone> faithZones = new ArrayList<>();
    private HashMap<Integer, Integer> VPspaces = new HashMap<Integer, Integer>();

    /**
     * Class constructor.
     */
    public FaithTrack(ArrayList<FaithZone> faithZones, HashMap<Integer, Integer> VPspaces) {
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

    public List<FaithZone> getFaithZones() {
        return faithZones;
    }

    public HashMap<Integer, Integer> getVPspaces() {
        return VPspaces;
    }

    public void setFaithZones(List<FaithZone> faithZones) {
        this.faithZones = faithZones;
    }

    public void setVPspaces(HashMap<Integer, Integer> VPspaces) {
        this.VPspaces = VPspaces;
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
}

