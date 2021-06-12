package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class ActionCrossTest {
    ActionCross testActionCross;
    SinglePlayerGame testGame;

    @Before
    public void setUp() {
        testActionCross = new ActionCross(2);
        testGame = new SinglePlayerGame();
    }

    @Test
    void doOperation() {
        setUp();

        testGame.getBlackCross().increaseBlackCross(10);
        Assert.assertEquals(10, testGame.getBlackCross().getFaithSpace());

        testActionCross.doOperation(testGame);
        Assert.assertEquals(12, testGame.getBlackCross().getFaithSpace());

        testActionCross.doOperation(testGame);
        Assert.assertEquals(14, testGame.getBlackCross().getFaithSpace());

        testGame.getBlackCross().increaseBlackCross(10);
        Assert.assertEquals(24, testGame.getBlackCross().getFaithSpace());

        testActionCross.doOperation(testGame);
        Assert.assertEquals(24, testGame.getBlackCross().getFaithSpace());

    }

}