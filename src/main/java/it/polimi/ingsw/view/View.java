package it.polimi.ingsw.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.DevCardSlot;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;

/**
 * This class represents the generic View which will be implemented by the CLI or GUI
 */
public interface View {

    /**
     * Ask the player to connect to the server providing ip address and port.
     */
    void askConnect();

    /**
     * Ask the player to connect to a game providing a username and a game id.
     */
    void askLobby();

    /**
     * Asks how many players the new game is going to have.
     */
    void askPlayersNumber();

    /**
     * Asks the player his next action.
     */
    void askAction();

    /**
     * Shows the user if the login succeeded.
     * @param username the player's username.
     * @param gameId the game id.
     * @param wasJoined indicates if the connection succeeded.
     */
    void showLoginResult(String username, String gameId, boolean wasJoined);

    /**
     * Shows the player a generic message.
     * @param message the message to be shown.
     */
    void showMessage(String message);

    /**
     * Allows the player to buy a Development Card.
     */
    void askDevCardToBuy();

    /**
     * Allows the player to get Market Resources.
     * @param conversion list of white marble conversion available for the player.
     */
    void askMarketLineToGet(ArrayList<ResourceType> conversion);

    /**
     * Ask the player to place his resources.
     * @param resToPlace resources to be placed.
     * @param numAny number of any in the HashMap passed as a parameter.
     * @param extraDepot possible leader card depot, null otherwise.
     */
    void askResourceToWarehouse(HashMap<ResourceType, Integer> resToPlace, int numAny, ArrayList<ResourceType> extraDepot);

    /**
     * Asks the player for payment towards his production choices
     * @param strongbox the player's strongbox
     * @param price the price of the production asked.
     * @param anyPayment How many "Any" resources the player has to pay
     * @param anyProduce How many "Any" resources the player will gain.
     */
    void askProduction(HashMap<ResourceType, Integer> strongbox, HashMap<ResourceType, Integer> warehouse, HashMap<ResourceType, Integer>  price, int anyPayment, int anyProduce);

    /**
     * Shows the Market.
     * @param market the market to be shown.
     * @param corner the marble corner.
     */
    void showMarket(ResourceType[][] market, ResourceType corner);

    /**
     * Shows the player Development Cards from the market.
     * @param availableCards the list of Development Card on the top of the DevMarket.
     * @param remainingCards the number of cards in each stack.
     */
    void showDevMarket(ArrayList<DevCard> availableCards, ArrayList<Integer> remainingCards);

    /**
     * Shows the strongbox owned by a player.
     * @param strongbox the player's strongbox.
     * @param username the player's username.
     */
    void showStrongbox(HashMap<ResourceType, Integer> strongbox, String username);

    /**
     * Shows the warehouse owned by a player.
     * @param depotToQuantity warehouse's floors associated to the resource quantity.
     * @param depotToResource warehouse's floors associated to the resource type.
     * @param username the player's username.
     */
    void showWarehouse(HashMap<Integer, Integer> depotToQuantity, HashMap<Integer, ResourceType> depotToResource, String username);

    /**
     * Shows the player its production cards
     * @param devCardList the cards they can activate.
     */
    void askCardsToActivateProd(ArrayList<DevCard> devCardList);

    /**
     * Shows the player an error message.
     * @param message the message to be shown.
     */
    void showErrorMsg(String message);

    /**
     * Shows the player the Faith Track state.
     * @param playerFaith players usernames and their faith points.
     * @param wasZoneActivated true if a faith zone was activated.
     * @param whichZone which faith zone has been activated.
     */
    void showFaithTrack(HashMap<String, Integer> playerFaith, boolean wasZoneActivated, int whichZone);

    /**
     * Shows the current Victory Points owned by all players.
     * @param victoryPoints players usernames and their amount of Victory Points.
     */
    void showCurrentVP(HashMap<String, Integer> victoryPoints);

    /**
     * Shows the player a Card Slot.
     * @param devCardSlot the Card Slot to be shown.
     * @param username the player who owns the CardSlot.
     */
    void showSlots(DevCardSlot devCardSlot, String username);

    /**
     * Shows the number of un-played leader cards owned by a player.
     * @param username the player's username.
     * @param remaining number of un-played leader cards.
     */
    void showRemainingLeaderCards(String username, int remaining);

    /**
     * Asks the player to choose one slot among the slots in the list passed as parameter.
     * @param strongbox the player's strongbox.
     * @param cardCost card's cost.
     * @param numAny amount of ANY resource.
     * @param availableSlots the list of the available slot the player can choose.
     */
    void askSlot(HashMap<ResourceType, Integer> strongbox, HashMap<ResourceType, Integer> warehouse, HashMap<ResourceType, Integer> cardCost, int numAny, ArrayList<Integer> availableSlots);

    /**
     * Allows the player to play a Leader Carder.
     * @param leaderCards the list of Leader Cards the player can activate.
     */
    void askLeaderCardToPlay(ArrayList<LeaderCard> leaderCards);

    /**
     * Allows the player to choose Leader Cards to keep.
     * @param leaderCards the list of initial Leader Cards.
     */
    void askLeaderCardToKeep(ArrayList<LeaderCard> leaderCards);

    /**
     * Shows the final results ant ends the game.
     * @param finalPoints the usernames and points of the players.
     */
    void showWinMessage(HashMap<String, Integer> finalPoints);

    /**
     * Shows the final results for a Single Player match and ends the game.
     */
    void showLoseMessage();

    /**
     * Shows leader cards to the player.
     * @param leaderCards list of leader cards with their respective boolean for activated.
     */
    void showLeaderCards(HashMap<LeaderCard, Boolean> leaderCards);
}
