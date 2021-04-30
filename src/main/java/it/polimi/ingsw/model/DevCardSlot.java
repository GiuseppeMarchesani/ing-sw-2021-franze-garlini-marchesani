package it.polimi.ingsw.model;

import java.util.ArrayList;

public class DevCardSlot {
    private ArrayList<ArrayList<DevCard>> slotDev;
    private final int slotNum = 3;
    private ArrayList<DevCard> slotLeader;


    public DevCardSlot(){
        slotDev = new ArrayList<ArrayList<DevCard>>(slotNum);
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

    public int getCardQuantity(){
        int cardQuantity = 0;
        for(int i=0; i<slotNum; i++) {
            cardQuantity += slotDev.get(i).size();
        }
        return cardQuantity;
    }


    public ArrayList<Card> getCardsAvailable(){
        ArrayList<Card> cards= new ArrayList<Card>();

        //Base production (scroll)
        cards.add(new DevCard());
        for (int i=0; i<slotNum; i++) {
                try{
                    cards.add(slotDev.get(i).get(slotDev.size()-1));
                }catch(IndexOutOfBoundsException e){

                }
            }

        for (int i=0; i<slotLeader.size(); i++) {
        try{
            cards.add(slotLeader.get(i));
        }catch(IndexOutOfBoundsException e){

        }
    }
        return cards;
    }


    public ArrayList<Integer> getAvailableSlots(int level){
        ArrayList<Integer> free = new ArrayList<>();
                for(int i=0; i<slotNum; i++){
                    if((slotDev.get(i).size()+1)==level){
                        free.add(i);
                    }
                }
                return free;
    }
}
