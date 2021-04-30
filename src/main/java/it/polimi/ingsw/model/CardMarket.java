package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;

public class CardMarket {
    public ArrayList<ArrayList<ArrayList<DevCard>>> devCardGrid;

    public CardMarket(ArrayList<DevCard> devCardDeck) {
        devCardGrid = new ArrayList<ArrayList<ArrayList<DevCard>>>();
        Collections.shuffle(devCardDeck);
        for (int i = 0; i < 3; i++) {
            devCardGrid.add(new ArrayList<ArrayList<DevCard>>());
            for (int j = 0; j < 4; j++) {
                devCardGrid.get(i).add(new ArrayList<DevCard>());
            }
        }
        int l;
        int c;
        for (int i = 0; i < devCardDeck.size(); i++) {
            l = devCardDeck.get(i).getCardType().getLevel();
            c = devCardDeck.get(i).getCardType().getColor().getVal();
            devCardGrid.get(l).get(c).add(devCardDeck.get(i));
        }

    }

    public ArrayList<DevCard> availableCards(){
        ArrayList<DevCard> available = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
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