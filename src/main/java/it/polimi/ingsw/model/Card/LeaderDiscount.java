package it.polimi.ingsw.model.Card;

import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.LeaderCost;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class LeaderDiscount extends LeaderCard<HashMap<Color, Integer>>  {
     /**
     * Default constructor
     * @param id id associated with the card
     * @param victoryPoints victory the points that from this card
     * @param cost cost to buy the card
     * @param resourceAbility ability related to the card
     */
    public LeaderDiscount(int id, int victoryPoints, HashMap<Color, Integer> cost, ResourceType resourceAbility) {
        super(id, victoryPoints, resourceAbility, LeaderCost.DEV_CARD_SINGLE, cost);
    }

    @Override
    public void activateAbility(Player player) {
        if(player.getResourceDiscount().containsKey(getResourceAbility())) {
            player.getResourceDiscount().put(getResourceAbility(), player.getResourceDiscount().get(getResourceAbility())+1);
        }
        else{
            player.getResourceDiscount().put(getResourceAbility(), 1);
        }
    }
    public String toString() {
        String leaderDiscountSTR = "Leader Card Depot: \n" +
                "id: " + getId() + "\n" +
                "card cost: " + "\n" +
                "{";

        for (Color color : getCost().keySet()) {
            if (getCost().get(color) > 0) {
                leaderDiscountSTR = leaderDiscountSTR.concat("\n    " + color.toString() + ": " + getCost().get(color));
            }
        }
        leaderDiscountSTR = leaderDiscountSTR.concat("\n}\ndepot ability: " + getResourceAbility().toString() + "\nvictory points: " + getVP() + "\n");
        return leaderDiscountSTR;
    }
}
