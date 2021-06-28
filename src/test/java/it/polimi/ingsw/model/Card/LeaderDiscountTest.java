package it.polimi.ingsw.model.Card;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import it.polimi.ingsw.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class LeaderDiscountTest {
    private LeaderDiscount leader, leader2;
    private Player player;

    @Before
    public void setUp() {
        player = new Player();
        String leaderJson="";
        try {
            leaderJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\leader-discount.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type foundHashMapType = new TypeToken<ArrayList<LeaderDiscount>>(){}.getType();
        ArrayList<LeaderDiscount> leaderCardDeck = new Gson().fromJson(leaderJson, foundHashMapType);

        leader = leaderCardDeck.get(0);
        leader2 = leaderCardDeck.get(1);

    }

    @Test
    public void testActivateAbility() {
        assertFalse(player.getResourceDiscount().containsKey(leader.getResourceAbility()));
        leader.activateAbility(player);
        assertTrue(player.getResourceDiscount().containsKey(leader.getResourceAbility()));
        assertEquals(1, (int) player.getResourceDiscount().get(leader.getResourceAbility()));

        leader.activateAbility(player);
        assertTrue(player.getResourceDiscount().containsKey(leader.getResourceAbility()));
        assertEquals(2, (int) player.getResourceDiscount().get(leader.getResourceAbility()));

        leader2.activateAbility(player);
        assertTrue(player.getResourceDiscount().containsKey(leader2.getResourceAbility()));
        assertEquals(1, (int) player.getResourceDiscount().get(leader2.getResourceAbility()));
    }

}