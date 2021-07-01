package it.polimi.ingsw.messages;

import java.util.ArrayList;

public class ShowPlayerListMsg extends ServerMessage {
    private ArrayList<String> players;
    public ShowPlayerListMsg(ArrayList<String> players) {
        super(MessageType.ASK_PLAYER);
        this.players=players;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }
}
