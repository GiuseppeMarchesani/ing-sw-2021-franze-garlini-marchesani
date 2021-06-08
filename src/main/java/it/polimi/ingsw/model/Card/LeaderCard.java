package it.polimi.ingsw.model.Card;


import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.LeaderCost;
import it.polimi.ingsw.model.enumeration.ResourceType;

public abstract class LeaderCard<T> extends Card{

    private T cost;
    private ResourceType resourceAbility;
    private LeaderCost costType;
    public LeaderCard(int id, int victoryPoints, ResourceType resourceAbility, LeaderCost costType, T cost) {
        super(id, victoryPoints);
        this.resourceAbility = resourceAbility;
        this.costType=costType;
        this.cost=cost;
    }

    public abstract void activateAbility(Player player);


    public  abstract String toString();

    public  LeaderCost getCostType(){ return costType;}
    public  T getCost(){
        return cost;
    }
    public ResourceType getResourceAbility() {
        return resourceAbility;
    }
}