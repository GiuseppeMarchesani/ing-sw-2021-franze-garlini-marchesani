package it.polimi.ingsw.model;

import java.util.HashMap;

public class LeaderMarble extends Card implements LeaderCard {
    private HashMap<DevCardType, Integer> devCardTypeReq;
    private ResourceType resourceAbility;

    public LeaderMarble(int id, int victoryPoints, HashMap<DevCardType, Integer> devCardTypeReq, ResourceType resourceAbility) {
            super(id, victoryPoints);
            this.devCardTypeReq = devCardTypeReq;
            this.resourceAbility = resourceAbility;
    }

    @Override
    public void activateAbility(Player player) {
        //TODO: Check if player can activate it
        if (!(player.getMarbleConversion().contains(resourceAbility))) {
            player.getMarbleConversion().add(resourceAbility);
        }
    }
}
