package it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.DevCardSlot;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.scene.*;
import it.polimi.ingsw.view.View;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.HashMap;

public class Gui extends ObservableView implements View {

    private static ResourceType[][] market = new ResourceType[4][3];
    private static ResourceType cornerMarble;
    private static ArrayList<DevCard> cardMarket = new ArrayList<>();
    private static ArrayList<String> playerList = new ArrayList<>();
    private ArrayList<LeaderCard> leaderCards = new ArrayList<>();
    private ArrayList<LeaderCard> chosenLeader = new ArrayList<>();
    private static HashMap<String, Integer> faithTrack = new HashMap<>();
    private ArrayList<ResourceType> conversion = new ArrayList<>();
    private HashMap<Integer, ResourceType> activeDepotT = new HashMap<>();
    private HashMap<Integer, Integer> activeDepotQ = new HashMap<>();
    private HashMap<String, Integer> finalVip = new HashMap<>();
    private HashMap<ResourceType, Integer> strongbox = new HashMap<>();
    private boolean startGame = false;
    private HashMap<ResourceType, Integer> anyRes = new HashMap<>();
    private HashMap<Boolean, Integer> faithZone = new HashMap<>();


    @Override
    public void askConnect() {
        InitSceneController isc = new InitSceneController();
        isc.addAllObservers(observers);
        Platform.runLater(() -> {
            MainApp.changeRootPane(observers, "/fxml/init_scene");
        });
    }

    @Override
    public void askLobby() {
        LobbySceneController lsc = new LobbySceneController();
        lsc.addAllObservers(observers);
        Platform.runLater(() -> MainApp.changeRootPane(observers, "/fxml/lobby_scene")
        );
    }

    @Override
    public void askPlayersNumber() {
        NumPlayerSceneController npsc = new NumPlayerSceneController();
        npsc.addAllObservers(observers);
        Platform.runLater(()->
                MainApp.changeRootPane(observers,"/fxml/numPlayer_scene")
            );
    }

    @Override
    public void askAction() {
        if(!startGame){
            startGame = true;
            Platform.runLater(()-> MainApp.changeRootMainScene(observers));
            Platform.runLater(() -> MainApp.getMainScene().update(market, cornerMarble, cardMarket, activeDepotQ, activeDepotT, chosenLeader, faithTrack, strongbox));
        }
        else{
            Platform.runLater(() -> MainApp.getMainScene().update(market, cornerMarble, cardMarket, activeDepotQ, activeDepotT, chosenLeader, faithTrack, strongbox));
            Platform.runLater(()-> MainApp.changeRootMainScene(observers));
        }



    }

