package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PlayerTest {
    Player player;


    @Before
    public void setUp() throws Exception {
        player= new Player(0);

    }
    @After
    public void tearDown() {
        player= null;
    }

    @Test
    public void TestGetUsername(){
        Player player= new Player("Martina");
        assertEquals("Martina", player.getUsername());
    }

    @Test
    public void storeResources() {
        HashMap<ResourceType, Integer> testResources = new HashMap<>();
        HashMap<ResourceType, Integer> testStrongbox = player.getStrongbox();

        testResources.put(new ResourceType(3), 1);
        testResources.put(new ResourceType(0), 2);
        testResources.put(new ResourceType(1), 1);
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
    public void placeResources() {
    }

    @Test
    public void placeDevCard() {
    }



    @Test
    public void choose() {
    }
}