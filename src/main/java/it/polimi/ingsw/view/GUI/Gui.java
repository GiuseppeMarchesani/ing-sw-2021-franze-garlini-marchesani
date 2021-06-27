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
    private static ArrayList<LeaderCard> availableLeader= new ArrayList<>();
    private static ResourceType[][] market = new ResourceType[4][3];
    private static ResourceType cornerMarble;
    private static ArrayList<DevCard> cardMarket = new ArrayList<>();
    private static String activePlayer;
    private static ArrayList<String> playerList = new ArrayList<>();
    private static ArrayList<LeaderCard> leaderCards = new ArrayList<>();
    private static HashMap<String, ArrayList<LeaderCard>> chosenLeader = new HashMap<>();
    private static HashMap<String, Integer> faithTrack = new HashMap<>();
    private static ArrayList<ResourceType> conversion = new ArrayList<>();
    private static HashMap<ResourceType, Integer> resToPlace = new HashMap<>();
    private static ArrayList<ResourceType> extraDepot = new ArrayList<>();
    private static HashMap<Integer, ResourceType> activeDepotT = new HashMap<>();
    private static HashMap<Integer, Integer> activeDepotQ = new HashMap<>();
    private static HashMap<String, Integer> finalVip = new HashMap<>();
    private static HashMap<ResourceType, Integer> strongbox = new HashMap<>();


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
        BoardSceneController bsc;
        try {
            bsc = (BoardSceneController) MainApp.getActiveController();
        } catch (ClassCastException e) {
            bsc = new BoardSceneController();
            bsc.addAllObservers(observers);
        }
        Platform.runLater(()->
                MainApp.changeRootPane(observers, "/fxml/board_scene"));
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
        Gui.conversion = conversion;
        MarketSceneController msc = new MarketSceneController();
        msc.addAllObservers(observers);
        Platform.runLater(()->
                MainApp.changeRootPane(observers,"/fxml/market_scene")
        );
    }

    @Override
    public void askResourceToWarehouse(HashMap<ResourceType, Integer> resToPlace, int numAny, ArrayList<ResourceType> extraDepot) {
        Gui.resToPlace.putAll(resToPlace);
        Gui.extraDepot = extraDepot;
        PlaceResourcesSceneController prsc = new PlaceResourcesSceneController();
        prsc.addAllObservers(observers);
        Platform.runLater(()->
                MainApp.changeRootPane(observers,"/fxml/place_resources_scene")
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
        cornerMarble = corner;
    }

    @Override
    public void showDevMarket(ArrayList<DevCard> availableCards, ArrayList<Integer> remainingCards) {
        Gui.cardMarket.addAll(availableCards);
    }

    @Override
    public void showStrongbox(HashMap<ResourceType, Integer> strongbox, String username) {

    }

    @Override
    public void showWarehouse(HashMap<Integer, Integer> depotToQuantity, HashMap<Integer, ResourceType> depotToResource, String username) {
        Gui.activeDepotQ.putAll(depotToQuantity);
        Gui.activeDepotT.putAll(depotToResource);
        Gui.activePlayer = username;
        /*
        activeDepotT.putAll(depotToResource);
        activeDepotQ.putAll(depotToQuantity);
        activePlayer = username;
        ShowWarehouseSceneController swsc = new ShowWarehouseSceneController();
        swsc.addAllObservers(observers);
        Platform.runLater(()->
                MainApp.changeRootPane(observers,"/fxml/showWarehouse_scene")
        );

         */
    }

    @Override
    public void showPlayedLeaderCards(ArrayList<LeaderCard> playedLeaderCards, String activePlayer){

    }

    @Override
    public void showPlayer(String username, int faithSpace, HashMap<Integer, ResourceType> depotToResource, HashMap<Integer, Integer> depotToQuantity, HashMap<ResourceType, Integer> strongbox, DevCardSlot devCardSlot, ArrayList<LeaderCard> playedLeaderCards, int remainingLeaderCards) {

    }

    @Override
    public void showPlayerFaith(ArrayList<Integer> faith) {

    }

    @Override
    public void askCardsToActivateProd(ArrayList<DevCard> devCardList) {

    }

    @Override
    public void showErrorMsg(String message) {

    }

    @Override
    public void showFaithTrack( boolean wasZoneActivated, int whichZone) {
    }

    @Override
    public void showCurrentVP(HashMap<String, Integer> victoryPoints) {

    }

    @Override
    public void showSlots(DevCardSlot devCardSlot, String username) {

    }


    @Override
    public void askSlot(HashMap<ResourceType, Integer> strongbox, HashMap<ResourceType, Integer> warehouse, HashMap<ResourceType, Integer> cardCost, int numAny, ArrayList<Integer> availableSlots) {

    }

    @Override
    public void askLeaderCardToPlay(ArrayList<LeaderCard> leaderCards) {

    }

    @Override
    public void askLeaderCardToKeep(ArrayList<LeaderCard> leaderCards) {
        availableLeader.addAll(leaderCards);
        ChooseLeaderToKeep cltk = new ChooseLeaderToKeep();
        cltk.addAllObservers(observers);
        Platform.runLater(()->
                MainApp.changeRootPane(observers,"/fxml/choose_leaderToKeep")
        );

    }

    @Override
    public void showWinMessage(HashMap<String, Integer> finalPoints) {
        finalVip.putAll(finalPoints);
        WinSceneController wsc = new WinSceneController();
        wsc.addAllObservers(observers);
        Platform.runLater(() -> MainApp.changeRootPane(observers, "/fxml/winner_scene"));
    }

    @Override
    public void showLoseMessage() {

    }

    @Override
    public void showLeaderCards(HashMap<LeaderCard, Boolean> leaderCards) {
        Gui.leaderCards.addAll(leaderCards.keySet());
    }

    public static ArrayList<LeaderCard> getAvailableLeader(){
        return availableLeader;
    }
    public static ArrayList<LeaderCard> getLeaderCards(){
        return leaderCards;
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

    public static String getActivePlayer(){
        return activePlayer;
    }

    public static ArrayList<String> getPlayerList(){
        return playerList;
    }

    public static HashMap<String, ArrayList<LeaderCard>> getChosenLeader(){
        return chosenLeader;
    }

    public static HashMap<String, Integer> getFaithTrack(){
        return faithTrack;
    }

    public static void getActivePlayer(String activePlayer) {
        Gui.activePlayer = activePlayer;
    }

    public static ArrayList<ResourceType> getConversion(){
        return conversion;
    }

    public static ArrayList<ResourceType> getExtraDepot() {
        return extraDepot;
    }

    public static HashMap<ResourceType, Integer> getResToPlace() {
        return resToPlace;
    }

    public static HashMap<Integer, Integer> getActiveDepotQ() {
        return activeDepotQ;
    }

    public static HashMap<Integer, ResourceType> getActiveDepotT() {
        return activeDepotT;
    }


    public static HashMap<String, Integer> getFinalVp(){
        return finalVip;
    }

}
