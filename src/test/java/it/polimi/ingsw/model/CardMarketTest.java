package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CardMarketTest {
    private CardMarket cardMarket;

    @Before
    public void setUp() {
        //DevCard generation
        String devCardListJson ="";
        ArrayList<DevCard> devCardDeck = null;

        try {
            devCardListJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\dev-cards.JSON")));

        } catch(IOException e) {
            e.printStackTrace();
        }
        Type foundListType = new TypeToken<ArrayList<DevCard>>(){}.getType();
        devCardDeck = new Gson().fromJson(devCardListJson, foundListType);

        cardMarket = new CardMarket(devCardDeck);
    }


    @Test
    public void testAvailableCards() {
        ArrayList<DevCard> cards = cardMarket.availableCards();

        for(int i=cardMarket.getDevCardGrid().size()-1; i>=0; i--) {
            for(int j=cardMarket.getDevCardGrid().get(i).size()-1; j>=0; j--) {
                assertTrue(cards.contains(cardMarket.getDevCardGrid().get(i).get(j).get(3)));
                assertFalse(cards.contains(cardMarket.getDevCardGrid().get(i).get(j).get(2)));
                assertFalse(cards.contains(cardMarket.getDevCardGrid().get(i).get(j).get(1)));
                assertFalse(cards.contains(cardMarket.getDevCardGrid().get(i).get(j).get(0)));
            }
        }

        cardMarket.getDevCardGrid().get(0).get(0).remove(3);
        cardMarket.getDevCardGrid().get(1).get(2).remove(3);
        cardMarket.getDevCardGrid().get(1).get(2).remove(2);
        cardMarket.getDevCardGrid().get(1).get(2).remove(1);

        DevCard card = cardMarket.getDevCardGrid().get(1).get(2).get(0);
        cardMarket.getDevCardGrid().get(1).get(2).remove(0);
        cardMarket.getDevCardGrid().get(2).get(0).remove(3);


        cards = cardMarket.availableCards();

        assertTrue(cards.contains(cardMarket.getDevCardGrid().get(0).get(0).get(2)));
        assertFalse(cards.contains(card));
        assertTrue(cards.contains(cardMarket.getDevCardGrid().get(2).get(0).get(2)));
        assertEquals(11, cards.size());
    }

    @Test
    public void testDiscardDevCard() {
        int cardOfAColor = cardMarket.getDevCardGrid().get(0).size() * cardMarket.getDevCardGrid().get(0).get(0).size();
        for(int i=0; i < cardOfAColor-1; i++) {
            assertEquals(-1, cardMarket.discardDevCard(Color.GREEN));
        }
        assertEquals(0, cardMarket.discardDevCard(Color.GREEN));
        assertEquals(0, cardMarket.discardDevCard(Color.GREEN));
    }
}