package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    private Game testGame;

    @Before
    public void setUp() {
        testGame = new Game();
    }

    @Test
    public void testAddPlayer() {
        assertEquals(0, testGame.getPlayersList().size());
        testGame.addPlayer(new Player("x"));
        assertEquals(1, testGame.getPlayersList().size());
        testGame.addPlayer(new Player("y"));
        assertEquals(2, testGame.getPlayersList().size());
        testGame.addPlayer(new Player("z"));
        testGame.addPlayer(new Player("w"));
        testGame.addPlayer(new Player("p"));
        assertEquals(4, testGame.getPlayersList().size());
    }

    @Test
    public void updateFaithTrack() {
        testGame.addPlayer(new Player("x"));
        testGame.addPlayer(new Player("y"));
        testGame.addPlayer(new Player("z"));
        assertFalse(testGame.increaseFaith(2, true, "x"));
        assertTrue(testGame.increaseFaith(6, true, "x"));
        assertFalse(testGame.increaseFaith(1, true, "x"));
        assertFalse(testGame.increaseFaith(8, false, "x"));
        assertTrue(testGame.increaseFaith(8, false, "x"));
    }

}