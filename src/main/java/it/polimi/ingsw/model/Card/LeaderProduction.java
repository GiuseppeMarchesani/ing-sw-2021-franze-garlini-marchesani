package it.polimi.ingsw.model.Card;

import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.LeaderCost;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class LeaderProduction extends LeaderCard<HashMap<Color, Integer>>  {

    private HashMap<ResourceType, Integer> productionIncome;

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
    @Override
    public String toString(){
        return getResourceAbility().name()+" Production";
    }

}
