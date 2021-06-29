package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Card.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.ClientHandlerInterface;
import it.polimi.ingsw.network.LobbyServer;
import it.polimi.ingsw.view.VirtualView;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class GameControllerTest {
    private GameController gameControllerMulti;
    private GameController gameControllerSingle;
    private ArrayList<VirtualView> multiViews;
    private VirtualView singleView;
    private VirtualView excessView;
    private ClientHandlerInterface clientHandler;
    private ArrayList<LeaderCard> leaderCardDeck;
    @Before
    public void setUp() {
       gameControllerMulti=new GameController();
        gameControllerSingle=new GameController();
        multiViews=new ArrayList<>();
        LobbyServer lobbyServer=new LobbyServer();
        clientHandler=new ClientHandlerInterface(){
            @Override
            public void disconnect() {

            }

            @Override
            public void sendMessage(GeneralMessage message) {

            }
        };
        for(int i=0; i<4; i++){
            multiViews.add(new VirtualView(clientHandler));
        }
        singleView=new VirtualView(clientHandler);
        excessView=new VirtualView(clientHandler);
        //LeaderCard generation
        String leaderJson="";

        //LeaderDepot generation
        try {
            leaderJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\leader-depot.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type foundHashMapType = new TypeToken<ArrayList<LeaderDepot>>(){}.getType();
        leaderCardDeck = new Gson().fromJson(leaderJson, foundHashMapType);


        //LeaderDiscount generation
        try {
            leaderJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\leader-discount.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        foundHashMapType = new TypeToken<ArrayList<LeaderDiscount>>(){}.getType();
        leaderCardDeck.addAll(new Gson().fromJson(leaderJson, foundHashMapType));

        //LeaderMarble generation
        try {
            leaderJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\leader-marble.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        foundHashMapType = new TypeToken<ArrayList<LeaderMarble>>(){}.getType();
        leaderCardDeck.addAll(new Gson().fromJson(leaderJson, foundHashMapType));

        //LeaderProduction generation
        try {
            leaderJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\leader-production.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        foundHashMapType = new TypeToken<ArrayList<LeaderProduction>>(){}.getType();
        leaderCardDeck.addAll(new Gson().fromJson(leaderJson, foundHashMapType));


    }

    @Test
    public void testMultiPlayer() {
        assertFalse( gameControllerMulti.isGameStarted());

        for(int i=0; i<4;i++){
            gameControllerMulti.newPlayer(""+i , "gameId", multiViews.get(i));
            assertEquals(i+1, gameControllerMulti.getAllVirtualView().size());
            if(i==0){
                gameControllerMulti.getMessage(new PlayersNumberRequest("0",4));
            }
        }
        gameControllerMulti.newPlayer("4" , "gameId", excessView);
        assertEquals(4, gameControllerMulti.getAllVirtualView().size());


        assertTrue( gameControllerMulti.isGameStarted());
        assertEquals(GameState.DRAWLEADER, gameControllerMulti.getGameState());
        for(int i=0; i<4;i++) {
            ArrayList<LeaderCard> hand=new ArrayList<>();
            hand.add(leaderCardDeck.get(2*i));
            hand.add(leaderCardDeck.get(2*i+1));
            gameControllerMulti.getMessage(new StartingLeadersRequestMsg(""+i, hand));
        }
        assertEquals("1", gameControllerMulti.getActivePlayer());
        assertEquals(GameState.GIVERES, gameControllerMulti.getGameState());
        HashMap<Integer, ResourceType> depotToResource= new HashMap<>();
        HashMap<Integer, Integer> depotToQuantity= new HashMap<>();
        depotToResource.put(0, ResourceType.COIN);
        depotToQuantity.put(0,1);
        depotToResource.put(1, ResourceType.EMPTY);
        depotToQuantity.put(1,0);
        depotToResource.put(2, ResourceType.EMPTY);
        depotToQuantity.put(2,0);
        gameControllerMulti.getMessage(new ResourceToWarehouseRequestMsg("1",depotToResource,depotToQuantity, new ArrayList<Integer>(),0));

        gameControllerMulti.getMessage(new ResourceToWarehouseRequestMsg("2",depotToResource,depotToQuantity, new ArrayList<Integer>(),0));
        depotToQuantity.put(0,2);
        gameControllerMulti.getMessage(new ResourceToWarehouseRequestMsg("3",depotToResource,depotToQuantity, new ArrayList<Integer>(),0));
        assertEquals("0", gameControllerMulti.getActivePlayer());
        assertEquals(GameState.IN_GAME, gameControllerMulti.getGameState());
        gameControllerMulti.getMessage(new ActionRequest("0", MessageType.MAIN_MARBLE));

        int faithMarket=0;
        for(int i=0;i<3;i++){
            if(gameControllerMulti.getGameSession().getMarket().getMarketTray()[3][i]==ResourceType.FAITH){
                faithMarket=1;
                break;
            }
        }
        gameControllerMulti.getMessage(new GetMarketResRequest("0", 'c', 3, ResourceType.EMPTY));
        emptyHashMapWarehouse(depotToResource,depotToQuantity);
        assertTrue(gameControllerMulti.isMainActionDone());
        gameControllerMulti.getMessage(new ResourceToWarehouseRequestMsg("0", depotToResource, depotToQuantity, new ArrayList<Integer>(),3));
        assertEquals(faithMarket, gameControllerMulti.getGameSession().getPlayer("0").getFaithSpace());
        assertEquals(3, gameControllerMulti.getGameSession().getPlayer("1").getFaithSpace());
        assertEquals(4, gameControllerMulti.getGameSession().getPlayer("2").getFaithSpace());
        assertEquals(5, gameControllerMulti.getGameSession().getPlayer("3").getFaithSpace());

        gameControllerMulti.getMessage(new ActionRequest("0", MessageType.END_TURN));
        assertFalse(gameControllerMulti.isMainActionDone());
        gameControllerMulti.getMessage(new ActionRequest("1", MessageType.END_TURN));
        gameControllerMulti.getMessage(new ActionRequest("2", MessageType.END_TURN));
        assertEquals("3", gameControllerMulti.getActivePlayer());
        gameControllerMulti.disconnect("3");
        assertEquals("0", gameControllerMulti.getActivePlayer());

    }

    public void emptyHashMapWarehouse(HashMap<Integer, ResourceType> depotToResource,    HashMap<Integer, Integer> depotToQuantity){
        for(int i=0;i<3;i++){
            depotToQuantity.put(i,0);
            depotToResource.put(i, ResourceType.EMPTY);
        }
    }
    @Test
    public void testSinglePlayer(){
        assertFalse( gameControllerSingle.isGameStarted());
        gameControllerSingle.newPlayer("0", "gameIdS", singleView);
        assertEquals(1, gameControllerSingle.getAllVirtualView().size());
        gameControllerSingle.getMessage(new PlayersNumberRequest("0",1));
        gameControllerSingle.newPlayer("4" , "gameId", excessView);
        assertEquals(1, gameControllerSingle.getAllVirtualView().size());
        assertTrue( gameControllerSingle.isGameStarted());
        assertEquals(GameState.DRAWLEADER, gameControllerSingle.getGameState());
        ArrayList<LeaderCard> hand=new ArrayList<>();
        hand.add(leaderCardDeck.get(0));
        hand.add(leaderCardDeck.get(1));
        gameControllerSingle.getMessage(new StartingLeadersRequestMsg("0", hand));
        assertEquals("0", gameControllerSingle.getActivePlayer());
        assertEquals(GameState.IN_GAME, gameControllerSingle.getGameState());

    }


}
