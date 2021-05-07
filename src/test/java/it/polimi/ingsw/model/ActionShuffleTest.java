package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;
import org.testng.Assert;

import static org.junit.Assert.*;

public class ActionShuffleTest {
    private ActionShuffle testActionShuffle;
    private SinglePlayerGame testGame;
    @Before
    public void setUp() throws Exception {
        testActionShuffle=new ActionShuffle(1);
        testGame=new SinglePlayerGame();
    }

    @Test
    public void doOperation() {
        testGame.getBlackCross().increaseBlackCross(3);

        Assert.assertEquals(testActionShuffle.doOperation(testGame), -1);

        testGame.getBlackCross().increaseBlackCross(20);

        Assert.assertEquals(testActionShuffle.doOperation(testGame), 1);
    }
}