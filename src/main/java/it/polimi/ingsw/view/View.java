package it.polimi.ingsw.view;

import java.util.HashMap;
import java.util.List;

import it.polimi.ingsw.model.*;

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
     * Asks the user to create a new lobby.
     */
    public void askLobbyCreation();

    /**
     * Asks how many players the game is going to have.
     */
    public void askPlayersNumber();

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
     * Asks the player to choose the two Leader Cards to discard.
     * @param leaderCards to choose between.
     */
    public void askChooseLeaderCards(List<LeaderCard> leaderCards);

    /**
     * Allows the player to buy a Development Card.
     * @param devCardList the list of Development Card the player can choose between.
     */
    public void askBuyDevCard(List<DevCard> devCardList);

    /**
     * Allows the player to get Market Resources.
     * @param rowOrCol indicates if the player choose a row or a column. 'r' for row, 'c' for column.
     * @param rowOrColNum indicates the number of row or column chosen.
     */
    public void askGetMarketRes(char rowOrCol, int rowOrColNum);

    /**
     * Shows the Market.
     * @param market the market to be shown.
     */
    public void showMarket(Market market);

    /**
     * Allows the player to activate Development Card production.
     * @param devCardList the list of available Development Card for production.
     */
    public void askActivateProduction(List<DevCard> devCardList);

    /**
     * Shows the player the resources he owns.
     * @param strongbox the player's strongbox.
     * @param warehouse the player's warehouse.
     */
    public void showResources(HashMap<ResourceType, Integer> strongbox, Warehouse warehouse);

    /**
     * Shows the player the Faith Track state.
     * @param faithTrackState list of the player's faith points.
     */
    public void showFaithTrack(List<Integer> faithTrackState);

    /**
     * Shows the player his actual Victory Points.
     * @param victoryPoints the player's Victory Points to be shown.
     */
    public void showActualVP(int victoryPoints);

    /**
     * Allows the player to see his resources.
     */
    public void askToShowResources();

    /**
     * Allows the player see the Faith Track state.
     */
    public void askToShowFaithTrack();

    /**
     * Allows the player to see his actual Victory Points.
     */
    public void askToShowActualVP();

    /**
     * Allows the player to choose resources for payment.
     * @param strongbox the player's strongbox.
     * @param warehouse the player's warehouse.
     */
    public void chooseResToPay(HashMap<ResourceType, Integer> strongbox, Warehouse warehouse);

    /**
     * Allows the player to rearrange depot.
     * @param depot1 the first Depot which it contents will be switched.
     * @param depot2 the second Depot which it contents will be switched.
     */
    public void askRearrangeDepot(Depot depot1, Depot depot2);

    /**
     * Allows the player to play a Leader Carder.
     * @param leaderCards the list of Leader Cards the player can activate.
     */
    public void playLeaderCard(List<LeaderCard> leaderCards);

    /**
     * Allows the player to discard a Leader Card.
     * @param leaderCards the list of Leader Cards the player can discard.
     */
    public void discardLeaderCard(List<LeaderCard> leaderCards);

    /**
     * Shows the winner.
     * @param winnerUser the username of the winner.
     */
    public void showWinMessage(String winnerUser);

}
