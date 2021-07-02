package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Observer interface for the view.
 */
public interface ObserverView {

    /**
     * Tries to connect the client to a socket.
     * @param address the ip address to connect to.
     * @param port the port to connect to.
     */
    public void updateConnect(String address, int port);

    /**
     * Tries to log the user to a certain lobby
     * @param username the username of the player trying to log in
     * @param gameID the lobby/game id.
     */
    public void updateLobby(String username, String gameID);

    /**
     * Sends a message to the server with the player number chosen by the user.
     * @param playersNumber the number of players to be sent.
     */
    public void updatePlayersNumber(int playersNumber);

    /**
     * Sends a message to the server containing the action the player wants to do.
     * @param actionCode the action code associated to the action the player wants to do.
     */
    public void updateAction(int actionCode);

    /**
     * Asks to buy a card from the market.
     * @param level level of the chosen Development Card.
     * @param color color of the Development Card.
     */
    public void updateBuyDevCard(int level, Color color);

    /**
     * Sends a message to the server with the chosen row/column.
     * @param rowOrCol 'c' stands for column, 'r' stands for row.
     * @param rowColNumber the row/column number.
     * @param conversion the ResourceType available for leader marble conversion.
     */
    public void updateGetFromMarket(char rowOrCol, int rowColNumber, ResourceType conversion);

    /**
     * Sends a message to the server containing the chosen LeaderCard to discard.
     * @param leaderCard the LeaderCards to be kept.
     */
    public void updateDiscardLeader(ArrayList<LeaderCard> leaderCard);

    /**
     * Updates a new username.
     * @param username the player's username.
     */
    public void updateNewUsername(String username);

    /**
     * Updates the player warehouse after getting resources from market.
     * @param depotToResource Map Floor to Resource Type.
     * @param depotToQuantity Map Floor to Quantity.
     * @param resourceToLeader Array of quantity for every leader depot.
     * @param discard number of discarded resources.
     */
    public void updateWarehouse(HashMap<Integer, ResourceType> depotToResource,HashMap<Integer, Integer> depotToQuantity, ArrayList<Integer> resourceToLeader, int discard);

    /**
     *Spends resources and places a card in a slot.
     * @param paymentWarehouse the resource that will be spent from the depot.
     * @param newStrongbox the new strongbox.
     * @param slotToPlace the slot to place the game.
     */
    public void updatePlaceDevCard(HashMap<ResourceType, Integer> paymentWarehouse, HashMap<ResourceType, Integer> newStrongbox, int slotToPlace);

    /**
     * Gets production resources by paying their cost.
     * @param newStrongbox the new strongbox.
     * @param paymentWarehouse the resource that will be spent from the warehouse.
     */
    public void updateGetProdRes(HashMap<ResourceType, Integer> newStrongbox, HashMap<ResourceType, Integer> paymentWarehouse);

    /**
     * Sends chosen DevCards to be activated
     * @param chosenCards the chosen devcards.
     */
    public void updateChosenProdCards(ArrayList<DevCard> chosenCards);

    /**
     * Plays or discards a leader card.
     * @param card the chosen card.
     * @param dOrP identifies if the player wants to discard or play the chosen card.
     */
    public void updatePlayLeaderCard(LeaderCard card, char dOrP);

    /**
     * Shows a player
     * @param indexOf the username of the player
     */
    public void updateShowPlayer(String indexOf);

    /**
     * Disconnects from the game.
     */
    public void updateDisconnect();
}
