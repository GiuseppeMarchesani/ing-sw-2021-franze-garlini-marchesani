package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

public class ResToPayMsg extends GeneralMessage{
    private ResourceType res;
    private String WorS; //from Warehouse or Strongbox
    public ResToPayMsg(String username, MessageType messageType, String gameID, ResourceType res, String WorS) {
        super(username, messageType, gameID);
        this.res=res;
        this.WorS=WorS;
    }

    public ResourceType getRes() {
        return res;
    }

    public String getWorS() {
        return WorS;
    }
}
