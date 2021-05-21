package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Card.*;
import it.polimi.ingsw.model.enumeration.*;
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

public class TurnController {

    private HashMap<String, Boolean> activePlayer;
    private HashMap<String, VirtualView> allVirtualView;
    private String playingPlayer;
    private boolean endGame;
    private boolean mainAction;
    private int leaderAction=0;
    private Game gameSession;
    private TurnState turnState;
    private final GameController gameController;
    private PhaseTurn phaseTurn;

    public TurnController(GameController gameController){
        endGame=false;
        mainAction=false;
        leaderAction=0;
        this.gameController=gameController;
        playingPlayer =  gameController.getGameSession().getPlayersList().get(0).getUsername();
        phaseTurn = PhaseTurn.START_TURN;
        this.gameSession = gameController.getGameSession();
        for(int i=0; i<gameController.getPlayers().size(); i++){
            activePlayer.put(gameController.getPlayers().get(i), true);
            allVirtualView = gameController.getAllVirtualView();
        }

    }

    public void getMessage (GeneralMessage receivedMessage){
        switch (phaseTurn){
            case START_TURN:
                startTurn(receivedMessage);
                break;
            case ACTION:
                action(receivedMessage);
                break;
            case NEXT_TURN:
                endTurn((EndTurnMsg) receivedMessage);
                break;
            default:
                allVirtualView.get(playingPlayer).showErrorMsg("Error!");
                break;

        }
    }

