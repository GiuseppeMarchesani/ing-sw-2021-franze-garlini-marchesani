package it.polimi.ingsw.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.polimi.ingsw.model.Board.Market;
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
     * Asks the user to choose a username.
     */
    public void askUsername();

    /**
     * Asks the user to enter the GameID.
     */
    public void askGameID();

    /**
     * Asks the user to create a new game.
     */
    public void askGameCreation();

    /**
     * Asks how many players the game is going to have.
     */
    public void askPlayersNumber();

    void askInitialRes();

    /**
     * Asks the player his next action.
     */
    public void askAction();

    /**
     * Shows the user if the login succeeded.
     * @param wasJoined indicates if the connection succeeded.
     */
    public void showLoginResult(String username, String gameId, boolean wasJoined);

    /**
     * Shows the player a generic message.
     * @param message the message to be shown.
     */
    public void showMessage(String message);

    /**
     * Shows the player a disconnection message.
     * @param disconnectedUser the username of the disconnected player.
     * @param message the message to be shown.
     */
    public void showDisconnectionMsg(String disconnectedUser, String message);

    /**
     * Shows the player the numbers of players, their usernames and other info.
     * @param players the list of the playing players.
     * @param activePlayer the active player's username.
     */
    public void showMatchInfo(List<String> players, String activePlayer);

    /**
     * Shows the player his Leader Cards.
     * @param leaderCards the list of the Leader Cards to be shown.
     * @param username the username of the player who owns the leader cards.
     */
    public void showLeaderCards(List<LeaderCard> leaderCards, String username);

    /**
     * Allows the player to buy a Development Card.
     * @param availableCards the list of Development Card the player can choose between.
     */
    public void askDevCardToBuy(List<DevCard> availableCards);

    /**
     * Allows the player to get Market Resources.
     * @param market the market to be shown.
     */
    public void askMarketLineToGet(Market market);

    /**
     * Shows the Market.
     * @param market the market to be shown.
     */
    public void showMarket(Market market);

    /**
     * Shows the player Development Cards from the market.
     * @param availableCards the list of Development Card on the top of the DevMarket.
     */
    public void showDevMarket(List<DevCard> availableCards);

    /**
     * Allows the player to activate Development Card production.
     * @param availableCards the list of available Development Card for production.
     */
    public void askCardsToActivateProd(List<DevCard> availableCards);

    /**
     * Shows the resources owned by a player.
     * @param strongbox the player's strongbox.
     * @param warehouse the player's warehouse.
     * @param username the username of the player who owns the resources.
     */
    public void showResources(HashMap<ResourceType, Integer> strongbox, Warehouse warehouse, String username);

    /**
     * Shows the player the resources he owns.
     * @param strongbox the player's strongbox.
     * @param warehouse the player's warehouse.
     */
    public void showResources(HashMap<ResourceType, Integer> strongbox, Warehouse warehouse);

    /**
     * Shows the player an error message.
     * @param message the message to be shown.
     */
    public void showErrorMsg(String message);

    /**
     * Shows the player the Faith Track state.
     * @param faithTrackState list of the player's faith points.
     */
    public void showFaithTrack(HashMap<String, Integer> faithTrackState);

    /**
     * Shows the player an amount of actual Victory Points.
     * @param victoryPoints the amount of Victory Points to be shown.
     * @param username the player who owns the Victory Points.
     */
    public void showCurrentVP(int victoryPoints, String username);

    /**
     * Shows the player a Card Slot.
     * @param devCardSlot the Card Slot to be shown.
     * @param username the player who owns the CardSlot.
     */
    public void showSlots(DevCardSlot devCardSlot, String username);

    /**
     * Asks the player to choose one slot among the slots in the list passed as parameter.
     * @param availableSlots the list of the available slot the player can choose.
     */
    public void askSlot(ArrayList<Integer> availableSlots);

    /**
     * Asks the player to choose a white marble conversion among the available conversions.
     * @param availableConversions the list of the available resource type for conversion.
     */
    public void askChooseMarbleConversion(ArrayList<ResourceType> availableConversions);

    /**
     * Allows the player to choose resources for payment.
     * @param strongbox the player's strongbox.
     * @param warehouse the player's warehouse.
     * @param resource the resource that must be paid.
     */
    public void askChooseResToPay(HashMap<ResourceType, Integer> strongbox, Warehouse warehouse, ResourceType resource);

    /**
     * Allows the player to choose a resource.
     * @param resources the list of resources available for choosing.
     * @param message the message to be shown.
     */
    public void askChooseOneRes(ArrayList<String> resources, String message);

    /**
     * Allows the player to choose the Warehouse floor to put the resource.
     * @param warehouse the player's warehouse.
     * @param resToPlace the resource to be placed.
     */
    public void askChooseFloor(Warehouse warehouse, ResourceType resToPlace);

    /**
     * Asks the player if he wants to rearrange depots.
     * @param warehouse the player's warehouse.
     * @param resources
     */
    public void askRearrange(Warehouse warehouse, HashMap<ResourceType, Integer> resources);

    void askRearrange(String username, String gameId, Warehouse warehouse, HashMap<ResourceType, Integer> resources);

    /**
     * Allows the player to play a Leader Carder.
     * @param leaderCards the list of Leader Cards the player can activate.
     */
    public void askLeaderCardToPlay(List<LeaderCard> leaderCards);

    /**
     * Allows the player to discard a Leader Card.
     * @param leaderCards the list of Leader Cards the player can discard.
     */
    public void askLeaderCardToKeep(List<LeaderCard> leaderCards);

    /**
     * Shows the winner.
     * @param winnerUser the username of the winner.
     */
    public void showWinMessage(String winnerUser);


}
