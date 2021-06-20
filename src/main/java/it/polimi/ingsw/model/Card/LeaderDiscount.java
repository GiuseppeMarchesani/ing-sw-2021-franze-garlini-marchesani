package it.polimi.ingsw.model.Card;

import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.LeaderCost;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

/**
 * This class is used to represents the Leader Card which has discount ability.
 */
public class LeaderDiscount extends LeaderCard  {
    private HashMap<Color, Integer> cost;

     /**
     * Default constructor.
     * @param id id associated with the card
     * @param victoryPoints victory points you can receive playing this card.
     * @param cost required resources or cards you must have to play this card.
     * @param resourceAbility ability related to the card.
     */
    public LeaderDiscount(int id, int victoryPoints, HashMap<Color, Integer> cost, ResourceType resourceAbility) {
        super(id, victoryPoints, resourceAbility);
        this.cost=cost;
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
        String leaderDiscountSTR = "Leader Card Discount: \n" +
                "id: " + getId() + "\n" +
                "requirement: " + "\n" +
                "{";

        for (Color color : cost.keySet()) {
            if (cost.get(color) > 0) {
                leaderDiscountSTR = leaderDiscountSTR.concat("\n    " + color.toString() + ": " + cost.get(color));
            }
        }
        leaderDiscountSTR = leaderDiscountSTR.concat("\n}\nresource ability: " + getResourceAbility().toString() + "\nvictory points: " + getVP() + "\n");
        return leaderDiscountSTR;
    }

    @Override
    public LeaderCost getCostType() {
        return LeaderCost.DEV_CARD_SINGLE;
    }

    @Override
    public HashMap<Color, Integer> getCardCost() {
        return cost;
    }
}
