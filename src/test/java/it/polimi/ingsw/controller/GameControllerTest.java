package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.PlayersNumberRequest;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.LobbyServer;
import it.polimi.ingsw.view.VirtualView;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class GameControllerTest {
    private GameController gameControllerMulti;
    private GameController gameControllerSingle;
    private ArrayList<VirtualView> multiViews;
    private VirtualView singleView;
    private VirtualView excessView;
    @Before
    public void setUp() {
       gameControllerMulti=new GameController();
        gameControllerSingle=new GameController();
        multiViews=new ArrayList<>();
        LobbyServer lobbyServer=new LobbyServer();
        for(int i=0; i<4; i++){
            multiViews.add(new VirtualView(new ClientHandler(new Socket(),lobbyServer)));
        }
        singleView=new VirtualView(new ClientHandler(new Socket(),lobbyServer));
        excessView=new VirtualView(new ClientHandler(new Socket(),lobbyServer));
    }

    @Test
    public void testNewPlayer() {
        assertFalse( gameControllerMulti.isGameStarted());
        assertFalse( gameControllerSingle.isGameStarted());
        for(int i=0; i<4;i++){
            gameControllerMulti.newPlayer(""+i , "gameId", multiViews.get(i));
            assertEquals(i+1, gameControllerMulti.getAllVirtualView().size());
            if(i==0){
                gameControllerMulti.getMessage(new PlayersNumberRequest("0",4));
            }
        }
        gameControllerMulti.newPlayer("4" , "gameId", excessView);
        assertEquals(4, gameControllerMulti.getAllVirtualView().size());

        gameControllerSingle.newPlayer("0", "gameIdS", singleView);
        assertEquals(1, gameControllerSingle.getAllVirtualView().size());
        gameControllerSingle.getMessage(new PlayersNumberRequest("0",1));
        gameControllerMulti.newPlayer("4" , "gameId", excessView);
        assertEquals(1, gameControllerSingle.getAllVirtualView().size());

        assertTrue( gameControllerMulti.isGameStarted());
        assertTrue( gameControllerMulti.isGameStarted());
        assertEquals(GameState.DRAWLEADER, gameControllerMulti.getGameState());
        assertEquals(GameState.DRAWLEADER, gameControllerSingle.getGameState());

    }


}
