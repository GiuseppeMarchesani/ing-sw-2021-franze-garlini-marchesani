package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


import static org.junit.Assert.*;

public class MarketTest {
    private Market testMarket;

    @Before
    public void setUp() throws Exception {
        String marbleJson = "";
        //Marble generation
        ArrayList<ResourceType> totalMarbles;
        try {
            System.out.println(System.getProperty("user.dir") + "\\src\\main\\resources\\marbles.JSON");
            marbleJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\marbles.JSON")));
        } catch (IOException e) {
            System.out.println("Error while reading marbles.JSON");
        }
        Type foundHashMapType = new TypeToken<ArrayList<ResourceType>>() {}.getType();
        totalMarbles = new Gson().fromJson(marbleJson, foundHashMapType);
        testMarket = new Market(totalMarbles);


    }

    @Test

    public void testGetMarketTray() {
        ResourceType[][] testMarketTray = testMarket.getMarketTray();
        for(int i=0; i<4; i++){
            for(int j=0; j<3; j++) {
                System.out.print(testMarketTray[i][j].getVal());
                System.out.print(" ");
            }
            System.out.println("");
        }

    }

    @Test
    public void testPickResources() {
        ResourceType[][] testMarketTray = testMarket.getMarketTray();
        HashMap<ResourceType, Integer> test = new HashMap<>();
        test = testMarket.pickResources('c', 2);

        ArrayList<ResourceType> lKey = new ArrayList<>();
        for (ResourceType key : test.keySet()) {
            lKey.add(key);
        }
        assertTrue(lKey.contains(testMarketTray[2][0]));
        assertTrue(lKey.contains(testMarketTray[2][1]));
        assertTrue(lKey.contains(testMarketTray[2][2]));


    }


}








