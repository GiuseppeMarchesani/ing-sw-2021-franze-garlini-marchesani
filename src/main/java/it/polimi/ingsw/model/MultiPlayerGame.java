package it.polimi.ingsw.model;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used for MultiPlayer mode and it's the intermediary between all the game's components.
 */

public class MultiPlayerGame extends Game{

    private List<Player> playersList = new ArrayList<Player>();

    /**
     * MultiPlayerGame class constructor.
     * @param playersList The Array containing all the players.
     */
    public MultiPlayerGame(ArrayList<Player> playersList) {
        super();
        this.playersList = playersList;
    }

    /**
     * Sets all the components up, gives the Leader Cards to the players and starts the game.
     */
    public void start() {
        List<LeaderCard> leadCardDeck = new ArrayList<LeaderCard>();
        //TODO: LeaderCard generation
    }

    /**
     * Calls the method placeResources from the class player in order to store the resources obtained by the market.
     * @param resources The resources obtained by the Market.
     * @param playerID The ID of the player who is going to take the resources.
     */
    public void giveMarketRes(HashMap<ResourceType, Integer> resources, int playerID) {
        playersList.get(playerID).placeResources(resources);
    }

    /**
     * Calls the method storeResources from the class player in order to store the resources obtained by production.
     * @param resources The resources obtained by production.
     * @param playerID The ID of the player who is going to take the resources.
     */
    public void giveProductionRes(HashMap<ResourceType, Integer> resources, int playerID) {
        playersList.get(playerID).storeResources(resources);
    }

    /**
     * Uses placeDevCard and removeDevCard in order to take a Development Card from the market and place it to the player's Development Card Slot.
     * @param playerID The ID of the player who bought the card.
     */
    public void giveDevCard(int playerID) {
        playersList.get(playerID).placeDevCard(CardMarket.removeDevCard());
    }

    /**
     * Calls the increaseVP method from the class player in order to increase the Victory Points.
     * @param VP The amount of Victory Points to add.
     * @param playerID The ID of the player who earned new Victory Points.
     */
    public void giveVP(int VP, int playerID) {
        playersList.get(playerID).increaseVP(VP);
    }

    /**
     * Ends the game and communicate the result.
     * @param playerID The ID of the player who called the end game. Will be used to determine the next and lasts turns.
     */
    public void endGame(int playerID) {
        //TODO: Last turns

        //Getting results as an array
        int numOfPlayers = playersList.size();
        int[] results = new int[numOfPlayers];
        for(int i=0; i< numOfPlayers; i++) {
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
            if(maxNumOfResources < playersList.get(i).getNumOfResources()) {
                maxNumOfResources = playersList.get(i).getNumOfResources();
                winnerID=i;
            }
        }
        return winnerID;
    }
}