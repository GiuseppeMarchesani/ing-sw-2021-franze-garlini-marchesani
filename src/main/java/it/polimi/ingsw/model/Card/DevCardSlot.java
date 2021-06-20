package it.polimi.ingsw.model.Card;

import it.polimi.ingsw.model.enumeration.Color;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This Class represents the Development Card slots.
 */
public class DevCardSlot implements Serializable {
    private ArrayList<ArrayList<DevCard>> slotDev;
    private final int SLOT_NUM = 3;
    private ArrayList<DevCard> slotLeader;

    /**
     * Default constructor.
     */
    public DevCardSlot(){
        slotDev = new ArrayList<ArrayList<DevCard>>();
        for(int i = 0; i< SLOT_NUM; i++){
            slotDev.add(new ArrayList<DevCard>());
        }
        slotLeader = new ArrayList<DevCard>();
    }

    /**
     * This method counts all the Development Card in the slots.
     * @return the number of Development Card in the slot.
     */
    public int getCardQuantity(){
        int cardQuantity = 0;
        for(int i = 0; i< SLOT_NUM; i++) {
            cardQuantity += slotDev.get(i).size();
        }
        return cardQuantity;
    }

    /**
     * This method returns all the Development Cards available for production.
     * @return a list of Development Cards available for production.
     */
    public ArrayList<DevCard> getCardsAvailable(){
        ArrayList<DevCard> devCards= new ArrayList<DevCard>();

        //Base production (scroll)
        devCards.add(new DevCard());

        for (int i = 0; i< SLOT_NUM; i++) {
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
                for(int i = 0; i< SLOT_NUM; i++){
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

    /**
     * This method is used to know if the player owns a level two card for a given color.
     * @param color the requested color.
     * @return true if the player has a level two card for the given color, false otherwise.
     */
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

    /**
     * Used to get the sum of the Victory Points for each card in the slots.
     * @return the amount of Victory Points.
     */
    public int getCardPoints(){
        int vp=0;
        for(int i=0; i<3;i++){
            for(DevCard card: slotDev.get(i)){
                vp+=card.getVP();
            }
        }
        return vp;
    }

    public ArrayList<ArrayList<DevCard>> getSlotDev(){
        return slotDev;
    }

    public ArrayList<DevCard> getSlotLeader(){
        return slotLeader;
    }
}
