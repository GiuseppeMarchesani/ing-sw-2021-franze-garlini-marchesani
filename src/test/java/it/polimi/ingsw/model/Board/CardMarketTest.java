package it.polimi.ingsw.model.Board;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.enumeration.Color;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CardMarketTest {
    private CardMarket testCM;
    private ArrayList<DevCard> devCardDeck;

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

        testCM = new CardMarket(devCardDeck);
    }

    @Test
    public void pickCard() {
        assertTrue(testCM.getDevCardGrid().get(Color.GREEN.getVal()).get(1).get(testCM.getDevCardGrid().get(Color.GREEN.getVal()).get(1).size()-1) == testCM.pickCard(Color.GREEN, 1));
    }

    @Test
    public void returnDevCard() {
        DevCard removed = testCM.pickCard(Color.GREEN, 1);
        assertNotEquals(testCM.getDevCardGrid().get(Color.GREEN.getVal()).get(1).get(testCM.getDevCardGrid().get(Color.GREEN.getVal()).get(1).size() - 1), removed);
        assertEquals(3, testCM.getDevCardGrid().get(Color.GREEN.getVal()).get(1).size());
        testCM.returnDevCard(removed);
        assertEquals(4, testCM.getDevCardGrid().get(Color.GREEN.getVal()).get(1).size());
    }

    @Test
    public void availableCards() {
        assertTrue(testCM.availableCards().size()==12);
        assertTrue(testCM.availableCards().contains(testCM.getDevCardGrid().get(0).get(1).get(testCM.getDevCardGrid().get(0).get(1).size()-1)));
        assertTrue(testCM.availableCards().contains(testCM.getDevCardGrid().get(1).get(1).get(testCM.getDevCardGrid().get(0).get(1).size()-1)));
        testCM.discardDevCard(Color.PURPLE);
        assertTrue(testCM.availableCards().size()==12);
    }

    @Test
    public void remainingCards() {
        for(int i=0; i<testCM.remainingCards().size(); i++) {
            assertEquals(4, (int) testCM.remainingCards().get(i));
        }

        testCM.discardDevCard(Color.PURPLE);
        assertTrue(testCM.remainingCards().contains(2));
        assertFalse(testCM.remainingCards().contains(0));
        testCM.discardDevCard(Color.PURPLE);
        testCM.discardDevCard(Color.PURPLE);
        testCM.discardDevCard(Color.PURPLE);
        testCM.discardDevCard(Color.PURPLE);
        testCM.discardDevCard(Color.PURPLE);
        assertTrue(testCM.remainingCards().contains(0));
        assertTrue(testCM.noCardsOfAColor());
    }

    @Test
    public void discardDevCard() {
        testCM.discardDevCard(Color.PURPLE);
        assertTrue(testCM.remainingCards().contains(2));
        assertFalse(testCM.remainingCards().contains(0));
        testCM.discardDevCard(Color.GREEN);
        assertEquals(10, testCM.remainingCardsOfColor(Color.GREEN));
    }

    @Test
    public void remainingCardsOfColor() {
        assertEquals(12, testCM.remainingCardsOfColor(Color.GREEN));
        assertEquals(12, testCM.remainingCardsOfColor(Color.PURPLE));
        assertEquals(12, testCM.remainingCardsOfColor(Color.YELLOW));
        assertEquals(12, testCM.remainingCardsOfColor(Color.BLUE));
        testCM.discardDevCard(Color.PURPLE);
        assertEquals(10, testCM.remainingCardsOfColor(Color.PURPLE));
        testCM.discardDevCard(Color.PURPLE);
        assertEquals(8, testCM.remainingCardsOfColor(Color.PURPLE));
        testCM.discardDevCard(Color.YELLOW);
        assertEquals(10, testCM.remainingCardsOfColor(Color.YELLOW));
    }

    @Test
    public void noCardsOfAColor() {
        assertFalse(testCM.noCardsOfAColor());
        testCM.discardDevCard(Color.PURPLE);
        testCM.discardDevCard(Color.PURPLE);
        testCM.discardDevCard(Color.PURPLE);
        testCM.discardDevCard(Color.PURPLE);
        testCM.discardDevCard(Color.PURPLE);
        testCM.discardDevCard(Color.PURPLE);
        assertTrue(testCM.noCardsOfAColor());
    }
}