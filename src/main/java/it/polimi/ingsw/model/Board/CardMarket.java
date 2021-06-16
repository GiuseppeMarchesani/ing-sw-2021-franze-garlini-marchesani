package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.enumeration.Color;

import java.util.ArrayList;
import java.util.Collections;

public class CardMarket {
    private ArrayList<ArrayList<ArrayList<DevCard>>> devCardGrid;

    /**
     * Default constructor
     * @param devCardDeck contains all the development cards
     */
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

    /**
     * Takes the available card of the required color and level and removed this card
     * from the card market
     * @param color color the player requested
     * @param level level the player requested
     * @return returns the required card if it's available otherwise returns null
     */
    public DevCard pickCard(Color color, int level) {
        DevCard requestCard;
        ArrayList<DevCard> availableCards = availableCards();
        for(int i=0; i< availableCards.size(); i++) {
            if(availableCards.get(i).getCardType().getLevel() == level && availableCards.get(i).getCardType().getColor().equals(color)) {
                requestCard = availableCards.get(i);
                for (int x=0; x<4; x++) {
                    for (int y = 0; y < 3; y++) {
                        if(devCardGrid.get(x).get(y).get(devCardGrid.get(x).get(y).size() - 1).equals(requestCard)) {
                            devCardGrid.get(x).get(y).remove(devCardGrid.get(x).get(y).size() - 1);
                        }
                    }
                }
                return requestCard;
            }
        }
        return null;
    }

    /**
     * Adds the card to the market
     * @param card card that needs to be added
     */
    public void returnDevCard(DevCard card){
        devCardGrid.get(card.getCardType().getColor().getVal()).get(card.getCardType().getLevel()-1).add(card);
    }

    /**
     * Takes all available cards from the card market
     * @return returns the cards available
     */
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

    /**
     * Takes the cards that have remained in the market
     * @return
     */
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

    /**
     * Discards cards when an action token is drawn and it requires discarding
     * @param color color required by the action token
     */
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

    /**
     * Counts the cards that are left of a specific color
     * @param color color required
     * @return returns the number of the cards
     */
    public int remainingCardsOfColor(Color color){
        int size=0;
        for (int j=0; j<3; j++) {
            size+=devCardGrid.get(color.getVal()).get(j).size();
        }
        return size;
    }

    /**
     * Checks for all colors if there are still cards available
     * @return returns true if there are otherwise returns false
     */
    public boolean noCardsOfAColor(){
        for(Color color: Color.values()){
           if( remainingCardsOfColor(color)==0){
               return true;
           }
        }
        return false;
    }
}