    private void startTurn(GeneralMessage msg){
        VirtualView vv = allVirtualView.get(playingPlayer);
        int indexPlayer = gameSession.getPlayerListByUsername().indexOf(msg.getUsername());
        if(msg.getMessageType() == SHOW_LEADER){
            ArrayList<LeaderCard> leaderCards= new ArrayList<>();
            leaderCards.addAll(gameSession.getPlayersList().get(indexPlayer).getLeaderCardList().keySet());
            vv.showLeaderCards(leaderCards, msg.getUsername());
        }
        else if(msg.getMessageType()==SHOW_MARKET){
            vv.showMarket(gameSession.getMarket());
        }
        else if(msg.getMessageType()== SHOW_DEV_MARKET){
            vv.showDevMarket(gameSession.getCardMarket().availableCards());
        }
        else if(msg.getMessageType()== SHOW_SLOT){
            vv.showSlots(gameSession.getPlayersList().get(indexPlayer).getDevCardSlot(), msg.getUsername());
        }
        else if(msg.getMessageType() == SHOW_ALL_SLOT){
            for(int i=0; i<gameSession.getPlayersList().size(); i++){
                vv.showSlots(gameSession.getPlayersList().get(i).getDevCardSlot(),
                        gameSession.getPlayersList().get(i).getUsername());
            }
        }
        else if(msg.getMessageType() == SHOW_RES){
            vv.showResources(gameSession.getPlayersList().get(indexPlayer).getStrongbox(),
                    gameSession.getPlayersList().get(indexPlayer).getWarehouse(), msg.getUsername());
        }
        else if(msg.getMessageType()== SHOW_ALL_RES){
            for(int i=0; i<gameSession.getPlayersList().size(); i++){
                vv.showResources(gameSession.getPlayersList().get(i).getStrongbox(),
                        gameSession.getPlayersList().get(i).getWarehouse(),
                        gameSession.getPlayersList().get(i).getUsername());
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
        int indexPlayer = gameSession.getPlayerListByUsername().indexOf(msg.getUsername());
        Player player = gameSession.getPlayersList().get(indexPlayer);
        if(msg.getMessageType() == PLAYLEADER){
            showLeaderCards((PlayLeaderMsg) msg, player);
        }
        else if(msg.getMessageType() == LEADER_REPLY){
            playLeader((ChoseLeadersMsg) msg, player);
        }
        else if(msg.getMessageType() == PICK_DEVCARD){
            showDevCardMarket((DevCardMsg) msg);
        }
        else if(msg.getMessageType() == DEVCARD_REPLY){
            pickDevCard((DevCardReplyMessage) msg, player);
        }
        else if(msg.getMessageType()== PLACE_CARD){
            placeCard((PlaceCardMsg) msg, player);
        }
        else if(msg.getMessageType() == ACTIVATE_PRODUCTION){
            activateProduction((ActivateProductionMsg) msg, player);
        }
        else if(msg.getMessageType() == PRODUCTION_RES){
            productionRes((ProductionMsg) msg, player);
        }
        else if(msg.getMessageType()== PAY_RES){
            resToPay((ResToPayMsg) msg, player);
        }
        else if(msg.getMessageType() == PICK_MARKETRES){
            pickMarketRes((PickResMsg) msg, player);
        }
        else if(msg.getMessageType()== ROW_OR_COL){
            rowOrCol((GetMarketLineReply) msg, player);
        }
        else if(msg.getMessageType()== WHITE_CONVERSION){
            chosenMarbleConversion((WhiteConversionMsg) msg, player);
        }
        else if(msg.getMessageType()== REARRANGE_REPLY){
            askRearrange((RearrangeMsg) msg, player);
        }
        else if(msg.getMessageType()==PLACE_RES) {
            placeRes((PlaceMsg) msg, player);
        }
        else{
            allVirtualView.get(playingPlayer).showErrorMsg("Invalid action. Try again!");
        }

    }

    private void showLeaderCards(PlayLeaderMsg msg, Player player){
        if(leaderAction!=2 && !mainAction) {
            ArrayList<LeaderCard> availableCards = new ArrayList<>();
            for (LeaderCard leaderCard : player.getLeaderCardList().keySet()) {
                if (player.getLeaderCardList().get(leaderCard)) {
                    availableCards.add(leaderCard);
                }
            }
            if (!availableCards.isEmpty()) {
                leaderAction++;
                allVirtualView.get(playingPlayer).askLeaderCardToPlay(availableCards);
            } else {
                allVirtualView.get(playingPlayer).showErrorMsg("You don't have available leader's card.");
            }
        }
        else {
            allVirtualView.get(playingPlayer).showErrorMsg("You can't do other action in this turn.");
        }
    }

    private void playLeader(ChoseLeadersMsg msg, Player player){
        if(msg.getDisOrPlay() == 'D' || msg.getDisOrPlay() == 'd'){
            if(player.getLeaderCardList().containsKey(msg.getLeaderCard())) {
                player.getLeaderCardList().remove(msg.getLeaderCard());
            }
        }
        else if(msg.getDisOrPlay() == 'P'|| msg.getDisOrPlay()=='p'){
            boolean activate = false;
            LeaderCard leaderCard = msg.getLeaderCard();
            if (player.getLeaderCardList().containsKey(leaderCard)) {
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
                            activate = true;
                        } else {
                            activate = false;
                            break;
                        }
                    }
                } else if (idCard >= 52 && idCard <= 55) {
                    LeaderDepot leaderDepot = (LeaderDepot) leaderCard;
                    HashMap<ResourceType, Integer> leaderReq = leaderDepot.getResourceReq();
                    for (ResourceType res : leaderReq.keySet()) {
                        if (player.getAllResources().get(res) >= leaderReq.get(res)) {
                            activate = true;
                        } else {
                            activate = false;
                            break;
                        }
                    }
                }
                if(activate){
                   msg.getLeaderCard().activateAbility(player);
                }

            }
        }
        ArrayList<LeaderCard> leaderCard= new ArrayList<>();
        leaderCard.add(msg.getLeaderCard());
        for(VirtualView vv: allVirtualView.values()){
            vv.showLeaderCards(leaderCard, msg.getUsername());
        }
    }

    private void showDevCardMarket(DevCardMsg msg){
        if (!mainAction) {
            mainAction=true;
            allVirtualView.get(playingPlayer).askDevCardToBuy(gameSession.getCardMarket().availableCards());
        }
        else {
            allVirtualView.get(playingPlayer).showErrorMsg("You can't do main action in this turn.");
        }
    }

    private void pickDevCard(DevCardReplyMessage msg, Player player){
        for(ResourceType res: msg.getDevCard().getCardCost().keySet()){
            for(int i=0; i<msg.getDevCard().getCardCost().get(res); i++){
                allVirtualView.get(playingPlayer).askChooseResToPay(player.getStrongbox(), player.getWarehouse(), res);
            }
        }
        gameSession.pickDevCard(msg.getDevCard().getCardType().getColor(), msg.getDevCard().getCardType().getLevel());
        for(VirtualView vv: allVirtualView.values()){
            vv.showDevMarket(gameSession.getCardMarket().availableCards());
        }
        allVirtualView.get(playingPlayer).askSlot(player.getDevCardSlot().getAvailableSlots(msg.getDevCard().getCardType().getLevel()));

    }

    public void resToPay(ResToPayMsg msg, Player player){
        if(msg.getWorS().equals("W") || msg.getWorS().equals("w")){
            int depot = player.getWarehouse().hasResource(msg.getRes());
            if(depot >= 0){
                player.removeRes(msg.getRes(), 1);
                allVirtualView.get(playingPlayer).showResources(player.getStrongbox(), player.getWarehouse(), player.getUsername());
            }
            else{
                allVirtualView.get(playingPlayer).showErrorMsg("Invalid depot. Try again!");
                allVirtualView.get(playingPlayer).askChooseResToPay(player.getStrongbox(), player.getWarehouse(), msg.getRes());
            }
        }
        else if(msg.getWorS().equals("S") || msg.getWorS().equals("s")){
            int quantity = player.getStrongbox().get(msg.getRes());
            if(quantity > 1){
                player.getStrongbox().remove(msg.getRes(), quantity);
                player.getStrongbox().put(msg.getRes(), quantity-1);
                allVirtualView.get(playingPlayer).showResources(player.getStrongbox(), player.getWarehouse(), player.getUsername());
            }
            else if(quantity == 1){
                player.getStrongbox().remove(msg.getRes(), quantity);
                allVirtualView.get(playingPlayer).showResources(player.getStrongbox(), player.getWarehouse(), player.getUsername());

            }
            else {

                allVirtualView.get(playingPlayer).showErrorMsg("You don't have enough resources. Try again!");
                allVirtualView.get(playingPlayer).askChooseResToPay(player.getStrongbox(), player.getWarehouse(), msg.getRes());
            }
        }
        else {
            allVirtualView.get(playingPlayer).showErrorMsg("Invalid choice. Try again!");
        }
    }

    public void placeCard(PlaceCardMsg msg, Player player){
        player.placeDevCard(msg.getDevCard(), msg.getSlot());
        for(VirtualView vv: allVirtualView.values()){
            vv.showSlots(player.getDevCardSlot(), msg.getUsername());
        }
        mainAction=true;
    }

    public void activateProduction(ActivateProductionMsg msg, Player player){
        if(!mainAction) {
            mainAction=true;
            allVirtualView.get(playingPlayer).askCardsToActivateProd(player.getDevCardSlot().getCardsAvailable());
        }
        else {
            allVirtualView.get(playingPlayer).showErrorMsg("You can't do main action in this turn.");
        }
    }

    public void productionRes(ProductionMsg msg, Player player){
        for(DevCard devCard : msg.getDevCards()){
            for(ResourceType res : devCard.getProductionCost().keySet()) {
                for(int i=0; i< devCard.getProductionCost().get(res); i++) {
                    allVirtualView.get(playingPlayer).askChooseResToPay(player.getStrongbox(), player.getWarehouse(), res);
                }
            }
        }
        HashMap<ResourceType, Integer> rest = player.storeResources(gameSession.pickProductionRes(msg.getDevCards()));
        for(ResourceType res: rest.keySet()){
            if(res == ResourceType.FAITH){
                player.increaseFaith(rest.get(res));
            }
            else{
                for(int i=0; i<rest.get(res); i++){
                    ArrayList<String> resourceTypes= gameController.availableRes();
                    allVirtualView.get(playingPlayer).askChooseOneRes(resourceTypes, "Choose resource by typing COIN, SHIELD, SERVANT or STONE");
                    if(player.getStrongbox().containsKey(msg.getRes())){
                       int quantity= player.getStrongbox().get(msg.getRes());
                       player.getStrongbox().replace(msg.getRes(), quantity+1);
                    }
                    else{
                        player.getStrongbox().put(msg.getRes(), 1);
                    }
                }
            }
        }
        for(VirtualView vv: allVirtualView.values()) {
            vv.showResources(player.getStrongbox(), player.getWarehouse(), msg.getUsername());
        }


    }

    private void placeChooseRes(ResourceReply msg, Player player){
        if(player.getStrongbox().containsKey(msg.getRes())){
            player.getStrongbox().replace(msg.getRes(), player.getStrongbox().get(msg.getRes())+1);
        }
        else{
            player.getStrongbox().put(msg.getRes(), 1);
        }
    }

    /**
     * if the player wants to buy from the marbles market.
     * @param msg
     * @param player
     */
    public void pickMarketRes(PickResMsg msg, Player player){
        if(!mainAction){
            mainAction=true;
            allVirtualView.get(playingPlayer).askMarketLineToGet(gameSession.getMarket());
        }
        else {
            allVirtualView.get(playingPlayer).showErrorMsg("You can't do main action in this turn.");
        }
    }

    /**
     * the choice of which row or column is passed; control of white and red marbles.
     * @param msg
     */
    public void rowOrCol(GetMarketLineReply msg, Player player){
        HashMap<ResourceType, Integer> resources = gameSession.pickMarketRes(msg.getRowOrCol(), msg.getNum());
        for(VirtualView vv: allVirtualView.values()){
            vv.showMarket(gameSession.getMarket());
        }
        for(ResourceType res: resources.keySet()){
            if(res == ResourceType.EMPTY){

                if(player.getMarbleConversion().size()==1){
                    ResourceType conversion = player.getMarbleConversion().get(0);
                    int numWhiteMarble= resources.get(ResourceType.EMPTY);
                    resources.remove(ResourceType.EMPTY);
                    resources.put(conversion, numWhiteMarble);
                    for(int i=0; i<resources.get(res); i++){
                        allVirtualView.get(playingPlayer).askRearrange(player.getWarehouse());
                        allVirtualView.get(playingPlayer).askFloor(player.getWarehouse(), res);

                    }

                }
                else if(player.getMarbleConversion().size()>1){
                    for(int i=0; i<resources.get(res); i++){
                        allVirtualView.get(playingPlayer).askChooseMarbleConversion();
                    }
                }
                else{
                    resources.remove(ResourceType.EMPTY);
                }
            }
            else if(res == ResourceType.FAITH){
                player.increaseFaith(resources.get(res));
            }
            else{
                for (int i=0; i<resources.get(res); i++) {
                    allVirtualView.get(playingPlayer).askFloor(player.getWarehouse(), res);
                }
            }
        }
    }

    /**
     * only if the player has more than one marble's ability.
     * @param msg
     * @param player
     */
    private void chosenMarbleConversion(WhiteConversionMsg msg, Player player){
        allVirtualView.get(playingPlayer).askFloor(player.getWarehouse());
    }

    private void askRearrange(RearrangeMsg msg, Player player){
        if(msg.isRearrange()){
            allVirtualView.get(playingPlayer).askDepotToRearrange();
            player.getWarehouse().rearrange(msg.getDepot1(), msg.getDepot2());
        }
    }
    /**
     * place the resources in the chosen depot.
     * @param msg
     * @param player
     */
    public void placeRes(PlaceMsg msg, Player player){
        if(msg.getFloor()>0){
            int restRes = player.placeResources(msg.getRes(), 1, msg.getFloor());
            if (restRes>0){
                for(int i=0; i<gameSession.getPlayersList().size(); i++){
                    if(gameSession.getPlayersList().indexOf(player)!=i){
                        gameSession.getPlayersList().get(i).increaseFaith(restRes);
                    }
                }
            }
        }
        else{
            for(int i=0; i<gameSession.getPlayersList().size(); i++){
                if(gameSession.getPlayersList().indexOf(player)!=i){
                    gameSession.getPlayersList().get(i).increaseFaith(1);
                }
            }
        }

        for(VirtualView vv: allVirtualView.values()){
            vv.showResources(player.getStrongbox(), player.getWarehouse(), player.getUsername());
        }
        mainAction=true;
}

    private void endTurn(EndTurnMsg msg){
        if(gameController.getGameSession().getPlayersList().size() == 1){
            drawToken();
            proxPlayer();
        }
        else{
            proxPlayer();
            setPhaseTurn(PhaseTurn.START_TURN);
            allVirtualView.get(playingPlayer).showMessage("It's your turn!");
            allVirtualView.get(playingPlayer).askAction();
            leaderAction=0;
            mainAction=false;
        }
    }

    private void drawToken() {
        int endGameCode = ((SinglePlayerGame) gameController.getGameSession()).turnAction();
        if(endGameCode == 1) {
            HashMap<String, Integer> faithTrack= new HashMap<>();
            faithTrack.put(playingPlayer, gameSession.getPlayersList().get(0).getFaithSpace());
            faithTrack.put("Lorenzo il Magnifico", ((SinglePlayerGame) gameSession).getBlackCross().getFaithSpace());
            allVirtualView.get(playingPlayer).showFaithTrack(faithTrack);
            allVirtualView.get(playingPlayer).showWinMessage("Lorenzo il Magnifico");
            gameController.setGameState(GameState.END_GAME);
        }
        else if(endGameCode == -1) {
            int finalVP= gameSession.getPlayersList().get(0).getFinalVP();
            allVirtualView.get(playingPlayer).showCurrentVP(finalVP);
            allVirtualView.get(playingPlayer).showWinMessage(playingPlayer);
            gameController.setGameState(GameState.END_GAME);
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

    public void newTurn(){
        VirtualView vv = allVirtualView.get(playingPlayer);
        vv.showMessage("It's your turn");
        vv.askAction();
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

