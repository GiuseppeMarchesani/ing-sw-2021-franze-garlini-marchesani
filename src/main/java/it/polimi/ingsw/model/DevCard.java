package it.polimi.ingsw.model;

import java.util.HashMap;
public class DevCard extends Card {
    private DevCardType cardType;
    private HashMap<ResourceType, Integer> cardCost;
    private HashMap<ResourceType, Integer> productionCost;
    private HashMap<ResourceType, Integer> productionIncome;

    public DevCard(int i, int vp, int c, int l, HashMap<ResourceType, Integer> cc, HashMap<ResourceType, Integer> pc, HashMap<ResourceType, Integer> pi){
        super(i,vp);
        cardType=new DevCardType(c,l);
        cardCost=cc;
        productionCost=pc;
        productionIncome=pi;
    }
    public DevCard(){
        super(0,0);
        cardType=null;
        cardCost=null;
        productionCost= new HashMap<ResourceType, Integer>();
        productionCost.put(new ResourceType(1), Integer.valueOf(2));
        productionIncome= new HashMap<ResourceType, Integer>();
        productionIncome.put(new ResourceType(1), Integer.valueOf(1));
    }
    public DevCard(ResourceType R){
        super(0,0);
        cardType=null;
        cardCost=null;
        productionCost= new HashMap<ResourceType, Integer>();
        productionCost.put(new ResourceType(R.getResource()), Integer.valueOf(1));
        productionIncome= new HashMap<ResourceType, Integer>();
        productionIncome.put(new ResourceType(1), Integer.valueOf(1));
        productionIncome.put(new ResourceType(6), Integer.valueOf(1));
    }
    public DevCardType getCardType()
    {
        return cardType;
    }
    public HashMap<ResourceType, Integer> getCardCost(){
        return cardCost;
    }
    public HashMap<ResourceType, Integer> getProductionCost(){
        return productionCost;
    }
    public void setCardType(DevCardType x)
    {
        cardType=x;
    }
    public void setCardCost(HashMap<ResourceType, Integer> x)
    {
        cardCost=x;
    }
    public void setProductionCost(HashMap<ResourceType, Integer> x)
    {
        productionCost=x;
    }
    public void setProductionIncome(HashMap<ResourceType, Integer> x)
    {
        productionIncome=x;
    }
    public HashMap<ResourceType, Integer> getProductionIncome() {
        return productionIncome;
    }
}
