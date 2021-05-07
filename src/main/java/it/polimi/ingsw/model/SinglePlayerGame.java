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
     */
    public SinglePlayerGame() {
        super();
        blackCross = new BlackCross();
        tokenBag = new TokenBag(generateTokenBag());
    }

    /**
     * This method activates an ActionToken each turn.
     * @return An int representing the endgame code if it's > 0 or -1 if the endgame mustn't be called yet.
     */
    public int turnAction() {
        ActionToken actionToken = tokenBag.drawToken();
        return actionToken.doOperation(this);
    }

    public Boolean updateFaithTrack(int position) {

        //Checking if the player is in a pope space
        FaithTrack faithTrack = getFaithTrack();
        int whichFaithZone = faithTrack.isOnPopeSpace(position);
        if(whichFaithZone >= 0) {
            int vp = faithTrack.getFaithZones().get(whichFaithZone).getFaithZoneVP();

            //Delivering faith zone VP
            Player player = getPlayersList().get(0);
            if(faithTrack.isInFaithZone(player.getFaithSpace(), whichFaithZone)) {
                player.increaseVP(faithTrack.getFaithZones().get(whichFaithZone).getFaithZoneVP());
            }

            //If it's the last pope space
            if(whichFaithZone == faithTrack.getFaithZones().indexOf(faithTrack.getFaithZones().get(faithTrack.getFaithZones().size()-1))) {
                //Player gain extra VP according to their final position
                player.increaseVP(faithTrack.getAssociatedVP(player.getFaithSpace()));
                return true;
            }
        }
        return false;
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

        return tokens;
    }

    public BlackCross getBlackCross() {
        return blackCross;
    }

    public TokenBag getTokenBag() {
        return tokenBag;
    }
}
