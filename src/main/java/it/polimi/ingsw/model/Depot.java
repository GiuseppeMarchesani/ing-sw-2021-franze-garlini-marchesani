package it.polimi.ingsw.model;

public class Depot {
    private ResourceType resourceType;
    private int resourceQuantity;
    private int size;
    private boolean rearrangeable;

    /**
     * Class Constructor.
     * @param res (type of resource in the depot)
     * @param n (quantity of resources)
     * @param x (size of depot)
     */
    public Depot(ResourceType res, int n, int x){
        resourceType = res;
        resourceQuantity = n;
        size = x;
    }

    public ResourceType getResourceType(){

        return resourceType;
    }

    public void setResourceType(ResourceType res){

        resourceType = res;
    }

    public int getResourceQuantity() {

        return resourceQuantity;
    }

    public void setResourceQuantity(int n){

        resourceQuantity = n;
    }

    public int getSize() {

        return size;
    }

    public int setSize(int s) {
        size = s;
        return s;
    }
}
