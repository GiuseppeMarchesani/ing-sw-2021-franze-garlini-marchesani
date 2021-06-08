package it.polimi.ingsw.model.Card;

import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.LeaderCost;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class LeaderDiscount extends LeaderCard<HashMap<Color, Integer>>  {
    public LeaderDiscount(int id, int victoryPoints, HashMap<Color, Integer> cost, ResourceType resourceAbility) {
        super(id, victoryPoints, resourceAbility, LeaderCost.DEV_CARD_SINGLE, cost);
    }

    @Override
    public void activateAbility(Player player) {
        if(player.getResourceDiscount().containsKey(getResourceAbility())) {
            player.getResourceDiscount().put(getResourceAbility(), player.getResourceDiscount().get(getResourceAbility())+1);
        }
        else{
            player.getResourceDiscount().put(getResourceAbility(), 1);
        }
    }
    public String toString(){
        return getResourceAbility().name()+" Discount";
    }
}
