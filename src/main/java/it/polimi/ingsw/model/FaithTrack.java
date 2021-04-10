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
     * @throws IOException
     */
    public FaithTrack() throws IOException {
        //Faith Zone generation
        String faithZonesJson = new String(Files.readAllBytes(Paths.get("ing-sw-2021-franze-garlini-marchesani/src/main/resources/faith-track.JSON")));
        Type foundListType = new TypeToken<ArrayList<FaithZone>>(){}.getType();
        faithZones = new Gson().fromJson(faithZonesJson, foundListType);

        //TODO: VPspaces generation
    }

    public Boolean isOnFinalPosition(int position) {
        return (position >= (faithZones.get(faithZones.size()-1).getEnd()));
    }

    public Boolean isInFaithZone(int position, int whichFaithZone) {
        return (position >= faithZones.get(whichFaithZone).getStart() && position <= faithZones.get(whichFaithZone).getEnd())
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
