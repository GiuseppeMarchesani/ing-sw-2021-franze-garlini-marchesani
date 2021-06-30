package it.polimi.ingsw.messages;

import java.util.HashMap;

/**
 * This message contains the all the player's victory points.
 */
public class ShowVictoryPointsMsg extends ServerMessage {
    private HashMap<String, Integer> victoryPoints;
    public ShowVictoryPointsMsg(HashMap<String, Integer> victoryPoints) {
        super(MessageType.SHOW_VICTORY_POINTS);
        this.victoryPoints=victoryPoints;
    }

    public HashMap<String, Integer> getVictoryPoints() {
        return victoryPoints;
    }
}
