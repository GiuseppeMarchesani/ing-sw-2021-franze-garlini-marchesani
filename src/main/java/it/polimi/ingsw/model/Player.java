package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.DevCardSlot;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represent the player in the game
 */
public class Player {
    private String username;
    private int victoryPoint;
    private DevCardSlot devCardSlot;
    private HashMap<ResourceType, Integer> strongbox;
    private Warehouse warehouse;
    private int faithSpace;
    private  HashMap<LeaderCard, Boolean> leaderCards;
    private HashMap<ResourceType, Integer> resourceDiscount;
    private ArrayList<ResourceType> marbleConversion;

    /**
     * Default constructor
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
     * Player constructor used in game controller to add a new player
     * @param username username chosen by the player
     */
    public Player(String username){
        this.leaderCards = new HashMap<>();
        this.strongbox = new HashMap<>();
        this.warehouse = new Warehouse();
        this.faithSpace = 0;
        this.devCardSlot = new DevCardSlot();
        this.marbleConversion = new ArrayList<>();
        this.resourceDiscount = new HashMap<>();
        this.victoryPoint = 0;
        this.username=username;
    }
    public void setUsername(String name){this.username= name; }
    public String getUsername(){
        return username;
    }

    public int getVictoryPoint() { return victoryPoint; }
    public void setVictoryPoint(int VP){ this.victoryPoint=VP;
    }

    public ArrayList<LeaderCard> getLeaderCardList() {
        ArrayList<LeaderCard> cards= new ArrayList<>();
        for(LeaderCard card: leaderCards.keySet()){
            if (!leaderCards.get(card)){
                cards.add(card);
            }
        }
        return cards;
    }

    public HashMap<LeaderCard, Boolean> getLeaderCards() {
        return leaderCards;
    }

    /**
     * Discards the card chosen by the player among his leader cards
     * @param card the chosen card
     */
    public void discardLeader(LeaderCard card){
        leaderCards.remove(card);
    }

    /**
     * Plays the card by activating its ability
     * @param card the card chosen by the player
     */
    public void playLeader(LeaderCard card){
        if(leaderCards.containsKey(card)){
            leaderCards.put(card, true);
        }
        card.activateAbility(this);
    }

    public DevCardSlot getDevCardSlot(){
        return devCardSlot;
    }

    public int getFaithSpace() { return faithSpace;}

    public void setFaithSpace(int faithSpace) { this.faithSpace = faithSpace;}

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

    /**
     * Adds the resources earned in the strongbox.
     * @param resources resources earned
     * @return returns the faith and the any resources if there are.
     */
    public HashMap<ResourceType, Integer> storeResources(HashMap<ResourceType, Integer> resources) {
        HashMap<ResourceType,Integer> rest = new HashMap<>();
        for (ResourceType resourceType : resources.keySet()) {
            if(resourceType.getVal()>=0 && resourceType.getVal()<4){
                Integer quantity;
                if (strongbox.containsKey(resourceType))
                    quantity = strongbox.get(resourceType) + resources.get(resourceType);
                else {
                    quantity = resources.get(resourceType);
                }
                strongbox.put(resourceType, quantity);
            }
            else if(resourceType.getVal()==-1 || resourceType.getVal()==4){
                rest.put(resourceType, resources.get(resourceType));
            }
        }
        return rest;
    }
    /**
     * Place resources in the plan indicated by the player up to the maximum size of that depot.
     * @param res resource type
     * @param resQuantity quantity of resources
     * @param floor floor where the player wants put the resource
     * @return returns the number of the resources rest
     * @throws InvalidParameterException throws this exception if the resource type isn't equals
     * coin, servant, shield or stone.
     */
    public int placeResources(ResourceType res, int resQuantity, int floor)
            throws InvalidParameterException{
        int leftRes=0;
        ArrayList<Integer> freeDepot;
        if (res.getVal() >= 0 && res.getVal() < 4) {
            freeDepot = warehouse.availableDepot(res);
            if(freeDepot.contains(floor)){
                int newQuantity = warehouse.getDepotList().get(floor).getResourceQuantity() + resQuantity;
                warehouse.getDepotList().get(floor).setResourceType(res);
                if (newQuantity > warehouse.getDepotList().get(floor).getSize()) {
                    warehouse.getDepotList().get(floor).setResourceQuantity(warehouse.getDepotList().get(floor).getSize());
                    leftRes = newQuantity - warehouse.getDepotList().get(floor).getSize();
                } else {
                    warehouse.getDepotList().get(floor).setResourceQuantity(newQuantity);
                }
            }
            else return -1;
        }
        else throw new InvalidParameterException();
        return  leftRes;
    }

    /**
     * Places the development card bought by the player.
     * @param devCard card bought from market
     * @param slot slot chosen by the player
     * @throws InvalidParameterException throws the exception if the slot isn't free
     */
    public void placeDevCard(DevCard devCard, int slot) throws InvalidParameterException {
        int level= devCard.getCardType().getLevel();
        ArrayList<Integer> freeSlot = devCardSlot.getAvailableSlots(level);
        if(freeSlot.contains(slot)){
            devCardSlot.getSlotDev().get(slot).add(devCard);
        }
        else throw new InvalidParameterException();

    }

    /**
     * Increases the victory points of the player
     * @param vp victory points to add
     */
    public int increaseVP(int vp){
        victoryPoint += vp;
        return victoryPoint;
    }

    /**
     * Moves the player to the faith track
     * @param steps steps to advance
     */
    public int increaseFaith(int steps){
        faithSpace+=steps;
        if(faithSpace>24){
            faithSpace=24;
        }
        return faithSpace;
    }

    /**
     * Takes all the resources from strongbox and warehouse.
     * @return returns all the resources that the player owns
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
     * Takes the victory points given by the resources
     * @return returns the victory points
     */
    public int getResourceVP() {
        int totalResources = 0;
        HashMap<ResourceType, Integer> allResources = getAllResources();
        for (ResourceType res : allResources.keySet()) {
            totalResources += allResources.get(res);
        }
        return totalResources / 5;
    }

    /**
     * Checks if the player has enough resources to pay .
     * @param price price requested
     * @return returns true if the player can otherwise returns false
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
     * Check if the player has enough cards of the required color to active the leader card
     * @param color color of the card
     * @param requiredCard id of the card
     * @return returns true if the player can active the card otherwise returns false
     */
    public boolean checkHasEnoughCardOfColor(Color color, int requiredCard){
        return devCardSlot.numCardsPerColor(color)>requiredCard;
    }
    public int getLeaderVp(){
        int vp=0;
        for(LeaderCard card: leaderCards.keySet()){
            if(leaderCards.get(card)){
                vp+=card.getVP();
            }
        }
        return vp;
    }
    public int getFinalVP(){
        return getDevCardSlot().getCardPoints()+getResourceVP()+getLeaderVp()+victoryPoint;
    }
}










