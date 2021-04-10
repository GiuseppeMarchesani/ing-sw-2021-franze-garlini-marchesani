package it.polimi.ingsw.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private int playerID;
    private int victoryPoint = 0;
    private CardSlot developCardSlot;
    private HashMap<ResourceType, Integer> strongbox;
    private Warehouse warehouse;
    private int faithSpace;
    private ArrayList<LeaderCard> leaderCardList;
    private HashMap<ResourceType, Integer> resourceDiscount;
    private ArrayList<ResourceType> marbleConversion;


    public Player(int playerID, int victoryPoint) {
        this.playerID = playerID;
        this.leaderCardList = null;
        this.strongbox = new HashMap<>();
        this.warehouse = new Warehouse();
        this.faithSpace = 0;
        this.developCardSlot = new CardSlot();
        this.marbleConversion = new ArrayList<>();
        this.resourceDiscount = new HashMap<>();
        this.victoryPoint = victoryPoint;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getVictoryPoint() { return victoryPoint; }


    public ArrayList<LeaderCard> getLeaderCardList() { return leaderCardList;}

    public void setLeaderCardList(ArrayList<LeaderCard> leaderCardList) { this.leaderCardList = leaderCardList;}

    public void setDevelopCardSlot(CardSlot developCardSlot) { this.developCardSlot = developCardSlot;}

    public CardSlot getDevelopCardSlot() { return developCardSlot;}

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

    public void marbleConversion(ResourceType resourceType) {
        marbleConversion.add(resourceType);
    }

    public void storeResources(HashMap<ResourceType, Integer> resources) {
        for (ResourceType resourceType : resources.keySet()) {
            Integer quantity;
            if (strongbox.containsKey(resourceType))
                quantity = strongbox.get(resourceType) + resources.get(resourceType);
            else {
                quantity = resources.get(resourceType);
            }
            strongbox.put(resourceType, quantity);
        }

    }

    /**
     *
     * @param resources
     * @return
     */
    public HashMap<ResourceType, Integer> placeResources(HashMap<ResourceType, Integer> resources) {
        HashMap<ResourceType,Integer> resResources= new HashMap<>();
        int []availableDepot=null;
        int restResource=0;
        for (ResourceType resourceType : resources.keySet()) {

            if(resourceType.getResource()==4){
                increaseFaith(resources.get(resourceType));
                resResources.put(resourceType, resources.get(resourceType));
            }
            if (warehouse.isEmpty()) {
                for(int i=0; i<3; i++){
                    availableDepot[i]=1;
                }
                int d = Choices.chooseDepot(availableDepot);
                restResource= warehouse.place(resourceType, resources.get(resourceType), d);
            } else if (warehouse.hasResource(resourceType) == 0 || warehouse.hasResource(resourceType) == 1 ||
                    warehouse.hasResource(resourceType) == 2) {
                restResource = warehouse.place(resourceType,resources.get(resourceType), warehouse.hasResource(resourceType));
            }
            else if (warehouse.getSpace().get(0) == 0 || warehouse.getSpace().get(1) == 0 || warehouse.getSpace().get(2) == 0) {
                restResource= warehouse.place(resourceType, resources.get(resourceType), warehouse.hasResource(resourceType));
            }
            resResources.put(resourceType, restResource);
        }
        return resResources;
    }

    /**
     *
     * @param devCard
     */
    public void placeDevCard(DevCard devCard) {
        int level= devCard.getLevel();
        ArrayList<Integer> slot = new ArrayList<>(3);
        slot = developCardSlot.getCardAvailable(level);
        int x = Choices.chooseDevSlot(slot);
        developCardSlot.store(level, x);
    }

    /**
     *
     * @param vp
     */
    public void increaseVP(int vp){
        victoryPoint += vp;
    }

    /**
     *
     * @param steps
     */
    public void increaseFaith(int steps){
        faithSpace+=steps;
    }

    /**
     *
     * @return
     */
    public int getResourcesQuantity(){
        int resourcesQuantity=0;
        for(int i=0; i<3;i++){
            resourcesQuantity+= warehouse.getDepotList().get(i).getResourceQuantity();
        }
        HashMap<ResourceType,Integer> strongboxRes = getStrongbox();
        for(ResourceType res: strongboxRes.keySet()){
            resourcesQuantity+= strongboxRes.get(res);
        }
        return resourcesQuantity;
    }

    /**
     *
     * @return
     */
    public int getFinalVP(){
        int resourcesQuantity= getResourcesQuantity();

        return victoryPoint+= resourcesQuantity/5;
    }
}










