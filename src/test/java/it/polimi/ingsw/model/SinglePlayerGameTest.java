package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Action.ActionToken;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SinglePlayerGameTest {
    SinglePlayerGame game;

    @Before
    public void setUp() throws Exception {
        game = new SinglePlayerGame();
        game.addPlayer(new Player("x"));
    }

    @Test
    public void updateFaithTrack() {
        assertFalse(game.updateFaithTrack(5));
        assertFalse(game.updateFaithTrack(8));
        assertFalse(game.updateFaithTrack(16));
        assertFalse(game.updateFaithTrack(14));
        assertFalse(game.updateFaithTrack(8));
        assertFalse(game.updateFaithTrack(20));
        assertTrue(game.updateFaithTrack(24));
    }

    @Test
    public void testDrawToken() {
        assertEquals(7, game.getTokenBag().getAvailableTokens().size());
        assertEquals(0, game.getTokenBag().getUsedTokens().size());
        ActionToken tkn = game.drawToken();
        assertFalse((game.getTokenBag().getAvailableTokens().contains(tkn)));
        assertTrue((game.getTokenBag().getUsedTokens().contains(tkn)));
    }

    @Test
    public void testIncreaseFaith() {
        assertFalse(game.increaseFaith(2, true, "x"));
        assertTrue(game.increaseFaith(6, true, "x"));
        assertFalse(game.increaseFaith(1, true, "x"));
        assertFalse(game.increaseFaith(8, false, "x"));
        assertTrue(game.increaseFaith(8, false, "x"));
    }
}