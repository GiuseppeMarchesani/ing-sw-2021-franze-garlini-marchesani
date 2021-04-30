package it.polimi.ingsw.model;

import java.util.HashMap;

public class LeaderProduction extends Card implements LeaderCard  {
    private HashMap<DevCardType, Integer> devCardTypeReq;
    private HashMap<ResourceType, Integer> productionIncome;
    private ResourceType resourceAbility;


    public LeaderProduction(int id, int victoryPoints, HashMap<DevCardType, Integer> devCardTypeReq, HashMap<ResourceType, Integer> productionIncome, ResourceType resourceAbility) {
        super(id, victoryPoints);
        this.devCardTypeReq = devCardTypeReq;
        this.productionIncome = productionIncome;
        this.resourceAbility = resourceAbility;
    }

    @Override
    public void activateAbility(Player player) {
        //TODO: Check if player can activate it
        player.getDevCardSlot().getSlotLeader().add(new DevCard(resourceAbility, productionIncome));
    }

}
