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
    public void addPlayer(Player player) throws IOException{
        if(playersList.size()<4){
            playersList.add(player);
        }
        else throw new IOException();
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
        return cardMarket.pickCard(color, level);
    }


    /**
     * This method checks if the player activates a new faith zone or if he reaches the final space.
     * @param position The player's position.
     * @return An int representing if Turn must call the endgame or not.
     */
    public Boolean updateFaithTrack(int position) {
        Boolean endGame = false;

        //Checking if the player is in a pope space
        int whichFaithZone = faithTrack.isOnPopeSpace(position);
        if(whichFaithZone >= 0) {
            int vp = faithTrack.getFaithZones().get(whichFaithZone).getFaithZoneVP();

            //Delivering faith zone VP
            for(Player p: playersList) {
                if(faithTrack.isInFaithZone(p.getFaithSpace(), whichFaithZone)) {
                    p.increaseVP(faithTrack.getFaithZones().get(whichFaithZone).getFaithZoneVP());
                }
            }

            //If it's the last pope space
            if(whichFaithZone == faithTrack.getFaithZones().indexOf(faithTrack.getFaithZones().get(faithTrack.getFaithZones().size()-1))) {
                for(Player p: playersList) {
                    //Players gain extra VP according to their final position
                    p.increaseVP(faithTrack.getAssociatedVP(p.getFaithSpace()));
                }
                endGame = true;
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
        int currNumOfResources = 0;
        HashMap<ResourceType, Integer> allResources = new HashMap<>();
        int winnerID=playersList.size()-1;

        //Checking the real winner between players who have the same and the maximum game score
        for(int i = playersList.size() - maxScoreRepetition; i<playersList.size(); i++) {

            allResources = playersList.get(i).getAllResources();
            for(ResourceType res: allResources.keySet()) {
                currNumOfResources += allResources.get(res);
            }

            if(maxNumOfResources < currNumOfResources) {
                maxNumOfResources = currNumOfResources;
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

}
