package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Card.*;
import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.PhaseTurn;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.model.enumeration.TurnState;
import it.polimi.ingsw.network.ClientHandler;

import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static it.polimi.ingsw.messages.MessageType.*;
import static it.polimi.ingsw.messages.MessageType.PLACE_RES;

public class Turn {

    private HashMap<String, Boolean> activePlayer;
    private HashMap<String, VirtualView> allVirtualView;
    private String playingPlayer;
    private boolean endGame;
    private boolean mainAction;
    private boolean leaderAction;
    private Game gameSession;
    private TurnState turnState;
    private GameController gameController;
    private PhaseTurn phaseTurn;
    private ResourceType resTmp;
    private int resQuantityTmp;

    public Turn(GameController gameController){
        endGame=false;
        mainAction=false;
        leaderAction=false;
        this.gameController=gameController;
        playingPlayer =  gameController.getGameSession().getPlayersList().get(0).getUsername();
        phaseTurn = PhaseTurn.START_TURN;
        this.gameSession = gameController.getGameSession();
        for(int i=0; i<gameController.getPlayers().size(); i++){
            activePlayer.put(gameController.getPlayers().get(i), true);
            allVirtualView.put(gameController.getGameSession().getPlayersList().get(i).getUsername(),
                    gameController.getAllVirtualView().get(i));
        }

    }

    public void getMessage (GeneralMessage receivedMessage){
        switch (phaseTurn){
            case START_TURN:
                startTurn(receivedMessage);
            case ACTION:
                action(receivedMessage);
            case NEXT_TURN:
                endTurn(receivedMessage);
            default:
                allVirtualView.get(playingPlayer).showErrorMsg("Error!");
                break;

        }
    }

    private void startTurn(GeneralMessage msg){
        VirtualView vv = allVirtualView.get(playingPlayer);
        int indexPlayer = gameSession.getPlayerListByUsername().indexOf(msg.getUsername());
        if(msg.getMessageType()== SHOW_LEADER){
            vv.showLeaderCards(gameSession.getPlayersList().get(indexPlayer).getLeaderCardList());
        }
        else if(msg.getMessageType()==SHOW_MARKET){
            vv.showMarket(gameSession.getMarket());
        }
        else if(msg.getMessageType()== SHOW_DEV_MARKET){
            vv.showDevMarket(gameSession.getCardMarket());
        }
        else if(msg.getMessageType()== SHOW_SLOT){
            vv.showSlots(gameSession.getPlayersList().get(indexPlayer).getDevCardSlot());
        }
        else if(msg.getMessageType() == SHOW_ALL_SLOT){
            for(int i=0; i<gameSession.getPlayersList().size(); i++){
                vv.showSlots(gameSession.getPlayersList().get(i).getDevCardSlot());
            }
        }
        else if(msg.getMessageType()== SHOW_RES){
            vv.showResources(gameSession.getPlayersList().get(indexPlayer).getStrongbox(),
                    gameSession.getPlayersList().get(indexPlayer).getWarehouse());
        }
        else if(msg.getMessageType()== SHOW_ALL_RES){
            for(int i=0; i<gameSession.getPlayersList().size(); i++){
                vv.showResources(gameSession.getPlayersList().get(i).getStrongbox(),
                        gameSession.getPlayersList().get(i).getWarehouse());
            }
        }
        else if(msg.getMessageType()== SHOW_FAITH_TRACK){
            HashMap<String, Integer> tmp= new HashMap<>();
            for(int i=0; i<gameSession.getPlayersList().size(); i++){
                tmp.put(gameSession.getPlayersList().get(i).getUsername(),
                        gameSession.getPlayersList().get(i).getFaithSpace());
            }
            vv.showFaithTrack(tmp);
        }
        else if(msg.getMessageType()== SHOW_INFO){
            vv.showMatchInfo(gameController.getPlayers(), playingPlayer);
        }
        else{
            setPhaseTurn(PhaseTurn.ACTION);
        }
    }

