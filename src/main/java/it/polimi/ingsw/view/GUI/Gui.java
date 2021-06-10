package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.DevCardSlot;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.View;
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
    public void askDevCardToBuy() {

    }

    @Override
    public void askMarketLineToGet(ArrayList<ResourceType> conversion) {

    }

    @Override
    public void askResourceToWarehouse(HashMap<ResourceType, Integer> resToPlace, int numAny, ArrayList<ResourceType> extraDepot) {

    }

    @Override
    public void askProduction(HashMap<ResourceType, Integer> strongbox, HashMap<ResourceType, Integer> price, int anyPayment, int anyProduce) {

    }

    @Override
    public void showMarket(ResourceType[][] market, ResourceType corner) {

    }

    @Override
    public void showDevMarket(ArrayList<DevCard> availableCards, ArrayList<Integer> remainingCards) {

    }

    @Override
    public void showStrongbox(HashMap<ResourceType, Integer> strongbox, String username) {

    }

    @Override
    public void showWarehouse(HashMap<Integer, Integer> depotToQuantity, HashMap<Integer, ResourceType> depotToResource, String username) {

    }

    @Override
    public void askCardsToActivateProd(ArrayList<DevCard> devCardList) {

    }

    @Override
    public void showErrorMsg(String message) {

    }

    @Override
    public void showFaithTrack(HashMap<String, Integer> playerFaith, boolean wasZoneActivated, int whichZone) {

    }

    @Override
    public void showCurrentVP(HashMap<String, Integer> victoryPoints) {

    }

    @Override
    public void showSlots(DevCardSlot devCardSlot, String username) {

    }

    @Override
    public void showRemainingLeaderCards(String username, int remaining) {

    }

    @Override
    public void askSlot(HashMap<ResourceType, Integer> strongbox, HashMap<ResourceType, Integer> cardCost, int numAny, ArrayList<Integer> availableSlots) {

    }

    @Override
    public void askLeaderCardToPlay(ArrayList<LeaderCard> leaderCards) {

    }

    @Override
    public void askLeaderCardToKeep(ArrayList<LeaderCard> leaderCards) {

    }

    @Override
    public void showWinMessage(HashMap<String, Integer> finalPoints) {

    }

    @Override
    public void showLoseMessage() {

    }
}
