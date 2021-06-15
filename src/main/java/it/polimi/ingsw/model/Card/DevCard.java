package it.polimi.ingsw.model.Card;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class DevCard extends Card {
    private DevCardType cardType;
    private HashMap<ResourceType, Integer> cardCost;
    private HashMap<ResourceType, Integer> productionCost;
    private HashMap<ResourceType, Integer> productionIncome;
    private final int STARTING_LEADER_ID = 49;

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
    public DevCard(int id, ResourceType resourceAbility, HashMap<ResourceType, Integer> productionIncome) {
        super(id,0);
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

    @Override
    public String toString() {
        String devcardSTR;
        if(getId()==0) {
            devcardSTR = "Base production scroll: \n" +
                    "id: " + getId() +"\n" +
                    "Cost: 2 ANY\n" +
                    "Income: 1 ANY\n";
            return devcardSTR;
        }
        else if(getId() >= STARTING_LEADER_ID) {
            devcardSTR = "Leader Card Production: \n" +
                    "id: " + getId() + "\n";
        }
        else {
            devcardSTR = "DevCard: \n" +
                    "id: " + getId() + "\n" +
                    "color: " + getCardType().getColor() + "\n" +
                    "level: " + getCardType().getLevel() + "\n" +
                    "card cost: " + "\n" +
                    "{";
            for (ResourceType res : getCardCost().keySet()) {
                if (getCardCost().get(res) > 0) {
                 devcardSTR = devcardSTR.concat("\n    " + res.toString() + ": " + getCardCost().get(res) + "\n");
                }
            }
            devcardSTR = devcardSTR.concat("}");
        }
        devcardSTR = devcardSTR.concat("\n production cost: \n{");
        for(ResourceType res: getProductionCost().keySet()) {
            if (getProductionCost().get(res) > 0) {
                devcardSTR = devcardSTR.concat("\n    " + res.toString() + ": " + getProductionCost().get(res) + "\n");
            }
        }
        devcardSTR = devcardSTR.concat("} \n production income: \n{");
        for(ResourceType res: getProductionIncome().keySet()) {
            if (getProductionIncome().get(res) > 0) {
                devcardSTR = devcardSTR.concat("\n    " + res.toString() + ": " + getProductionIncome().get(res) + "\n");
            }
        }
        devcardSTR = devcardSTR.concat("}\n");
        return devcardSTR;
    }

    public HashMap<ResourceType, Integer> getProductionIncome() {
        return productionIncome;
    }
}
