package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for solo-mode and it's the intermediary between all the game's components.
 */
public class SinglePlayerGame extends Game {
    private BlackCross blackCross;
    private TokenBag tokenBag;

    /**
     * SinglePlayerGame class constructor.
     * @param playersList The Array containing all the players, in this case contains just the solo-mode player.
     */
    public SinglePlayerGame(ArrayList<Player> playersList) {
        //super(playersList);
        blackCross = new BlackCross();
        tokenBag = new TokenBag(generateTokenBag());
    }

    /**
     * This method activates an ActionToken each turn.
     */
    public void turnAction() {
        ActionToken actionToken = tokenBag.drawToken();

        //actionToken.doOperation();

        if(actionToken.toString() == "Action Discard") {
            getCardMarket().discardDevCard(((ActionDiscard)actionToken).getColor());
            //Check if deck in cardMarket is empty and then call endGame
        }
        else if(actionToken.toString()=="Action Shuffle") {
            blackCross.increaseBlackCross(((ActionShuffle)actionToken).getSpaces());
            tokenBag.shuffle();
            //Check if blackCross reaches the end
        }
        else if(actionToken.toString()=="Action Cross") {
            blackCross.increaseBlackCross(((ActionCross)actionToken).getSpaces());
            //Check if blackCross reaches the end
        }
    }

    /**
     * Ends the game and communicate the result.
     *
     */
    public void endGame(int endGameCode) {
            //TODO
        if(endGameCode == 0) {
            //DevCard not available
        }
        else if(endGameCode == 1) {
            //BlackCross reached the final space
        }
        else if(endGameCode == 2) {

        }
    }

    private ArrayList<ActionToken> generateTokenBag() {
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
        foundListType = new TypeToken<ArrayList<ActionShuffle>>(){}.getType();
        tokens.addAll(new Gson().fromJson(actionTokenJson, foundListType));


        //ActionCross generation
        try {
            actionTokenJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\token-cross.JSON")));
        } catch (IOException e) {
            System.out.println("Error while reading token-cross.JSON");
        }
        foundListType = new TypeToken<ArrayList<ActionCross>>(){}.getType();
        tokens.addAll(new Gson().fromJson(actionTokenJson, foundListType));

        return tokens;
    }

    public BlackCross getBlackCross() {
        return blackCross;
    }

    public TokenBag getTokenBag() {
        return tokenBag;
    }
}
