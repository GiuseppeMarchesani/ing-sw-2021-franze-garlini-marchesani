package it.polimi.ingsw.model.Action;

import it.polimi.ingsw.model.SinglePlayerGame;
import it.polimi.ingsw.model.enumeration.Color;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        testActionDiscardBlue = new ActionDiscard(Color.BLUE, ActionTokenType.DISCARD);
        testActionDiscardYellow = new ActionDiscard(Color.YELLOW, ActionTokenType.DISCARD);
        testActionDiscardGreen = new ActionDiscard(Color.GREEN, ActionTokenType.DISCARD);
        testActionDiscardPurple = new ActionDiscard(Color.PURPLE, ActionTokenType.DISCARD);
        testGame = new SinglePlayerGame();
    }

    @Test
    public void doOperation() {
        setUp();

        ArrayList<Integer> stacks = testGame.getCardMarket().remainingCards();
        for(int i=0; i< stacks.size(); i++) {
            Assert.assertEquals(4, stacks.get(i).intValue());
        }

        testActionDiscardBlue.doOperation(testGame);
        assertTrue(testGame.getCardMarket().remainingCards().contains(4));
        assertTrue(testGame.getCardMarket().remainingCards().contains(2));

        testActionDiscardBlue.doOperation(testGame);
        assertTrue(testGame.getCardMarket().remainingCards().contains(4));
        assertTrue(testGame.getCardMarket().remainingCards().contains(0));
        assertFalse(testGame.getCardMarket().remainingCards().contains(2));

        testActionDiscardBlue.doOperation(testGame);
        assertTrue(testGame.getCardMarket().remainingCards().contains(4));
        assertTrue(testGame.getCardMarket().remainingCards().contains(0));
        assertTrue(testGame.getCardMarket().remainingCards().contains(2));

        testActionDiscardBlue.doOperation(testGame);
        assertTrue(testGame.getCardMarket().remainingCards().contains(4));
        assertTrue(testGame.getCardMarket().remainingCards().contains(0));
        assertFalse(testGame.getCardMarket().remainingCards().contains(2));

        testActionDiscardYellow.doOperation(testGame);
        assertTrue(testGame.getCardMarket().remainingCards().contains(4));
        assertTrue(testGame.getCardMarket().remainingCards().contains(2));
    }

}