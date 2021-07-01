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
    private HashMap<LeaderCard, Boolean> chosenLeader = new HashMap<>();
    private static HashMap<String, Integer> faithTrack = new HashMap<>();
    private ArrayList<ResourceType> conversion = new ArrayList<>();
    private HashMap<Integer, ResourceType> activeDepotT = new HashMap<>();
    private HashMap<Integer, Integer> activeDepotQ = new HashMap<>();
    private HashMap<String, Integer> finalVip = new HashMap<>();
    private HashMap<ResourceType, Integer> strongbox = new HashMap<>();
    private boolean startGame = false;
    private HashMap<ResourceType, Integer> anyRes = new HashMap<>();
    private HashMap<Integer, Boolean> faithZone = new HashMap<>();
    private DevCardSlot devCardSlot = new DevCardSlot();
    private static ArrayList<Integer> remainingCards = new ArrayList<>();
    private HashMap<ResourceType, Integer> extraDepotRes = new HashMap<>();


    @Override
    public void askConnect() {
        InitSceneController isc = new InitSceneController();
        isc.addAllObservers(observers);
        Platform.runLater(() -> {
            SceneController.changeRootPane(observers, "/fxml/init_scene");
        });
    }

    @Override
    public void askLobby() {
        LobbySceneController lsc = new LobbySceneController();
        lsc.addAllObservers(observers);
        Platform.runLater(() -> SceneController.changeRootPane(observers, "/fxml/lobby_scene")
        );
    }

    @Override
    public void askPlayersNumber() {
        NumPlayerSceneController npsc = new NumPlayerSceneController();
        npsc.addAllObservers(observers);
        Platform.runLater(()->
                SceneController.changeRootPane(observers,"/fxml/numPlayer_scene")
            );
    }

    @Override
    public void askAction() {
        if(faithZone.size()==0){
            faithZone.put(0, false);
            faithZone.put(1, false);
            faithZone.put(2, false);
        }
        if(!startGame){
            startGame = true;
            Platform.runLater(()-> SceneController.changeRootMainScene(observers));
            Platform.runLater(() -> SceneController.getMainScene().update(market, cornerMarble, cardMarket, remainingCards, activeDepotQ, activeDepotT, chosenLeader, faithTrack, faithZone, strongbox, devCardSlot));
        }
        else{
            Platform.runLater(() -> SceneController.getMainScene().update(market, cornerMarble, cardMarket, remainingCards,  activeDepotQ, activeDepotT, chosenLeader, faithTrack, faithZone, strongbox, devCardSlot));
            Platform.runLater(()-> SceneController.changeRootMainScene(observers));
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
        cdcsc.setDevCard(cardMarket);
        cdcsc.setRemainingCards(remainingCards);
        Platform.runLater(()->
                SceneController.changeRootPane(cdcsc,"/fxml/cardMarket_scene")
        );
    }

    @Override
    public void askMarketLineToGet(ArrayList<ResourceType> conversion) {
        MarketSceneController msc = new MarketSceneController();
        msc.addAllObservers(observers);
        Platform.runLater(()->
                SceneController.changeRootPane(msc,"/fxml/market_scene")
        );
    }

    @Override
    public void askResourceToWarehouse(HashMap<ResourceType, Integer> resToPlace, int numAny, ArrayList<ResourceType> extraDepot) {
        if (numAny > 0) {
            for(int i=0; i<numAny; i++){
                AnySceneController asc = new AnySceneController();
                asc.addAllObservers(observers);
                asc.setResToPlace(resToPlace);
                asc.setExtraDepot(extraDepot);
                Platform.runLater(() -> SceneController.changeRootPane(observers, "/fxml/any_scene"));
            }
        }
        else {
            PlaceResourcesSceneController prsc = new PlaceResourcesSceneController();
            prsc.addAllObservers(observers);
            prsc.setResToPlace(resToPlace);
            prsc.setExtraDepot(extraDepot);
            Platform.runLater(() ->
                    SceneController.changeRootPane(prsc, "/fxml/place_resources_scene")
            );
        }
        /*
        extraDepotRes = asc.getExtraDepotRes();

         */

    }

    @Override
    public void askProduction(HashMap<ResourceType, Integer> strongbox, HashMap<ResourceType, Integer> warehouse, HashMap<ResourceType, Integer> price, int anyPayment, int anyProduce) {
        if (anyPayment > 0) {
            AnyForProdPaySceneController afp = new AnyForProdPaySceneController();
            afp.addAllObservers(observers);
            afp.setStrongbox(strongbox);
            afp.setWarehouse(warehouse);
            afp.setPrice(price);
            afp.setAnyPayment(anyPayment);
            afp.setAnyProduce(anyProduce);
            Platform.runLater(() -> SceneController.changeRootPane(afp, "/fxml/anyForProduction_scene"));
        }
        else {
            AskProductionSceneController apsc = new AskProductionSceneController();
            apsc.addAllObservers(observers);
            apsc.setStrongbox(strongbox);
            apsc.setWarehouse(warehouse);
            apsc.setPrice(price);
            apsc.setAnyProduce(anyProduce);
            Platform.runLater(() -> SceneController.changeRootPane(apsc, "/fxml/chooseCard_production_scene"));
        }
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
        Gui.remainingCards = remainingCards;
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
        for(LeaderCard ld: playedLeaderCards){
            for(LeaderCard ld1 : chosenLeader.keySet()){
                if(ld1.getId() == ld.getId()){
                    chosenLeader.remove(ld1);
                    chosenLeader.put(ld, true);
                }
            }
    }

    }

    @Override
    public void showPlayer(String username, int faithSpace, HashMap<Integer, ResourceType> depotToResource, HashMap<Integer, Integer> depotToQuantity, HashMap<ResourceType, Integer> strongbox, DevCardSlot devCardSlot, ArrayList<LeaderCard> playedLeaderCards, int remainingLeaderCards, String self) {

    }

    @Override
    public void showPlayerFaith(ArrayList<Integer> faith) {
        for(int i=0; i<playerList.size(); i++){
            faithTrack.put(playerList.get(i), faith.get(i));
            if(playerList.size()==1) {
                faithTrack.put("Lorenzo il Magnifico", faith.get(i+1));
            }
        }
    }

    @Override
    public void askCardsToActivateProd(ArrayList<DevCard> devCardList) {
        AskCardsForProdSceneController acsc = new AskCardsForProdSceneController();
        acsc.addAllObservers(observers);
        acsc.setDevCardList(devCardList);
        Platform.runLater(() -> SceneController.changeRootPane(acsc, "/fxml/chooseCard_production_scene"));
    }

    @Override
    public void showErrorMsg(String message) {
        Platform.runLater(() -> MessageSceneController.display("Error", message));
    }

    @Override
    public void showFaithTrack( boolean wasZoneActivated, int whichZone) {
        faithZone.replace(whichZone, wasZoneActivated);
    }

    @Override
    public void showCurrentVP(HashMap<String, Integer> victoryPoints) {

    }

    @Override
    public void showSlots(DevCardSlot devCardSlot, String username) {
        this.devCardSlot = devCardSlot;
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
        Platform.runLater(() -> SceneController.changeRootPane(assc, "/fxml/askSlot_scene"));
    }

    @Override
    public void askLeaderCardToPlay(ArrayList<LeaderCard> allLeaderCards) {
        ChooseLeaderToPlay cltp = new ChooseLeaderToPlay();
        cltp.addAllObservers(observers);
        cltp.setAllLeaderCards(allLeaderCards);
        Platform.runLater(() -> SceneController.changeRootPane(cltp, "/fxml/choose_leaderToPlay"));
    }

    @Override
    public void askLeaderCardToKeep(ArrayList<LeaderCard> leaderCards) {
        ChooseLeaderToKeep cltk = new ChooseLeaderToKeep();
        cltk.addAllObservers(observers);
        cltk.setAllLeaders(leaderCards);
        Platform.runLater(()->
                SceneController.changeRootPane(cltk,"/fxml/choose_leaderToKeep")
        );
        chosenLeader = cltk.getRestLeader();
    }

    @Override
    public void showWinMessage(HashMap<String, Integer> finalPoints) {
        finalVip.putAll(finalPoints);
        WinSceneController wsc = new WinSceneController();
        wsc.addAllObservers(observers);
        wsc.setFinalVp(finalPoints);
        Platform.runLater(() -> SceneController.changeRootPane(wsc, "/fxml/winner_scene"));
    }

    @Override
    public void showLoseMessage() {

    }

    private ResourceType askAnyResource(){
        AnySceneController asc = new AnySceneController();
        asc.addAllObservers(observers);
        Platform.runLater(() -> SceneController.changeRootPane(observers, "/fxml/any_scene"));
        return asc.getAny();
    }

    @Override
    public void showLeaderCards(HashMap<LeaderCard, Boolean> leaderCards) {
    }

    public static ResourceType[][] getMarket() {
        return market;
    }

    public static ResourceType getCornerMarble(){
        return cornerMarble;
    }
}
