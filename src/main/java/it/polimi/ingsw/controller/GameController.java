package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Board.FaithTrack;
import it.polimi.ingsw.model.Card.*;
import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.view.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.messages.MessageType.*;

public class GameController implements VirtualController {
    private Game gameSession;
    private int gameID;
    private ArrayList<ClientHandler> players;
    private VirtualView virtualView;
    private Turn turnController;
    private GameState gameState;
    private int maxPlayers;

    public GameController(int gameID, int maxPlayers){
        this.players = new ArrayList<>();
        this.gameID = gameID;
        this.maxPlayers = maxPlayers;
        this.turnController= new Turn(this);
        if(maxPlayers == 1){
            this.gameSession= new SinglePlayerGame();
        }
        else this.gameSession= new Game();
    }



    public void newPlayer(String username) throws Exception {
        players.add(new ClientHandler(new Socket()));
        this.gameSession.addPlayer(new Player(username));
    }

    /** Turn state.
     *
     */

    public void getMessage (GeneralMessage receivedMessage){
        switch (gameState){
            case MAIN_ACTION:
                mainAction(receivedMessage);
            case LEADER_ACTION:
                leaderAction(receivedMessage);
            case CHECK:
                check(receivedMessage);
            default:
                //non deve mai succedere, da scrivere
                break;

        }
    }
    //non so se davvero utile dato che ci penserà la gui
    private void check(GeneralMessage msg){
        int indexPlayer = gameSession.getPlayerListByUsername().indexOf(msg.getUsername());
        virtualView.showFaithTrack(gameSession.getFaithTrack());
        virtualView.showMarket(gameSession.getMarket());
        virtualView.showLeaderCards(gameSession.getPlayersList().get(indexPlayer).getLeaderCardList());

    }

    private void mainAction(GeneralMessage msg){

        if(msg.getMessageType() == PICK_DEVCARD){
            showDevCard((DevCardMsg) msg);
        }
        else if(msg.getMessageType() == DEVCARD_REPLY){
            pickDevCard((DevCardReplyMessage) msg);
        }
        else if(msg.getMessageType() == ACTIVATE_PRODUCTION){
            activateProduction((ProductionMsg) msg);
        }
        else if(msg.getMessageType() == PICK_MARKETRES){
            pickMarketRes((PickResMsg) msg);
        }
        else if(msg.getMessageType()== ROW_OR_COL){
        rowOrCol((RowOrColMsg) msg);
        }
        else if(msg.getMessageType()==PLACE_RES) {
            placeRes((PlaceMsg) msg);
        }
        else{
            //error's message
        }

    }


    private void leaderAction(GeneralMessage msg){
        if(msg.getMessageType() == PLAYLEADER){
            playLeader((PlayLeaderMsg) msg);
        }
        else if(msg.getMessageType() == DISCARDLEADER){
            discardLeader((DiscardLeaderMsg) msg);
        }
        else{
            //error's message
        }
    }

    public void discardLeader(DiscardLeaderMsg msg){
        int indexPlayer = gameSession.getPlayerListByUsername().indexOf(msg.getUsername());
        if(gameSession.getPlayersList().get(indexPlayer).getLeaderCardList().contains(msg.getLeaderCard())){
            gameSession.getPlayersList().get(indexPlayer).getLeaderCardList().remove(msg.getLeaderCard());
        }
        //altrimenti ritorno un'eccezione o errore?
    }

