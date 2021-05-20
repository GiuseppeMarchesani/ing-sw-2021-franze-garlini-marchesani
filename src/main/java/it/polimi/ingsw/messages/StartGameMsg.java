package it.polimi.ingsw.messages;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.controller.Turn;

import java.io.IOException;
import java.util.ArrayList;

public class StartGameMsg extends GeneralMessage{
    private LeaderCard leaderCard;
    private ResourceType res;
    public StartGameMsg(String username, MessageType messageType, String gameID,
                        LeaderCard leaderCard, ResourceType res) {
        super(username, messageType, gameID);
        this.leaderCard= leaderCard;
        this.res=res;
    }

    public LeaderCard getLeader() {
        return leaderCard;
    }

    public ResourceType getRes() {
        return res;
    }

    /*
    public void processMessage(ClientHandler clientHandler) throws IOException{
        GameController gameController = clientHandler.getGameHandler();
        gameController.startGame();
        ArrayList<LeaderCard> cards= new ArrayList<LeaderCard>();
        cards=turn.drawLeaders();
        DrawLeaderMsg answerMsg= new DrawLeaderMsg(this, cards);
        clientHandler.sendAnswerMessage(answerMsg);
    };

     */

}
