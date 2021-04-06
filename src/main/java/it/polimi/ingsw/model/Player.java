package it.polimi.ingsw.model;

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

    public void addDiscount(HashMap<ResourceType, Integer> resources) {
        for (ResourceType resourceType : resources.keySet()) {
            Integer quantity = resources.get(resourceType);
            resourceDiscount.put(resourceType, quantity);
        }

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


    public HashMap<ResourceType, Integer> placeResources(HashMap<ResourceType, Integer> resources) {

        for (ResourceType resourceType : resources.keySet()) {
            if (warehouse.isEmpty()) {
                HashMap<ResourceType, Integer> n = new HashMap<>();
                n.put(resourceType, resources.get(resourceType));
                int d = Turn.choose();
                return warehouse.place(n, d);
            } else if (warehouse.hasResource(resourceType) == 0 || warehouse.hasResource(resourceType) == 1 ||
                    warehouse.hasResource(resourceType) == 2) {
                HashMap<ResourceType, Integer> n = new HashMap<>();
                n.put(resourceType, resources.get(resourceType));
                return warehouse.place(n, warehouse.hasResource(resourceType));
            } else if (warehouse.getSpace().get(0) == 0 || warehouse.getSpace().get(1) == 0 || warehouse.getSpace().get(2) == 0) {
                HashMap<ResourceType, Integer> n = new HashMap<>();
                n.put(resourceType, resources.get(resourceType));
                return warehouse.place(n, warehouse.hasResource(resourceType));
            }
        }
        return resources;
    }

    public CardSlot placeDevCard(int n) {
        ArrayList<Integer> slot = new ArrayList<>(3);
        int x=0;
        for(int i=0; i<3; i++){
            slot.set(i, developCardSlot.getLevel().get(i));
        }
        if(slot.get(0)==0 && slot.get(1)==0 && slot.get(2)==0){
            x=Turn.choose();
            return developCardSlot.place(n,x);
        }
        else if(slot.get(0)==0 && slot.get(1)==0 || slot.get(0)==0 && slot.get(2)==0 ||
                slot.get(1)==0 && slot.get(2)==0){
            x=Turn.choose();
            return developCardSlot.place(n,x);
        }
        else if(slot.get(0)==0 || slot.get(1)==0 || slot.get(2)==0){
            for(int i=0; i<3; i++){
                if(slot.get(i)==0)
                    x=i;
            }
            return  developCardSlot.place(n, x);
        }
        else if(slot.get(0)==1 && slot.get(1)==1 && slot.get(2)==1){
            x=Turn.choose();
            return developCardSlot.place(n,x);
        }
        else if(slot.get(0)==1 && slot.get(1)==1 || slot.get(0)==1 && slot.get(2)==1 ||
                slot.get(1)==1 && slot.get(2)==1){
            x=Turn.choose();
            return developCardSlot.place(n,x);
        }
        else if(slot.get(0)==1 || slot.get(1)==1 || slot.get(2)==1){
            for(int i=0; i<3; i++){
                if(slot.get(i)==1)
                    x=i;
            }
            return  developCardSlot.place(n, x);
        }
        else if(slot.get(0)==2 && slot.get(1)==2 && slot.get(2)==2){
            x=Turn.choose();
            return developCardSlot.place(n,x);
        }
        else if(slot.get(0)==2 && slot.get(1)==2 || slot.get(0)==2 && slot.get(2)==2 ||
                slot.get(1)==2 && slot.get(2)==2){
            x=Turn.choose();
            return developCardSlot.place(n,x);
        }
        else if(slot.get(0)==2 || slot.get(1)==2 || slot.get(2)==2){
            for(int i=0; i<3; i++){
                if(slot.get(i)==1)
                    x=i;
            }
            return  developCardSlot.place(n, x);
        }
        return developCardSlot;
    }
}








