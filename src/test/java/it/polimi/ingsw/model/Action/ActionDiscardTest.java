package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;
import it.polimi.ingsw.model.enumeration.Color;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ActionDiscardTest {
    ActionDiscard testActionDiscardBlue;
    ActionDiscard testActionDiscardYellow;
    ActionDiscard testActionDiscardGreen;
    ActionDiscard testActionDiscardPurple;

    SinglePlayerGame testGame;

    @Before
    public void setUp() {
        testActionDiscardBlue = new ActionDiscard(Color.BLUE);
        testActionDiscardYellow = new ActionDiscard(Color.YELLOW);
        testActionDiscardGreen = new ActionDiscard(Color.GREEN);
        testActionDiscardPurple = new ActionDiscard(Color.PURPLE);
        testGame = new SinglePlayerGame();
    }

    @Test
    void doOperation() {
        setUp();

        ArrayList<Integer> stacks = testGame.getCardMarket().remainingCards();
        for(int i=0; i< stacks.size(); i++) {
            Assert.assertEquals(4, stacks.get(i).intValue());
        }

        testActionDiscardBlue.doOperation(testGame);
        Assert.assertTrue(testGame.getCardMarket().remainingCards().contains(4));
        Assert.assertTrue(testGame.getCardMarket().remainingCards().contains(2));

        testActionDiscardBlue.doOperation(testGame);
        Assert.assertTrue(testGame.getCardMarket().remainingCards().contains(4));
        Assert.assertTrue(testGame.getCardMarket().remainingCards().contains(0));
        Assert.assertFalse(testGame.getCardMarket().remainingCards().contains(2));

        testActionDiscardBlue.doOperation(testGame);
        Assert.assertTrue(testGame.getCardMarket().remainingCards().contains(4));
        Assert.assertTrue(testGame.getCardMarket().remainingCards().contains(0));
        Assert.assertTrue(testGame.getCardMarket().remainingCards().contains(2));

        testActionDiscardBlue.doOperation(testGame);
        Assert.assertTrue(testGame.getCardMarket().remainingCards().contains(4));
        Assert.assertTrue(testGame.getCardMarket().remainingCards().contains(0));
        Assert.assertFalse(testGame.getCardMarket().remainingCards().contains(2));

        testActionDiscardYellow.doOperation(testGame);
        Assert.assertTrue(testGame.getCardMarket().remainingCards().contains(4));
        Assert.assertTrue(testGame.getCardMarket().remainingCards().contains(2));
    }

}