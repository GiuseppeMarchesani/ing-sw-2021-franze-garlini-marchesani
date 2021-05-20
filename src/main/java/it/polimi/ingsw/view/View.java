package it.polimi.ingsw.view;

import java.util.HashMap;
import java.util.List;

import it.polimi.ingsw.model.*;
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

    /**
     * Asks the player his next action.
     */
    public void askAction();

    /**
     * Shows the user if the login succeeded.
     * @param username the username of the player who wants to login.
     * @param usernameAccepted indicates if the chosen username has been accepted.
     * @param connectionSuccessful indicates if the connection succeeded.
     */
    public void showLoginResult(boolean usernameAccepted, boolean connectionSuccessful, String username);

    /**
     * Shows the player a generic message.
     * @param message the message to be shown.
     */
    public void showMessage(String message);

    /**
     * Shows the player a disconnection message.
     */
    public void showDisconnectionMsg(String disconnectedUser, String message);

    /**
     * Shows the player the numbers of players, their usernames and other info.
     * @param players the list of the playing players.
     */
    public void showMatchInfo(List<String> players, String activePlayer);

    /**
     * Shows the player his Leader Cards.
     * @param leaderCards the list of the Leader Cards to be shown.
     */
    public void showLeaderCards(List<LeaderCard> leaderCards);

    /**
     * Allows the player to buy a Development Card.
     * @param availableCards the list of Development Card the player can choose between.
     */
    public void askDevCardToBuy(List<DevCard> availableCards);

    /**
     * Allows the player to get Market Resources.
     */
    public void askMarketLineToGet();

    /**
     * Shows the Market.
     * @param market the market to be shown.
     */
    public void showMarket(Market market);

    /**
     * Allows the player to activate Development Card production.
     * @param availableCards the list of available Development Card for production.
     */
    public void askCardsToActivateProd(List<DevCard> availableCards);

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
     * Shows the player his actual Victory Points.
     * @param victoryPoints the player's Victory Points to be shown.
     */
    public void showCurrentVP(int victoryPoints);

    /**
     * Shows the player his Card Slot.
     * @param devCardSlot the player's Card Slot.
     */
    public void showSlots(DevCardSlot devCardSlot);

    /**
     * Allows the player to choose resources for payment.
     * @param strongbox the player's strongbox.
     * @param warehouse the player's warehouse.
     */
    public void askChooseResToPay(HashMap<ResourceType, Integer> strongbox, Warehouse warehouse);

    /**
     * Allows the player to rearrange depot.
     */
    public void askDepotToRearrange();

    /**
     * Allows the player to play a Leader Carder.
     * @param leaderCards the list of Leader Cards the player can activate.
     */
    public void askLeaderCardToPlay(List<LeaderCard> leaderCards);

    /**
     * Allows the player to discard a Leader Card.
     * @param leaderCards the list of Leader Cards the player can discard.
     */
    public void askLeaderCardToDiscard(List<LeaderCard> leaderCards);

    /**
     * Shows the winner.
     * @param winnerUser the username of the winner.
     */
    public void showWinMessage(String winnerUser);


}
