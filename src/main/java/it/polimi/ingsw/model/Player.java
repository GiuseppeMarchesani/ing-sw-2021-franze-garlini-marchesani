package it.polimi.ingsw.model;

import java.awt.*;
import java.io.ObjectInputStream;
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
    public void setUsername(String name){this.username= name; }
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
            if(resourceType.getVal()>=0 && resourceType.getVal()<5){
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
/*
    public HashMap<ResourceType, Integer> placeResources(HashMap<ResourceType, Integer> resources) throws InvalidParameterException{
        HashMap<ResourceType,Integer> leftResources= new HashMap<>();
        int []availableDepot=null;
        int restResource=0;
        for (ResourceType resourceType : resources.keySet()) {
            if(resourceType.getVal()>=0 && resourceType.getVal()<5) {
                if (resourceType.getVal() != 4) {
                    if (warehouse.isEmpty()) {
                        ArrayList<Integer> freeDepot = new ArrayList<>();
                        for (int i = 0; i < warehouse.getDepotList().size(); i++) {
                            freeDepot.add(i, 1);
                        }
                        int d = choose(freeDepot);
                        restResource = warehouse.place(resourceType, resources.get(resourceType), d);
                    } else if (warehouse.hasResource(resourceType) >= 0 ){
                        restResource = warehouse.place(resourceType, resources.get(resourceType), warehouse.hasResource(resourceType));
                    } else {
                        ArrayList<Integer> freeDepot = new ArrayList<>();
                        for (int i = 0; i < warehouse.getDepotList().size(); i++) {
                            if(warehouse.getSpace().get(i) == 0) {
                                freeDepot.add(i, 1);
                            }
                            else {
                                freeDepot.add(i, 0);
                            }
                        }
                        int d = choose(freeDepot);
                        restResource = warehouse.place(resourceType, resources.get(resourceType), d);
                    }
                    leftResources.put(resourceType, restResource);
                } else {
                    leftResources.put(resourceType, resources.get(resourceType));
                }
            } else throw new InvalidParameterException();
        }
        return leftResources;
    }

 */

    public int addInDepot(ResourceType res, int resQuantity, int floor) throws InvalidParameterException{
        int leftRes=0;
        if(res.getVal() >= 0 && res.getVal()<=4){
            int newQuantity = warehouse.getDepotList().get(floor).getResourceQuantity() + resQuantity;
            warehouse.getDepotList().get(floor).setResourceType(res);

            if( newQuantity > warehouse.getDepotList().get(floor).getSize()){
                warehouse.getDepotList().get(floor).setResourceQuantity(warehouse.getDepotList().get(floor).getSize());
                leftRes= newQuantity - warehouse.getDepotList().get(floor).getSize();
            }
            else {
                warehouse.getDepotList().get(floor).setResourceQuantity(newQuantity);
            }
        }
        else throw new InvalidParameterException();
        return  leftRes;
    }
    /**
     *
     * @param devCard
     */
    public void placeDevCard(DevCard devCard) {
        int level= devCard.getCardType().getLevel();
        ArrayList<Integer> slot = new ArrayList<>(3);

        slot = devCardSlot.getAvailableSlots(level);
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
        for(int i=0; i<warehouse.getDepotList().size();i++){
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










