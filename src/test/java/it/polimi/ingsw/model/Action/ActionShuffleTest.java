package it.polimi.ingsw.model.Action;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.SinglePlayerGame;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ActionShuffleTest {
    private ActionShuffle testActionShuffle;
    private SinglePlayerGame testGame;

    @Before
    public void setUp() {
        testActionShuffle = new ActionShuffle(1, ActionTokenType.FAITH);
        testGame=new SinglePlayerGame();
    }

    @Test
    public void doOperation() {
        setUp();
        assertEquals(0, testGame.getBlackCross().getFaithSpace());
        assertEquals(7, testGame.getTokenBag().getAvailableTokens().size());

        testActionShuffle.doOperation(testGame);
        assertEquals(1, testGame.getBlackCross().getFaithSpace());
        assertEquals(7, testGame.getTokenBag().getAvailableTokens().size());
        testActionShuffle.doOperation(testGame);

        testGame.getBlackCross().increaseBlackCross(22);
        assertEquals(24, testGame.getBlackCross().getFaithSpace());
        testActionShuffle.doOperation(testGame);
        assertEquals(24, testGame.getBlackCross().getFaithSpace());

    }
}