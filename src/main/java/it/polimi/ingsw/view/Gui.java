package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board.Market;
import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.DevCardSlot;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.observer.ObserverView;
import it.polimi.ingsw.view.ScenesController.SceneController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Gui extends ObservableView implements View {
    private Scene activeScene;
    private SceneController activeController;

    private void changePane(Scene scene, SceneController sceneController, String fxmlFile) {
        this.activeScene = scene;
        this.activeController = sceneController;

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/" + fxmlFile));
            loader.setController(sceneController);

            Parent root = loader.load();

            activeScene = scene;
            activeScene.setRoot(root);
        } catch(IOException e) {

        }

    }

    @Override
    public void askConnect() {

    }

    @Override
    public void askLobby() {

    }

    @Override
    public void askPlayersNumber() {

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
    public void askMarketLineToGet(ResourceType[][] market, ResourceType corner) {

    }

    @Override
    public void askResourceToWarehouse(HashMap<ResourceType, Integer> resToPlace, int numAny, ArrayList<ResourceType> extraDepot) {

    }

    @Override
    public void showMarket(ResourceType[][] market, ResourceType corner) {

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
    public void askLeaderCardToPlay(List<LeaderCard> leaderCards) {

    }

    @Override
    public void askLeaderCardToKeep(ArrayList<LeaderCard> leaderCards) {

    }

    @Override
    public void showWinMessage(String winnerUser) {

    }
}
