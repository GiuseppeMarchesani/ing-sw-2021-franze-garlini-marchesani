package it.polimi.ingsw.model;


import java.util.HashMap;

/**
 * This class is the intermediary between all the game's components
 */
public abstract class Game {

    public abstract void start();

    /**
     * Call the method placeResources from the class player in order to store the resources obtain by the market
     * @param resources The resources obtained by the Market
     * @param player The player who is going to take the resources
     */
    public void giveMarketRes(HashMap<ResourceType, int> resources, Player player) {
        player.placeResources(resources);
    }

    /**
     * Call the method placeResources from the class player in order to store the resources obtained by production
     * @param resources The resources obtained by production
     * @param player The player who is going to take the resources
     */
    public void giveProductionRes(HashMap<ResourceType, int> resources, Player player) {
        player.placeResources(resources); //maybe we need a different method for placing resources in the strongbox
    }

    /**
     * Use placeDevCard and removeDevCard in order to take a Development Card from the market and place it to the player's Development Card Slot
     * @param player The player who bought the card
     */
    public void giveDevCard(Player player) {
        player.placeDevCard(CardMarket.removeDevCard());
    }

    /**
     * Call the increaseVP method from the class player in order to increase the Victory Points
     * @param VP The amount of Victory Points to add
     * @param player The player who earned new Victory Points
     */
    public void giveVP(int VP, Player player) {
        player.increaseVP(player.getVP() + VP);
    }

    public abstract void endGame();


}
