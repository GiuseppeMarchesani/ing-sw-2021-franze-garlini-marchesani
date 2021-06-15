package it.polimi.ingsw.model.Card;

import it.polimi.ingsw.model.enumeration.Color;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This Class represents the Development Card slot.
 */
public class DevCardSlot implements Serializable {
    private ArrayList<ArrayList<DevCard>> slotDev;
    private final int slotNum = 3;
    private ArrayList<DevCard> slotLeader;

    /**
     * Class constructor.
     */
    public DevCardSlot(){
        slotDev = new ArrayList<ArrayList<DevCard>>();
        for(int i=0; i<slotNum;i++){
            slotDev.add(new ArrayList<DevCard>());
        }
        slotLeader = new ArrayList<DevCard>();
    }

    public ArrayList<ArrayList<DevCard>> getSlotDev(){
        return slotDev;
    }

    public ArrayList<DevCard> getSlotLeader(){
        return slotLeader;
    }

    /**
     * This method counts all the Development Card in the slots.
     * @return the number of Development Card in the slot.
     */
    public int getCardQuantity(){
        int cardQuantity = 0;
        for(int i=0; i<slotNum; i++) {
            cardQuantity += slotDev.get(i).size();
        }
        return cardQuantity;
    }

    /**
     * This method returns all the Development Cards available for production.
     * @return the Development Card available for production.
     */
    public ArrayList<DevCard> getCardsAvailable(){
        ArrayList<DevCard> devCards= new ArrayList<DevCard>();

        //Base production (scroll)
        devCards.add(new DevCard());
        for (int i=0; i<slotNum; i++) {
            if (slotDev.get(i).size() > 0)
                devCards.add(getSlotDev().get(i).get(getSlotDev().get(i).size() - 1));


        }
        for (DevCard devCard : slotLeader) {
                devCards.add(devCard);
        }
        return devCards;
    }

    /**
     * This method returns all the slots available for placing a Development Card of the level passed as a parameter.
     * @param level the level of the card the player wants to place.
     * @return the number of the slots available for placing a card of the level passed as a parameter.
     */
    public ArrayList<Integer> getAvailableSlots(int level){
        ArrayList<Integer> free = new ArrayList<>();
                for(int i=0; i<slotNum; i++){
                    if((slotDev.get(i).size()+1)==level){
                        free.add(i);
                    }
                }
                return free;
    }

    /**
     * This method returns all the Development Card in the slots.
     * @return all the Development Card in the slots.
     */
    public ArrayList<DevCard> getAllDevCards(){
        ArrayList<DevCard> devCards = new ArrayList<>();
        for(int i=0; i<getSlotDev().size(); i++){
            for(int j=0; j<getSlotDev().get(i).size(); j++){
                devCards.add(getSlotDev().get(i).get(j));
            }
        }
        return devCards;
    }

    /**
     * This method returns all the Development Card, of the color passed as a parameter, in the slots.
     * @param color the color of the Development Cards.
     * @return all the Development Card of the color passed as a parameter.
     */
    public ArrayList<DevCard> getAllDevCardsPerColor(Color color) {
        ArrayList<DevCard> allCards = getAllDevCards();
        ArrayList<DevCard> allCardsPerColor = new ArrayList<>();

        for(DevCard devCard: allCards) {
            if(devCard.getCardType().getColor().equals(color)) {
                allCardsPerColor.add(devCard);
            }
        }
        return allCardsPerColor;
    }

    /**
     * This method returns the number of Development Card of the color passed as a parameter.
     * @param color the color of Development Cards.
     * @return an int representing the number of Development Card of a given color.
     */
    public int numCardsPerColor(Color color) {
        return getAllDevCardsPerColor(color).size();
    }

    public boolean hasLevelTwoOfColor(Color color){
        for(int i=0;i<3;i++){
            if(slotDev.get(i).size()>=2){
                if(slotDev.get(i).get(1).getCardType().getColor()==color){
                    return true;
                }
            }
        }
        return false;

    }
    public int getCardPoints(){
        int vp=0;
        for(int i=0; i<3;i++){
            for(DevCard card: slotDev.get(i)){
                vp+=card.getVP();
            }
        }
        return vp;
    }
}
