package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Card.*;
import it.polimi.ingsw.model.enumeration.ResourceType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player testPlayer;
    private Player testRiccoPlayer;
    private ArrayList<LeaderCard> leaderCardDeck;

    @Before
    public void setUp() {
        testPlayer = new Player();
        testRiccoPlayer = new Player("ricco");

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

        HashMap<LeaderCard, Boolean> leaderHand = new HashMap<>();

        leaderHand.put(leaderCardDeck.get(0), false);
        leaderHand.put(leaderCardDeck.get(8), false);
        testPlayer.setLeaderCards(leaderHand);
    }

    @Test
    public void testConstructor() {
        assertNull(testPlayer.getUsername());
        assertSame("ricco", testRiccoPlayer.getUsername());
        assertEquals(999, (int) testRiccoPlayer.getStrongbox().get(ResourceType.COIN));
        assertEquals(999, (int) testRiccoPlayer.getStrongbox().get(ResourceType.STONE));
        assertEquals(999, (int) testRiccoPlayer.getStrongbox().get(ResourceType.SHIELD));
        assertEquals(999, (int) testRiccoPlayer.getStrongbox().get(ResourceType.SERVANT));

    }

    @Test
    public void discardLeader() {
        assertEquals(2, testPlayer.getLeaderCardList().size());
        for(LeaderCard leader: testPlayer.getLeaderCards().keySet()) {
            assertFalse((boolean) testPlayer.getLeaderCards().get(leader));
        }
        LeaderCard discarded = testPlayer.getLeaderCardList().get(0);
        testPlayer.discardLeader(discarded.getId());
        assertEquals(1, testPlayer.getLeaderCardList().size());
        for(LeaderCard leader: testPlayer.getLeaderCards().keySet()) {
            assertTrue(leader.getId()!= discarded.getId());
        }
        discarded = testPlayer.getLeaderCardList().get(0);
        testPlayer.discardLeader(discarded.getId());
        assertEquals(0, testPlayer.getLeaderCardList().size());
    }

    @Test
    public void playLeader() {
        for(LeaderCard leader: testPlayer.getLeaderCards().keySet()) {
            assertEquals(false, (boolean) testPlayer.getLeaderCards().get(leader));
        }
        LeaderCard played = testPlayer.getLeaderCardList().get(0);
        testPlayer.playLeader(played);
        assertTrue((boolean) testPlayer.getLeaderCards().get(played));
        played = testPlayer.getLeaderCardList().get(0);
        testPlayer.playLeader(played);
        for(LeaderCard leader: testPlayer.getLeaderCards().keySet()) {
            assertEquals(true, (boolean) testPlayer.getLeaderCards().get(leader));
        }
    }

    @Test
    public void increaseVP() {
        assertEquals(0, testPlayer.getVictoryPoint());
        testPlayer.increaseVP(3);
        assertEquals(3, testPlayer.getVictoryPoint());
        testPlayer.increaseVP(2);
        assertEquals(5, testPlayer.getVictoryPoint());
        testPlayer.increaseVP(8);
        assertEquals(13, testPlayer.getVictoryPoint());
    }

    @Test
    public void increaseFaith() {
        assertEquals(0, testPlayer.getFaithSpace());
        testPlayer.increaseFaith(2);
        assertEquals(2, testPlayer.getFaithSpace());
        testPlayer.increaseFaith(2);
        assertEquals(4, testPlayer.getFaithSpace());
        testPlayer.increaseFaith(8);
        assertEquals(12, testPlayer.getFaithSpace());
    }

    @Test
    public void getAllResources() {
        testPlayer.getWarehouse().getDepotList().get(0).placeInDepot(ResourceType.COIN, 1);
        testPlayer.getWarehouse().getDepotList().get(1).placeInDepot(ResourceType.STONE, 2);
        testPlayer.getStrongbox().put(ResourceType.COIN, 3);
        testPlayer.getStrongbox().put(ResourceType.SERVANT, 1);
        testPlayer.getStrongbox().put(ResourceType.SHIELD, 4);
        HashMap<ResourceType, Integer> warehouse = testPlayer.getAllResources();
        assertEquals(4, (int) warehouse.get(ResourceType.COIN));
        assertEquals(2, (int) warehouse.get(ResourceType.STONE));
        assertEquals(1, (int) warehouse.get(ResourceType.SERVANT));
        assertEquals(4, (int) warehouse.get(ResourceType.SHIELD));

    }

    @Test
    public void getResourceVP() {
        testPlayer.getWarehouse().getDepotList().get(0).placeInDepot(ResourceType.COIN, 1);
        testPlayer.getWarehouse().getDepotList().get(1).placeInDepot(ResourceType.STONE, 2);
        testPlayer.getStrongbox().put(ResourceType.COIN, 3);
        testPlayer.getStrongbox().put(ResourceType.SERVANT, 1);
        testPlayer.getStrongbox().put(ResourceType.SHIELD, 4);
        int numResources = 0;
        for(ResourceType res: testPlayer.getAllResources().keySet()) numResources += testPlayer.getAllResources().get(res);
        assertEquals(testPlayer.getResourceVP(), (numResources / 5));
    }

    @Test
    public void checkPriceCanBePaid() {
        testPlayer.getWarehouse().getDepotList().get(0).placeInDepot(ResourceType.COIN, 1);
        testPlayer.getWarehouse().getDepotList().get(1).placeInDepot(ResourceType.STONE, 2);
        testPlayer.getStrongbox().put(ResourceType.COIN, 3);
        testPlayer.getStrongbox().put(ResourceType.SERVANT, 1);
        testPlayer.getStrongbox().put(ResourceType.SHIELD, 4);

        HashMap<ResourceType, Integer> price = new HashMap<>();
        price.put(ResourceType.COIN, 3);
        price.put(ResourceType.SHIELD, 2);
        assertTrue(testPlayer.checkPriceCanBePaid(price));
        price.put(ResourceType.COIN, 4);
        assertTrue(testPlayer.checkPriceCanBePaid(price));
        price.put(ResourceType.COIN, 5);
        price.put(ResourceType.STONE, 4);
        assertFalse(testPlayer.checkPriceCanBePaid(price));
    }

    @Test
    public void getLeaderVp() {
        int vp = 0;
        assertEquals(2, testPlayer.getLeaderCardList().size());
        assertEquals(0, testPlayer.getLeaderVp());

        vp = testPlayer.getLeaderCardList().get(0).getVP();
        vp += testPlayer.getLeaderCardList().get(1).getVP();
        for(LeaderCard leader: testPlayer.getLeaderCards().keySet()) testPlayer.playLeader(leader);
        assertEquals(8, testPlayer.getLeaderVp());
    }
}