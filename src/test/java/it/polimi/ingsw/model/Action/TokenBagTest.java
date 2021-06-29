package it.polimi.ingsw.model.Action;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Game;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TokenBagTest {
    private TokenBag testTokenBag;


    @Before
    public void setUp() {

        ArrayList<ActionToken> tokens = new ArrayList<>();
        String actionTokenJson = "";

        //ActionDiscard generation
        try {
            InputStream is = Game.class.getResourceAsStream("/token-discard.JSON");
            StringBuilder sb = new StringBuilder();
            for (int ch; (ch = is.read()) != -1; ) {
                sb.append((char) ch);
            }
            actionTokenJson = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Type foundListType = new TypeToken<ArrayList<ActionDiscard>>(){}.getType();
        tokens = new Gson().fromJson(actionTokenJson, foundListType);


        //ActionShuffle generation
        try {
            InputStream is = Game.class.getResourceAsStream("/token-shuffle.JSON");
            StringBuilder sb = new StringBuilder();
            for (int ch; (ch = is.read()) != -1; ) {
                sb.append((char) ch);
            }
            actionTokenJson = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        foundListType = new TypeToken<ActionShuffle>(){}.getType();
        tokens.add(new Gson().fromJson(actionTokenJson, foundListType));


        //ActionCross generation
        try {
            InputStream is = Game.class.getResourceAsStream("/token-cross.JSON");
            StringBuilder sb = new StringBuilder();
            for (int ch; (ch = is.read()) != -1; ) {
                sb.append((char) ch);
            }
            actionTokenJson = sb.toString();} catch (IOException e) {
            e.printStackTrace();
        }
        foundListType = new TypeToken<ArrayList<ActionCross>>(){}.getType();
        tokens.addAll(new Gson().fromJson(actionTokenJson, foundListType));

        testTokenBag=new TokenBag(tokens);
    }

    @Test
    public void testDrawToken() {
        TokenBag beforeTokenBag= testTokenBag;
        ActionToken testTokenRemoved = testTokenBag.drawToken();
        assertTrue(testTokenBag.getUsedTokens().contains(testTokenRemoved));
        assertEquals(testTokenBag.getAvailableTokens().size(), 6);

        testTokenRemoved = testTokenBag.drawToken();
        assertTrue(testTokenBag.getUsedTokens().contains(testTokenRemoved));
        assertEquals(testTokenBag.getAvailableTokens().size(), 5);


    }

    @Test
    public void shuffle() {
        ActionToken testTokenRemoved=testTokenBag.drawToken();
        testTokenBag.shuffle();
        assertEquals(testTokenBag.getAvailableTokens().size(), 7);
        assertEquals(testTokenBag.getUsedTokens().size(),0);

    }

}