package it.polimi.ingsw.model;

import java.awt.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private int playerID;
    private String username;
    private int victoryPoint;
    private DevCardSlot devCardSlot;
    private HashMap<ResourceType, Integer> strongbox;
    private Warehouse warehouse;
    private int faithSpace;
    private ArrayList<LeaderCard> leaderCardList;
    private HashMap<ResourceType, Integer> resourceDiscount;
    private ArrayList<ResourceType> marbleConversion;

    public Player(String name){
        this.username= name;
    }

    public Player() {
        this.leaderCardList = null;
        this.strongbox = new HashMap<>();
        this.warehouse = new Warehouse();
        this.faithSpace = 0;
        this.devCardSlot = new DevCardSlot();
        this.marbleConversion = new ArrayList<>();
        this.resourceDiscount = new HashMap<>();
        this.victoryPoint = 0;
    }
    public String getUsername(){
        return username;
    }
    public int getPlayerID() {
        return playerID;
    }

    public int getVictoryPoint() { return victoryPoint; }

    public ArrayList<LeaderCard> getLeaderCardList() { return leaderCardList;}

    public void setLeaderCardList(ArrayList<LeaderCard> leaderCardList) { this.leaderCardList = leaderCardList;}

    public void setDevCardSlot(DevCardSlot developCardSlot) { this.devCardSlot = developCardSlot;}

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

    public void storeResources(HashMap<ResourceType, Integer> resources) throws InvalidParameterException {
        for (ResourceType resourceType : resources.keySet()) {
            if(resourceType.getResource()>1 && resourceType.getResource()<6){
                Integer quantity;
                if (strongbox.containsKey(resourceType))
                    quantity = strongbox.get(resourceType) + resources.get(resourceType);
                else {
                    quantity = resources.get(resourceType);
                }
                strongbox.put(resourceType, quantity);
            } else throw new InvalidParameterException();
        }
    }





    public HashMap<ResourceType, Integer> placeResources(HashMap<ResourceType, Integer> resources) throws InvalidParameterException{
        HashMap<ResourceType,Integer> resResources= new HashMap<>();
        int []availableDepot=null;
        int restResource=0;
        for (ResourceType resourceType : resources.keySet()) {
            if(resourceType.getResource()!=0 && resourceType.getResource()!=1) {
                if (resourceType.getResource() != 6) {
                    if (warehouse.isEmpty()) {
                        ArrayList<Integer> freeDepot = new ArrayList<>();
                        for (int i = 0; i < warehouse.getDepotList().size(); i++) {
                            freeDepot.add(i, 1);
                        }
                        int d = choose(freeDepot);
                        restResource = warehouse.place(resourceType, resources.get(resourceType), d);
                    } else if (warehouse.hasResource(resourceType) == 0 || warehouse.hasResource(resourceType) == 1 ||
                            warehouse.hasResource(resourceType) == 2) {
                        restResource = warehouse.place(resourceType, resources.get(resourceType), warehouse.hasResource(resourceType));
                    } else if (warehouse.getSpace().get(0) == 0 || warehouse.getSpace().get(1) == 0 || warehouse.getSpace().get(2) == 0) {
                        restResource = warehouse.place(resourceType, resources.get(resourceType), warehouse.hasResource(resourceType));
                    }
                    resResources.put(resourceType, restResource);
                } else {
                    resResources.put(resourceType, resources.get(resourceType));
                }
            } else throw new InvalidParameterException();
        }
        return resResources;
    }

    /**
     *
     * @param devCard
     */
    public void placeDevCard(DevCard devCard) {
        int level= devCard.getCardType().getLevel();
        ArrayList<Integer> slot = new ArrayList<>(3);

         //TODO
        /* slot = developCardSlot.getAvailableSlots(level);

         */
        int x = choose(slot);
        devCardSlot.getSlotDev().get(x).add(devCard);
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
    public int increaseFaith(int steps){
        faithSpace+=steps;
        return faithSpace;
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

    public int choose(ArrayList<?> T){
        int n=0;

        return n;
    }
}










