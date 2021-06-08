package it.polimi.ingsw.model.Card;

import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class LeaderDiscount extends Card implements LeaderCard  {
    private HashMap<Color, Integer> colorReq;
    private ResourceType resourceAbility;

    public LeaderDiscount(int id, int victoryPoints, HashMap<Color, Integer> colorReq, ResourceType resourceAbility) {
        super(id, victoryPoints);
        this.colorReq = colorReq;
        this.resourceAbility = resourceAbility;
    }

    @Override
    public void activateAbility(Player player) {
        if(player.getResourceDiscount().containsKey(resourceAbility)) {
            player.getResourceDiscount().put(resourceAbility, player.getResourceDiscount().get(resourceAbility)+1);
        }
        else{
            player.getResourceDiscount().put(resourceAbility, 1);
        };
    }

    @Override
    public int getLeaderID() {
        return this.getId();
    }

    public HashMap<Color, Integer> getDevCardTypeReq() {
        return colorReq;
    }

    public void setDevCardTypeReq(HashMap<Color, Integer> colorReq) {
        this.colorReq = colorReq;
    }

    public ResourceType getResourceAbility() {
        return resourceAbility;
    }

    public void setResourceAbility(ResourceType resourceAbility) {
        this.resourceAbility = resourceAbility;
    }
    public String toString(){
        return resourceAbility.name()+" Discount";
    }
}
