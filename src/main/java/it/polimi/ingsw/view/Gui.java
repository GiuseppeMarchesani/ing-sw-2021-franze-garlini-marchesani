package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board.Market;
import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.DevCardSlot;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Gui extends ObservableView implements View {

    @Override
    public void askUsername() {

    }

    @Override
    public void askGameID() {

    }

    @Override
    public void askGameCreation() {

    }

    @Override
    public void askPlayersNumber() {

    }

    @Override
    public void askInitialRes() {

    }

    @Override
    public void askAction() {

    }

    @Override
    public void showLoginResult(String username, String gameId, boolean wasJoined) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showDisconnectionMsg(String disconnectedUser, String message) {

    }

    @Override
    public void showMatchInfo(List<String> players, String activePlayer) {

    }

    @Override
    public void showLeaderCards(List<LeaderCard> leaderCards, String username) {

    }

    @Override
    public void askDevCardToBuy(List<DevCard> availableCards) {

    }

    @Override
    public void askMarketLineToGet(Market market) {

    }

    @Override
    public void showMarket(Market market) {

    }

    @Override
    public void showDevMarket(List<DevCard> availableCards) {

    }

    @Override
    public void askCardsToActivateProd(List<DevCard> availableCards) {

    }

    @Override
    public void showResources(HashMap<ResourceType, Integer> strongbox, Warehouse warehouse, String username) {

    }

    @Override
    public void showResources(HashMap<ResourceType, Integer> strongbox, Warehouse warehouse) {

    }

    @Override
    public void showErrorMsg(String message) {

    }

    @Override
    public void showFaithTrack(HashMap<String, Integer> faithTrackState) {

    }

    @Override
    public void showCurrentVP(int victoryPoints, String username) {

    }

    @Override
    public void showSlots(DevCardSlot devCardSlot, String username) {

    }

    @Override
    public void askSlot(ArrayList<Integer> availableSlots) {

    }

    @Override
    public void askChooseMarbleConversion(ArrayList<ResourceType> availableConversions) {

    }

    @Override
    public void askChooseResToPay(HashMap<ResourceType, Integer> strongbox, Warehouse warehouse, ResourceType resource) {

    }

    @Override
    public void askChooseOneRes(ArrayList<String> resources, String message) {

    }

    @Override
    public void askChooseFloor(Warehouse warehouse, ResourceType resToPlace) {

    }

    @Override
    public void askRearrange(Warehouse warehouse, HashMap<ResourceType, Integer> resources) {

    }

    @Override
    public void askRearrange(String username, String gameId, Warehouse warehouse, HashMap<ResourceType, Integer> resources) {

    }

    @Override
    public void askLeaderCardToPlay(List<LeaderCard> leaderCards) {

    }

    @Override
    public void askLeaderCardToKeep(List<LeaderCard> leaderCards) {

    }

    @Override
    public void showWinMessage(String winnerUser) {

    }
}
