package it.polimi.ingsw.model.enumeration;

public enum ResourceType {
    ANY(-1),
    COIN(0),
    STONE(1),
    SERVANT(2),
    SHIELD(3),
    FAITH(4),
    EMPTY(5);

    private int resource;

    ResourceType(int resource) {
        this.resource = resource;
    }

    public int getVal(){
        return resource;
    }

}