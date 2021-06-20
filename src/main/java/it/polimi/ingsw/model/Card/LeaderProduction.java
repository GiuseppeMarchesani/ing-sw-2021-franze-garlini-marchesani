package it.polimi.ingsw.model.Card;

import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.LeaderCost;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

/**
 * This class is used to represents the Leader Card which has the production ability.
 */
public class LeaderProduction extends LeaderCard  {

    private HashMap<ResourceType, Integer> productionIncome;
    private HashMap<Color, Integer> cost;

    /**
     * Default constructor.
     * @param id id associated with the card.
     * @param victoryPoints victory points you can receive playing this card.
     * @param cost required resources or cards you must have to play this card.
     * @param resourceAbility ability related to the card.
     */
    public LeaderProduction(int id, int victoryPoints, HashMap<Color, Integer> cost, HashMap<ResourceType, Integer> productionIncome, ResourceType resourceAbility) {
        super(id, victoryPoints, resourceAbility);
        this.cost=cost;
        this.productionIncome = productionIncome;

    }

    @Override
    public void activateAbility(Player player) {
        player.getDevCardSlot().getSlotLeader().add(new DevCard(getId(), getResourceAbility(), productionIncome));
    }

    public HashMap<ResourceType, Integer> getProductionIncome() {
        return productionIncome;
    }

    public String toString() {
        String leaderProdSTR = "Leader Card Production: \n" +
                "id: " + getId() + "\n" +
                "requirement: " + "\n" +
                "{";

        for (Color color : cost.keySet()) {
            if (cost.get(color) > 0) {
                leaderProdSTR = leaderProdSTR.concat("\n    " + color.toString() + ": " + cost.get(color));
            }
        }
        leaderProdSTR = leaderProdSTR.concat("\n}\nresource ability: " + getResourceAbility().toString() + "\nvictory points: " + getVP() + "\n");
        return leaderProdSTR;
    }

    @Override
    public LeaderCost getCostType() {
        return LeaderCost.LEVEL_TWO;
    }

    @Override
    public HashMap<Color, Integer> getLevelTwoCost() {
        return cost;
    }

    public Color getColorCost(){
        return cost.entrySet().iterator().next().getKey();
    }
}