    private void action(GeneralMessage msg){
        if(msg.getMessageType() == PLAYLEADER){
            showLeaderCards((PlayLeaderMsg) msg);
        }
        else if(msg.getMessageType() == LEADER_REPLY){
            playLeader((ChoseLeadersMsg) msg);
        }
        else if(msg.getMessageType() == PICK_DEVCARD){
            showDevCardMarket((DevCardMsg) msg);
        }
        else if(msg.getMessageType() == DEVCARD_REPLY){
            pickDevCard((DevCardReplyMessage) msg);
        }
        else if(msg.getMessageType()== PLACE_CARD){
            placeCard((PlaceCardMsg) msg);
        }
        else if(msg.getMessageType() == ACTIVATE_PRODUCTION){
            activateProduction((ActivateProductionMsg) msg);
        }
        else if(msg.getMessageType() == PRODUCTION_RES){
            productionRes((ProductionMsg) msg);
        }
        else if(msg.getMessageType()== PAY_RES){
            resToPay((ResToPayMsg) msg);
        }
        else if(msg.getMessageType() == PICK_MARKETRES){
            pickMarketRes((PickResMsg) msg);
        }
        else if(msg.getMessageType()== ROW_OR_COL){
            rowOrCol((RowOrColMsg) msg);
        }
        else if(msg.getMessageType()== WHITE_CONVERSION){
            chosenMarbleConversion((WhiteConversionMsg) msg);
        }
        else if(msg.getMessageType()==PLACE_RES) {
            placeRes((PlaceMsg) msg);
        }
        else{
            allVirtualView.get(playingPlayer).showErrorMsg("Invalid action. Try again!");
        }

    }

    public void showLeaderCards(PlayLeaderMsg msg){
        int indexPlayer = gameSession.getPlayerListByUsername().indexOf(msg.getUsername());

        if(gameSession.getPlayersList().get(indexPlayer).getLeaderCardList().size()>0){
            leaderAction=true;
            allVirtualView.get(playingPlayer).askChooseLeader(gameSession.getPlayersList().get(indexPlayer).getLeaderCardList());
        }
        else {
            allVirtualView.get(playingPlayer).showErrorMsg("You don't have available leader's card.");
        }

    }

    public void playLeader(ChoseLeadersMsg msg){
        if(msg.getDisOrPlay() == 'D' || msg.getDisOrPlay() == 'd'){
            int indexPlayer = gameController.getGameSession().getPlayerListByUsername().indexOf(msg.getUsername());
            if(gameController.getGameSession().getPlayersList().get(indexPlayer).getLeaderCardList().contains(msg.getLeaderCard())) {
                gameController.getGameSession().getPlayersList().get(indexPlayer).getLeaderCardList().remove(msg.getLeaderCard());
            }
        }
        else if(msg.getDisOrPlay() == 'P'|| msg.getDisOrPlay()=='p'){
            boolean activable = false;
            int indexPlayer = gameController.getGameSession().getPlayerListByUsername().indexOf(msg.getUsername());
            Player player = gameController.getGameSession().getPlayersList().get(indexPlayer);
            LeaderCard leaderCard = msg.getLeaderCard();
            if (player.getLeaderCardList().contains(leaderCard)) {
                int indexLeader = gameSession.getPlayersList().get(indexPlayer).getLeaderCardList().indexOf(msg.getLeaderCard());
                int idCard = leaderCard.getLeaderID();
                if ((idCard >= 48 && idCard <= 51)||(idCard>=56 && idCard<=59) || (idCard>=60 && idCard<=63)) {
                    ArrayList<DevCard> devCardsPlayer = player.getDevCardSlot().getAllDevCards();
                    HashMap<Color,Integer> leaderReq= new HashMap<>();
                    if(idCard <= 51) {
                        LeaderDiscount leaderDiscount = (LeaderDiscount) leaderCard;
                        leaderReq = leaderDiscount.getDevCardTypeReq();
                    } else if(idCard<=59) {
                        LeaderMarble leaderMarble = (LeaderMarble) leaderCard;
                        leaderReq = leaderMarble.getDevCardTypeReq();
                    } else {
                        LeaderProduction leaderProduction = (LeaderProduction) leaderCard;
                        leaderReq = leaderProduction.getDevCardTypeReq();
                    }
                    for (Color colorCard : leaderReq.keySet()) {
                        int quantity = leaderReq.get(colorCard);
                        for (DevCard devCard : devCardsPlayer) {
                            if (quantity > 0 && colorCard.equals(devCard.getCardType().getColor())) {
                                quantity--;
                            }
                        }
                        if (quantity == 0) {
                            activable = true;
                        } else {
                            activable = false;
                            break;
                        }
                    }
                } else if (idCard >= 52 && idCard <= 55) {
                    LeaderDepot leaderDepot = (LeaderDepot) leaderCard;
                    HashMap<ResourceType, Integer> leaderReq = leaderDepot.getResourceReq();
                    for (ResourceType res : leaderReq.keySet()) {
                        if (player.getAllResources().get(res) >= leaderReq.get(res)) {
                            activable = true;
                        } else {
                            activable = false;
                            break;
                        }
                    }
                }
                if(activable){
                    player.getLeaderCardList().get(indexLeader).activateAbility(player);
                }

            }
        }
    }

