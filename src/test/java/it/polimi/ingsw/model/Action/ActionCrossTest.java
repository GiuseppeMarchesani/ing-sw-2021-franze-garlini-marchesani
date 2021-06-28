package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActionCrossTest {
    ActionCross testActionCross;
    SinglePlayerGame testGame;

    @Before
    public void setUp() {
        testActionCross = new ActionCross(2, ActionTokenType.FAITH);
        testGame = new SinglePlayerGame();
    }

    @Test
    public void doOperation() {
        setUp();

        testGame.getBlackCross().increaseBlackCross(10);
        assertEquals(10, testGame.getBlackCross().getFaithSpace());

        testActionCross.doOperation(testGame);
        assertEquals(12, testGame.getBlackCross().getFaithSpace());

        testActionCross.doOperation(testGame);
        assertEquals(14, testGame.getBlackCross().getFaithSpace());

        testGame.getBlackCross().increaseBlackCross(10);
        assertEquals(24, testGame.getBlackCross().getFaithSpace());

        testActionCross.doOperation(testGame);
        assertEquals(24, testGame.getBlackCross().getFaithSpace());
    }

}