package it.polimi.ingsw.model.Card;


import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.LeaderCost;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public abstract class LeaderCard extends Card{

    private ResourceType resourceAbility;
    public LeaderCard(int id, int victoryPoints, ResourceType resourceAbility) {
        super(id, victoryPoints);
        this.resourceAbility = resourceAbility;
    }

    public abstract void activateAbility(Player player);


    public  abstract String toString();

    public  abstract LeaderCost getCostType();

    public HashMap<Color, Integer> getCardCost(){
        return null;
    }
    public HashMap<Color, Integer> getLevelTwoCost(){
        return null;
    }
    public HashMap<ResourceType, Integer> getResourceCost(){
        return null;
    }
    public ResourceType getResourceAbility() {
        return resourceAbility;
    }
}