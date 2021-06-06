package it.polimi.ingsw.messages;

import java.util.HashMap;

public class ShowFaithTrackMsg extends GeneralMessage {
    private HashMap<String, Integer> playerFaith;
    private boolean zoneActivated;
    private int whichZone;
    public ShowFaithTrackMsg(HashMap<String, Integer> playerFaith, boolean zoneActivated, int whichZone) {
        super(MessageType.SHOW_FAITH_TRACK);
        this.playerFaith=playerFaith;
        this.zoneActivated=zoneActivated;
        this.whichZone=whichZone;
    }

    public HashMap<String, Integer> getPlayerFaith() {
        return playerFaith;
    }

    public int getWhichZone() {
        return whichZone;
    }

    public boolean isZoneActivated() {
        return zoneActivated;
    }
}
