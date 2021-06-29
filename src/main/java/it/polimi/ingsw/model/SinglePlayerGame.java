package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Action.*;
import it.polimi.ingsw.model.Board.FaithTrack;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used for solo-mode and it's the intermediary between all the game's components.
 */
public class SinglePlayerGame extends Game {
    private BlackCross blackCross;
    private TokenBag tokenBag;

    /**
     * Default constructor.
     */
    public SinglePlayerGame() {
        super();
        blackCross = new BlackCross();
        tokenBag = new TokenBag(generateTokenBag());
    }

    /**
     * Updates the faith track if the player has earned faith points. If it's true delivers faith
     * zone victory points, else if it's the last pope space player gain extra VP according to their
     * final position.
     * @param position position reached by the player.
     * @return returns true if it's on the last pope space otherwise returns false.
     */
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

    /**
     * Takes the action token from the json file and generates it.
     * @return returns the tokens
     */
    private ArrayList<ActionToken> generateTokenBag() {
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

        return tokens;
    }

    @Override
    public boolean checkLoss() {
        return (getFaithTrack().isOnFinalPosition(blackCross.getFaithSpace())|| getCardMarket().noCardsOfAColor());
    }

    /**
     * Takes a token from the TokenBag.
     * @return returns the drawn token.
     */
    public ActionToken drawToken(){
        ActionToken token =tokenBag.drawToken();
        token.doOperation(this);
        return token;
    }

    /**
     * It updates the FaithTrack and checks if a player passed a FaithZone.
     * @return true if a FaithZone has been activated.
     */
    public boolean updateFaithTrack(){
      if(super.updateFaithTrack()){
            return true;
      }
      else{
          int thresholdH=getFaithTrack().getNextFaithZone().getEnd();
          if(blackCross.getFaithSpace()>=thresholdH){
              getFaithTrack().getNextFaithZone().setActivated();
              return true;
          }
          else return false;
      }

    }

    /**
     * It returns the Faith Track stats.
     * @return the FaithTrack stats.
     */
    public ArrayList<Integer> getFaith(String username){
        ArrayList<Integer> faith=  super.getFaith(username);
       faith.add( blackCross.getFaithSpace());
       return faith;
    }

    /**
     * Moves the player on the faith track.
     * @param faith steps to move forward.
     * @param activateOnYourself activated by the player.
     * @param username username of the player.
     * @return true if a FaithZone has been activated, false otherwise.
     */
    public boolean increaseFaith(int faith, boolean activateOnYourself, String username) {
        Player active = super.getPlayer(username);
        if (activateOnYourself) {
            active.increaseFaith(faith);
        } else {
            blackCross.increaseBlackCross(faith);
        }
        return updateFaithTrack();
    }

    public BlackCross getBlackCross() {
        return blackCross;
    }

    public TokenBag getTokenBag() {
        return tokenBag;
    }
}
