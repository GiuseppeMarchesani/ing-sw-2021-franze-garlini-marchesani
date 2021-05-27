package it.polimi.ingsw.messages;

public class RearrangeMsg extends GeneralMessage{
    private boolean rearrange;
    private int depot1;
    private int depot2;
    public RearrangeMsg(String username, String gameID, boolean rearrange,
                        int depot1, int depot2) {
        super(username, gameID);
        this.rearrange=rearrange;
        this.depot1=depot1;
        this.depot2=depot2;
    }

    public boolean isRearrange() {
        return rearrange;
    }

    public int getDepot1() {
        return depot1;
    }

    public int getDepot2() {
        return depot2;
    }
}
