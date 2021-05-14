package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Action.ActionCross;
import org.junit.Before;
import org.junit.Test;
import org.testng.Assert;

public class ActionCrossTest {
    ActionCross testActionCross;
    SinglePlayerGame testGame;

    @Before
    public void setUp() throws Exception {
        testActionCross = new ActionCross(2);
        testGame = new SinglePlayerGame();
    }

    @Test
    public void doOperation() {
        testGame.getBlackCross().increaseBlackCross(10);

        Assert.assertEquals(testActionCross.doOperation(testGame), -1);

        testGame.getBlackCross().increaseBlackCross(12);

        Assert.assertEquals(testActionCross.doOperation(testGame), 1);

         
    }
}