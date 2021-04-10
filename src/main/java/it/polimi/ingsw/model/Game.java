package it.polimi.ingsw.model;
import java.io.IOException;
import java.util.*;

/**
 * This class is the intermediary between all the game's components.
 */
public class Game {
    private Market market ;
    private FaithTrack faithTrack ;
    private CardMarket cardMarket;
    private List<Player> playersList = new ArrayList<Player>();

    /**
     * Class constructor.
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
     * Calls the method pickResources from the class Market and handles the left resources in order to discard them.
     * @param player The player who is going to take the resources.
     * @param rowOrCol False stands for row, true stands for column.
     * @param rowOrColNumber The number of the row/column. Starts from 0.
     */
    public void pickMarketRes(Player player, Boolean rowOrCol, int rowOrColNumber) {

        //Resources to discard plus faith points to deliver to the player who got resources from market
        HashMap<ResourceType, Integer> leftResources = market.pickResources(player, rowOrCol, rowOrColNumber);

        //This variable counts the amount of left resources in order to increase the other players' position
        int faithSpaceToInc = 0;

        //Converting the faith resources in faith points
        for(ResourceType resource: leftResources.keySet()) {
            if(resource.getResource() == 4) {
                int position = player.increaseFaith(leftResources.get(resource));
                updateFaithTrack(player, position);
            }
            else{
                //Counting resources that will be discarded in order to increase players' position
                faithSpaceToInc += leftResources.get(resource);
            }
        }

        int pPosition = 0;

        for(Player p: playersList) {
            if(!(p.equals(player))){
                pPosition = p.increaseFaith(faithSpaceToInc);
                updateFaithTrack(p, pPosition);
            }
        }
    }

    /**
     * Gets the income of the Development Cards production passed as parameters and calls the Player's methods in order to store the resources in the Strongbox and increase his faith space..
     * @param player The player who is going to take the resources.
     * @param devCardList The ArrayList of Development Card chosen by the player.
     */
    public void pickProductionRes(Player player, ArrayList<DevCard> devCardList) {
        HashMap<ResourceType, Integer> resources = null;
        int faithSpaceToInc = 0;

        //Scanning the DevCards
        for(int i=0; i<devCardList.size(); i++) {
            HashMap<ResourceType, Integer> productionIncome = devCardList.get(i).getProductionIncome();
            if(i==0) {
                resources = new HashMap<ResourceType, Integer>(productionIncome);
            }
            else {
                //Building a new HashMap containing the sum of the income of each DevCard
                for(ResourceType res: resources.keySet()) {
                    if(res.getResource() == 4) {
                        faithSpaceToInc += resources.get(res);
                    }
                    else {
                        resources.replace(res, resources.get(res) + productionIncome.get(res));
                    }
                }
            }
        }
        int position = player.increaseFaith(faithSpaceToInc);
        player.storeResources(resources);
    }

    /**
     * Uses placeDevCard in order to place the DevCard, passed as parameter, in the player's Development Card Slot and give VP to the player.
     * @param player The player who wants to buy a new Development Card.
     * @param devCard The DevCard the player wants to buy.
     */
    public void pickDevCard(Player player, DevCard devCard) {
        int numOfCards = player.placeDevCard(devCard);
        giveVP(player, devCard.getVP());
        if(numOfCards == 7){
            endGame(player);
        }
    }

    /**
     * Calls the increaseVP method from the player class in order to increase Victory Points.
     * @param VP The amount of Victory Points to add.
     * @param player The player who earned new Victory Points.
     */
    public void giveVP(Player player, int VP) {
        player.increaseVP(VP);
    }

    /**
     * This method checks if the player activates a new faith zone or if he reaches the final space.
     * @param player The player to submit to the test.
     * @param position The player's position.
     */
    public void updateFaithTrack(Player player, int position) {

        //Checking if the player is in a pope space
        int whichFaithZone = faithTrack.isOnPopeSpace(position);
        if(whichFaithZone > 0) {

            //Delivering faith zone VP
            for(Player p: playersList) {
                if(faithTrack.isInFaithZone(p.getFaithSpace(), whichFaithZone)) {
                    giveVP(p, faithTrack.getFaithZones().get(whichFaithZone).getFaithZoneVP());
                }
            }

            //If it's the last pope space
            if(whichFaithZone == faithTrack.getFaithZones().indexOf(faithTrack.getFaithZones().get(faithZones.size()-1))) {
                for(Player p: playersList) {
                    //Players gain extra VP according to their final position
                    giveVP(p, faithTrack.getAssociatedVP(p.getFaithSpace()) );
                }
                endGame(player);
            }
        }

    }

    /**
     * Ends the game and communicate the result.
     * @param player The player who called the end game. Will be used to determine the next and lasts turns.
     */
    public void endGame(Player player) {
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