    public void playLeader(PlayLeaderMsg msg) {
        boolean activable = false;
        int indexPlayer = gameSession.getPlayerListByUsername().indexOf(msg.getUsername());
        Player player = gameSession.getPlayersList().get(indexPlayer);
        LeaderCard leaderCard = msg.getLeaderCard();
        if (player.getLeaderCardList().contains(leaderCard)) {
            int indexLeader = gameSession.getPlayersList().get(indexPlayer).getLeaderCardList().indexOf(msg.getLeaderCard());
            int idCard = leaderCard.getLeaderID();
            if ((idCard >= 48 && idCard <= 51)||(idCard>=56 && idCard<=59) || (idCard>=60 && idCard<=63)) {
                ArrayList<DevCard> devCardsPlayer = player.getDevCardSlot().getAllDevCards();
                HashMap<Color,Integer> leaderReq= new HashMap<>();
                if(idCard >= 48 && idCard <= 51) {
                    LeaderDiscount leaderDiscount = (LeaderDiscount) leaderCard;
                    leaderReq = leaderDiscount.getDevCardTypeReq();
                } else if(idCard>=56 && idCard<=59) {
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


    private void showDevCard(DevCardMsg msg){
        virtualView.showCardMarket(gameSession.getCardMarket());
        virtualView.askCooseDevCard();
    }

    public void pickDevCard(DevCardReplyMessage msg){
        int indexPlayer = gameSession.getPlayerListByUsername().indexOf(msg.getUsername());
        Player player= gameSession.getPlayersList().get(indexPlayer);

        virtualView.showResources(player.getStrongbox(), player.getWarehouse());
        virtualView.askResToPay(msg.getDevCard().getCardCost().keySet());
        gameSession.pickDevCard(msg.getDevCard().getCardType().getColor(), msg.getDevCard().getCardType().getLevel());
        // TODO: view.showDevCardMarket(gameSession.getCardMarket());

    }

    public void activateProduction(ProductionMsg msg){

        int indexPlayer = gameSession.getPlayerListByUsername().indexOf(msg.getUsername());
        Player player = gameSession.getPlayersList().get(indexPlayer);

        virtualView.chooseResToPay(player.getStrongbox(), player.getWarehouse());
        for (ResourceType res: msg.getDevCard().getProductionCost().keySet()){

        }

        ArrayList<DevCard> devCardsProduction= new ArrayList<>();
        player.storeResources(gameSession.pickProductionRes(devCardsProduction));

        // controllo sulle risorse per scegliere ANY e togliere FAITH
        // aggiungere quelle rimanenti nella strongbox
        //TODO: notifare alla view le modifiche
    }

    //chiede alla view di fargli vedere la market per scegliere e la view gli chiede di comunicare la scelta
    public void pickMarketRes(PickResMsg msg){
        virtualView.showMarket(gameSession.getMarket());
        virtualView.askRowOrCol();
    }

    //viene passata la scelta con row or col e numero; si controlla la biglia bianca
    //le biglie rosse sono già state tolte?
    public void rowOrCol(RowOrColMsg msg){
        HashMap<ResourceType, Integer> resources = gameSession.pickMarketRes(msg.getRowOrCol(), msg.getNum());
        int indexPlayer = this.getPlayers().indexOf(turnController.getActivePlayer());
        for(ResourceType res: resources.keySet()){
            if(res == ResourceType.EMPTY){
                if(gameSession.getPlayersList().get(indexPlayer).getMarbleConversion().size()==1){
                    ResourceType conversion = gameSession.getPlayersList().get(indexPlayer).getMarbleConversion().get(0);
                    int numWhiteMarble= resources.get(ResourceType.EMPTY);
                    resources.remove(ResourceType.EMPTY);
                    resources.put(conversion, numWhiteMarble);
                }
                else if(gameSession.getPlayersList().get(indexPlayer).getMarbleConversion().size()>1){
                    virtualView.askChooseMarbleConversion(resources.get(res));
                }
                else{
                    resources.remove(ResourceType.EMPTY);
                }
            }
            else{
                virtualView.askFloor(res, resources.get(res));
            }
        }


    }

    //nel caso di più abilità con biglia bianca si fa scegliere la risorsa e si chiede dove si vuole posizionare
    public void chosenMarbleConversion(WhiteConversionMsg msg){
        virtualView.askFloor(msg.getRes(), msg.getQuantity());
    }

    //si posizionano le risorse nei piani scelti della warehouse
    public void placeRes(PlaceMsg msg){
        int indexPlayer = gameSession.getPlayerListByUsername().indexOf(msg.getUsername());
        this.gameSession.getPlayersList().get(indexPlayer).placeResources(msg.getRes(), msg.getQuantity(), msg.getFloor());

    }

    public Game getGameSession(){
        return gameSession;
    }
    public ArrayList<ClientHandler> getPlayers() {
        return players;
    }

    public int getGameID() {
        return gameID;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public Turn getTurnController() {
        return turnController;
    }

    public void disconnect(String username){
        turnController.disconnect(username);
    }
    public boolean status(){
        return turnController.status();
    }
    public Map<String, Boolean> inactivePlayers(){
       return turnController.inactivePlayers();
    }
    public boolean hasInactivePlayers(){
        return turnController.hasInactivePlayers();
    }

}
