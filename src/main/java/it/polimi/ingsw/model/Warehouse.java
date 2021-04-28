package it.polimi.ingsw.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

public class Warehouse {
    private int initialDepot = 3;
    private ArrayList<Depot> depotList;

    /**
     * Class constructor.
     */
    public Warehouse(){
        this.depotList = new ArrayList<>(initialDepot);
        int sizeD = initialDepot;

        for(int i=0; i<initialDepot; i++){
            depotList.add(i, new Depot(sizeD, 1));
            sizeD--;
        }
    }

    public ArrayList<Depot> getDepotList(){
        return depotList;
    }

    /**
     * This method adds a special depot.
     * @param resourceType type of resources in leader card' ability
     */
    public void addDepot(ResourceType resourceType){
        Depot depotLeader = new Depot(2, 0);
        depotLeader.setResourceType(resourceType);
        depotList.add(depotLeader);
    }

    /**
     * This method allows the player to rearrange depots
     * @param depot1 index of the first floor
     * @param depot2 index of the second floor
     */

    public void rearrange(int depot1, int depot2) throws InvalidParameterException{
        //From depot2 to tempDepot
        if(getDepotList().get(depot1).getRearrangeble()==1 && getDepotList().get(depot2).getRearrangeble()==1) {
            Depot depot = new Depot(depotList.get(depot2).getSize(), depotList.get(depot2).getRearrangeble());
            depot.setResourceType(depotList.get(depot2).getResourceType());
            depot.setResourceQuantity(depotList.get(depot2).getResourceQuantity());

            //From depot1 to depot2
            depotList.get(depot2).setResourceType(depotList.get(depot1).getResourceType());
            depotList.get(depot2).setResourceQuantity(depotList.get(depot1).getResourceQuantity());

            //From tempDepot to depot1
            depotList.get(depot1).setResourceType(depot.getResourceType());
            depotList.get(depot1).setResourceQuantity(depot.getResourceQuantity());
        } else throw new InvalidParameterException();

    }

    /**
     *
     * @param resource
     * @param resourceQuantity
     * @param floor
     * @return
     */

    public int place(ResourceType resource, int resourceQuantity, int floor){
        int leftResources = resourceQuantity - getSpace().get(floor);
        depotList.get(floor).setResourceType(resource);
        if(leftResources>0) {
            depotList.get(floor).setResourceQuantity(depotList.get(floor).getSize());
            return leftResources;
        }
        else {
            depotList.get(floor).setResourceQuantity(resourceQuantity + depotList.get(floor).getResourceQuantity());
            return 0;
        }


    }



    /**
     * this method is used to know if there is resource in warehouse
     * @param res type of resource that are you looking for
     * @return i the depot that have res, -1 if the depot does not have the requested resource
     */
    public int hasResource(ResourceType res){
        for(int i=0; i<depotList.size(); i++){
            if(depotList.get(i).getResourceType().equals(res) && depotList.get(i).getResourceQuantity() == depotList.get(i).getSize())
                return i;
            else if(depotList.get(i).getResourceType() == res)
                return i;
        }
        return -1;
    }

    /**
     * this method is used to see if warehouse is empty
     * @return (true if warehouse is empty and false if it is not)
     */

    public Boolean isEmpty() {
        for(int i=0; i<depotList.size(); i++){
            if(depotList.get(i).getResourceQuantity() != 0)
                return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    public HashMap<ResourceType, Integer> getSpace(){
        HashMap<ResourceType, Integer> space= new HashMap<>();
        for(int i=0; i<depotList.size(); i++){
            space.put(depotList.get(i).getResourceType(),
                    depotList.get(i).getSize() - depotList.get(i).getResourceQuantity());
        }
        return space;
    }

}
