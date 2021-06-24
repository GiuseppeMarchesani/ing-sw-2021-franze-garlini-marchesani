package it.polimi.ingsw.model.Card;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Card.*;
import it.polimi.ingsw.model.enumeration.Color;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DevCardSlotTest {
    private DevCardSlot devCardSlot = new DevCardSlot();
    private ArrayList<DevCard> devCardDeck;
    private ArrayList<LeaderCard> leaderCardDeck;

    @Before
    public void setUp() {
        //DevCard generation
        String devCardListJson ="";

        try {
            devCardListJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\dev-cards.JSON")));

        } catch(IOException e) {
            e.printStackTrace();
        }
        Type foundListType = new TypeToken<ArrayList<DevCard>>(){}.getType();
        devCardDeck = new Gson().fromJson(devCardListJson, foundListType);


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

        //LeaderMarble production
        try {
            leaderJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\leader-production.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        foundHashMapType = new TypeToken<ArrayList<LeaderProduction>>(){}.getType();
        leaderCardDeck.addAll(new Gson().fromJson(leaderJson, foundHashMapType));

        devCardSlot.getSlotDev().get(0).add(devCardDeck.get(0));    //GREEN 1
        devCardSlot.getSlotDev().get(0).add(devCardDeck.get(19));   //YELLOW 2
        devCardSlot.getSlotDev().get(1).add(devCardDeck.get(2));    //BLUE 1
        devCardSlot.getSlotDev().get(2).add(devCardDeck.get(3));    //YELLOW 1
        devCardSlot.getSlotDev().get(2).add(devCardDeck.get(20));   //GREEN 2
        devCardSlot.getSlotDev().get(2).add(devCardDeck.get(32));   //GREEN 3

    }

    @Test
    public void testGetCardQuantity() {
        assertEquals(devCardSlot.getCardQuantity(), 6);
    }

    @Test
    public void testGetCardsAvailable() {

        ArrayList<Card> devCardOnTop = new ArrayList<>();
        devCardOnTop.add(devCardDeck.get(16));
        devCardOnTop.add(devCardDeck.get(2));
        devCardOnTop.add(devCardDeck.get(36));

        assertEquals(devCardOnTop.size() + devCardSlot.getSlotLeader().size() +1, devCardSlot.getCardsAvailable().size());

        LeaderProduction leader = (LeaderProduction) leaderCardDeck.get(14);
        devCardSlot.getSlotLeader().add(new DevCard(leader.getId(), leader.getResourceAbility(), leader.getProductionIncome()));

        assertEquals(devCardOnTop.size() + devCardSlot.getSlotLeader().size() +1, devCardSlot.getCardsAvailable().size());

        LeaderProduction leader2 = (LeaderProduction) leaderCardDeck.get(15);
        devCardSlot.getSlotLeader().add(new DevCard(leader.getId(), leader.getResourceAbility(), leader.getProductionIncome()));

        assertEquals(devCardOnTop.size() + devCardSlot.getSlotLeader().size() +1, devCardSlot.getCardsAvailable().size());


    }

    @Test
    public void testGetAvailableSlots() {
        ArrayList<Integer> freeSlots = new ArrayList<>();

        assertEquals(freeSlots, devCardSlot.getAvailableSlots(1));

        freeSlots.add(1);
        assertEquals(freeSlots, devCardSlot.getAvailableSlots(2));

        freeSlots.remove(0);
        freeSlots.add(0);
        assertEquals(freeSlots, devCardSlot.getAvailableSlots(3));

        freeSlots.remove(0);
        devCardSlot.getSlotDev().get(2).remove(2);

        freeSlots.add(0);
        freeSlots.add(2);
        assertEquals(freeSlots, devCardSlot.getAvailableSlots(3));
    }

    @Test
    public void testGetAllDevCards() {
        assertEquals(6, devCardSlot.getAllDevCards().size());
        assertTrue(devCardSlot.getAllDevCards().contains(devCardDeck.get(0)));
        assertTrue(devCardSlot.getAllDevCards().contains(devCardDeck.get(19)));
        assertTrue(devCardSlot.getAllDevCards().contains(devCardDeck.get(2)));
        assertTrue(devCardSlot.getAllDevCards().contains(devCardDeck.get(3)));
        assertTrue(devCardSlot.getAllDevCards().contains(devCardDeck.get(20)));
        assertTrue(devCardSlot.getAllDevCards().contains(devCardDeck.get(32)));
    }

    @Test
    public void testGetAllDevCardsPerColor() {
        assertEquals(0, devCardSlot.getAllDevCardsPerColor(Color.PURPLE).size());
        assertEquals(3, devCardSlot.getAllDevCardsPerColor(Color.GREEN).size());
        assertEquals(2, devCardSlot.getAllDevCardsPerColor(Color.YELLOW).size());
        assertEquals(1, devCardSlot.getAllDevCardsPerColor(Color.BLUE).size());

        assertTrue(devCardSlot.getAllDevCardsPerColor(Color.GREEN).contains(devCardDeck.get(0)));
        assertTrue(devCardSlot.getAllDevCardsPerColor(Color.GREEN).contains(devCardDeck.get(32)));
        assertTrue(devCardSlot.getAllDevCardsPerColor(Color.GREEN).contains(devCardDeck.get(20)));
        assertTrue(devCardSlot.getAllDevCardsPerColor(Color.YELLOW).contains(devCardDeck.get(19)));
        assertTrue(devCardSlot.getAllDevCardsPerColor(Color.YELLOW).contains(devCardDeck.get(3)));
        assertTrue(devCardSlot.getAllDevCardsPerColor(Color.BLUE).contains(devCardDeck.get(2)));
        assertFalse(devCardSlot.getAllDevCardsPerColor(Color.YELLOW).contains(devCardDeck.get(20)));
        assertFalse(devCardSlot.getAllDevCardsPerColor(Color.BLUE).contains(devCardDeck.get(0)));
        assertFalse(devCardSlot.getAllDevCardsPerColor(Color.YELLOW).contains(devCardDeck.get(0)));
        assertFalse(devCardSlot.getAllDevCardsPerColor(Color.GREEN).contains(devCardDeck.get(19)));
    }

    @Test
    public void testNumCardsPerColor() {
        assertEquals(3, devCardSlot.numCardsPerColor(Color.GREEN));
        assertEquals(0, devCardSlot.numCardsPerColor(Color.PURPLE));
        assertEquals(2, devCardSlot.numCardsPerColor(Color.YELLOW));
    }

    @Test
    public void hasLevelTwoOfColor() {
        assertTrue(devCardSlot.hasLevelTwoOfColor(Color.GREEN));
        assertTrue(devCardSlot.hasLevelTwoOfColor(Color.YELLOW));
        assertFalse(devCardSlot.hasLevelTwoOfColor(Color.PURPLE));
        assertFalse(devCardSlot.hasLevelTwoOfColor(Color.BLUE));
    }

    @Test
    public void testGetCardPoints() {
        assertEquals(23, devCardSlot.getCardPoints());
    }
}