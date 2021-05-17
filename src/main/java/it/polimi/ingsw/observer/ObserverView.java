package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Board.Depot;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.HashMap;
import java.util.List;

/**
 * Observer interface for the view.
 */
public interface ObserverView {

    /**
     * Sends a message to the server with the username chosen by the player.
     * @param username the username to be sent.
     */
    public void updateUsername(String username);

    /**
     * Sends a message to the server with the chosen gameID.
     * @param gameID the gameID to be sent.
     */
    public void updateGameID(int gameID);

    /**
     * Sends a message to the server with the new game.
     * @param game the game to be sent.
     */
    public void updateGame(Game game);

    /**
     * Sends a message to the server with the player number chosen by the user.
     * @param playersNumber the number of players to be sent.
     */
    public void updatePlayersNumber(int playersNumber);

    /**
     * Sends a message to the server with the chosen LeaderCard .
     * @param leaderCard the chosen LeaderCard.
     */
    public void updateChooseLeaderCard(LeaderCard leaderCard);

    /**
     * Sends a message to the server with the chosen DevCard.
     * @param boughtDevCard the chosen DevCard.
     */
    public void updateBuyDevCard(DevCard boughtDevCard);

    /**
     * Sends a message to the server with the chosen row/column.
     * @param rowOrCol 'c' stands for column, 'r' stands for row.
     * @param rowColNumber the row/column number.
     */
    public void updateGetMarketRes(char rowOrCol, int rowColNumber);

    /**
     * Sends a message to the server with the chosen DevCards for production.
     * @param chosenCards the chosen DevCards.
     */
    public void updateActivateProduction(List<DevCard> chosenCards);

    /**
     * Sends a message to the server asking to show the player's resources.
     */
    public void updateShowResources();

    /**
     * Sends a message to the server asking to show the FaithTrack.
     */
    public void updateShowFaithTrack();

    /**
     * Sends a message to the server asking to show the player's VictoryPoints.
     */
    public void updateShowActualVP();

    /**
     * Sends a message to the server containing the resources of the warehouse to be used as payment.
     * @param chosenRes the hashmap containing the warehouse's resources the player is going to use as a payment.
     */
    public void updateResToPay(HashMap<ResourceType, Integer> chosenRes);

    /**
     * Sends a message to the server containing the depots to be rearranged.
     * @param depot1 the first depot to be sent.
     * @param depot2 the second depot to be sent.
     */
    public void updateRearrange(Depot depot1, Depot depot2);

    /**
     * Sends a message to the server containing the chosen LeaderCard to play.
     * @param leaderCard the LeaderCard to be played.
     */
    public void updatePlayLeaderCard(LeaderCard leaderCard);

    /**
     * Sends a message to the server containing the chosen LeaderCard to discard.
     * @param leaderCard the LeaderCard to be discarded.
     */
    public void discardLeaderCard(LeaderCard leaderCard);

}