    @Override
    public void showLoginResult(String username, String gameId, boolean wasJoined) {
       playerList.add(username);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void askDevCardToBuy() {
        ChooseDevCardSceneController cdcsc = new ChooseDevCardSceneController();
        cdcsc.addAllObservers(observers);
        Platform.runLater(()->
                MainApp.changeRootPane(observers,"/fxml/cardMarket_scene")
        );
    }

    @Override
    public void askMarketLineToGet(ArrayList<ResourceType> conversion) {
        //TODO: add conversion
        MarketSceneController msc = new MarketSceneController();
        msc.addAllObservers(observers);
        Platform.runLater(()->
                MainApp.changeRootPane(msc,"/fxml/market_scene")
        );
    }

    @Override
    public void askResourceToWarehouse(HashMap<ResourceType, Integer> resToPlace, int numAny, ArrayList<ResourceType> extraDepot) {
        PlaceResourcesSceneController prsc = new PlaceResourcesSceneController();
        prsc.addAllObservers(observers);
        prsc.setResToPlace(resToPlace);
        prsc.setExtraDepot(extraDepot);
        Platform.runLater(()->
                MainApp.changeRootPane(prsc,"/fxml/place_resources_scene")
        );

    }

    @Override
    public void askProduction(HashMap<ResourceType, Integer> strongbox, HashMap<ResourceType, Integer> warehouse, HashMap<ResourceType, Integer> price, int anyPayment, int anyProduce) {

    }

    @Override
    public void showMarket(ResourceType[][] market, ResourceType corner) {
        for(int i =0; i<4; i++){
            for (int j = 0; j<3; j++){
                Gui.market[i][j] = market[i][j];
            }
        }
        Gui.cornerMarble = corner;
    }

    @Override
    public void showDevMarket(ArrayList<DevCard> availableCards, ArrayList<Integer> remainingCards) {
        Gui.cardMarket = availableCards;
    }

    @Override
    public void showStrongbox(HashMap<ResourceType, Integer> strongbox, String username) {
        this.strongbox = strongbox;
    }

    @Override
    public void showWarehouse(HashMap<Integer, Integer> depotToQuantity, HashMap<Integer, ResourceType> depotToResource, String username) {
        this.activeDepotQ =depotToQuantity;
        this.activeDepotT =depotToResource;
    }

    @Override
    public void showPlayedLeaderCards(ArrayList<LeaderCard> playedLeaderCards, String activePlayer){

    }

    @Override
    public void showPlayer(String username, int faithSpace, HashMap<Integer, ResourceType> depotToResource, HashMap<Integer, Integer> depotToQuantity, HashMap<ResourceType, Integer> strongbox, DevCardSlot devCardSlot, ArrayList<LeaderCard> playedLeaderCards, int remainingLeaderCards) {

    }

    @Override
    public void showPlayerFaith(ArrayList<Integer> faith) {
        for(int i=0; i<playerList.size(); i++){
            faithTrack.put(playerList.get(i), faith.get(i));
        }
    }

    @Override
    public void askCardsToActivateProd(ArrayList<DevCard> devCardList) {

    }

    @Override
    public void showErrorMsg(String message) {
        Platform.runLater(() -> MessageSceneController.display("Error", message));
    }

    @Override
    public void showFaithTrack( boolean wasZoneActivated, int whichZone) {
        this.faithZone.put(wasZoneActivated, whichZone);
    }

    @Override
    public void showCurrentVP(HashMap<String, Integer> victoryPoints) {

    }

    @Override
    public void showSlots(DevCardSlot devCardSlot, String username) {

    }

    @Override
    public void askSlot(HashMap<ResourceType, Integer> strongbox, HashMap<ResourceType, Integer> warehouse, HashMap<ResourceType, Integer> cardCost, int numAny, ArrayList<Integer> availableSlots) {

        for(int i=0; i<numAny; i++){
            ResourceType res = askAnyResource();
            if(anyRes.containsKey(res)){
                anyRes.put(res, anyRes.get(res)+1);
            }
            else{
                anyRes.put(res, 1);
            }
        }
        AskSlotSceneController assc = new AskSlotSceneController();
        assc.addAllObservers(observers);
        assc.setCardCost(cardCost);
        assc.setStrongbox(strongbox);
        assc.setAvailableSlots(availableSlots);
        assc.setWarehouse(warehouse);
        Platform.runLater(() -> MainApp.changeRootPane(assc, "/fxml/askSlot_scene"));
    }

    @Override
    public void askLeaderCardToPlay(ArrayList<LeaderCard> leaderCards) {

    }

    @Override
    public void askLeaderCardToKeep(ArrayList<LeaderCard> leaderCards) {
        ChooseLeaderToKeep cltk = new ChooseLeaderToKeep();
        cltk.addAllObservers(observers);
        cltk.setAllLeaders(leaderCards);
        Platform.runLater(()->
                MainApp.changeRootPane(cltk,"/fxml/choose_leaderToKeep")
        );
        chosenLeader = cltk.getRestLeader();
    }

    @Override
    public void showWinMessage(HashMap<String, Integer> finalPoints) {
        finalVip.putAll(finalPoints);
        WinSceneController wsc = new WinSceneController();
        wsc.addAllObservers(observers);
        wsc.setFinalVp(finalPoints);
        Platform.runLater(() -> MainApp.changeRootPane(wsc, "/fxml/winner_scene"));
    }

    @Override
    public void showLoseMessage() {

    }

    private ResourceType askAnyResource(){
        AnySceneController asc = new AnySceneController();
        asc.addAllObservers(observers);
        Platform.runLater(() -> MainApp.changeRootPane(observers, "/fxml/any_scene"));
        return asc.getAny();
    }

    @Override
    public void showLeaderCards(HashMap<LeaderCard, Boolean> leaderCards) {
        this.leaderCards.addAll(leaderCards.keySet());
    }

    public static ResourceType[][] getMarket() {
        return market;
    }

    public static ResourceType getCornerMarble(){
        return cornerMarble;
    }

    public static ArrayList<DevCard> getCardMarket(){
        return cardMarket;
    }

    public static ArrayList<String> getPlayerList(){
        return playerList;
    }
}
