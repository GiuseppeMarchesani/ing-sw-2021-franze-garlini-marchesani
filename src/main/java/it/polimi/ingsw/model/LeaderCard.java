package it.polimi.ingsw.model;

import java.util.HashMap;

public class LeaderCard extends Card{
    private HashMap<ResourceType, Integer> resourceReq;
    private HashMap<DevCardType, Integer> devCardReq;
    private Ability ability;
    public LeaderCard(int i, int v, HashMap<ResourceType, Integer> rr, HashMap<DevCardType, Integer> dcr, Ability a) {
        super(i, v);
        resourceReq=rr;
        devCardReq=dcr;
        ability=a;
    }
    public HashMap<ResourceType, Integer> getResourceReq(){
        return resourceReq;
    }
    public HashMap<DevCardType, Integer> getDevCardReq(){
        return devCardReq;
    }
    public Ability getAbility(){
        return ability;
    }
}