package it.polimi.ingsw.model.Card;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.LeaderCost;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class LeaderDepot extends LeaderCard  {
    private HashMap<ResourceType, Integer> cost;
    /**
     * Default constructor
     * @param id id associated with the card
     * @param victoryPoints victory points that received from this card
     * @param cost cost to buy the card
     * @param resourceAbility ability related to the card
     */
    public LeaderDepot(int id, int victoryPoints, HashMap<ResourceType, Integer> cost, ResourceType resourceAbility) {
        super(id, victoryPoints, resourceAbility);
        this.cost=cost;

    }

    public String toString() {
        String leaderDepotSTR = "Leader Card Depot: \n" +
                    "id: " + getId() + "\n" +
                    "requirement: " + "\n" +
                    "{";

        for (ResourceType res : cost.keySet()) {
            if (cost.get(res) > 0) {
                leaderDepotSTR = leaderDepotSTR.concat("\n    " + res.toString() + ": " + cost.get(res));
            }
        }
        leaderDepotSTR = leaderDepotSTR.concat("\n}\nresource ability: " + getResourceAbility().toString() + "\nvictory points: " + getVP() + "\n");
        return leaderDepotSTR;
    }

    @Override
    public void activateAbility(Player player) {
        player.getWarehouse().addDepot(getResourceAbility());
    }

    @Override
    public LeaderCost getCostType() {
        return LeaderCost.RESOURCES;
    }

    @Override
    public HashMap<ResourceType, Integer> getResourceCost() {
        return cost;
    }
}
