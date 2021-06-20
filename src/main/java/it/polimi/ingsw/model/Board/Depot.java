package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.enumeration.ResourceType;

/**
 *  This class represents Warehouse's Depot and the special ones added by some LeaderCards.
 */
public class Depot {
    private ResourceType resourceType;
    private int resourceQuantity;
    private int size;
    private int arrangeable;

    /**
     * Default constructor.
     * @param size depot size.
     * @param arrangeable it says if the depot is arrangeable.
     */
    public Depot(int size, int arrangeable){
        resourceType = null;
        resourceQuantity = 0;
        this.size = size;
        this.arrangeable = arrangeable;
    }

    /**
     * Places resources in the depot.
     * @param resource type of the resource to place.
     * @param quantity quantity of the resource to place.
     */
    public void placeInDepot(ResourceType resource, int quantity){
        setResourceType(resource);
        setResourceQuantity(quantity);
    }

    public void setResourceType(ResourceType res) {
        resourceType = res;
    }

    public void setResourceQuantity(int n) {
        resourceQuantity = n;
    }

    public void setArrangeable(int arrangeable) {
        this.arrangeable = arrangeable;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public int getResourceQuantity() {
        return resourceQuantity;
    }

    public int getSize() {
        return size;
    }

    public int getArrangeable() {
        return arrangeable;
    }
}
