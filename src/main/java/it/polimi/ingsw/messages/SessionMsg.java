package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.ClientHandler;
import it.polimi.ingsw.model.GameList;
import it.polimi.ingsw.model.Turn;

import java.io.IOException;
import java.util.Locale;

public class SessionMsg extends CommandMsg{
    private String gameId;


    public SessionMsg(String gameId)
    {
        this.gameId=gameId.toUpperCase(Locale.ROOT);

    }


    @Override
    public void processMessage(ClientHandler clientHandler) throws IOException
    {

        SessionAnswerMsg answerMsg;
        GameList activeGames= GameList.getInstance();
                if(activeGames.getActiveGames().get(gameId)==null){
                    Turn newGame = new Turn();
                    clientHandler.setTurnHandler(newGame);
                    clientHandler.getTurnHandler().setActivePlayer(clientHandler);
                    clientHandler.getTurnHandler().addPlayer(clientHandler);
                    activeGames.getActiveGames().put(gameId,newGame);
                    answerMsg = new SessionAnswerMsg(this, SessionAnswerMsg.Result.CREATED);
                }
                else if(activeGames.getActiveGames().get(gameId).getPlayers().size()<4&&!(activeGames.getActiveGames().get(gameId).status())){
                    clientHandler.setTurnHandler(activeGames.getActiveGames().get(gameId));
                    clientHandler.getTurnHandler().addPlayer(clientHandler);
                    answerMsg = new SessionAnswerMsg(this, SessionAnswerMsg.Result.JOINED);
                }
                else{
                    answerMsg = new SessionAnswerMsg(this, SessionAnswerMsg.Result.FAILED);
                }


        clientHandler.sendAnswerMessage(answerMsg);
    }
}
