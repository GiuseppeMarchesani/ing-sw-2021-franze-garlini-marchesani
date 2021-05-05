package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
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
        ArrayList<ResourceType> totalMarbles = null;


        try {
            marbleJson =(String) new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\marbles.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type foundHashMapType = new TypeToken<ArrayList<ResourceType>>(){}.getType();
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
        ResourceType testCorner = testMarket.getCornerMarble();
        test = testMarket.pickResources('c', 2);

        ArrayList<ResourceType> lKey = new ArrayList<>();
        for (ResourceType key : test.keySet()) {
            lKey.add(key);
        }
        assertTrue(lKey.contains(testMarketTray[2][0]));
        assertTrue(lKey.contains(testMarketTray[2][1]));
        assertTrue(lKey.contains(testMarketTray[2][2]));

        ResourceType[][] testMarketTrayPost = testMarket.getMarketTray();
        ResourceType testCornerPost = testMarket.getCornerMarble();
        assertTrue(testMarketTray[2][1].getVal() == testMarketTrayPost[2][0].getVal());
        assertTrue(testCornerPost.getVal() == testMarketTray[2][0].getVal());
        assertTrue(testMarketTray[2][2].getVal() == testMarketTrayPost[2][1].getVal());
        assertTrue(testMarketTrayPost[2][2].getVal() == testCorner.getVal());

    }



}








