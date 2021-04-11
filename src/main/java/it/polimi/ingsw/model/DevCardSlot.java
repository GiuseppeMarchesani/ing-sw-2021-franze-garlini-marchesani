package it.polimi.ingsw.model;

import java.util.ArrayList;

public class DevCardSlot {
    ArrayList<ArrayList<DevCard>> slotDev;
    ArrayList<LeaderCard> slotLeader;

    public DevCardSlot(){
        slotDev= new ArrayList<ArrayList<DevCard>>(3);
        for(int i=0; i<3;i++){
            slotDev.add(new ArrayList<DevCard>());
        }
        slotLeader= new ArrayList<LeaderCard>();
    }
    public ArrayList<ArrayList<DevCard>> getSlotDev(){
        return slotDev;
    }
    public ArrayList<LeaderCard> getSlotLeader(){
        return slotLeader;
    }
    public int getCardQuantity(){
        return slotDev.get(0).size()+slotDev.get(1).size()+slotDev.get(2).size();
    }

    public ArrayList<Integer> placeChoices(int level){
        ArrayList<Integer> levels= new ArrayList<Integer>();
        for(int i=0; i<3;i++){

            if(level==slotDev.get(i).size()+1){
                levels.add(Integer.valueOf(i+1));

            }
        }
        return levels;
    }
    public ArrayList<Card> getCardsAvailable(){
        ArrayList<Card> cards= new ArrayList<Card>();
        for (int i = 0; i < 3; i++) {
                try{
                    cards.add(slotDev.get(i).get(slotDev.size()-1));
                }catch(IndexOutOfBoundsException e){}
            }

        for (int i = 0; i < slotLeader.size(); i++) {
        try{
            cards.add(slotLeader.get(i));
        }catch(IndexOutOfBoundsException e){}
    }
        return cards;
    }
}
