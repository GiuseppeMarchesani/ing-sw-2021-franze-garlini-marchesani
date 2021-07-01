package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.DevCardSlot;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * This class represents the player and it contains all his resources and his cards.
 */
public class Player {
    private String username;
    private int victoryPoint;
    private DevCardSlot devCardSlot;
    private HashMap<ResourceType, Integer> strongbox;
    private Warehouse warehouse;
    private int faithSpace;
    private HashMap<LeaderCard, Boolean> leaderCards;
    private HashMap<ResourceType, Integer> resourceDiscount;
    private ArrayList<ResourceType> marbleConversion;

    /**
     * Default constructor.
     */
    public Player() {
        this.leaderCards = new HashMap<>();
        this.strongbox = new HashMap<>();
        this.warehouse = new Warehouse();
        this.faithSpace = 0;
        this.devCardSlot = new DevCardSlot();
        this.marbleConversion = new ArrayList<>();
        this.resourceDiscount = new HashMap<>();
        this.victoryPoint = 0;
    }

    /**
     * Player constructor.
     * @param username player's username.
     */
    public Player(String username){
        this.leaderCards = new HashMap<>();
        this.strongbox = new HashMap<>();
        this.warehouse = new Warehouse();
        if(username.equals("ricco")){
            strongbox.put(ResourceType.STONE,999);
            strongbox.put(ResourceType.COIN,999);
            strongbox.put(ResourceType.SERVANT,999);
            strongbox.put(ResourceType.SHIELD,999);
        }
        this.faithSpace = 0;
        this.devCardSlot = new DevCardSlot();
        this.marbleConversion = new ArrayList<>();
        this.resourceDiscount = new HashMap<>();
        this.victoryPoint = 0;
        this.username=username;
    }

    /**
     * Returns the Leader Cards owned and not played by the player.
     * @return the list of Leader Cards owned by the player.
     */
    public ArrayList<LeaderCard> getLeaderCardList() {
        ArrayList<LeaderCard> cards= new ArrayList<>();
        for(LeaderCard card: leaderCards.keySet()){
            if (!leaderCards.get(card)){
                cards.add(card);
            }
        }
        Collections.reverse(cards);
        return cards;
    }

    /**
     * Returns the number of leadercards in a player's hand.
     * @return the number of leadercards in a player's hand.
     */
    public int remainingLeaderCards(){
        return getLeaderCardList().size();
    }

    /**
     * Discards a Leader Card from the player's hand.
     * @param cardId the id of the card to be discarded.
     */
    public void discardLeader(int cardId){
        for(LeaderCard leader: leaderCards.keySet()) {
            if(leader.getId()==cardId){ leaderCards.remove(leader);
            break;}
        }
    }

    /**
     * Plays a Leader Card from the player's hand.
     * @param leader the card to be played
     */
    public void playLeader(LeaderCard leader){
                discardLeader(leader.getId());
                leaderCards.put(leader, true);
                leader.activateAbility(this);

    }

    /**
     * Increases the Victory Points of the player by the amount passed as parameter.
     * @param vp the Victory Points to be added.
     */
    public void increaseVP(int vp){
        victoryPoint += vp;
    }

    /**
     * Increases the player's faith space.
     * @param steps the number of space to move forward.
     * @return the new faith space.
     */
    public int increaseFaith(int steps){
        faithSpace+=steps;
        if(faithSpace>24){
            faithSpace=24;
        }
        return faithSpace;
    }

    /**
     * Used to get all the player's resources which reside in both Strongbox and Warehouse.
     * @return all the player's resources in a HashMap.
     */
    public HashMap<ResourceType, Integer> getAllResources() {
        HashMap<ResourceType, Integer> allResources = new HashMap<>(getStrongbox());
        for (int i = 0; i < warehouse.getDepotList().size(); i++) {
            if (allResources.containsKey(warehouse.getDepotList().get(i).getResourceType())) {
                int quantity = allResources.get(warehouse.getDepotList().get(i).getResourceType());
                allResources.put(warehouse.getDepotList().get(i).getResourceType(),
                        quantity + warehouse.getDepotList().get(i).getResourceQuantity());
            } else {
                allResources.put(warehouse.getDepotList().get(i).getResourceType(),
                        warehouse.getDepotList().get(i).getResourceQuantity());
            }
        }
        return allResources;
    }

