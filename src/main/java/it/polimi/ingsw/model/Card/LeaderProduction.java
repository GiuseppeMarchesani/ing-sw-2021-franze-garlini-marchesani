package it.polimi.ingsw.model.Card;

import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.LeaderCost;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class LeaderProduction extends LeaderCard<HashMap<Color, Integer>>  {

    private HashMap<ResourceType, Integer> productionIncome;

    /**
     * Default constructor
     * @param id id associated with the card
     * @param victoryPoints victory the points that received from this card
     * @param cost cost to buy the card
     * @param resourceAbility ability related to the card
     */
    public LeaderProduction(int id, int victoryPoints, HashMap<Color, Integer> cost, HashMap<ResourceType, Integer> productionIncome, ResourceType resourceAbility) {
        super(id, victoryPoints, resourceAbility, LeaderCost.LEVEL_TWO, cost);
        this.productionIncome = productionIncome;

    }

    @Override
    public void activateAbility(Player player) {
        player.getDevCardSlot().getSlotLeader().add(new DevCard(getId(), getResourceAbility(), productionIncome));
    }

    public Color getColorCost(){
        return getCost().entrySet().iterator().next().getKey();
    }

    public HashMap<ResourceType, Integer> getProductionIncome() {
        return productionIncome;
    }

    public String toString() {
        String leaderProdSTR = "Leader Card Depot: \n" +
                "id: " + getId() + "\n" +
                "card cost: " + "\n" +
                "{";

        for (Color color : getCost().keySet()) {
            if (getCost().get(color) > 0) {
                leaderProdSTR = leaderProdSTR.concat("\n    " + color.toString() + ": " + getCost().get(color) + "\n");
            }
        }
        leaderProdSTR = leaderProdSTR.concat("\n}\ndepot ability: " + getResourceAbility().toString() + "\nvictory points: " + getVP());
        return leaderProdSTR;
    }
}
