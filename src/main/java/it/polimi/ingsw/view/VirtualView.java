package it.polimi.ingsw.view;


import it.polimi.ingsw.messages.GeneralMessage;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Board.Market;
import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.observer.Observer;

import java.util.HashMap;
import java.util.List;

/**
 * Hides the network implementation from the controller.
 * The controller calls methods from this class instead of calling methods from the view.
 */
public class VirtualView implements View, Observer {
    private final ClientHandler clientHandler;

    /**
     * Class constructor.
     * @param clientHandler the clientHandler the view will sends messages to.
     */
    public VirtualView(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public void askUsername() {
        //clientHandler.sendMessage(new LoginReply());
    }

    @Override
    public void askGameID() {
        //clientHandler.sendMessage(new GameIDRequest());
    }

    @Override
    public void askGameCreation() {
        //clientHandler.sendMessage(new NewGameRequest());
    }

    @Override
    public void askPlayersNumber() {
        //clientHandler.sendMessage(new PlayersNumberRequest());
    }

    @Override
    public void showLoginResult(boolean usernameAccepted, boolean connectionSuccessful, String username) {
        //clientHandler.sendMessage(new LoginReply(usernameAccepted, connectionSuccessful));
    }

    @Override
    public void showMessage(String message) {
        //clientHandler.sendMessage(new GeneralMessage(message));
    }

    @Override
    public void showDisconnectionMsg(String disconnectedUser, String message) {
        //clientHandler.sendMessage(new DisconnectionMessage(disconnectedUser, message));
    }

    @Override
    public void showMatchInfo(List<String> players, String activePlayer) {
        //clientHandler.sendMessage(new MatchInfoRequest(players, activePlayer));
    }

    @Override
    public void showLeaderCards(List<LeaderCard> leaderCards) {
        //clientHandler.sendMessage(new LeaderCardShowReq(leaderCards));
    }

    @Override
    public void askChooseLeaderCards(List<LeaderCard> leaderCards) {
        //clientHandler.sendMessage(new LeaderCardChooseMsg(leaderCards));
    }

    @Override
    public void askBuyDevCard(List<DevCard> devCardList) {
        //clientHandler.sendMessage(new BuyDevCardRequest(devCardList));
    }

    @Override
    public void askGetMarketRes() {
        //clientHandler.sendMessage(new GetMarketRequest(rowOrCol, rowOrColNum));
    }

    @Override
    public void showMarket(Market market) {
        //clientHandler.sendMessage(new MarketMessage());
    }

    @Override
    public void askActivateProduction(List<DevCard> devCardList) {
        //clientHandler.sendMessage(new ActivateProductionRequest(devCardList));
    }

    @Override
    public void showResources(HashMap<ResourceType, Integer> strongbox, Warehouse warehouse) {
        //clientHandler.sendMessage(new ShowResourcesMsg(strongbox, warehouse));
    }

    @Override
    public void showErrorMsg(String message) {
        //clientHandler.sendMessage(new
    }

    @Override
    public void showFaithTrack(List<Integer> faithTrackState) {
        //clientHandler.sendMessage(new ShowFaithTrackMsg(faithTrackState));
    }

    @Override
    public void showActualVP(int victoryPoints) {
        //clientHandler.sendMessage(new ShowActualVPMsg(victoryPoints));
    }

    @Override
    public void askToShowResources() {
        //clientHandler.sendMessage(new ShowResourcesRequest());
    }

    @Override
    public void askToShowFaithTrack() {
        //clientHandler.sendMessage(new ShowFaithTrackRequest());
    }

    @Override
    public void askToShowActualVP() {
        //clientHandler.sendMessage(new ShowActualVPRequest());
    }

    @Override
    public void chooseResToPay(HashMap<ResourceType, Integer> strongbox, Warehouse warehouse) {
        //clientHandler.sendMessage(new ResourceToPayMsg(strongbox, warehouse));
    }

    @Override
    public void askRearrangeDepot() {
        //clientHandler.sendMessage(new RearrangeRequest(depot1, depot2));
    }

    @Override
    public void playLeaderCard(List<LeaderCard> leaderCards) {
        //clientHandler.sendMessage(new PlayLeaderRequest(leaderCards));
    }

    @Override
    public void discardLeaderCard(List<LeaderCard> leaderCards) {
        //clientHandler.sendMessage(new DiscardLeaderRequest(leaderCards));
    }

    @Override
    public void showWinMessage(String winnerUser) {
        //clientHandler.sendMessage(new WinMessage(winner))
    }

    /**
     * Receives an update message from the model.
     * The message is sent over the network to the client.
     * @param message the update message.
     */
    @Override
    public void update(GeneralMessage message) {
        //clientHandler.sendMessage(message);
    }
}
