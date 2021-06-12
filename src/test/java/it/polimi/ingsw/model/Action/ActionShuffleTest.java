package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ActionShuffleTest {
    private ActionShuffle testActionShuffle;
    private SinglePlayerGame testGame;

    @Before
    public void setUp() {
        testActionShuffle=new ActionShuffle(1);
        testGame=new SinglePlayerGame();
    }

    @Test
    public void doOperation() {
        setUp();
        assertEquals(0, testGame.getBlackCross().getFaithSpace());
        List<ActionToken> testTokenBag = testGame.getTokenBag().getAvailableTokens();
        assertEquals(7, testTokenBag.size());
        testActionShuffle.doOperation(testGame);
        assertEquals(1, testGame.getBlackCross().getFaithSpace());
        assertEquals(7, testTokenBag.size());
        testActionShuffle.doOperation(testGame);

    }
}