    public void showDevCardMarket(DevCardMsg msg){
        allVirtualView.get(playingPlayer).askBuyDevCard(gameSession.getCardMarket(),
                gameSession.getCardMarket().availableCards());
    }

    public void pickDevCard(DevCardReplyMessage msg){
        int indexPlayer = gameSession.getPlayerListByUsername().indexOf(msg.getUsername());
        Player player= gameSession.getPlayersList().get(indexPlayer);

        for(ResourceType res: msg.getDevCard().getCardCost().keySet()){
            for(int i=0; i<msg.getDevCard().getCardCost().get(res); i++){
                allVirtualView.get(playingPlayer).askResToPay(player.getStrongbox(), player.getWarehouse());
            }
        }
        gameSession.pickDevCard(msg.getDevCard().getCardType().getColor(), msg.getDevCard().getCardType().getLevel());
        allVirtualView.get(playingPlayer).askSlot(gameSession.getPlayersList().get(indexPlayer).getDevCardSlot(),
                gameSession.getPlayersList().get(indexPlayer).getDevCardSlot().getAvailableSlots(msg.getDevCard().getCardType().getLevel()));
    }

    public void resToPay(ResToPayMsg msg){
        int indexPlayer = gameSession.getPlayerListByUsername().indexOf(playingPlayer);
        if(msg.getWorS().equals("W") || msg.getWorS().equals("w")){
            int depot = gameSession.getPlayersList().get(indexPlayer).getWarehouse().hasResource(msg.getRes());
            if(depot >= 0){
                gameSession.getPlayersList().get(indexPlayer).removeRes(msg.getRes(), 1);
                allVirtualView.get(playingPlayer).showWarehouse(gameSession.getPlayersList().get(indexPlayer).getWarehouse());
            }
            else{
                allVirtualView.get(playingPlayer).showErrorMsg("Invalid depot. Try again!");
            }
        }
        else if(msg.getWorS().equals("S") || msg.getWorS().equals("s")){
            int quantity = gameSession.getPlayersList().get(indexPlayer).getStrongbox().get(msg.getRes());
            if(quantity > 1){
                gameSession.getPlayersList().get(indexPlayer).getStrongbox().remove(msg.getRes(), quantity);
                gameSession.getPlayersList().get(indexPlayer).getStrongbox().put(msg.getRes(), quantity-msg.getQuantity());
                allVirtualView.get(playingPlayer).showStrongbox(gameSession.getPlayersList().get(indexPlayer).getStrongbox());
            }
            else if(quantity == 1){
                gameSession.getPlayersList().get(indexPlayer).getStrongbox().remove(msg.getRes(), quantity);
                allVirtualView.get(playingPlayer).showStrongbox(gameSession.getPlayersList().get(indexPlayer).getStrongbox());

            }
            else {
                allVirtualView.get(playingPlayer).showErrorMsg("You don't have enough resources. Try again!");
            }
        }
        else {
            allVirtualView.get(playingPlayer).showErrorMsg("Invalid choice. Try again!");
        }
    }

