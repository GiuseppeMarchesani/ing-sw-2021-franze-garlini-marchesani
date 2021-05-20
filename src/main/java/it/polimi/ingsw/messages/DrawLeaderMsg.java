package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.LeaderCard;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DrawLeaderMsg extends AnswerMsg{
    ArrayList<LeaderCard> leaders;
    public DrawLeaderMsg(CommandMsg parent, ArrayList<LeaderCard> leaders){
        super(parent);
        this.leaders=leaders;

    }

    @Override
    public void processMessage(ObjectOutputStream output) throws IOException {

    }
}
