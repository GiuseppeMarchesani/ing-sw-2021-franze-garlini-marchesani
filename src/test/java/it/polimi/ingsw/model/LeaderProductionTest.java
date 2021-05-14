package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Card.LeaderProduction;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class LeaderProductionTest {
    private Player player;
    private LeaderProduction leader, leader2;

    @Before
    public void setUp() {
        player = new Player();
        String leaderJson="";
        try {
            leaderJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\leader-production.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type foundHashMapType = new TypeToken<ArrayList<LeaderProduction>>(){}.getType();
        ArrayList<LeaderProduction> leaderCardDeck = new Gson().fromJson(leaderJson, foundHashMapType);

        leader = leaderCardDeck.get(0);
        leader2 = leaderCardDeck.get(1);
    }

    @Test
    public void testActivateAbility() {
        assertEquals(0, player.getDevCardSlot().getSlotLeader().size());
        leader.activateAbility(player);
        assertEquals(1, player.getDevCardSlot().getSlotLeader().size());
        assertTrue(player. getDevCardSlot().getSlotLeader().get(0).getId() == leader.getId());
        assertTrue(player. getDevCardSlot().getSlotLeader().get(0).getProductionCost().get(leader.getResourceAbility())==1);

        leader2.activateAbility(player);
        assertEquals(2, player.getDevCardSlot().getSlotLeader().size());
        assertTrue(player. getDevCardSlot().getSlotLeader().get(1).getId() == leader2.getId());
        assertTrue(player. getDevCardSlot().getSlotLeader().get(1).getProductionCost().get(leader2.getResourceAbility())==1);
    }

}