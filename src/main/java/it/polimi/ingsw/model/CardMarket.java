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
    public DevCard pickCard(Color color, int level) throws IllegalArgumentException{
        DevCard requestCard = new DevCard();
        if(devCardGrid.get(color.getVal()).get(level).size() > 0){
            requestCard = devCardGrid.get(color.getVal()).get(level).get(devCardGrid.get(color.getVal()).get(level).size()-1);
            devCardGrid.get(color.getVal()).get(level).remove(devCardGrid.get(color.getVal()).get(level).size()-1);
        }
        else throw new IllegalArgumentException();

        return requestCard;
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


    public ArrayList<ArrayList<ArrayList<DevCard>>> getDevCardGrid(){
        return devCardGrid;
    }

    public int discardDevCard(Color color) {
        int availableDeck = 3;
        int j;

        for (j=0; j<3; j++) {
            if(devCardGrid.get(color.getVal()).get(j).size() > 0) {
                devCardGrid.get(color.getVal()).get(j).remove(devCardGrid.get(color.getVal()).get(j).size()-1);
                break;
            }
        }

        if(j>=2 && devCardGrid.get(color.getVal()).get(2).size() == 0 ) {
            return 0;
        }
        else {
            return -1;
        }
    }
}