package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SinglePlayerGameTest {
    SinglePlayerGame game;

    @Before
    public void setUp() throws Exception {
        game = new SinglePlayerGame();
        game.addPlayer(new Player());

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
}