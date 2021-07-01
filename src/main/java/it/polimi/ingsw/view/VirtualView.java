package it.polimi.ingsw.view;


import it.polimi.ingsw.messages.GeneralMessage;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.DevCardSlot;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.network.ClientHandlerInterface;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Hides the network implementation from the controller.
 * The controller calls methods from this class instead of calling methods from the view.
 */
public class VirtualView implements View, Observer {
    private final ClientHandlerInterface clientHandler;


    /**
     * Class constructor.
     * @param clientHandler the clientHandler the view will sends messages to.
     */
    public VirtualView(ClientHandlerInterface clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public void askConnect() {
        //Won't be used
    }

    @Override
    public void askLobby() {
        //Won't be used
    }

    @Override
    public void askPlayersNumber() {
        clientHandler.sendMessage(new HostGameReply());
    }

    /**
     * This method is used to asks the player to choose his initial resources.
     * Will use the method askResourceToWarehouse.
     */
    public void askInitialRes(int numAny) {
        askResourceToWarehouse(new HashMap<ResourceType, Integer>(), numAny , new ArrayList<>());
    }

    @Override
    public void askAction() {
        clientHandler.sendMessage(new StartTurnReply());
    }

    @Override
    public void showLoginResult(String username, String gameId, boolean wasJoined) {
        clientHandler.sendMessage(new LoginReply(username, gameId, wasJoined));
    }

    @Override
    public void showMessage(String message) {
        clientHandler.sendMessage(new StringMessage(message));
    }

    @Override
    public void askDevCardToBuy() {
        clientHandler.sendMessage(new BuyDevCardReply());
    }

    @Override
    public void askMarketLineToGet(ArrayList<ResourceType> conversion) {
        clientHandler.sendMessage(new GetMarketResReply(conversion));
    }

    @Override
    public void askResourceToWarehouse(HashMap<ResourceType, Integer> resToPlace, int numAny, ArrayList<ResourceType> extraDepot) {
        clientHandler.sendMessage(new ResourceToWarehouseReply(resToPlace, numAny, extraDepot));
    }

    @Override
    public void showMarket(ResourceType[][] market, ResourceType corner) {
        clientHandler.sendMessage(new ShowMarketMsg(market, corner));
    }

    @Override
    public void showDevMarket(ArrayList<DevCard> availableCards, ArrayList<Integer> remainingCards) {
        clientHandler.sendMessage(new ShowDevMarketMsg(availableCards, remainingCards));
    }

    @Override
    public void askCardsToActivateProd(ArrayList<DevCard> devCardList) {
        clientHandler.sendMessage(new AskProductionReply(devCardList));
    }

    @Override
    public void askProduction(HashMap<ResourceType, Integer> strongbox, HashMap<ResourceType, Integer> warehouse, HashMap<ResourceType, Integer>  price, int anyPayment, int anyProduce){
        clientHandler.sendMessage(new GetProductionReply(strongbox, warehouse, price, anyPayment, anyProduce));
    }

    @Override
    public void showErrorMsg(String message) {
        clientHandler.sendMessage(new ErrorMsg(message));
    }

    @Override
    public void showFaithTrack( boolean wasZoneActivated, int whichZone) {
        clientHandler.sendMessage(new ShowFaithTrackMsg( wasZoneActivated, whichZone));
    }

    @Override
    public void showCurrentVP(HashMap<String, Integer> victoryPoints) {
        clientHandler.sendMessage(new ShowVictoryPointsMsg(victoryPoints));
    }

    @Override
    public void showSlots(DevCardSlot devCardSlot, String username) {
        clientHandler.sendMessage(new ShowSlotsMsg(devCardSlot, username));
    }

    @Override
    public void askSlot( HashMap<ResourceType, Integer> strongbox, HashMap<ResourceType, Integer> warehouse, HashMap<ResourceType, Integer> cardCost, int any, ArrayList<Integer> availableSlots) {
        clientHandler.sendMessage(new PlaceDevCardReply(strongbox, warehouse, cardCost,any, availableSlots));
    }

    @Override
    public void askLeaderCardToPlay(ArrayList<LeaderCard> AllleaderCards) {
        clientHandler.sendMessage(new SideLeaderReply( AllleaderCards));
    }

    @Override
    public void askLeaderCardToKeep(ArrayList<LeaderCard> leaderCards) {
        clientHandler.sendMessage(new StartingLeadersReplyMsg(leaderCards));
    }

    @Override
    public void showWinMessage(HashMap<String, Integer> finalPoints) {
        clientHandler.sendMessage(new WinMessage(finalPoints));
    }

    /**
     * Receives an update message from the model.
     * The message is sent over the network to the client.
     * @param message the update message.
     */
    @Override
    public void update(GeneralMessage message) {
        clientHandler.sendMessage(message);
    }

    @Override
    public void showWarehouse(HashMap<Integer, Integer> depotToQuantity, HashMap<Integer, ResourceType> depotToResource, String activePlayer){
        clientHandler.sendMessage(new ShowWarehouseMsg(depotToResource, depotToQuantity, activePlayer));
    }

    @Override
    public void showStrongbox(HashMap<ResourceType, Integer> strongbox, String activePlayer){
        clientHandler.sendMessage(new ShowStrongboxMsg(strongbox, activePlayer));
    }


    @Override
    public void showLoseMessage() {
        clientHandler.sendMessage(new LoseMessage());
    }

    @Override
    public void showLeaderCards(HashMap<LeaderCard, Boolean> leaderCards) {
        clientHandler.sendMessage(new ShowLeaderCardsMsg(leaderCards));
    }
    @Override
    public void showPlayedLeaderCards(ArrayList<LeaderCard> playedLeaderCards, String activePlayer) {
        clientHandler.sendMessage(new ShowPlayedLeadersMsg(playedLeaderCards, activePlayer));
    }
    @Override
    public void showPlayer(String username, int faithSpace, HashMap<Integer, ResourceType> depotToResource, HashMap<Integer, Integer> depotToQuantity, HashMap<ResourceType, Integer> strongbox, DevCardSlot devCardSlot, ArrayList<LeaderCard> playedLeaderCards, int remainingLeaderCards, String self){
        clientHandler.sendMessage(new ShowPlayerReply(username, faithSpace,depotToResource,depotToQuantity,strongbox,devCardSlot,playedLeaderCards, remainingLeaderCards));
    }
    @Override
    public void showPlayerFaith(ArrayList<Integer> faith){
        clientHandler.sendMessage(new ShowPlayerFaithMsg(faith));
    }
}
