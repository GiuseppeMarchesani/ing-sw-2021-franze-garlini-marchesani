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
import java.util.LinkedHashMap;

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
            GuiManager.changeRootPane(observers, "/fxml/init_scene");
        });
    }

    @Override
    public void askLobby() {
        LobbySceneController lsc = new LobbySceneController();
        lsc.addAllObservers(observers);
        Platform.runLater(() -> GuiManager.changeRootPane(observers, "/fxml/lobby_scene")
        );
    }

    @Override
    public void askPlayersNumber() {
        NumPlayerSceneController npsc = new NumPlayerSceneController();
        npsc.addAllObservers(observers);
        Platform.runLater(()->
                GuiManager.changeRootPane(observers,"/fxml/numPlayer_scene")
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
            Platform.runLater(()-> GuiManager.changeRootMainScene(observers));
            Platform.runLater(() -> GuiManager.getMainScene().update(market, cornerMarble, cardMarket, remainingCards, activeDepotQ, activeDepotT, chosenLeader, faithTrack, faithZone, strongbox, devCardSlot, extraDepotRes));
        }
        else{
            Platform.runLater(() -> GuiManager.getMainScene().update(market, cornerMarble, cardMarket, remainingCards,  activeDepotQ, activeDepotT, chosenLeader, faithTrack, faithZone, strongbox, devCardSlot, extraDepotRes));
            Platform.runLater(()-> GuiManager.changeRootMainScene(observers));
        }
    }

    @Override
    public void showLoginResult(String username, String gameId, boolean wasJoined) {
       Gui.playerList.add(username);
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
                GuiManager.changeRootPane(cdcsc,"/fxml/cardMarket_scene")
        );
    }

    @Override
    public void askMarketLineToGet(ArrayList<ResourceType> conversion) {
        if(conversion.size()>1){
            ChooseConversionSceneController ccsc = new ChooseConversionSceneController();
            ccsc.addAllObservers(observers);
            ccsc.setConversions(conversion);
            Platform.runLater(() -> GuiManager.changeRootPane(ccsc, "/fxml/choose_marbleConversion_scene"));
        }
        else {
            MarketSceneController msc = new MarketSceneController();
            msc.addAllObservers(observers);
            Platform.runLater(() ->
                    GuiManager.changeRootPane(msc, "/fxml/market_scene")
            );
        }
    }

    @Override
    public void askResourceToWarehouse(HashMap<ResourceType, Integer> resToPlace, int numAny, ArrayList<ResourceType> extraDepot) {
         if(numAny > 0) {
            for(int i=0; i<numAny; i++){
                AnySceneController asc = new AnySceneController();
                asc.addAllObservers(observers);
                Platform.runLater(() -> GuiManager.changeRootPane(observers, "/fxml/any_scene"));
            }
        }
        else if(extraDepot.size()>0){
            ExtraDepotSceneController edsc = new ExtraDepotSceneController();
            edsc.addAllObservers(observers);
            edsc.setExtraDepot(extraDepot);
            edsc.setResToPlace(resToPlace);
            Platform.runLater(() -> GuiManager.changeRootPane(edsc, "/fxml/extraDepot_scene"));
            extraDepotRes = edsc.getExtraDepotRes();
        }
        else {
            PlaceResourcesSceneController prsc = new PlaceResourcesSceneController();
            prsc.addAllObservers(observers);
            prsc.setResToPlace(resToPlace);
            Platform.runLater(() ->
                    GuiManager.changeRootPane(prsc, "/fxml/place_resources_scene")
            );
        }

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
            Platform.runLater(() -> GuiManager.changeRootPane(afp, "/fxml/anyForProduction_scene"));
        }
        else {
            AskProductionSceneController apsc = new AskProductionSceneController();
            apsc.addAllObservers(observers);
            apsc.setStrongbox(strongbox);
            apsc.setWarehouse(warehouse);
            apsc.setPrice(price);
            apsc.setAnyProduce(anyProduce);
            Platform.runLater(() -> GuiManager.changeRootPane(apsc, "/fxml/chooseCard_production_scene"));
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
            for(LeaderCard lead: chosenLeader.keySet()) {
                if(ld.getId()==lead.getId()) chosenLeader.replace(lead, true);
            }
        }
    }


    @Override
    public void showPlayer(String username, int faithSpace, HashMap<Integer, ResourceType> depotToResource, HashMap<Integer, Integer> depotToQuantity, HashMap<ResourceType, Integer> strongbox, DevCardSlot devCardSlot, ArrayList<LeaderCard> playedLeaderCards, int remainingLeaderCards, String user) {
        ShowPlayerSceneController spsc = new ShowPlayerSceneController();
        spsc.addAllObservers(observers);
        spsc.setChosenPlayer(username);
        spsc.setDepotToQuantity(depotToQuantity);
        spsc.setDepotToResource(depotToResource);
        spsc.setFaithSpace(faithSpace);
        spsc.setPlayedLeaderCards(playedLeaderCards);
        spsc.setDevCardSlot(devCardSlot);
        spsc.setStrongbox(strongbox);
        spsc.setSelf(user);
        Platform.runLater(() -> GuiManager.changeRootPane(spsc, "/fxml/showPlayer_scene"));

    }

    @Override
    public void showPlayerFaith(ArrayList<Integer> faith) {
        if(faith.size() ==1) faithTrack.put(playerList.get(0), faith.get(0));
        else {
            faithTrack.put(playerList.get(0), faith.get(0));
            faithTrack.put("Lorenzo il Magnifico", faith.get(1));
        }
    }

    @Override
    public void showPlayerList(ArrayList<String> playerOrder) {
        //playerList = new ArrayList<>(playerOrder);
        ChoosePlayerSceneController cpsc = new ChoosePlayerSceneController();
        cpsc.addAllObservers(observers);
        cpsc.setPlayersList(playerOrder);
        Platform.runLater(()-> GuiManager.changeRootPane(cpsc, "/fxml/choose_player"));

    }

    @Override
    public void askCardsToActivateProd(ArrayList<DevCard> devCardList) {
        AskCardsForProdSceneController acsc = new AskCardsForProdSceneController();
        acsc.addAllObservers(observers);
        acsc.setDevCardList(devCardList);
        Platform.runLater(() -> GuiManager.changeRootPane(acsc, "/fxml/chooseCard_production_scene"));
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
        Platform.runLater(() -> GuiManager.changeRootPane(assc, "/fxml/askSlot_scene"));
    }

    @Override
    public void askLeaderCardToPlay(ArrayList<LeaderCard> allLeaderCards) {
        ChooseLeaderToPlay cltp = new ChooseLeaderToPlay();
        cltp.addAllObservers(observers);
        cltp.setAllLeaderCards(allLeaderCards);
        Platform.runLater(() -> GuiManager.changeRootPane(cltp, "/fxml/choose_leaderCardToPlay"));
    }

    @Override
    public void askLeaderCardToKeep(ArrayList<LeaderCard> leaderCards) {
        ChooseLeaderToKeep cltk = new ChooseLeaderToKeep();
        cltk.addAllObservers(observers);
        cltk.setAllLeaders(leaderCards);
        Platform.runLater(()->
                GuiManager.changeRootPane(cltk,"/fxml/choose_leaderToKeep")
        );
        chosenLeader = cltk.getRestLeader();
    }

    @Override
    public void showWinMessage(LinkedHashMap<String, Integer> finalPoints) {
        finalVip.putAll(finalPoints);
        WinSceneController wsc = new WinSceneController();
        wsc.addAllObservers(observers);
        wsc.setFinalVp(finalPoints);
        Platform.runLater(() -> GuiManager.changeRootPane(wsc, "/fxml/winner_scene"));
    }

    @Override
    public void showLoseMessage() {
    }

    private ResourceType askAnyResource(){
        AnySceneController asc = new AnySceneController();
        asc.addAllObservers(observers);
        Platform.runLater(() -> GuiManager.changeRootPane(observers, "/fxml/any_scene"));
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
