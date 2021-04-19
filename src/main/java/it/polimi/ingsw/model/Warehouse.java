package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Warehouse {
    private ArrayList<Depot> depotList;

    /**
     * Class constructor.
     */
    public Warehouse(){
        this.depotList = new ArrayList<>(3);
        int sizeD = depotList.size();

        for(int i=0; i<depotList.size(); i++){
            depotList.get(i).setSize(sizeD);
            depotList.get(i).setRearrangeble(1);
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
        Depot depotLeader = new Depot(resourceType, 0, 2, 0);
        depotList.add(depotLeader);
    }

    /**
     * This method allows the player to rearrange depots
     * @param depot1 index of the first floor
     * @param depot2 index of the second floor
     */

    public void rearrange(int depot1, int depot2){
        if(depotList.get(depot1).getResourceQuantity()==depotList.get(depot2).getResourceQuantity()) {
            Depot depot = new Depot(depotList.get(depot2).getResourceType(), depotList.get(depot2).getResourceQuantity(),
                    depotList.get(depot2).getSize(), depotList.get(depot2).getRearrangeble());
            depotList.get(depot2).setResourceType(depotList.get(depot1).getResourceType());
            depotList.get(depot1).setResourceType(depot.getResourceType());
        }
    }

    /**
     *
     * @param resource
     * @param resourceQuantity
     * @param floor
     * @return
     */

    public int place(ResourceType resource, int resourceQuantity, int floor){
        int availableSpace = getSpace().get(floor) + resourceQuantity ;
        int restResource=0;
        depotList.get(floor).setResourceType(resource);
        if(resourceQuantity>=availableSpace) {
            depotList.get(floor).setResourceQuantity(depotList.get(floor).getSize());
            return (resourceQuantity-depotList.get(floor).getSize());
        }
        depotList.get(floor).setResourceQuantity(resourceQuantity + depotList.get(floor).getResourceQuantity());
        return 0;

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
