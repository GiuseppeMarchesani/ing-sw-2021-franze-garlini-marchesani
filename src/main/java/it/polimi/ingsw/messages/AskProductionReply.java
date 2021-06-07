package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.DevCard;

import java.util.ArrayList;

public class AskProductionReply extends ServerMessage {
    private ArrayList<DevCard> devCardList;
    public AskProductionReply(ArrayList<DevCard> devCardList) {
        super(MessageType.MAIN_PRODUCTION);
        this.devCardList=devCardList;
    }

    public ArrayList<DevCard> getDevCardList() {
        return devCardList;
    }
}
