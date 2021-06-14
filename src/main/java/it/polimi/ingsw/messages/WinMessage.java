package it.polimi.ingsw.messages;

import java.util.HashMap;

public class WinMessage extends ServerMessage {
    private HashMap<String, Integer> finalPoints;
    public WinMessage(HashMap<String, Integer> finalPoints) {
        super(MessageType.WIN);
        this.finalPoints=finalPoints;
    }

    public HashMap<String, Integer> getFinalPoints() {
        return finalPoints;
    }
}
