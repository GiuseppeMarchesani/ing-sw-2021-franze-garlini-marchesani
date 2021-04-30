package it.polimi.ingsw.model;

import java.util.HashMap;

public class LeaderMarble extends Card implements LeaderCard {
    private HashMap<Color, Integer> colorReq;
    private ResourceType resourceAbility;

    public LeaderMarble(int id, int victoryPoints, HashMap<Color, Integer> colorReq, ResourceType resourceAbility) {
            super(id, victoryPoints);
            this.colorReq = colorReq;
            this.resourceAbility = resourceAbility;
    }

    @Override
    public void activateAbility(Player player) {
        //TODO: Check if player can activate it
        if (!(player.getMarbleConversion().contains(resourceAbility))) {
            player.getMarbleConversion().add(resourceAbility);
        }
    }

    public HashMap<Color, Integer> getDevCardTypeReq() {
        return colorReq;
    }

    public void setDevCardTypeReq(HashMap<Color, Integer> colorReq) {
        this.colorReq = colorReq;
    }

    public ResourceType getResourceAbility() {
        return resourceAbility;
    }

    public void setResourceAbility(ResourceType resourceAbility) {
        this.resourceAbility = resourceAbility;
    }
}
