package it.polimi.ingsw.model;

public class Marble {
    private ResourceType resourceType;
    public Marble(ResourceType res){
        resourceType= res;
    }
    public Marble(){

    }
    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setType(ResourceType res) {
        resourceType = res;
    }

}
