package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.enumeration.ResourceType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;
public class PlayerTest {
    Player player;
    Game testGame;


    @Before
    public void setUp() throws Exception {
        player= new Player();

    }
    @After
    public void tearDown() {
        player= null;
    }


    @Test
    public void storeResources() {
        HashMap<ResourceType, Integer> testResources = new HashMap<>();
        HashMap<ResourceType, Integer> testStrongbox = player.getStrongbox();

        testResources.put(ResourceType.SHIELD, 1);
        testResources.put(ResourceType.COIN, 2);
        testResources.put(ResourceType.STONE, 1);
        player.storeResources(testResources);
        player.storeResources(testResources);
        player.storeResources(testResources);

        for(ResourceType res: testResources.keySet()){
                int n = testResources.get(res);
                int x = player.getStrongbox().get(res);
                assertEquals(n+n+n , x );
            }

    }

    @Test
    public void placeResources() throws InvalidParameterException, IndexOutOfBoundsException {
        Warehouse testWarehouse = new Warehouse();
        int rest = 0;
        rest = player.placeResources(ResourceType.SHIELD, 3, 2);
        assertEquals(2, rest);

        rest = player.placeResources(ResourceType.SERVANT, 2, 0);
        assertEquals(0, rest);
        Assertions.assertThrows(InvalidParameterException.class, ()->{
            player.placeResources(ResourceType.FAITH, 1,1);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class, ()->{
            player.placeResources(ResourceType.COIN, 1,0);
        });

    }


    @Test
    public void testPlaceDevCard() throws InvalidParameterException {
        String devCardListJson ="";
        ArrayList<DevCard> devCardDeck = null;

        try {
            devCardListJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\dev-cards.JSON")));

        } catch(IOException e) {
            e.printStackTrace();
        }
        Type foundListType = new TypeToken<ArrayList<DevCard>>(){}.getType();
        devCardDeck = new Gson().fromJson(devCardListJson, foundListType);


        player.placeDevCard(devCardDeck.get(5), 1);
        player.placeDevCard(devCardDeck.get(16), 1);
        player.placeDevCard(devCardDeck.get(2), 0);
        Assert.assertTrue(player.getDevCardSlot().getSlotDev().get(1).contains(devCardDeck.get(5)));
        Assert.assertTrue(player.getDevCardSlot().getSlotDev().get(1).contains(devCardDeck.get(16)));
        Assert.assertTrue(player.getDevCardSlot().getSlotDev().get(0).contains(devCardDeck.get(2)));
        ArrayList<DevCard> finalDevCardDeck = devCardDeck;
        Assertions.assertThrows(InvalidParameterException.class, ()->{
            player.placeDevCard(finalDevCardDeck.get(15), 1);
        });
        Assertions.assertThrows(InvalidParameterException.class,() ->{
            player.placeDevCard(finalDevCardDeck.get(8), 0);
        });
    }

    @Test
    public void testIncreaseFaith(){
        player.setFaithSpace(12);
        int finalSpace= player.increaseFaith(10);
        assertEquals(22, finalSpace);
    }

    @Test
    public void testGetAllResources(){
        HashMap<ResourceType,Integer> testAllResources = new HashMap<>();
        player.getStrongbox().put(ResourceType.STONE,5);
        player.getStrongbox().put(ResourceType.SERVANT,2);
        player.getWarehouse().getDepotList().get(0).setResourceType(ResourceType.COIN);
        player.getWarehouse().getDepotList().get(0).setResourceQuantity(2);
        player.getWarehouse().getDepotList().get(2).setResourceType(ResourceType.STONE);
        player.getWarehouse().getDepotList().get(2).setResourceQuantity(1);
        testAllResources=player.getAllResources();

        assertEquals(6, testAllResources.get(ResourceType.STONE));
        assertEquals(2, testAllResources.get(ResourceType.SERVANT));
        assertEquals(2, testAllResources.get(ResourceType.COIN));

    }

    @Test
    public void testGetFinalVP(){
        player.setVictoryPoint(24);
        HashMap<ResourceType,Integer> testAllResources = new HashMap<>();
        player.getStrongbox().put(ResourceType.STONE,5);
        player.getStrongbox().put(ResourceType.SERVANT,2);
        player.getWarehouse().getDepotList().get(0).setResourceType(ResourceType.COIN);
        player.getWarehouse().getDepotList().get(0).setResourceQuantity(2);
        player.getWarehouse().getDepotList().get(2).setResourceType(ResourceType.STONE);
        player.getWarehouse().getDepotList().get(2).setResourceQuantity(1);
        assertEquals(26, player.getFinalVP());
    }

}