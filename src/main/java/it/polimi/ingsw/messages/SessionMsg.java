package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.ClientHandler;
import it.polimi.ingsw.model.GameList;
import it.polimi.ingsw.model.Turn;

import java.io.IOException;

public class SessionMsg extends CommandMsg{
    private int gameId;



    public SessionMsg(int gameId)
    {
        this.gameId = gameId;
    }


    @Override
    public void processMessage(ClientHandler clientHandler) throws IOException
    {
        SessionAnswerMsg answerMsg;
        GameList activeGames= GameList.getInstance();
        try{
            if(activeGames.getActiveGames().containsKey(gameId)&&!(activeGames.getActiveGames().get(gameId).getGameSession().status())){
                clientHandler.setTurnHandler(activeGames.getActiveGames().get(gameId));
                answerMsg = new SessionAnswerMsg(this, "Game "+ gameId + " joined.", true);

            }
            else{
                Turn newGame= new Turn();
                clientHandler.setTurnHandler(newGame);
                activeGames.getActiveGames().put(gameId,newGame);
                clientHandler.getTurnHandler().setActivePlayer(clientHandler);
                answerMsg = new SessionAnswerMsg(this, "Game "+ gameId + " created. Type S when 2 or more players have joined.", true);

            }
            clientHandler.getTurnHandler().addPlayer(clientHandler);
        }catch (Exception e){
            answerMsg = new SessionAnswerMsg(this, "Game creation failed.", false);
        }
        clientHandler.sendAnswerMessage(answerMsg);
    }
}
