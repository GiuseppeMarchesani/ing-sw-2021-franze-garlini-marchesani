package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    private boolean ongoing;


    /**
     * Game constructor.
     */
    public Game() {
        playersList = new ArrayList<Player>();
        market = new Market(generateMarbles());
        faithTrack = new FaithTrack(generateFaithTrack(), generateVPspaces());
        cardMarket = new CardMarket(generateDevCardDeck());
        ongoing=false;
    }


    /**
     * Return if the game has started.
     * @return ongoing true if the game has already started, otherwise false.
     */
    public boolean status(){
        return ongoing;
    }


    /**
     * The start-game method which sets ongoin attribute on 'true' and return the Leader Card Deck.
     * @return An ArrayList of LeaderCard class representing the Leader Card Deck
     * @throws Exception If it's asked to start the game but the players are less than two.
     */
    public ArrayList<LeaderCard> start() throws Exception {
        if(playersList.size()>=2) {
            ongoing=true;
            return generateLeaderCards();
        } else {
            throw new Exception("You can't start a game without any players");
        }
    }


    /**
     * Calls the method pickResources from the class Market and returns the resources.
     * @param rowOrCol 'c' stands for column, 'r' stands for row.
     * @param rowOrColNumber The number of the row/column. Starts from 0.
     * @return The HashMap representing the resources list.
     */
    public HashMap<ResourceType, Integer> pickMarketRes(char rowOrCol, int rowOrColNumber) {
        return market.pickResources(rowOrCol,rowOrColNumber);
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


    /**
     * Pick the Development Card with the features passed as parameters.
     * @param color The chosen Development Card color.
     * @param level The chosen Development Card level.
     * @return The chosen Development Card.
     */
    public DevCard pickDevCard(Color color, int level) throws IndexOutOfBoundsException{
        ArrayList<ArrayList<ArrayList<DevCard>>> temp = cardMarket.getDevCardGrid();
        return temp.get(level).get(color.getVal()).get(temp.get(level).get(color.getVal()).size()-1);
    }


    /**
     * This method checks if the player activates a new faith zone or if he reaches the final space.
     * @param playerID The player's ID who must update his position.
     * @param position The player's position.
     * @return the ID of the player who calls the endgame, if the endgame is not called the method returns -1.
     */
    public int updateFaithTrack(int playerID, int position) {
        int endGame = -1;

        //Checking if the player is in a pope space
        int whichFaithZone = faithTrack.isOnPopeSpace(position);
        if(whichFaithZone >= 0) {
            int vp = faithTrack.getFaithZones().get(whichFaithZone).getFaithZoneVP();

            //Delivering faith zone VP
            for(Player p: playersList) {
                if(faithTrack.isInFaithZone(p.getFaithSpace(), whichFaithZone)) {

                }
            }

            //If it's the last pope space
            if(whichFaithZone == faithTrack.getFaithZones().indexOf(faithTrack.getFaithZones().get(faithTrack.getFaithZones().size()-1))) {
                for(Player p: playersList) {
                    //Players gain extra VP according to their final position
                    p.increaseVP(faithTrack.getAssociatedVP(p.getFaithSpace()));
                }
                //endGame(playersList.get(playerID));
                endGame = playerID;
            }
        }
        return endGame;
    }

    /**
     * Ends the game and communicate the result.
     * @return the ID of the winner.
     */

    public int endGame() {
        //Turn has already let players to play the last turn.

        int winnerID = -1;
        //Getting results as an array
        int numOfPlayers = playersList.size();
        int[] results = new int[numOfPlayers];
        for(int i=0; i<numOfPlayers; i++) {
            results[i]=playersList.get(i).getFinalVP();
        }

        //Sorting the results array, the winner is the last one
        Arrays.sort(results);

        //Checking if there are players with the same amount of VP
        int maxScore = results[numOfPlayers-1];
        int maxScoreRepetition = 1;
        for(int i=0; i<numOfPlayers-1; i++) {
            if(results[i]==maxScore) maxScoreRepetition++;
        }

        //The winner is...
        if(maxScoreRepetition==1) {
            winnerID = playersList.size()-1;
        }

        else if(maxScoreRepetition>1) {
            winnerID = whoWin(maxScoreRepetition);
        }
        //ongoing = false;
        return winnerID;
    }

    /**
     * Finds the winner between those players who have the same and the highest score.
     * @return The ID of the winner.
     */
    private int whoWin(int maxScoreRepetition) {
        int maxNumOfResources = -1;
        int winnerID=playersList.size()-1;

        //Checking the real winner between players who have the same and the maximum game score
        for(int i = playersList.size() - maxScoreRepetition; i<playersList.size(); i++) {
            if(maxNumOfResources < playersList.get(i).getResourcesQuantity()) {
                maxNumOfResources = playersList.get(i).getResourcesQuantity();
                winnerID=i;
            }
        }
        return winnerID;
    }

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

    private HashMap<Integer, Integer> generateVPspaces() {
        //VPspaces generation
        String VPspacesJson = "";
        HashMap<Integer, Integer> VPspaces = null;

        try {
            VPspacesJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\vp-spaces.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
            Type foundHashMapType = new TypeToken<HashMap<Integer, Integer>>(){}.getType();
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

        //LeaderMarble production
        try {
            leaderJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "\\src\\main\\resources\\leader-production.JSON")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        foundHashMapType = new TypeToken<ArrayList<LeaderProduction>>(){}.getType();
        leaderCardDeck.addAll(new Gson().fromJson(leaderJson, foundHashMapType));

        return leaderCardDeck;
    }

    public void addPlayer(Player player) {
        playersList.add(player);
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

}
