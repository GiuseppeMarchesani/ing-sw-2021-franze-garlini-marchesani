package it.polimi.ingsw.model;

public class ResourceType {
    private int resource;

    public String toString(int res) {
        String s = null;
        switch (res){
            case 0:
                s="COIN";
                break;
            case 1:
                s= "STONE";
                break;
            case 2:
                s="SERVANT";
                break;
            case 3:
                s="SHIELD";
                break;
            case 4:
                s="FAITH";
                break;
            case 5:
                s="EMPTY";
                break;
        }
        return s;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int res) {
        resource = res;
    }
}