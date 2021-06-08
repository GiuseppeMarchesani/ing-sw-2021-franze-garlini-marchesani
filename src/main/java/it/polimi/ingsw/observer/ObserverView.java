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
     * Sends a message to the server with the player number chosen by the user.
     * @param playersNumber the number of players to be sent.
     */
    public void updatePlayersNumber(int playersNumber);

    /**
     * Sends a message to the server containing the action the player wants to do.
     * @param actionCode the action code of the action the player wants to do.
     */
    public void updateAction(int actionCode);

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
     * Sends a message to the server containing the chosen LeaderCard to discard.
     * @param leaderCard the LeaderCard to be discarded.
     */
    public void updateDiscardLeader(ArrayList<LeaderCard> leaderCard);

    public void updateNewUsername(String username);

    public void updateWarehouse(HashMap<Integer, ResourceType> depotToResource,HashMap<Integer, Integer> depotToQuantity, ArrayList<Integer> resourceToLeader, int discard);

    public void updatePlaceDevCard(HashMap<ResourceType, Integer> paymentWarehouse, HashMap<ResourceType, Integer> newStrongbox, int i);

    public void updateGetProdRes(HashMap<ResourceType, Integer> newStrongbox, HashMap<ResourceType, Integer> paymentWarehouse);

    public void updateChosenProdCards(ArrayList<DevCard> chosenCards);

    public void updatePlayLeaderCard(LeaderCard card, char dOrP);
}
