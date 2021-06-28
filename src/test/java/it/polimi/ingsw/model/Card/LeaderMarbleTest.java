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

public class LeaderMarbleTest {
    private LeaderMarble leader, leader2;
    private Player player;

    @Before
    public void setUp() {
        player = new Player();
        String leaderJson="";
        try {
            leaderJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\leader-marble.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type foundHashMapType = new TypeToken<ArrayList<LeaderMarble>>(){}.getType();
        ArrayList<LeaderMarble> leaderCardDeck = new Gson().fromJson(leaderJson, foundHashMapType);

        leader = leaderCardDeck.get(0);
        leader2 = leaderCardDeck.get(1);
    }

    @Test
    public void testActivateAbility() {
        leader.activateAbility(player);
        assertEquals(1, player.getMarbleConversion().size());
        assertEquals(leader.getResourceAbility(), player.getMarbleConversion().get(0));

        leader2.activateAbility(player);
        assertEquals(2, player.getMarbleConversion().size());
        assertEquals(leader.getResourceAbility(), player.getMarbleConversion().get(0));
        assertEquals(leader2.getResourceAbility(), player.getMarbleConversion().get(1));
    }

}