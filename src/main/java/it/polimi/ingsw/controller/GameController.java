package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.view.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static it.polimi.ingsw.messages.MessageType.*;

public class GameController {
    private Game gameSession;
    private View view;
    private Turn turnController;
    private GameState gameState;

    public GameController(char gameType) throws IllegalArgumentException {

        if(gameType == 's' || gameType=='S'){
            gameSession = new SinglePlayerGame();
        }
        else if(gameType=='m' || gameType=='M'){
            gameSession = new Game();
        }
        else throw new IllegalArgumentException();
    }


    /** Turn state.
     *
     */

    public void getMessage (CommandMsg receivedMessage){
        switch (gameState){
            case MAIN_ACTION:
                mainAction(receivedMessage);
                break;
            case LEADER_ACTION:
                leaderAction(receivedMessage);
                break;
            default:
                //non deve mai succedere, da scrivere
                break;

        }
    }

    private void mainAction(CommandMsg msg){
        if(msg.getMessageType() == PICK_DEVCARD){
            pickDevCard((DevCardMsg) msg);
        }
        else if(msg.getMessageType() == ACTIVATE_PRODUCTION){
            activateProduction((ProductionMsg) msg);
        }
        else if(msg.getMessageType() == PICK_MARKETRES){
            pickMarketRes((PickResMsg) msg);
        }
        else{
            //error's message
        }

    }

    private void leaderAction(CommandMsg msg){
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



    private void discardLeader(DiscardLeaderMsg msg){
        int indexPlayer = gameSession.getPlayerListByID().indexOf(msg.getPlayerID());
        if(gameSession.getPlayersList().get(indexPlayer).getLeaderCardList().contains(msg.getLeaderCard())){
            gameSession.getPlayersList().get(indexPlayer).getLeaderCardList().remove(msg.getLeaderCard());
        }
        //altrimenti ritorno un'eccezione o errore?
    }

    private void playLeader(PlayLeaderMsg msg) {
        boolean activable = false;
        int indexPlayer = gameSession.getPlayerListByID().indexOf(msg.getPlayerID());
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

    private void pickDevCard(DevCardMsg msg){
        int indexPlayer = gameSession.getPlayerListByID().indexOf(msg.getPlayerID());
        Player player= gameSession.getPlayersList().get(indexPlayer);

        view.showResources(player.getStrongbox(), player.getWarehouse());
        for (ResourceType res: msg.getDevCard().getCardCost().keySet()){
            //metodo della view che passa quali risorse ha scelto e dove

        }

        gameSession.pickDevCard(msg.getDevCard().getCardType().getColor(), msg.getDevCard().getCardType().getLevel());
        view.showDevCardMarket(gameSession.getCardMarket());

    }

    private void activateProduction(ProductionMsg msg){
        int indexPlayer = gameSession.getPlayerListByID().indexOf(msg.getPlayerID());
        Player player = gameSession.getPlayersList().get(indexPlayer);

        view.chooseResToPay(player.getStrongbox(), player.getWarehouse());
        //turnController.getActivePlayer().sendAnswerMessage();
        for (ResourceType res: msg.getDevCard().getProductionCost().keySet()){


        }

        ArrayList<DevCard> devCardsProduction= new ArrayList<>();
        player.storeResources(gameSession.pickProductionRes(devCardsProduction));

        // controllo sulle risorse per scegliere ANY e togliere FAITH
        // aggiungere quelle rimanenti nella strongbox
        //TODO: notifare alla view le modifiche
    }

    public void pickMarketRes(PickResMsg msg){
        ArrayList<ResourceType> resources = view.showMarket(gameSession.getMarket());
        //il player deve decidere dove metterle


    }

    public Game getGameSession(){
        return gameSession;
    }

}
