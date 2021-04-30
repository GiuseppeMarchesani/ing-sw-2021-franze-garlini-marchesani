package it.polimi.ingsw.model;

import java.util.HashMap;

public class DevCard extends Card {
    private DevCardType cardType;
    private HashMap<ResourceType, Integer> cardCost;
    private HashMap<ResourceType, Integer> productionCost;
    private HashMap<ResourceType, Integer> productionIncome;

    public DevCard(int id, int victoryPoints, DevCardType cardType, HashMap<ResourceType, Integer> cardCost, HashMap<ResourceType, Integer> productionCost, HashMap<ResourceType, Integer> productionIncome){
        super(id,victoryPoints);
        this.cardType = cardType;
        this.cardCost = cardCost;
        this.productionCost = productionCost;
        this.productionIncome = productionIncome;
    }

    //This constructor is used for base production (scroll)
    public DevCard(){
        super(0,0);
        cardType=null;
        cardCost=null;
        productionCost= new HashMap<ResourceType, Integer>();
        productionCost.put(ResourceType.ANY, 2);
        productionIncome= new HashMap<ResourceType, Integer>();
        productionIncome.put(ResourceType.ANY, 1);
    }

    //This constructor is used for LeaderCard Production
    public DevCard(ResourceType resourceAbility, HashMap<ResourceType, Integer> productionIncome) {
        super(-1,0);
        this.cardType = null;
        this.cardCost = null;
        this.productionCost = new HashMap<ResourceType, Integer>();
        this.productionCost.put(resourceAbility, 1);
        this.productionIncome = productionIncome;
    }

    public DevCardType getCardType()
    {
        return cardType;
    }

    public HashMap<ResourceType, Integer> getCardCost(){
        return cardCost;
    }

    public HashMap<ResourceType, Integer> getProductionCost(){
        return productionCost;
    }

    public void setCardType(DevCardType cardType)
    {
        this.cardType=cardType;
    }

    public void setCardCost(HashMap<ResourceType, Integer> cardCost)
    {
        this.cardCost = cardCost;
    }

    public void setProductionCost(HashMap<ResourceType, Integer> productionCost)
    {
        this.productionCost = productionCost;
    }

    public void setProductionIncome(HashMap<ResourceType, Integer> productionIncome)
    {
        this.productionIncome=productionIncome;
    }

    public HashMap<ResourceType, Integer> getProductionIncome() {
        return productionIncome;
    }
}
