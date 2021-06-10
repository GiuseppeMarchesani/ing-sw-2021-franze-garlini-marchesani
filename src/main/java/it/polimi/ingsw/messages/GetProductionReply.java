package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;

public class GetProductionReply extends GeneralMessage {


    private HashMap<ResourceType, Integer> strongbox;
    private HashMap<ResourceType, Integer> price;
    private int anyPayment;
    private int anyProduce;
    public GetProductionReply(HashMap<ResourceType, Integer> strongbox, HashMap<ResourceType, Integer> price, int anyPayment, int anyProduce) {
        super(MessageType.ACTIVATE_PRODUCTION);
        this.strongbox=strongbox;
        this.price=price;
        this.anyPayment=anyPayment;
        this.anyProduce=anyProduce;
    }

    public int getAnyPayment() {
        return anyPayment;
    }

    public HashMap<ResourceType, Integer> getPrice() {
        return price;
    }

    public int getAnyProduce() {
        return anyProduce;
    }

    public HashMap<ResourceType, Integer> getStrongbox() {
        return strongbox;
    }
}