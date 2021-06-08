package it.polimi.ingsw.model.Card;

import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class LeaderProduction extends Card implements LeaderCard  {
    private HashMap<Color, Integer> devCardTypeReq;
    private HashMap<ResourceType, Integer> productionIncome;
    private ResourceType resourceAbility;


    public LeaderProduction(int id, int victoryPoints, HashMap<Color, Integer> devCardTypeReq, HashMap<ResourceType, Integer> productionIncome, ResourceType resourceAbility) {
        super(id, victoryPoints);
        this.devCardTypeReq = devCardTypeReq;
        this.productionIncome = productionIncome;
        this.resourceAbility = resourceAbility;
    }

    @Override
    public void activateAbility(Player player) {
        player.getDevCardSlot().getSlotLeader().add(new DevCard(getId(), resourceAbility, productionIncome));
    }

    @Override
    public int getLeaderID() {
        return this.getId();
    }

    public HashMap<Color, Integer> getDevCardTypeReq() {
        return devCardTypeReq;
    }

    public void setDevCardTypeReq(HashMap<Color, Integer> devCardTypeReq) {
        this.devCardTypeReq = devCardTypeReq;
    }

    public HashMap<ResourceType, Integer> getProductionIncome() {
        return productionIncome;
    }

    public void setProductionIncome(HashMap<ResourceType, Integer> productionIncome) {
        this.productionIncome = productionIncome;
    }

    public ResourceType getResourceAbility() {
        return resourceAbility;
    }

    public void setResourceAbility(ResourceType resourceAbility) {
        this.resourceAbility = resourceAbility;
    }

    public String toString(){
        return resourceAbility.name()+" Production";
    }
}
