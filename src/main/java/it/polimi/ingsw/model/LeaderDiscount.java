package it.polimi.ingsw.model;

import java.util.HashMap;

public class LeaderDiscount extends Card implements LeaderCard  {
    private HashMap<DevCardType, Integer> devCardTypeReq;
    private ResourceType resourceAbility;

    public LeaderDiscount(int id, int victoryPoints, HashMap<DevCardType, Integer> devCardTypeReq, ResourceType resourceAbility) {
        super(id, victoryPoints);
        this.devCardTypeReq = devCardTypeReq;
        this.resourceAbility = resourceAbility;
    }

    @Override
    public void activateAbility(Player player) {
        //TODO: Check if the player can activate it
        if(player.getResourceDiscount().containsKey(resourceAbility)) {
            player.getResourceDiscount().put(resourceAbility, player.getResourceDiscount().get(resourceAbility)+1);
        }
        else{
            player.getResourceDiscount().put(resourceAbility, 1);
        };
    }

    public HashMap<DevCardType, Integer> getDevCardTypeReq() {
        return devCardTypeReq;
    }

    public void setDevCardTypeReq(HashMap<DevCardType, Integer> devCardTypeReq) {
        this.devCardTypeReq = devCardTypeReq;
    }

    public ResourceType getResourceAbility() {
        return resourceAbility;
    }

    public void setResourceAbility(ResourceType resourceAbility) {
        this.resourceAbility = resourceAbility;
    }
}
