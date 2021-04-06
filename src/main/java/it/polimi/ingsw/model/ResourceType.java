package it.polimi.ingsw.model;

public class ResourceType {
    private static int resource;

    public ResourceType(int res){
        resource= res;
    }

    public static String toString(int res) {
        resource = res;
        String s = null;

        if ( res == 0) {
            s = "COIN";
        } else if (res == 1) {
            s = "STONE";
        } else if (res == 2) {
            s = "SERVANT";
        } else if (res == 3) {
            s = "SHIELD";
        } else if (res == 4) {
            s = "FAITH";
        } else if(res == 5){
            s = "EMPTY";
        }

        return s;
    }
}