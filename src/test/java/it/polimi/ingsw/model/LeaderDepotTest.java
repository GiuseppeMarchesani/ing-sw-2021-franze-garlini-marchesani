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

public class LeaderDepotTest {
    private LeaderDepot leader1, leader2;
    private Player player;

    @Before
    public void setUp() {
        player = new Player();
        String leaderJson = "";

        //LeaderDepot generation
        try {
            leaderJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\leader-depot.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type foundHashMapType = new TypeToken<ArrayList<LeaderDepot>>(){}.getType();
        ArrayList<LeaderDepot> leaderCardDeck = new Gson().fromJson(leaderJson, foundHashMapType);

        leader1 = leaderCardDeck.get(0);
        leader2 = leaderCardDeck.get(0);
    }

    @Test
    public void testActivateAbility() {
        assertEquals(3, player.getWarehouse().getDepotList().size());
        leader1.activateAbility(player);
        assertEquals(4, player.getWarehouse().getDepotList().size());
        assertEquals(leader1.getResourceAbility(), player.getWarehouse().getDepotList().get(player.getWarehouse().getDepotList().size()-1).getResourceType());

        leader2.activateAbility(player);
        assertEquals(5, player.getWarehouse().getDepotList().size());
        assertEquals(leader2.getResourceAbility(), player.getWarehouse().getDepotList().get(player.getWarehouse().getDepotList().size()-1).getResourceType());
    }
}