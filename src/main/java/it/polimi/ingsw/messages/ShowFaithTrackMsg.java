package it.polimi.ingsw.messages;

import java.util.HashMap;

public class ShowFaithTrackMsg extends ServerMessage {
    private boolean zoneActivated;
    private int whichZone;
    public ShowFaithTrackMsg( boolean zoneActivated, int whichZone) {
        super(MessageType.SHOW_FAITH_TRACK);
        this.zoneActivated=zoneActivated;
        this.whichZone=whichZone;
    }


    public int getWhichZone() {
        return whichZone;
    }

    public boolean isZoneActivated() {
        return zoneActivated;
    }
}
