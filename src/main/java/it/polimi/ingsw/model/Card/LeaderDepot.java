package it.polimi.ingsw.model.Card;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.LeaderCost;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class LeaderDepot extends LeaderCard< HashMap<ResourceType, Integer>>  {

    public LeaderDepot(int id, int victoryPoints, HashMap<ResourceType, Integer> cost, ResourceType resourceAbility) {
        super(id, victoryPoints, resourceAbility, LeaderCost.RESOURCES, cost);

    }

    @Override
    public void activateAbility(Player player) {
        player.getWarehouse().addDepot(getResourceAbility());
    }

     public String toString(){
        return getResourceAbility().name()+" Depot";
    }

}
