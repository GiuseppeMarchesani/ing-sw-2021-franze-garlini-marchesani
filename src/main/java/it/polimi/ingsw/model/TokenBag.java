package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class is the container of the Action Tokens.
 */
public class TokenBag {
    private List<ActionToken> availableTokens = new ArrayList<ActionToken>();
    private List<ActionToken> usedTokens = new ArrayList<ActionToken>();

    /**
     * Class constructor.
     */
    public TokenBag() {
        try {
            generateTokenBag();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method fills the TokenBag with the Action Tokens.
     * @throws IOException
     */

    //rimettere private
    public void generateTokenBag() throws IOException {
        List<ActionToken> bufferToken;

        //ActionDiscard generation
        String actionTokenJson = new String(Files.readAllBytes(Paths.get("ing-sw-2021-franze-garlini-marchesani/src/main/resources/token-discard.JSON")));
        Type foundListType = new TypeToken<ArrayList<ActionDiscard>>(){}.getType();
        bufferToken = new Gson().fromJson(actionTokenJson, foundListType);
        availableTokens.addAll(bufferToken);
        bufferToken.clear();


        //ActionShuffle generation
        actionTokenJson = new String(Files.readAllBytes(Paths.get("ing-sw-2021-franze-garlini-marchesani/src/main/resources/token-shuffle.JSON")));
        foundListType = new TypeToken<ArrayList<ActionShuffle>>(){}.getType();
        bufferToken = new Gson().fromJson(actionTokenJson, foundListType);
        availableTokens.addAll(bufferToken);
        bufferToken.clear();


        //ActionCross generation
        actionTokenJson = new String(Files.readAllBytes(Paths.get("ing-sw-2021-franze-garlini-marchesani/src/main/resources/token-cross.JSON")));
        foundListType = new TypeToken<ArrayList<ActionCross>>(){}.getType();
        bufferToken = new Gson().fromJson(actionTokenJson, foundListType);
        availableTokens.addAll(bufferToken);
        bufferToken.clear();

        Collections.shuffle(availableTokens);
    }

    /**
     * This method pick a random Action Token from the Token Bag.
     * @return The drawn Action Token.
     */
    public ActionToken drawToken() {
        Random random = new Random();
        int tokenIndex = random.nextInt(availableTokens.size());
        ActionToken extractedToken = availableTokens.get(tokenIndex);
        usedTokens.add(extractedToken);
        availableTokens.remove(tokenIndex);
        return extractedToken;
    }

    /**
     * This method fill the availableTokens list with the element of the usedTokens list, setting the Token Bag to its original state.
     */
    public void shuffle() {
        availableTokens.addAll(usedTokens);
        usedTokens.clear();
        Collections.shuffle(availableTokens);
    }
}
