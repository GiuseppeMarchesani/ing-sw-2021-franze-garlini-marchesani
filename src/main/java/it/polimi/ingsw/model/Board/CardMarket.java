package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.enumeration.Color;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents the CardMarket used to buy DevCards.
 */
public class CardMarket {
    private ArrayList<ArrayList<ArrayList<DevCard>>> devCardGrid;

    /**
     * Default constructor.
     * @param devCardDeck contains all the development cards.
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
     * Takes the available card of the required color and level and removes it from the card market.
     * @param color color requested by the player
     * @param level level requested by the player
     * @return the required card if it's available, otherwise returns null
     */
    public DevCard pickCard(Color color, int level) {
        DevCard requestCard;
        if(devCardGrid.get(color.getVal()).get(level).size() > 0){
            requestCard = devCardGrid.get(color.getVal()).get(level).get(devCardGrid.get(color.getVal()).get(level).size()-1);
            devCardGrid.get(color.getVal()).get(level).remove(requestCard);
            return requestCard;
        }
        else return null;
    }

    /**
     * Adds the card to the market.
     * @param card card that needs to be added.
     */
    public void returnDevCard(DevCard card){
        devCardGrid.get(card.getCardType().getColor().getVal()).get(card.getCardType().getLevel()-1).add(card);
    }

    /**
     * Returns all the visible cards from the card market.
     * @return a list of DevCard.
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
     * Returns the number of cards in each CardMarket stack.
     * @return a list of integers representing the number of cards in each stack.
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


    /**
     * Will be called when a discard action token is drawn. This method discards cards from the CardMarket.
     * @param color color required by the action token.
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
    }

    /**
     * Counts the cards that are left of a specific color.
     * @param color color required.
     * @return returns the number of the cards of the given color.
     */
    public int remainingCardsOfColor(Color color){
        int size=0;
        for (int j=0; j<3; j++) {
            size+=devCardGrid.get(color.getVal()).get(j).size();
        }
        return size;
    }

    /**
     * Checks, for all colors, if there are still cards available.
     * @return returns true if there are some, otherwise returns false.
     */
    public boolean noCardsOfAColor(){
        for(Color color: Color.values()){
           if( remainingCardsOfColor(color)==0){
               return true;
           }
        }
        return false;
    }

    public ArrayList<ArrayList<ArrayList<DevCard>>> getDevCardGrid(){
        return devCardGrid;
    }
}