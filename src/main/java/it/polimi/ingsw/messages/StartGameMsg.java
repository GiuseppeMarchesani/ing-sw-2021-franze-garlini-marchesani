package it.polimi.ingsw.messages;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.controller.TurnController;

import java.io.IOException;
import java.util.ArrayList;

public class StartGameMsg extends GeneralMessage{

    public StartGameMsg(String username, MessageType messageType) {
        super(username, messageType);
    }

    public void processMessage(ClientHandler clientHandler) throws IOException{
        TurnController turnController =clientHandler.getTurnHandler();
        turnController.startGame();
        ArrayList<LeaderCard> cards= new ArrayList<LeaderCard>();
        cards= turnController.drawLeaders();
        // DrawLeaderMsg answerMsg= new DrawLeaderMsg(this, cards);
        // clientHandler.sendAnswerMessage(answerMsg);
    };

}
