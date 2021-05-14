package it.polimi.ingsw.model.Card;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.ResourceType;

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
        player.getWarehouse().addDepot(resourceAbility);
    }

    @Override
    public int getLeaderID() {
        return this.getId();
    }

    public HashMap<ResourceType, Integer> getResourceReq(){
        return resourceReq;
    }

    public ResourceType getResourceAbility() {
        return resourceAbility;
    }

    public void setResourceReq(HashMap<ResourceType, Integer> resourceReq) {
        this.resourceReq = resourceReq;
    }

    public void setResourceAbility(ResourceType resourceAbility) {
        this.resourceAbility = resourceAbility;
    }

}
