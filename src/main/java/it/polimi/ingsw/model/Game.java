package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Action.ActionToken;
import it.polimi.ingsw.model.Board.CardMarket;
import it.polimi.ingsw.model.Board.FaithTrack;
import it.polimi.ingsw.model.Board.FaithZone;
import it.polimi.ingsw.model.Board.Market;
import it.polimi.ingsw.model.Card.*;
import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * This class is the intermediary between all the game's components.
 */
public class Game {
    private Market market ;
    private FaithTrack faithTrack ;
    private CardMarket cardMarket;
    private ArrayList<Player> playersList;
    private ArrayList<LeaderCard> leaderCards;



    /**
     * Game class constructor.
     */
    public Game() {
        playersList = new ArrayList<Player>();
        market = new Market(generateMarbles());
        faithTrack = new FaithTrack(generateFaithTrack(), generateVPspaces());
        cardMarket = new CardMarket(generateDevCardDeck());
        leaderCards = generateLeaderCards();
        Collections.shuffle(leaderCards);
    }

    /**
     * This method adds a player in the playersList.
     * @param player The player who has just joined.
     */
    public void addPlayer(Player player) {
        if(playersList.size()<4){
            playersList.add(player);
        }
    }


    /**
     * This method is used to draw a Leader Card from the deck.
     * @return The drawn Leader Card.
     */
    public LeaderCard drawCard(){
        LeaderCard temp = leaderCards.get(0);
        leaderCards.remove(0);
        return temp;
    }


    /**
     * Calls the method pickResources from the class Market and returns the resources.
     * @param rowOrCol 'c' stands for column, 'r' stands for row.
     * @param rowOrColNumber The number of the row/column. Starts from 0.
     * @return The HashMap representing the resources list.
     */
    public HashMap<ResourceType, Integer> pickMarketRes(char rowOrCol, int rowOrColNumber, ResourceType conversion) {
        return market.pickResources(rowOrCol,rowOrColNumber,conversion);
    }

    public void returnDevCard(DevCard card){
        cardMarket.returnDevCard(card);
    }

    /**
     * Gets the income of the Development Cards production passed as parameters.
     * @param devCardList The ArrayList of DevCards chosen by the player.
     * @return The resources in an HashMap.
     */
    public HashMap<ResourceType, Integer> pickProductionRes(ArrayList<DevCard> devCardList) {
        HashMap<ResourceType, Integer> resources = null;

        //Scanning the DevCards
        for (int i = 0; i < devCardList.size(); i++) {
            HashMap<ResourceType, Integer> productionIncome = devCardList.get(i).getProductionIncome();
            if (i == 0) {
                resources = new HashMap<ResourceType, Integer>(productionIncome);
            } else {

                //Building a new HashMap containing the sum of the income of each DevCard
                for (ResourceType res : resources.keySet()) {
                    resources.replace(res, resources.get(res) + productionIncome.get(res));
                }
            }
        }
        return resources;
    }
    public Player getPlayer(String username){
        int indexPlayer = getPlayerListByUsername().indexOf(username);
        return getPlayersList().get(indexPlayer);
    }

    /**
     * Pick the Development Card with the features passed as parameters.
     * @param color The chosen Development Card color.
     * @param level The chosen Development Card level.
     * @return The chosen Development Card.
     */
    public DevCard pickDevCard(Color color, int level) throws IndexOutOfBoundsException{
        return cardMarket.pickCard(color, level);
    }


    /**
     * Ends the game and communicate the result.
     * @return the Hash Map of user and pointt.
     */
    public HashMap<String, Integer> endGame() {
        //Turn has already let players to play the last turn.
        HashMap<String, Integer> userPoints=new HashMap<>();
        for(Player player : playersList){
            userPoints.put(player.getUsername(), player.getFinalVP()+faithTrack.getAssociatedVP(player.getFaithSpace()));
        }
        return userPoints;
    }

    /**
     * Finds the winner between those players who have the same and the highest score.
     * @return The ID of the winner.
     */


    private ArrayList<DevCard> generateDevCardDeck() {

        //DevCard generation
        String devCardListJson ="";
        ArrayList<DevCard> devCardDeck = null;

        try {
            devCardListJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\dev-cards.JSON")));

        } catch(IOException e) {
            e.printStackTrace();
        }
        Type foundListType = new TypeToken<ArrayList<DevCard>>(){}.getType();
        devCardDeck = new Gson().fromJson(devCardListJson, foundListType);
        return devCardDeck;

    }

