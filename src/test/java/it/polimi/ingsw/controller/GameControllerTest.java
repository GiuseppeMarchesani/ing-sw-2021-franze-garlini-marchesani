package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Card.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.Color;
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

        for(int i=0; i<3;i++){
            gameControllerMulti.newPlayer(""+i , "gameId", multiViews.get(i));
            assertEquals(i+1, gameControllerMulti.getAllVirtualView().size());
            if(i==0){
                gameControllerMulti.getMessage(new PlayersNumberRequest("0",4));
            }
        }
        gameControllerMulti.newPlayer("ricco" , "gameId", excessView);
        gameControllerMulti.newPlayer("4" , "gameId", excessView);
        assertEquals(4, gameControllerMulti.getAllVirtualView().size());


        assertTrue( gameControllerMulti.isGameStarted());
        assertEquals(GameState.DRAWLEADER, gameControllerMulti.getGameState());
        for(int i=0; i<3;i++) {
            ArrayList<LeaderCard> hand=new ArrayList<>();
            hand.add(leaderCardDeck.get(2*i));
            hand.add(leaderCardDeck.get(2*i+1));
            gameControllerMulti.getMessage(new StartingLeadersRequestMsg(""+i, hand));
        }
        ArrayList<LeaderCard> hand=new ArrayList<>();
        hand.add(leaderCardDeck.get(6));
        hand.add(leaderCardDeck.get(12));
        gameControllerMulti.getMessage(new StartingLeadersRequestMsg("ricco", hand));
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
        gameControllerMulti.getMessage(new ResourceToWarehouseRequestMsg("ricco",depotToResource,depotToQuantity, new ArrayList<Integer>(),0));
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
        assertEquals(5, gameControllerMulti.getGameSession().getPlayer("ricco").getFaithSpace());
        gameControllerMulti.getMessage(new ActionRequest("0", MessageType.END_TURN));
        assertFalse(gameControllerMulti.isMainActionDone());
        assertEquals("1", gameControllerMulti.getActivePlayer());
       gameControllerMulti.disconnect("1");

        assertEquals("2", gameControllerMulti.getActivePlayer());

        gameControllerMulti.getMessage(new ActionRequest("2", MessageType.MAIN_CARD));

        gameControllerMulti.getMessage(new BuyDevCardRequest("2", 2, Color.PURPLE));
        assertFalse(gameControllerMulti.isMainActionDone());
        gameControllerMulti.getMessage(new BuyDevCardRequest("1", 2, Color.PURPLE));
        assertFalse(gameControllerMulti.isMainActionDone());
        ArrayList<DevCard> any=new ArrayList<>();
        any.add(new DevCard());
        gameControllerMulti.getMessage(new AskProductionRequest("2",any));
        assertFalse(gameControllerMulti.isMainActionDone());
        gameControllerMulti.getMessage(new LeaderActionRequest("2",leaderCardDeck.get(4), false));
        gameControllerMulti.getMessage(new LeaderActionRequest("2",leaderCardDeck.get(5), false));
        assertEquals(faithMarket, gameControllerMulti.getGameSession().getPlayer("0").getFaithSpace());
        assertEquals(3, gameControllerMulti.getGameSession().getPlayer("1").getFaithSpace());
        assertEquals(6, gameControllerMulti.getGameSession().getPlayer("2").getFaithSpace());
        assertEquals(5, gameControllerMulti.getGameSession().getPlayer("ricco").getFaithSpace());
        assertEquals(0,gameControllerMulti.getGameSession().getPlayer("2").getLeaderCards().size());
        gameControllerMulti.getMessage(new GetMarketResRequest("0", 'r', 3, ResourceType.EMPTY));
        assertFalse(gameControllerMulti.isMainActionDone());
        gameControllerMulti.getMessage(new GetMarketResRequest("2", 'r', 2, ResourceType.EMPTY));
        gameControllerMulti.getMessage(new ActionRequest("2", MessageType.END_TURN));

        assertEquals("ricco", gameControllerMulti.getActivePlayer());
        gameControllerMulti.getMessage(new LeaderActionRequest("ricco",leaderCardDeck.get(6), true));
        gameControllerMulti.getMessage(new LeaderActionRequest("ricco",leaderCardDeck.get(12), true));
        gameControllerMulti.getMessage(new BuyDevCardRequest("ricco", 1, Color.YELLOW));
        gameControllerMulti.getMessage(new PlaceDevCardRequest("ricco", new HashMap<ResourceType, Integer>(), fullStrongbox(),1));
        assertEquals(0, gameControllerMulti.getGameSession().getPlayer("ricco").getPlayedLeaderCards().size());
        assertEquals(1, gameControllerMulti.getGameSession().getPlayer("ricco").getDevCardSlot().getAllDevCards().size());
        assertEquals(0, gameControllerMulti.getGameSession().getPlayer("ricco").getDevCardSlot().getSlotLeader().size());
        gameControllerMulti.disconnect("0");
        gameControllerMulti.disconnect("2");
        gameControllerMulti.getMessage(new ActionRequest("ricco", MessageType.END_TURN));
        assertEquals("ricco", gameControllerMulti.getActivePlayer());
        gameControllerMulti.getMessage(new BuyDevCardRequest("ricco", 2, Color.YELLOW));
        gameControllerMulti.getMessage(new PlaceDevCardRequest("ricco", new HashMap<ResourceType, Integer>(), fullStrongbox(),1));
        gameControllerMulti.getMessage(new LeaderActionRequest("ricco",leaderCardDeck.get(6), true));
        gameControllerMulti.getMessage(new LeaderActionRequest("ricco",leaderCardDeck.get(12), true));
        assertEquals(1, gameControllerMulti.getGameSession().getPlayer("ricco").getPlayedLeaderCards().size());
        assertEquals(1, gameControllerMulti.getGameSession().getPlayer("ricco").getDevCardSlot().getSlotLeader().size());
        assertEquals(3, gameControllerMulti.getGameSession().getPlayer("ricco").getDevCardSlot().getCardsAvailable().size());
        gameControllerMulti.reconnect("0", multiViews.get(0));
        gameControllerMulti.getMessage(new ActionRequest("ricco", MessageType.END_TURN));
        assertEquals("0", gameControllerMulti.getActivePlayer());
        gameControllerMulti.disconnect("0");
        assertEquals("ricco", gameControllerMulti.getActivePlayer());

        gameControllerMulti.getMessage(new AskProductionRequest("ricco", gameControllerMulti.getGameSession().getPlayer("ricco").getDevCardSlot().getCardsAvailable()));
        gameControllerMulti.getMessage(new GetProductionRequest("ricco", new HashMap<ResourceType, Integer>(), fullStrongbox()));

    }

    public void emptyHashMapWarehouse(HashMap<Integer, ResourceType> depotToResource,    HashMap<Integer, Integer> depotToQuantity){
        for(int i=0;i<3;i++){
            depotToQuantity.put(i,0);
            depotToResource.put(i, ResourceType.EMPTY);
        }
    }
    public HashMap<ResourceType, Integer> fullStrongbox(){
        HashMap<ResourceType, Integer> newStrongbox = new HashMap<>();
        newStrongbox.put(ResourceType.COIN,999);
        newStrongbox.put(ResourceType.STONE,999);
        newStrongbox.put(ResourceType.SERVANT,999);
        newStrongbox.put(ResourceType.SHIELD,999);
        return newStrongbox;

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
