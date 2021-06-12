package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.enumeration.Color;

import java.util.ArrayList;
import java.util.Collections;

public class CardMarket {
    private ArrayList<ArrayList<ArrayList<DevCard>>> devCardGrid;

    public CardMarket(ArrayList<DevCard> devCardDeck) {
        //Creating instances
        devCardGrid = new ArrayList<ArrayList<ArrayList<DevCard>>>();
        for (int i=0; i<4; i++) {
            devCardGrid.add(new ArrayList<ArrayList<DevCard>>());
            for (int j=0; j<3; j++) {
                devCardGrid.get(i).add(new ArrayList<DevCard>());
            }
        }

        Collections.shuffle(devCardDeck);
        //Putting the cards in the grid
        DevCard card;
        for (DevCard devCard : devCardDeck) {
            card = devCard;
            devCardGrid.get(card.getCardType().getColor().getVal()).get(card.getCardType().getLevel() - 1).add(card);
        }



    }

    public DevCard pickCard(Color color, int level) {
        DevCard requestCard;
        if(devCardGrid.get(color.getVal()).get(level).size() > 0){
            requestCard = devCardGrid.get(color.getVal()).get(level).get(devCardGrid.get(color.getVal()).get(level).size()-1);
            devCardGrid.get(color.getVal()).get(level).remove(requestCard);
        }
        else requestCard=null;

        return requestCard;
    }

    public void returnDevCard(DevCard card){
        devCardGrid.get(card.getCardType().getColor().getVal()).get(card.getCardType().getLevel()-1).add(card);
    }

    public ArrayList<DevCard> availableCards(){
        ArrayList<DevCard> available = new ArrayList<>();
        for (int i=0; i<4; i++) {
            for (int j=0; j<3; j++) {
                try {
                    if(devCardGrid.get(i).get(j).size() > 0) {
                        available.add(devCardGrid.get(i).get(j).get(devCardGrid.get(i).get(j).size() - 1));
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

            }

        }
        return available;
    }

    public ArrayList<Integer> remainingCards(){
        ArrayList<Integer> remaining = new ArrayList<>();
        for (int i=0; i<4; i++) {
            for (int j=0; j<3; j++) {
                try {
                   remaining.add(devCardGrid.get(i).get(j).size());
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

            }

        }
        return remaining;
    }

    public ArrayList<ArrayList<ArrayList<DevCard>>> getDevCardGrid(){
        return devCardGrid;
    }

    public void discardDevCard(Color color) {
        int j;
        int removed=0;
        for (j=0; j<3; j++) {
            for(int i=0; i<2;i++) {
                if (devCardGrid.get(color.getVal()).get(j).size() >0) {
                    devCardGrid.get(color.getVal()).get(j).remove(devCardGrid.get(color.getVal()).get(j).size() - 1);
                    removed++;
                }
                if (removed==2) return;
            }

        }
        return;
    }
    public int remainingCardsOfColor(Color color){
        int size=0;
        for (int j=0; j<3; j++) {
            size+=devCardGrid.get(color.getVal()).get(j).size();
        }
        return size;
    }
    public boolean noCardsOfAColor(){
        for(Color color: Color.values()){
           if( remainingCardsOfColor(color)==0){
               return true;
           }
        }
        return false;
    }
}