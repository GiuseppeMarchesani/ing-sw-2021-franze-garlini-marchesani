package it.polimi.ingsw.messages;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.controller.Turn;

import java.io.IOException;
import java.util.ArrayList;

public class StartGameMsg extends GeneralMessage{

    public StartGameMsg(int playerId, MessageType messageType) {
        super(playerId, messageType);
    }

    public void processMessage(ClientHandler clientHandler) throws IOException{
        Turn turn=clientHandler.getTurnHandler();
        turn.startGame();
        ArrayList<LeaderCard> cards= new ArrayList<LeaderCard>();
        cards=turn.drawLeaders();
        DrawLeaderMsg answerMsg= new DrawLeaderMsg(this, cards);
        clientHandler.sendAnswerMessage(answerMsg);
    };

}