    public void placeCard(PlaceCardMsg msg){
        int indexPlayer = gameSession.getPlayerListByUsername().indexOf(msg.getUsername());
        gameSession.getPlayersList().get(indexPlayer).placeDevCard(msg.getDevCard(), msg.getSlot());
        allVirtualView.get(playingPlayer).showSlots(gameSession.getPlayersList().get(indexPlayer).getDevCardSlot());
        mainAction=true;
    }



    public void activateProduction(ActivateProductionMsg msg){
        int indexPlayer = gameSession.getPlayerListByUsername().indexOf(msg.getUsername());
        allVirtualView.get(playingPlayer).askActivateProduction(gameSession.getPlayersList().get(indexPlayer).getDevCardSlot().getCardsAvailable());
        // controllo sulle risorse per scegliere ANY e togliere FAITH
        // aggiungere quelle rimanenti nella strongbox
        //TODO: notifare alla view le modifiche
    }

    public void productionRes(ProductionMsg msg){
        int indexPlayer = gameSession.getPlayerListByUsername().indexOf(msg.getUsername());
        Player player = gameSession.getPlayersList().get(indexPlayer);
        ArrayList<DevCard> devCards = msg.getDevCards();
        for(DevCard devCard : devCards){
            for(ResourceType res : devCard.getProductionCost().keySet()) {
                for(int i=0; i< devCard.getProductionCost().get(res); i++) {
                    allVirtualView.get(playingPlayer).chooseResToPay(res, devCard.getProductionCost().get(res));
                }
            }
        }
        HashMap<ResourceType, Integer> rest = player.storeResources(gameSession.pickProductionRes(devCards));
        for(ResourceType res: rest.keySet()){
            if(res==ResourceType.FAITH){
                gameSession.getPlayersList().get(indexPlayer).increaseFaith(rest.get(res));
            }
            else{
                for(int i=0; i<rest.get(res); i++){
                    allVirtualView.get(playingPlayer).askChooseRes();
                    if(gameSession.getPlayersList().get(indexPlayer).getStrongbox().containsKey(msg.getRes())){
                       int quantity= gameSession.getPlayersList().get(indexPlayer).getStrongbox().get(msg.getRes());
                       gameSession.getPlayersList().get(indexPlayer).getStrongbox().remove(msg.getRes(), quantity);
                       gameSession.getPlayersList().get(indexPlayer).getStrongbox().put(msg.getRes(), quantity+1);
                    }
                    else{
                        gameSession.getPlayersList().get(indexPlayer).getStrongbox().put(msg.getRes(), 1);
                    }
                }
            }
        }
        allVirtualView.get(playingPlayer).showStrongbox(player.getStrongbox());


    }

    /**
     * if the player wants to buy from the marbles market.
     * @param msg
     */
    public void pickMarketRes(PickResMsg msg){
        allVirtualView.get(playingPlayer).showMarket(gameSession.getMarket());
        allVirtualView.get(playingPlayer).askGetMarketRes();
    }

    /**
     * the choice of which row or column is passed; control of white and red marbles.
     * @param msg
     */
    public void rowOrCol(RowOrColMsg msg){
        HashMap<ResourceType, Integer> resources = gameSession.pickMarketRes(msg.getRowOrCol(), msg.getNum());
        int indexPlayer = gameSession.getPlayerListByUsername().indexOf(msg.getUsername());
        for(ResourceType res: resources.keySet()){
            if(res == ResourceType.EMPTY){

                if(gameSession.getPlayersList().get(indexPlayer).getMarbleConversion().size()==1){
                    ResourceType conversion = gameSession.getPlayersList().get(indexPlayer).getMarbleConversion().get(0);
                    int numWhiteMarble= resources.get(ResourceType.EMPTY);
                    resources.remove(ResourceType.EMPTY);
                    resources.put(conversion, numWhiteMarble);
                    for(int i=0; i<resources.get(res); i++){
                        allVirtualView.get(playingPlayer).askFloor(gameSession.getPlayersList().get(indexPlayer).getWarehouse(), res);

                    }

                }
                else if(gameSession.getPlayersList().get(indexPlayer).getMarbleConversion().size()>1){
                    for(int i=0; i<resources.get(res); i++){
                        allVirtualView.get(playingPlayer).askChooseMarbleConversion();
                    }

                }
                else{
                    resources.remove(ResourceType.EMPTY);
                }
            }
            else if(res == ResourceType.FAITH){
                gameSession.getPlayersList().get(indexPlayer).increaseFaith(resources.get(res));
            }
            else{
                resTmp=res;
                resQuantityTmp=resources.get(res);
                for (int i=0; i<resources.get(res); i++) {
                    allVirtualView.get(playingPlayer).askFloor(gameSession.getPlayersList().get(indexPlayer).getWarehouse(), res);
                }
            }
        }
    }

