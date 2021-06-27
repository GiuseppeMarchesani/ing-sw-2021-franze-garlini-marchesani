package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Card.DevCardSlot;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowPlayerReply extends ServerMessage {
    private int faithSpace;
    private HashMap<Integer,ResourceType> depotToResource;
    private HashMap<Integer,Integer> depotToQuantity;
    private HashMap<ResourceType,Integer> strongbox;
    private DevCardSlot devCardSlot;
    private ArrayList<LeaderCard> playedLeaderCards;
    private String username;
    private int remainingLeaderCards;
    public ShowPlayerReply(String username,int faithSpace, HashMap<Integer, ResourceType> depotToResource, HashMap<Integer, Integer> depotToQuantity, HashMap<ResourceType, Integer> strongbox, DevCardSlot devCardSlot, ArrayList<LeaderCard> playedLeaderCards,int remainingLeaderCards) {
        super(MessageType.SHOW_PLAYER);
        this.username=username;
        this.faithSpace=faithSpace;
        this.depotToResource=depotToResource;
        this.depotToQuantity=depotToQuantity;
        this.strongbox=strongbox;
        this.playedLeaderCards=playedLeaderCards;
        this.devCardSlot=devCardSlot;
        this.remainingLeaderCards=remainingLeaderCards;
    }

    public ArrayList<LeaderCard> getPlayedLeaderCards() {
        return playedLeaderCards;
    }

    public HashMap<ResourceType, Integer> getStrongbox() {
        return strongbox;
    }

    public DevCardSlot getDevCardSlot() {
        return devCardSlot;
    }

    public HashMap<Integer, ResourceType> getDepotToResource() {
        return depotToResource;
    }

    public HashMap<Integer, Integer> getDepotToQuantity() {
        return depotToQuantity;
    }

    public int getFaithSpace() {
        return faithSpace;
    }

    public String getUsername() {
        return username;
    }

    public int getRemainingLeaderCards() {
        return remainingLeaderCards;
    }
}
