package it.polimi.ingsw.model;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public class LeaderDepot extends Card implements LeaderCard  {
    private HashMap<ResourceType, Integer> resourceReq;
    private ResourceType resourceAbility;


    public LeaderDepot(int id, int victoryPoints, HashMap<ResourceType, Integer> resourceReq, ResourceType resourceAbility) {
        super(id, victoryPoints);
        this.resourceReq = resourceReq;
        this.resourceAbility = resourceAbility;

    }

    @Override
    public void activateAbility(Player player) {
        //TODO: Check if the player can activate it
        player.getWarehouse().addDepot(resourceAbility);
    }

    public HashMap<ResourceType, Integer> getResourceReq(){
        return resourceReq;
    }

    public ResourceType getResourceAbility() {
        return resourceAbility;
    }

}