    /**
     * only if the player has more than one marble's ability.
     * @param msg
     */
    public void chosenMarbleConversion(WhiteConversionMsg msg){
        allVirtualView.get(playingPlayer).askFloor(gameSession.getPlayersList().get(indexPlayer).getWarehouse());
    }

    /**
     * place the resources in the chosen depot.
     * @param msg
     */
    public void placeRes(PlaceMsg msg){
        int indexPlayer = gameSession.getPlayerListByUsername().indexOf(msg.getUsername());
        if(msg.isRearrange()){
           gameSession.getPlayersList().get(indexPlayer).getWarehouse().rearrange(msg.getDepot1(), msg.getDepot2());
        }
        else{
            if(msg.getFloor()>0){
                int restRes = this.gameSession.getPlayersList().get(indexPlayer).placeResources(msg.getRes(), 1, msg.getFloor());
                if (restRes>0){
                    for(int i=0; i<gameSession.getPlayersList().size(); i++){
                        if(indexPlayer!=i){
                            gameSession.getPlayersList().get(i).increaseFaith(restRes);
                        }
                    }
                }
            }
            else{
                for(int i=0; i<gameSession.getPlayersList().size(); i++){
                    if(indexPlayer!=i){
                        gameSession.getPlayersList().get(i).increaseFaith(1);
                    }
                }
            }
        }
        mainAction=true;
    }

    private void endTurn(GeneralMessage msg){
        if(gameController.getGameSession().getPlayersList().size() == 1){
            drawToken();
        }
        else{
            proxPlayer();
            setPhaseTurn(PhaseTurn.START_TURN);
            leaderAction=false;
            mainAction=false;
        }
    }


    private void drawToken() {
        int endGameCode = ((SinglePlayerGame) gameController.getGameSession()).turnAction();
        if(endGameCode == 0) {
            //No more Development Card in a deck - You lose.
        }
        else if(endGameCode == 1) {
            //Lorenzo reached the last faith space - You lose.
        }
        else if(endGameCode == 2) {
            //You win - Total score:
        }
    }


    public void  proxPlayer(){
        int player = gameController.getPlayers().indexOf(playingPlayer);
        if(player +1 < gameController.getGameSession().getPlayersList().size()){
            player = player +1;
        }
        else {
            player = 0;
        }
        playingPlayer = gameController.getPlayers().get(player);
    }



    public GameController getGameController() {
        return gameController;
    }

    public void addPlayer(String username){
        gameController.getPlayers().add(username);
    }

    public String getActivePlayer() {
        return playingPlayer;
    }

    public void setActivePlayer(String activePlayer) {
        this.playingPlayer = activePlayer;
    }

    public HashMap<String, Boolean> getPlayers() {
        return activePlayer;
    }

    public void setPhaseTurn(PhaseTurn phaseTurn) {
        this.phaseTurn = phaseTurn;
    }
    public void disconnect(String username){
        activePlayer.put(username,false);
    }
    //TODO: Testing if it works
    public boolean hasInactivePlayers(){
        return(getInactivePlayers()!=null);
    }
    public List<String> getInactivePlayers(){
        return activePlayer.entrySet().stream().filter(entry -> (!entry.getValue()))
                .map(entry-> entry.getKey()).collect(Collectors.toList());
    }
    public void reconnect(String username){
        activePlayer.put(username, true);
    }
}

