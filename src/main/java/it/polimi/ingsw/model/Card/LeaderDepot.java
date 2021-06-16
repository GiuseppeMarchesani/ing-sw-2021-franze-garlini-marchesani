package it.polimi.ingsw.model.Card;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.LeaderCost;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class LeaderDepot extends LeaderCard< HashMap<ResourceType, Integer>>  {
    /**
     * Default constructor
     * @param id id associated with the card
     * @param victoryPoints victory points that received from this card
     * @param cost cost to buy the card
     * @param resourceAbility ability related to the card
     */
    public LeaderDepot(int id, int victoryPoints, HashMap<ResourceType, Integer> cost, ResourceType resourceAbility) {
        super(id, victoryPoints, resourceAbility, LeaderCost.RESOURCES, cost);

    }

    public String toString() {
        String leaderDepotSTR = "Leader Card Depot: \n" +
                    "id: " + getId() + "\n" +
                    "card cost: " + "\n" +
                    "{";

        for (ResourceType res : getCost().keySet()) {
            if (getCost().get(res) > 0) {
                leaderDepotSTR = leaderDepotSTR.concat("\n    " + res.toString() + ": " + getCost().get(res));
            }
        }
        leaderDepotSTR = leaderDepotSTR.concat("\n}\ndepot ability: " + getResourceAbility().toString() + "\nvictory points: " + getVP() + "\n");
        return leaderDepotSTR;
    }

    @Override
    public void activateAbility(Player player) {
        player.getWarehouse().addDepot(getResourceAbility());
    }




}
