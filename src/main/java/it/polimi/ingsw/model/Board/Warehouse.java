package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the place where the player can place market's resources.
 */
public class Warehouse {
    private int initialDepot = 3;
    private ArrayList<Depot> depotList;

    /**
     * Default constructor.
     */
    public Warehouse(){
        this.depotList = new ArrayList<>(initialDepot);
        int sizeD = initialDepot;

        for(int i=0; i<initialDepot; i++){
            depotList.add(i, new Depot(sizeD, 1));
            depotList.get(i).setResourceQuantity(0);
            depotList.get(i).setResourceType(ResourceType.EMPTY);
            sizeD--;
        }
    }

    /**
     * Returns all the warehouse's resources.
     * @return returns all the resources in a HashMap.
     */
    public HashMap<ResourceType, Integer> getAllResources(){
        HashMap<ResourceType,Integer> resource=new HashMap<ResourceType, Integer>();
        for(int i=0; i<depotList.size();i++){
            resource.put(depotList.get(i).getResourceType(),depotList.get(i).getResourceQuantity());
        }
        for(int i=3;i<depotList.size();i++){
            if (resource.containsKey(depotList.get(i).getResourceType())){
                resource.put(depotList.get(i).getResourceType(), resource.get(depotList.get(i).getResourceType())+depotList.get(i).getResourceQuantity());
            }
            else  resource.put(depotList.get(i).getResourceType(),depotList.get(i).getResourceQuantity());
        }
        resource.remove(ResourceType.EMPTY);
       return resource;
    }

    /**
     * Takes the resources needed to pay and removed them from the warehouse.
     * @param resources resources to be removed.
     */
    public void spendResources(HashMap<ResourceType, Integer> resources){
        for(int i=0; i<depotList.size(); i++){
            if(resources.containsKey(depotList.get(i).getResourceType())){
                if(resources.get(depotList.get(i).getResourceType())<=depotList.get(i).getResourceQuantity()){
                    depotList.get(i).setResourceQuantity(depotList.get(i).getResourceQuantity()-resources.get(depotList.get(i).getResourceType()));
                    resources.remove(depotList.get(i).getResourceType());
                    if( depotList.get(i).getResourceQuantity()==0){
                        depotList.get(i).setResourceType(ResourceType.EMPTY);
                    }
                }
                else{
                    resources.put(depotList.get(i).getResourceType(),resources.get(depotList.get(i).getResourceType())-depotList.get(i).getResourceQuantity() );
                    depotList.get(i).setResourceQuantity(0);
                    depotList.get(i).setResourceType(ResourceType.EMPTY);
                }
            }
        }
    }

    /**
     * This method adds a special depot.
     * @param resourceType the Depot ResourceType.
     */
    public void addDepot(ResourceType resourceType){
        Depot depotLeader = new Depot(2, 0);
        depotLeader.setResourceType(resourceType);
        depotList.add(depotLeader);
    }

    /**
     * Replaces resources after the player has taken them from the market.
     * @param depotToResource resources to put in the depot.
     * @param depotToQuantity quantity of resources to put in the depot.
     * @param resourceToLeader quantity of resources to put in the leader depot.
     */
    public void replaceResources(HashMap<Integer, ResourceType> depotToResource, HashMap<Integer, Integer> depotToQuantity, ArrayList<Integer> resourceToLeader){
        for(int i=0; i<depotList.size(); i++){
            depotList.get(i).placeInDepot(depotToResource.get(i), depotToQuantity.get(i));
        }
        for(int i=0;i<resourceToLeader.size();i++){
            depotList.get(i+3).setResourceQuantity(resourceToLeader.get(i));
        }
    }

    /**
     * This method is used to know if there is a certain ResourceType in the Warehouse.
     * @param res ResourceType that are you looking for.
     * @return the depot index that have res, -1 if Warehouse does not have the requested resource.
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
     * This method is used to see if warehouse is empty.
     * @return true if warehouse is empty, false otherwise.
     */
    public boolean isEmpty() {
        for(int i=0; i<depotList.size(); i++){
            if(depotList.get(i).getResourceQuantity() != 0 && depotList.get(i).getResourceType().getVal() != -1)
                return false;
        }
        return true;
    }

    /**
     * This method is used to obtain the available space for each depot.
     * @return an ArrayList of Integer representing the available space for each Depot.
     */
    public ArrayList<Integer> getSpace(){
        ArrayList<Integer> space= new ArrayList<>();
        for(int i=0; i<depotList.size(); i++){
            space.add(i,depotList.get(i).getSize() - depotList.get(i).getResourceQuantity());
        }
        return space;
    }

    /**
     * Used to obtain a HashMap representing the ResourceType for each Depot.
     * @return a HashMap representing each ResourceType per floor.
     */
    public HashMap<Integer,ResourceType> getDepotToResource(){
        HashMap<Integer,ResourceType> depotToResource=new HashMap<Integer, ResourceType>();
        for(int i=0; i<depotList.size();i++){
            depotToResource.put(i, depotList.get(i).getResourceType());
        }
        return depotToResource;
    }

    /**
     * Used to obtain a HashMap representing the ResourceType quantity for each Depot.
     * @return a HashMap representing each ResourceType quantity per floor.
     */
    public HashMap<Integer,Integer> getDepotToQuantity(){
        HashMap<Integer,Integer> depotToQuantity=new HashMap<Integer, Integer>();
        for(int i=0; i<depotList.size();i++){
            depotToQuantity.put(i, depotList.get(i).getResourceQuantity());
        }
        return depotToQuantity;
    }

    /**
     * This method returns a list of ResourceType representing the Leader Depot Resource.
     * @return the Leader Depot Resource as an ArrayList.
     */
    public ArrayList<ResourceType> getLeaderDepot(){
        ArrayList<ResourceType> leaderDepot=new ArrayList<ResourceType>();
        for(int i=3; i<depotList.size();i++){
            leaderDepot.add(depotList.get(i).getResourceType());
        }
        return leaderDepot;
    }

    public ArrayList<Depot> getDepotList(){
        return depotList;
    }
}
