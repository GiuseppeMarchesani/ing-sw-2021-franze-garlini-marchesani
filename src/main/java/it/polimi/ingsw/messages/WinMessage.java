package it.polimi.ingsw.messages;

import java.util.LinkedHashMap;

/**
 * This message contains the sorted hashmap of all players and victory points after
 * the game ends.
 */
public class WinMessage extends ServerMessage {
    private LinkedHashMap<String, Integer> finalPoints;
    public WinMessage(LinkedHashMap<String, Integer> finalPoints) {
        super(MessageType.WIN);
        this.finalPoints=finalPoints;
    }

    public LinkedHashMap<String, Integer> getFinalPoints() {
        return finalPoints;
    }
}
