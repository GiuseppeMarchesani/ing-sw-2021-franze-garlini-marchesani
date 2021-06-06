package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Board.Depot;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Observer interface for the view.
 */
public interface ObserverView {
    /**
     * Tries to connect to server
     */
    public void updateConnect(String username, int port);

    /**
     * Updates lobby and tries to connect to game.
     * @param username
     * @param gameID
     */
    public void updateLobby(String username, String gameID);
    /**
     * Sends a message to the server with the username chosen by the player.
     * @param username the username to be sent.
     */
    public void updateUsername(String username);

    /**
     * Sends a message to the server with the chosen gameID.
     * @param gameID the gameID to be sent.
     */
    public void updateGameID(String gameID);


    /**
     * Sends a message to the server with the player number chosen by the user.
     * @param playersNumber the number of players to be sent.
     */
    public void updatePlayersNumber(int playersNumber);

    /**
     * Sends a message to the server containing the action the player wants to do.
     * @param command the action type the player wants to do.
     */
    public void updateAction(String command);

    /**
     * Sends a message to the server with the chosen DevCard.
     * @param level level of the chosen Development Card.
     * @param color color of the Development Card.
     */
    public void updateBuyDevCard(int level, Color color);

    /**
     * Sends a message to the server with the chosen row/column.
     * @param rowOrCol 'c' stands for column, 'r' stands for row.
     * @param rowColNumber the row/column number.
     */
    public void updateGetFromMarket(char rowOrCol, int rowColNumber, ResourceType conversion);

    /**
     * Sends a message to the server with the chosen DevCards for production.
     * @param chosenCards the chosen DevCards.
     */
    public void updateActivateProduction(List<DevCard> chosenCards);

    /**
     * Sends a message to the server containing the destination used for pay.
     * @param destination could be "warehouse" or "strongbox".
     */
    public void updateResToPay(String destination);

    /**
     * Sends a message to the server containing the initial resource chosen by the player.
     * @param chosenRes the chosen resource.
     */
    public void updateChooseOneRes(ResourceType chosenRes);

    /**
     * Sends a message to the server containing the chosen depot to put a resource.
     * @param chosenDepot the chosen depot.
     */
    public void updateChooseFloor(int chosenDepot);

    /**
     * Sends a message to the server containing the chosen slot by the player.
     * @param chosenSlot the chosen slot.
     */
    public void updateAskSlot(int chosenSlot);

    /**
     * Sends a message to the server containing the depots to be rearranged.
     * @param rearrange a boolean representing if the player want to rearrange or not.
     * @param depot1 the first depot to be sent.
     * @param depot2 the second depot to be sent.
     */
    public void updateRearrange(boolean rearrange, int depot1, int depot2);

    /**
     * Sends a message to the server containing the chosen LeaderCard to play.
     * @param leaderCard the LeaderCard to be played.
     */
    public void updatePlayLeaderCard(LeaderCard leaderCard);

    /**
     * Sends a message to the server containing the chosen LeaderCard to discard.
     * @param leaderCard the LeaderCard to be discarded.
     */
    public void updateDiscardLeader(ArrayList<LeaderCard> leaderCard);

    public void updateNewUsername(String username);

    public void updateWarehouse(HashMap<Integer, ResourceType> depotToResource,HashMap<Integer, Integer> depotToQuantity, ArrayList<Integer> resourceToLeader, int discard);

    public void updateStrongbox(HashMap<ResourceType, Integer> toStrongbox);
}
