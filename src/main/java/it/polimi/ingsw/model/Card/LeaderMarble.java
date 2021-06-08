package it.polimi.ingsw.model.Card;

import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.LeaderCost;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class LeaderMarble extends LeaderCard<HashMap<Color, Integer>> {

    public LeaderMarble(int id, int victoryPoints, HashMap<Color, Integer> cost, ResourceType resourceAbility) {
        super(id, victoryPoints, resourceAbility, LeaderCost.DEV_CARD_DOUBLE, cost);
    }

    @Override
    public void activateAbility(Player player) {
        if (!(player.getMarbleConversion().contains(getResourceAbility()))) {
            player.getMarbleConversion().add(getResourceAbility());
        }
    }


    @Override
    public String toString(){
        return getResourceAbility().name()+" Marble Conversion";
    }
}
