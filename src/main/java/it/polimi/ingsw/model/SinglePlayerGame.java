package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is used for solo-mode and it's the intermediary between all the game's components.
 */
public class SinglePlayerGame extends Game {

    private Player singlePlayer;

    /**
     * SinglePlayerGame class constructor.
     * @param player The solo-mode player.
     */
    public SinglePlayerGame(Player player) {
        super();
        this.singlePlayer = player;
    }

    /**
     * Sets all the components up, gives the Leader Cards to the player and starts the game.
     */
    public void start() {
        List<LeaderCard> leadCardDeck = new ArrayList<LeaderCard>();
        //TODO: LeaderCard generation
    }

    /**
     * Calls the method placeResources from the class player in order to store the resources obtained by the market.
     * @param resources The resources obtained by the Market.
     */
    public void giveMarketRes(HashMap<ResourceType, Integer> resources) {
        singlePlayer.placeResources(resources);
    }

    /**
     * Calls the method storeResources from the class player in order to store the resources obtained by production.
     * @param resources The resources obtained by production.
     */
    public void giveProductionRes(HashMap<ResourceType, Integer> resources) {
        singlePlayer.storeResources(resources);
    }

    /**
     * Uses placeDevCard and removeDevCard in order to take a Development Card from the market and place it to the player's Development Card Slot.
     */
    public void giveDevCard() {
        singlePlayer.placeDevCard(CardMarket.removeDevCard());
    }

    /**
     * Calls the increaseVP method from the class player in order to increase the Victory Points.
     * @param VP The amount of Victory Points to add.
     */
    public void giveVP(int VP) {
        singlePlayer.increaseVP(VP);
    }

    /**
     * Ends the game and communicate the result.
     * @param endGameCode It's 0 if one type of development card is no longer available in the grid.
     *                    It's 1 if the black cross reaches the final space of the faith track.
     *                    It's 2 if the player's faith marker reaches the final space of the faith track or if the player buys the seventh development card.
     */
    public void endGame(int endGameCode) {
        if(endGameCode==0) {
            System.out.println("You lose");
        }
        else if(endGameCode==1) {
            System.out.println("You lose");
        }
        else if(endGameCode==2) {

            System.out.println("You win. Your score: " + singlePlayer.getFinalVP());
            //TODO update record
        }
    }
}
