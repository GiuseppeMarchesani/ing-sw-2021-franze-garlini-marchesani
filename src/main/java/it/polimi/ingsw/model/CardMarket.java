package it.polimi.ingsw.model;

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


        //Putting the cards in the grid
        ArrayList<DevCard> temp = new ArrayList<>();
        for(Color color: Color.values()) {
            for(int level=1; level<=3; level++) {
                for(int i=0; i<devCardDeck.size(); i++) {
                    if (devCardDeck.get(i).getCardType().getColor().equals(color) && devCardDeck.get(i).getCardType().getLevel() == level) {
                        temp.add(devCardDeck.get(i));
                    }
                }
                Collections.shuffle(temp);
                devCardGrid.get(color.getVal()).get(level-1).addAll(temp);
                temp.clear();
            }
        }
    }

    public ArrayList<DevCard> availableCards(){
        ArrayList<DevCard> available = new ArrayList<>();
        for (int i=0; i<4; i++) {
            for (int j=0; j<3; j++) {
                try {
                    available.add(devCardGrid.get(i).get(j).get(devCardGrid.get(i).get(j).size() - 1));
                } catch (IndexOutOfBoundsException e) {

                }//carta non disponibile

            }

        }
        return available;
    }


    public ArrayList<ArrayList<ArrayList<DevCard>>> getDevCardGrid(){
        return devCardGrid;
    }

    //ritorna -1 se le carte di un colore sono finite, 0 se ce ne sono disponibili.
    public int discardDevCard(Color color) {
        int val = color.getVal();
        int c = 2;
        int lvl = 0;
        while (lvl < 3 && c > 0) {

            try {
                devCardGrid.remove(devCardGrid.get(lvl).get(val).size() - 1);
                c--;
            } catch (IndexOutOfBoundsException e) {
                lvl++;
            }
        }
        if(lvl>=3||((devCardGrid.get(0).get(val).size()+devCardGrid.get(1).get(val).size()+devCardGrid.get(2).get(val).size())==0)){
            c=-1;
        }
        return c;
    }
}