    private ArrayList<FaithZone> generateFaithTrack() {
        //Faith Zone generation
        String faithZonesJson = "";
        ArrayList<FaithZone> faithZones = null;

        try {
            faithZonesJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\faith-track.JSON")));

        } catch (IOException e) {
            e.printStackTrace();
        }
        Type foundListType = new TypeToken<ArrayList<FaithZone>>(){}.getType();
        faithZones = new Gson().fromJson(faithZonesJson, foundListType);
        return faithZones;
    }

    private LinkedHashMap<Integer, Integer> generateVPspaces() {
        //VPspaces generation
        String VPspacesJson = "";
        LinkedHashMap<Integer, Integer> VPspaces = null;

        try {
            VPspacesJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\vp-spaces.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
            Type foundHashMapType = new TypeToken<LinkedHashMap<Integer, Integer>>(){}.getType();
            VPspaces = new Gson().fromJson(VPspacesJson, foundHashMapType);
            return VPspaces;

    }

    private ArrayList<ResourceType>  generateMarbles() {
        //Marble generation
        String marbleJson ="";
        ArrayList<ResourceType> totalMarbles = null;

        try {
            marbleJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\marbles.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type foundHashMapType = new TypeToken<ArrayList<ResourceType>>(){}.getType();
        totalMarbles = new Gson().fromJson(marbleJson, foundHashMapType);
        return totalMarbles;
    }

    private ArrayList<LeaderCard> generateLeaderCards() {
        //LeaderCard generation
        String leaderJson="";
        ArrayList<LeaderCard> leaderCardDeck = null;

        //LeaderDepot generation
        try {
            leaderJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\leader-depot.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type foundHashMapType = new TypeToken<ArrayList<LeaderDepot>>(){}.getType();
        leaderCardDeck = new Gson().fromJson(leaderJson, foundHashMapType);


        //LeaderDiscount generation
        try {
            leaderJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\leader-discount.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        foundHashMapType = new TypeToken<ArrayList<LeaderDiscount>>(){}.getType();
        leaderCardDeck.addAll(new Gson().fromJson(leaderJson, foundHashMapType));

        //LeaderMarble generation
        try {
            leaderJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\leader-marble.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        foundHashMapType = new TypeToken<ArrayList<LeaderMarble>>(){}.getType();
        leaderCardDeck.addAll(new Gson().fromJson(leaderJson, foundHashMapType));

        //LeaderProduction generation
        try {
            leaderJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\leader-production.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        foundHashMapType = new TypeToken<ArrayList<LeaderProduction>>(){}.getType();
        leaderCardDeck.addAll(new Gson().fromJson(leaderJson, foundHashMapType));

        return leaderCardDeck;
    }

    public Market getMarket() {
        return market;
    }

    public CardMarket getCardMarket() {
        return cardMarket;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public ArrayList<Player> getPlayersList() {
        return playersList;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public ArrayList<String> getPlayerListByUsername(){
        ArrayList<String> playerList = new ArrayList<>();
        for(int i=0; i<getPlayersList().size(); i++){
            playerList.add(getPlayersList().get(i).getUsername());
        }
        return playerList;
    }
    public boolean checkLoss(){
        return false;
    }
    public ActionToken drawToken(){
        return null;
    }

    public boolean updateFaithTrack(){
        ArrayList<Player> vpWinners=new ArrayList<Player>();
        int thresholdL=getFaithTrack().getNextFaithZone().getStart();
        boolean trigger=false;
        int thresholdH=getFaithTrack().getNextFaithZone().getEnd();

        for(Player player : playersList){
            if(player.getFaithSpace()>=thresholdL){
                vpWinners.add(player);
            }
            if(player.getFaithSpace()>=thresholdH){
                trigger=true;
            }

        }

        if (trigger){
            int vp=getFaithTrack().getNextFaithZone().getFaithZoneVP();
            for(Player p: vpWinners){
                p.increaseVP(vp);
            }
           getFaithTrack().getNextFaithZone().setActivated();
        }
        return trigger;
    }

    public int lastActivatedFaithZone() {
       if( faithTrack.indexOfNextFaithZone()<0){
           return 2;
       }
       else return faithTrack.indexOfNextFaithZone();
    }
    public HashMap<String, Integer> getFaithMap(){
        HashMap<String, Integer> faith=new HashMap<>();
        for(Player player: playersList){
            faith.put(player.getUsername(), player.getFaithSpace());
        }
        return faith;
    }
    public boolean increaseFaith(int faith, boolean activateOnYourself, String username) {
        Player active = getPlayer(username);
        if (activateOnYourself) {
            active.increaseFaith(faith);
        } else {
            for (Player player : playersList) {
                if (player != active) {
                    player.increaseFaith(faith);
                }
            }
        }
        return updateFaithTrack();
    }
}
