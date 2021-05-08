package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class GameTest {
    Game testGame;
    @Before
    public void setUp() throws Exception {
        testGame=new Game();
    }

    @Test
    public void testAddPlayer() throws IOException {
        Player player1=new Player();
        Player player2=new Player();
        Player player3=new Player();
        Player player4=new Player();
        Player player5=new Player();

        testGame.addPlayer(player1);
        Assert.assertEquals(testGame.getPlayersList().size(), 1);
        testGame.addPlayer(player2);
        testGame.addPlayer(player3);
        testGame.addPlayer(player4);
        Assert.assertEquals(testGame.getPlayersList().size(), 4);
        Assertions.assertThrows(IOException.class, ()->{
            testGame.addPlayer(player5);
        });

    }

    @Test
    public void testDrawCard() {
        LeaderCard testLeaderCard = testGame.drawCard();

        Assert.assertFalse(testGame.getLeaderCards().contains(testLeaderCard));
        Assert.assertEquals(testGame.getLeaderCards().size(), 15);
    }

    @Test
    public void testPickMarketRes() {
        ResourceType[][]  testMarketTray = testGame.getMarket().getMarketTray();
        HashMap<ResourceType, Integer> testResources= testGame.pickMarketRes('r', 1);
        Assert.assertTrue(testResources.containsKey(testMarketTray[0][1]));
        Assert.assertTrue(testResources.containsKey(testMarketTray[1][1]));
        Assert.assertTrue(testResources.containsKey(testMarketTray[2][1]));
        if(testMarketTray[0][1].getVal()==testMarketTray[1][1].getVal()
        && testMarketTray[0][1].getVal()==testMarketTray[2][1].getVal()){
          Assert.assertEquals((int) testResources.get(testMarketTray[1][1]), 3);
        }
        else if(testMarketTray[0][1]==testMarketTray[1][1] || testMarketTray[0][1]==testMarketTray[2][1]){
            Assert.assertEquals((int) testResources.get(testMarketTray[0][1]), 2);
        }
        else if(testMarketTray[1][1]==testMarketTray[2][1]){
            Assert.assertEquals((int) testResources.get(testMarketTray[1][1]), 2);
            Assert.assertEquals((int) testResources.get(testMarketTray[0][1]) , 1);
        }
        else{
            Assert.assertEquals((int) testResources.get(testMarketTray[0][1]), 1);
            Assert.assertEquals((int) testResources.get(testMarketTray[1][1]), 1);
            Assert.assertEquals((int) testResources.get(testMarketTray[2][1]), 1);
        }
    }

    @Test
    public void testPickProductionRes() {
        String devCardListJson ="";
        ArrayList<DevCard> devCardDeck = null;

        try {
            devCardListJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\dev-cards.JSON")));

        } catch(IOException e) {
            e.printStackTrace();
        }
        Type foundListType = new TypeToken<ArrayList<DevCard>>(){}.getType();
        devCardDeck = new Gson().fromJson(devCardListJson, foundListType);

        DevCard testDevCard1 = devCardDeck.get(12);
        DevCard testDevCard2 = devCardDeck.get(24);

        ArrayList<DevCard> testProductionCard = new ArrayList<>();
        testProductionCard.add(testDevCard1);
        testProductionCard.add(testDevCard2);

        HashMap<ResourceType, Integer> testProductionIncome = testGame.pickProductionRes(testProductionCard);

        Assert.assertTrue(testProductionIncome.containsKey(ResourceType.COIN));
        Assert.assertEquals((int)testProductionIncome.get(ResourceType.COIN), 2);
        Assert.assertTrue(testProductionIncome.containsKey(ResourceType.FAITH));
        Assert.assertEquals((int)testProductionIncome.get(ResourceType.FAITH), 3);
        Assert.assertTrue(testProductionIncome.containsKey(ResourceType.STONE));
        Assert.assertEquals((int)testProductionIncome.get(ResourceType.STONE), 2);

    }

    @Test
    public void testPickDevCard() {
        DevCard testDevCard1 = testGame.pickDevCard(Color.YELLOW, 2);
        Assert.assertEquals(testGame.getCardMarket().getDevCardGrid().get(Color.YELLOW.getVal()).get(2).size(), 3);

        DevCard testDevCard2 = testGame.pickDevCard(Color.YELLOW, 2);
        Assert.assertEquals(testGame.getCardMarket().getDevCardGrid().get(Color.YELLOW.getVal()).get(2).size(), 2);

        DevCard testDevCard3 = testGame.pickDevCard(Color.YELLOW, 2);
        DevCard testDevCard4 = testGame.pickDevCard(Color.YELLOW, 2);
        Assert.assertEquals(testGame.getCardMarket().getDevCardGrid().get(Color.YELLOW.getVal()).get(2).size(), 0);

        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            testGame.pickDevCard(Color.YELLOW, 2);
        });

    }

    @Test
    public void testUpdateFaithTrack() {
        Assert.assertFalse(testGame.updateFaithTrack(3));
        Assert.assertFalse(testGame.updateFaithTrack(10));
        Assert.assertFalse(testGame.updateFaithTrack(17));
    }

    @Test
    public void playerIsOnPopeSpace() throws IOException {
        testGame.addPlayer(new Player());
        testGame.addPlayer(new Player());
        testGame.addPlayer(new Player());
        testGame.getPlayersList().get(2).setVictoryPoint(5);
        testGame.getPlayersList().get(0).setVictoryPoint(3);
        testGame.getPlayersList().get(1).setVictoryPoint(2);
        testGame.getPlayersList().get(0).setFaithSpace(5);
        testGame.getPlayersList().get(1).setFaithSpace(2);
        testGame.getPlayersList().get(2).setFaithSpace(8);

        testGame.updateFaithTrack(16);

        Assert.assertEquals(testGame.getPlayersList().get(2).getVictoryPoint(), 7);
        Assert.assertEquals(testGame.getPlayersList().get(0).getVictoryPoint(), 5);
        Assert.assertEquals(testGame.getPlayersList().get(1).getVictoryPoint(), 2);


    }

    @Test
    public void playerIsOnLastPopeSpace(){


    }

    @Test
    public void endGame() {
    }
}