    /**
     * Returns all the Victory Points that came from owning a certain amount of resources.
     * @return the amount of Victory Points associated to the player's resources.
     */
    public int getResourceVP(){
        int totalResources=0;
        HashMap<ResourceType, Integer> allResources = getAllResources();
        for(ResourceType res : allResources.keySet()){
            totalResources+=allResources.get(res);
        }
        return totalResources/5;
    }

    /**
     * It checks if the price passed as a parameter can be afforded by the player.
     * @param price the price you want to know if the player can affords it.
     * @return a boolean representing if the player can afford this payment or not.
     */
    public boolean checkPriceCanBePaid(HashMap<ResourceType, Integer> price) {
        HashMap<ResourceType, Integer> available = getAllResources();

        int grandTotalCost = 0;
        int grandTotalAvailable=0;
        for (ResourceType r : price.keySet()) {
            grandTotalCost += price.get(r);
            if(r!=ResourceType.ANY){
                if(available.containsKey(r)){
                    if (available.get(r)< price.get(r)){
                        return false;
                    }
                }
                else return false;
            }
        }
        for(ResourceType r : available.keySet()){
            grandTotalAvailable += available.get(r);
        }
        return grandTotalAvailable >= grandTotalCost;
    }

    /**
     * It checks if the player has enough cards by a given color.
     * @param color the color of the cards you want to check.
     * @param requiredCard number of the cards, of the given color, required.
     * @return a boolean representing if the player has the required cards or not.
     */
    public boolean checkHasEnoughCardOfColor(Color color, int requiredCard){
        return devCardSlot.numCardsPerColor(color)>=requiredCard;
    }

    /**
     * Returns the amount of Victory Points associated to the Leader Cards owned by the player.
     * @return the amount of Victory Points associated to the player's Leader Cards.
     */
    public int getLeaderVp(){
        int vp=0;
        for(LeaderCard card: leaderCards.keySet()){
            if(leaderCards.get(card)){
                vp+=card.getVP();
            }
        }
        return vp;
    }

    /**
     * It returns the amount of Victory Points of the player when the endgame starts.
     * @return the amount of Victory Points owned by the player when the endgame starts.
     */
    public int getFinalVP(){
        return getDevCardSlot().getCardPoints()+getResourceVP()+getLeaderVp()+victoryPoint;
    }

    public void setUsername(String name) {
        this.username= name;
    }

    public String getUsername() {
        return username;
    }

    public int getVictoryPoint() {
        return victoryPoint;
    }

    public void setVictoryPoint(int VP) {
        this.victoryPoint=VP;
    }

    public DevCardSlot getDevCardSlot() {
        return devCardSlot;
    }

    public int getFaithSpace() {
        return faithSpace;
    }

    public void setFaithSpace(int faithSpace) {
        this.faithSpace = faithSpace;
    }

    public HashMap<ResourceType, Integer> getStrongbox() {
        return strongbox;
    }

    public void setStrongbox(HashMap<ResourceType, Integer> strongbox) {
        this.strongbox = strongbox;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public HashMap<ResourceType, Integer> getResourceDiscount() {
        return resourceDiscount;
    }

    public ArrayList<ResourceType> getMarbleConversion() {
        return marbleConversion;
    }

    public HashMap<LeaderCard, Boolean> getLeaderCards() {
        return leaderCards;
    }

    public void setLeaderCards(HashMap<LeaderCard, Boolean> leaderCards) {
        this.leaderCards = leaderCards;
    }

    /**
     * Returns a list of played leader cards.
     * @return a list of played leadercards.
     */
    public ArrayList<LeaderCard> getPlayedLeaderCards() {
        ArrayList<LeaderCard> list=new ArrayList<>();
        for(LeaderCard lc: leaderCards.keySet()){
            if(leaderCards.get(lc)){
                list.add(lc);
            }
        }
        return list;
    }
}










