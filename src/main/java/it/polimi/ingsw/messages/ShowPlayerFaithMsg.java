package it.polimi.ingsw.messages;

import java.util.ArrayList;

public class ShowPlayerFaithMsg extends ServerMessage {
    private ArrayList<Integer> faith;
    public ShowPlayerFaithMsg(ArrayList<Integer> faith) {
        super(MessageType.SHOW_PLAYER_FAITH);
        this.faith=faith;
    }

    public ArrayList<Integer> getFaith() {
        return faith;
    }
}
