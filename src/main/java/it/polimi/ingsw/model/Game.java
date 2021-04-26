package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
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
    private boolean ongoing;


    public Game() {
        market = new Market(generateMarbles());
        faithTrack = new FaithTrack(generateFaithTrack(), generateVPspaces());
        cardMarket = new CardMarket(generateDevCardDeck());
        ongoing=false;
    }



    public boolean status(){
        return ongoing;
    }

    public void start() throws FileNotFoundException {
        ongoing=true;
        Scanner input = new Scanner(new File("Leaders.txt"));
        input.useDelimiter("-|\n");

        List<LeaderCard> leadCardDeck = new ArrayList<LeaderCard>();
        while(input.hasNext()) {
            int id = input.nextInt();
            int vp = input.nextInt();
            HashMap<ResourceType, Integer> resReq = new HashMap<ResourceType,Integer>();
            for(int i=0; i<6;i++){
                ResourceType resource=new ResourceType(i+1);
                int quantity=input.nextInt();
                if(quantity>0){
                    resReq.put(resource,quantity);
                }
            }
            HashMap<DevCardType, Integer> cardReq = new HashMap<DevCardType,Integer>();
            for(int i=0; i<4;i++){
                int level=input.nextInt();
                int quantity=input.nextInt();
                if(quantity>0){
                    cardReq.put(new DevCardType(i,level),quantity);
                }
            }
            String ability=input.next();
            ResourceType resourceAbility=new ResourceType(input.nextInt());

            LeaderCard leader = new LeaderCard(id,vp,resReq,cardReq,ability,resourceAbility);
            leadCardDeck.add(leader);
        }
        ongoing=true;
    }


    /**
     * Calls the method pickResources from the class Market and handles the left resources in order to discard them.
     * @param rowOrCol False stands for row, true stands for column.
     * @param rowOrColNumber The number of the row/column. Starts from 0.
     */

    public HashMap<ResourceType, Integer> pickMarketRes(Boolean rowOrCol, int rowOrColNumber) {
        return market.pickResources(rowOrCol,rowOrColNumber);
    }

    /**
     * Gets the income of the Development Cards production passed as parameters and calls the Player's methods in order to store the resources in the Strongbox and increase his faith space..
     * @param devCardList The ArrayList of DevCards chosen by the player.
     */
    public HashMap<ResourceType, Integer> pickProductionRes(ArrayList<DevCard> devCardList) {

        HashMap<ResourceType, Integer> resources = new HashMap<ResourceType, Integer>();

        for(int i=0; i<devCardList.size(); i++) {
            HashMap<ResourceType, Integer> temp= devCardList.get(i).getProductionIncome();

            for(int j=2; j<7;j++){
                ResourceType h=new ResourceType(j);
                if(temp.containsKey(h)){
                    if(resources.containsKey(h)){
                        resources.put(h,resources.get(h)+temp.get(h));
                    }
                    else  resources.put(h,temp.get(h));
                }
            }
        }
        return resources;
    }



    /**
     * Uses placeDevCard in order to place the DevCard, passed as parameter, in the player's Development Card Slot and give VP to the player.
     */
    public DevCard pickDevCard(int color, int level) throws IndexOutOfBoundsException{
        ArrayList<ArrayList<ArrayList<DevCard>>> temp=cardMarket.getDevCardGrid();
        return temp.get(level).get(color).get(temp.get(level).get(color).size()-1);
    }


    /**
     * This method checks if the player activates a new faith zone or if he reaches the final space.
     * @param position The player's position.
     */
    //RIGUARDARE!!!!!!!!!!!!!!!!!!!!!!! HashMap inviata da turno????????????????????????????
    public HashMap<Player, Integer> updateFaithTrack(int position) {
        HashMap<Player, Integer> faith = new HashMap<Player, Integer>();
        //Checking if the player is in a pope space
        int whichFaithZone = faithTrack.isOnPopeSpace(position);
        if(whichFaithZone >= 0) {
            int vp=faithTrack.getFaithZones().get(whichFaithZone).getFaithZoneVP();
            //Delivering faith zone VP
            for(Player p: playersList) {
                if(faithTrack.isInFaithZone(p.getFaithSpace(), whichFaithZone)) {
                    faith.put(p,vp);
                }
            }

        }
        return faith;
    }

    /**
     * Ends the game and communicate the result.
     * @param player The player who called the end game. Will be used to determine the next and lasts turns.
     */
    public void endGame(Player player) {
        //TODO: Last turns ci pensa Turn

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
