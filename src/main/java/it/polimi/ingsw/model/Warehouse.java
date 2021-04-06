package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Warehouse {
    private ArrayList<Depot> d;

    /**
     * Class constructor.
     */
    public Warehouse(){
        this.d = new ArrayList<>(3);
        d.get(0).setSize(3);
        d.get(1).setSize(2);
        d.get(2).setSize(1);

    }

    /**
     * This method adds a special depot.
     * @param resourceType (type of resources in leader card' ability)
     */
    public void addDepot(ResourceType resourceType){
        Depot depotLeader = new Depot(resourceType, 0, 2);
        d.add(depotLeader);
    }


    public void rearrange(int n, int m){
        if(d.get(n).getResourceQuantity()==d.get(m).getResourceQuantity()) {
            Depot depot = new Depot(d.get(m).getResourceType(), d.get(m).getResourceQuantity(), d.get(m).getSize());
            d.get(m).setResourceType(d.get(n).getResourceType());
            d.get(n).setResourceType(depot.getResourceType());
        }
    }



    public HashMap<ResourceType, Integer> place(HashMap<ResourceType, Integer> res, int n){
        HashMap<ResourceType, Integer> map = new HashMap<>();
        for(ResourceType resourceType: res.keySet()) {
            d.get(n).setResourceType(resourceType);
            d.get(n).setResourceQuantity(d.get(n).getSize());
            map.put(resourceType, res.get(resourceType) - getSpace().get(res));
        }
        return map;
    }


    /**
     * this method is used to know if there is resource in warehouse
     * @param res (type of resource that are you looking for)
     * @return (i the depot that have res, -1 if the depot does not have the requested resource)
     */
    public int hasResource(ResourceType res){
        for(int i=0; i<4; i++){
            if(d.get(i).getResourceType().equals(res) && d.get(i).getResourceQuantity() == d.get(i).getSize())
                return i;
            else if(d.get(i).getResourceType() == res)
                return i;
        }
        return -1;
    }

    /**
     * this method is used to see if warehouse is empty
     * @return (true if warehouse is empty and false if it is not)
     */
    public Boolean isEmpty() {
        for(int i=0; i<3; i++){
            if(d.get(i).getResourceQuantity() != 0)
                return false;
        }
        return true;
    }


    public HashMap<ResourceType, Integer> getSpace(){
        HashMap<ResourceType, Integer> space= new HashMap<>();
        for(int i=0; i<3; i++){
            space.put(d.get(i).getResourceType(), d.get(i).getResourceQuantity());
        }
        return space;
    }

}
