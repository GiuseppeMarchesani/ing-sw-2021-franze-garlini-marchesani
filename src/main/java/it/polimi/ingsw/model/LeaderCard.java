package it.polimi.ingsw.model;

import java.util.HashMap;
import java.lang.reflect.*;

public class LeaderCard extends Card{
    private HashMap<ResourceType, Integer> resourceReq;
    private HashMap<DevCardType, Integer> devCardReq;
    private Ability ability;
    public LeaderCard(int i, int v, HashMap<ResourceType, Integer> rr, HashMap<DevCardType, Integer> dcr, String a, ResourceType rt) {
        super(i, v);
        resourceReq=rr;
        devCardReq=dcr;
        try {
            Class cl = null;
            cl = Class.forName("it.polimi.ingsw.model."+a);
            Constructor con = null;
            con = cl.getConstructor(ResourceType.class);
            ability = (Ability) con.newInstance(rt);
        } catch (Exception e) {
            e.printStackTrace();
        }
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