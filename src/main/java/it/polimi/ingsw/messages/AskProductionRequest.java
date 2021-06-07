package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.DevCard;

import java.util.ArrayList;

public class AskProductionRequest extends ClientMessage {
    private ArrayList<DevCard> chosen;
    public AskProductionRequest(String username, ArrayList<DevCard> chosen) {
        super(username, MessageType.CHECK_PRODUCTION);
        this.chosen=chosen;
    }

    public ArrayList<DevCard> getChosen() {
        return chosen;
    }
}
