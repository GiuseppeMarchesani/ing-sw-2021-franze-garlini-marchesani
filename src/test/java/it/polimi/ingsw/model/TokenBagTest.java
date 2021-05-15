package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Action.*;
import org.junit.Before;
import org.junit.Test;
import org.testng.Assert;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TokenBagTest {
    private TokenBag testTokenBag;


    @Before
    public void setUp() throws Exception {

        ArrayList<ActionToken> tokens = new ArrayList<>();
        String actionTokenJson = "";

        //ActionDiscard generation
        try {
            actionTokenJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\token-discard.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
            //DA AGG A TUTTI
        }

        Type foundListType = new TypeToken<ArrayList<ActionDiscard>>(){}.getType();
        tokens = new Gson().fromJson(actionTokenJson, foundListType);


        //ActionShuffle generation
        try{
            actionTokenJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\token-shuffle.JSON")));
        } catch (IOException e) {
            System.out.println("Error while reading token-shuffle.JSON");
        }
        foundListType = new TypeToken<ActionShuffle>(){}.getType();
        tokens.add(new Gson().fromJson(actionTokenJson, foundListType));


        //ActionCross generation
        try {
            actionTokenJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\token-cross.JSON")));
        } catch (IOException e) {
            System.out.println("Error while reading token-cross.JSON");
        }
        foundListType = new TypeToken<ArrayList<ActionCross>>(){}.getType();
        tokens.addAll(new Gson().fromJson(actionTokenJson, foundListType));

        testTokenBag=new TokenBag(tokens);
    }

    @Test
    public void testDrawToken() {
        TokenBag beforeTokenBag= testTokenBag;
        ActionToken testTokenRemoved = testTokenBag.drawToken();
        Assert.assertTrue(testTokenBag.getUsedTokens().contains(testTokenRemoved));
        Assert.assertEquals(testTokenBag.getAvailableTokens().size(), 6);

        testTokenRemoved = testTokenBag.drawToken();
        Assert.assertTrue(testTokenBag.getUsedTokens().contains(testTokenRemoved));
        Assert.assertEquals(testTokenBag.getAvailableTokens().size(), 5);


    }

    @Test
    public void shuffle() {
        ActionToken testTokenRemoved=testTokenBag.drawToken();
        testTokenBag.shuffle();
        Assert.assertEquals(testTokenBag.getAvailableTokens().size(), 7);
        Assert.assertEquals(testTokenBag.getUsedTokens().size(),0);

    }

}