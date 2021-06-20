package it.polimi.ingsw.model.Card;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

/**
 * Represents the Development Card.
 */
public class DevCard extends Card {
    private DevCardType cardType;
    private HashMap<ResourceType, Integer> cardCost;
    private HashMap<ResourceType, Integer> productionCost;
    private HashMap<ResourceType, Integer> productionIncome;
    private final int STARTING_LEADER_ID = 49;

    /**
     * Default Constructor.
     * @param id Card id.
     * @param victoryPoints Victory points you can receive buying this card.
     * @param cardType Color and level of the card.
     * @param cardCost Resources you must pay to buy this card.
     * @param productionCost Resources you must pay to activate production.
     * @param productionIncome Resources you will get after production.
     */
    public DevCard(int id, int victoryPoints, DevCardType cardType, HashMap<ResourceType, Integer> cardCost, HashMap<ResourceType, Integer> productionCost, HashMap<ResourceType, Integer> productionIncome){
        super(id,victoryPoints);
        this.cardType = cardType;
        this.cardCost = cardCost;
        this.productionCost = productionCost;
        this.productionIncome = productionIncome;
    }

    /**
     * This constructor is used for base production (scroll).
     */
    public DevCard(){
        super(0,0);
        cardType=null;
        cardCost=null;
        productionCost= new HashMap<ResourceType, Integer>();
        productionCost.put(ResourceType.ANY, 2);
        productionIncome= new HashMap<ResourceType, Integer>();
        productionIncome.put(ResourceType.ANY, 1);
    }

    /**
     * This constructor is used for LeaderCard Production
     * @param id card id.
     * @param resourceAbility resource associated to the ability of the card, this will be the production cost.
     * @param productionIncome resources the player will receive after production activation.
     */
    public DevCard(int id, ResourceType resourceAbility, HashMap<ResourceType, Integer> productionIncome) {
        super(id,0);
        this.cardType = null;
        this.cardCost = null;
        this.productionCost = new HashMap<ResourceType, Integer>();
        this.productionCost.put(resourceAbility, 1);
        this.productionIncome = productionIncome;
    }

    @Override
    public String toString() {
        String devcardSTR;

        //Base production scroll
        if(getId()==0) {
            devcardSTR = "Base production scroll: \n" +
                    "id: " + getId() +"\n" +
                    "Cost: 2 ANY\n" +
                    "Income: 1 ANY\n";
            return devcardSTR;
        }

        //Leader Card Production
        else if(getId() >= STARTING_LEADER_ID) {
            devcardSTR = "Leader Card Production: \n" +
                    "id: " + getId();
        }
        else {
            devcardSTR = "DevCard: \n" +
                    "id: " + getId() + "\n" +
                    "color: " + getCardType().getColor() + "\n" +
                    "level: " + getCardType().getLevel() + "\n" +
                    "victory points: " + getVP() + "\n" +
                    "card cost: " + "\n" +
                    "{";
            for (ResourceType res : getCardCost().keySet()) {
                if (getCardCost().get(res) > 0) {
                    devcardSTR = devcardSTR.concat("\n    " + res.toString() + ": " + getCardCost().get(res));
                }
            }
            devcardSTR = devcardSTR.concat("\n}");
        }
        devcardSTR = devcardSTR.concat("\n production cost: \n{");
        for(ResourceType res: getProductionCost().keySet()) {
            if (getProductionCost().get(res) > 0) {
                devcardSTR = devcardSTR.concat("\n    " + res.toString() + ": " + getProductionCost().get(res));
            }
        }
        devcardSTR = devcardSTR.concat("\n} \n production income: \n{");
        for(ResourceType res: getProductionIncome().keySet()) {
            if (getProductionIncome().get(res) > 0) {
                devcardSTR = devcardSTR.concat("\n    " + res.toString() + ": " + getProductionIncome().get(res));
            }
        }
        devcardSTR = devcardSTR.concat("\n}\n");
        return devcardSTR;
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
