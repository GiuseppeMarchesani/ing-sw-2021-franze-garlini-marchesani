package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BlackCrossTest {
    private BlackCross blackCross = new BlackCross();

    @Test
    public void testIncreaseBlackCross() {
        assertEquals(blackCross.increaseBlackCross(2),2);
        assertEquals(blackCross.increaseBlackCross(1),3);
        assertEquals(blackCross.increaseBlackCross(4),7);
        assertEquals(blackCross.increaseBlackCross(19), 24);
    }
}