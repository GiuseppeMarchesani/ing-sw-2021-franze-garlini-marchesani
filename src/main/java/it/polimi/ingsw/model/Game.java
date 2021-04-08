package it.polimi.ingsw.model;
import java.io.IOException;
import java.util.*;

/**
 * This class is the intermediary between all the game's components.
 */
public abstract class Game {
    private Market market ;
    private FaithTrack faithTrack ;
    private CardMarket cardMarket;
    private List<Player> playersList = new ArrayList<Player>();

    /**
     * Class constructor. Will be called by Game's subclasses with "super()" method.
     * @param playersList List of the players.
     * @throws IOException
     */
    public Game(ArrayList<Player> playersList) throws IOException {
        Collections.shuffle(playersList);

        market = new Market();
        faithTrack = new FaithTrack();
        cardMarket = new CardMarket();
        this.playersList = playersList;
    }

    public void start() {
        List<LeaderCard> leadCardDeck = new ArrayList<LeaderCard>();
        //TODO: LeaderCard generation
    }

    /**
     * Calls the method pickResources from the class Market.
     * @param player The player who is going to take the resources.
     * @param rowOrCol False stands for row, true stands for column.
     * @param rowOrColNumber The number of the row/column. Starts from 0.
     */
    public void pickMarketRes(Player player, Boolean rowOrCol, int rowOrColNumber) {
        market.pickResources(player, rowOrCol, rowOrColNumber);
    }

    /**
     * Gets the income of the Development Cards production passed as parameters and calls the Player's method storeResources in order to store the resources in the Strongbox.
     * @param player The player who is going to take the resources.
     * @param devCardList The ArrayList of Development Card chosen by the player.
     */
    public void pickProductionRes(Player player, ArrayList<DevCard> devCardList) {
        HashMap<ResourceType, Integer> resources = null;

        //Scanning the DevCards
        for(int i=0; i<devCardList.size(); i++) {
            HashMap<ResourceType, Integer> productionIncome = devCardList.get(i).getProductionIncome();
            if(i==0) {
                resources = new HashMap<ResourceType, Integer>(productionIncome);
            }
            else {
                //Building a new HashMap containing the sum of the income of each DevCard
                for(ResourceType res: resources.keySet()) {
                    resources.replace(res, resources.get(res) + productionIncome.get(res));
                }
            }
        }
        player.storeResources(resources);
    }

    /**
     * Uses placeDevCard and removeDevCard in order to take a Development Card from the market and place it to the player's Development Card Slot.
     * @param player The player who wants to buy a new Development Card.
     */
    public void pickDevCard(Player player, DevCard devCard) {
        player.placeDevCard(devCard);
        player.increaseVP(devCard.getVP());
    }

    /**
     * Calls the increaseVP method from the class player in order to increase the Victory Points.
     * @param VP The amount of Victory Points to add.
     * @param player The player who earned new Victory Points.
     */
    public void giveVP(Player player, int VP) {
        player.increaseVP(VP);
    }


    /**
     * Ends the game and communicate the result.
     * @param playerID The ID of the player who called the end game. Will be used to determine the next and lasts turns.
     */
    public void endGame() {
        //TODO: Last turns


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
            int winnerID=playersList.size()-1;
        }
        else if(maxScoreRepetition>1) {
            int winnerID = whoWin(maxScoreRepetition);
        }

        //TODO: Communicate the Winner

    }

    /**
     * Finds the winner between those players who have the same and the highest score.
     * @param maxScoreRepetition The amount of players who have the best score.
     * @return The ID of the winner.
     */
    private int whoWin(int maxScoreRepetition) {
        int maxNumOfResources = 0;
        int winnerID=playersList.size()-1;

        //Checking the real winner between players who have the same and the maximum game score
        for(int i=playersList.size() - maxScoreRepetition; i<playersList.size(); i++) {
            if(maxNumOfResources < playersList.get(i).getResourcesQuantity()) {
                maxNumOfResources = playersList.get(i).getResourcesQuantity();
                winnerID=i;
            }
        }
        return winnerID;
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

    public List<Player> getPlayersList() {
        return playersList;
    }
}
