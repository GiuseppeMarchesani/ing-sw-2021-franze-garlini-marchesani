package it.polimi.ingsw.model;

public class ResourceType {
    private int resource;

    public ResourceType(int x){
        resource=x;
    }
    public ResourceType(String x){
        switch (x){

            case "ANY":
               resource=1;
                break;
            case "SERVANT":
                resource=2;
                break;
            case "SHIELD":
                resource=3;
                break;
            case "STONE":
                resource=4;
                break;
            case "COIN":
                resource=5;
                break;
            case "FAITH":
                resource=6;
                break;
            default:
                resource=0;
        }
    }
    public String toString(int res) {
        String s;
        switch (res){

            case 1:
                s= "ANY";
                break;
            case 2:
                s="SERVANT";
                break;
            case 3:
                s="SHIELD";
                break;
            case 4:
                s="STONE";
                break;
            case 5:
                s="COIN";
                break;
            case 6:
                s="FAITH";
                break;
            default:
                s="EMPTY";
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