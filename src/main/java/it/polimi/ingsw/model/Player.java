package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.DevCardSlot;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;

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
    public Player(String username){
        this.leaderCardList = null;
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
    public int getPlayerID() {
        return playerID;
    }

    public int getVictoryPoint() { return victoryPoint; }
    public void setVictoryPoint(int VP){ this.victoryPoint=VP;
    }

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
            if(resourceType.getVal()>=0 && resourceType.getVal()<4){
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

    public int placeResources(ResourceType res, int resQuantity, int floor)
            throws InvalidParameterException, IndexOutOfBoundsException {
        int leftRes=0;
        ArrayList<Integer> freeDepot = new ArrayList<>();
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
            else throw new IndexOutOfBoundsException();
        }
        else throw new InvalidParameterException();
        return  leftRes;
    }
    /**
     *
     * @param devCard
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
        public int getFinalVP(){
        int totalResources=0;
        HashMap<ResourceType, Integer> allResources = getAllResources();
        for(ResourceType res : allResources.keySet()){
            totalResources+=allResources.get(res);
        }
        return victoryPoint+= totalResources/5;
    }


}










