package it.polimi.ingsw.messages;
import it.polimi.ingsw.model.ClientHandler;
import it.polimi.ingsw.model.GameList;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Turn;

import java.io.IOException;
import java.util.ArrayList;

public class StartGameMsg extends CommandMsg{

    public void processMessage(ClientHandler clientHandler) throws IOException{
        Turn turn=clientHandler.getTurnHandler();
        turn.startGame();
        ArrayList<LeaderCard> cards= new ArrayList<LeaderCard>();
        cards=turn.drawLeaders();
        DrawLeaderMsg answerMsg= new DrawLeaderMsg(this, cards);
        clientHandler.sendAnswerMessage(answerMsg);
    };

}
