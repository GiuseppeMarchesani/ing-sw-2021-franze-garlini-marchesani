package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.enumeration.ResourceType;

public class Depot {
    private ResourceType resourceType;
    private int resourceQuantity;
    private int size;
    private int rearrangeble;

    /**
     * Class Constructor.
     * @param x (size of depot)
     */
    public Depot(int x, int r){
        resourceType = null;
        resourceQuantity = 0;
        size = x;
        rearrangeble=r;
    }

    public ResourceType getResourceType(){ return resourceType; }

    public void setResourceType(ResourceType res){ resourceType = res; }

    public int getResourceQuantity() { return resourceQuantity; }

    public void setResourceQuantity(int n){ resourceQuantity = n; }

    public int getSize() { return size; }

    public int setSize(int s) {
        size = s;
        return s;
    }

    public int getRearrangeble() {
        return rearrangeble;
    }

    public void setRearrangeble(int rearrangeble) {
        this.rearrangeble = rearrangeble;
    }
}
