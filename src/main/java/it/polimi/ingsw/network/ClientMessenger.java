package it.polimi.ingsw.network;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ObserverView;
import it.polimi.ingsw.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientMessenger implements Observer, ObserverView {

    private String username;
    private View view;
    private ExecutorService queue;
    private ClientSocket client;
    private String lobby;

    public ClientMessenger(View view) {
        this.view = view;
        queue= Executors.newSingleThreadExecutor();
    }

    public void updateConnect(String address, int port){
        try {
            client = new ClientSocket(address, port);
            client.listen();
        } catch (Exception e) {
            queue.execute(view::askConnect);
        }

    }

    public void updateLobby(String username, String lobby){
        this.username= username;
        this.lobby=lobby;
        client.sendMessage(new LoginRequestMsg(username, lobby));
    }
    /**
     * Updates new username, if a player with the same username was already in the game.
     */
    public void updateNewUsername(String username){
        this.username= username;
    }

    public void updatePlayersNumber(int numPlayers){
        client.sendMessage(new PlayersNumberRequest(username, numPlayers));
    }

    public void updateDiscardLeader(ArrayList<LeaderCard> leaders){
        client.sendMessage(new StartingLeadersRequestMsg(username, leaders));
    }
   public void updateWarehouse(HashMap<Integer,ResourceType> depotToResource, HashMap<Integer,Integer> depotToQuantity, ArrayList<Integer> leaderToDepot,int discard){
        client.sendMessage(new ResourceToWarehouseRequestMsg(username, depotToResource, depotToQuantity, leaderToDepot ,discard));
    }

    public void updateAction(int actionCode){
        ClientMessage msg;
        switch(actionCode){
            case 0:
                msg= new ActionRequest(username, MessageType.MAIN_MARBLE);
                break;
            case 1:
                msg= new ActionRequest(username, MessageType.MAIN_CARD);
                break;
            case 2:
                msg= new ActionRequest(username, MessageType.MAIN_PRODUCTION);
                break;
            case 3:
                msg= new ActionRequest(username, MessageType.SIDE_LEADER);
                break;
            case 4:
                msg= new ActionRequest(username, MessageType.SHOW_LEADER);
                break;
            case 5:
                msg= new ActionRequest(username, MessageType.SHOW_MARKET);
                break;
            case 6:
                msg= new ActionRequest(username, MessageType.SHOW_DEV_MARKET);
                break;
            case 7:
                msg= new ActionRequest(username, MessageType.SHOW_FAITH_TRACK);
                break;
            case 8:
                msg= new ActionRequest(username, MessageType.SHOW_DEV_CARDS);
                break;
            case 8:
                msg= new ActionRequest(username, MessageType.SHOW_RES);
                break;
            case 9:
                msg= new ActionRequest(username, MessageType.SHOW_VICTORY_POINTS);
                break;
            default:
                msg= new ActionRequest(username, MessageType.END_TURN);

        }
        client.sendMessage(msg);
    }



    public void updateBuyDevCard(int level, Color color){
        client.sendMessage(new BuyDevCardRequest(username, level, color));
    }
    public void updatePlaceDevCard(HashMap<ResourceType, Integer> expenseDepot, HashMap<ResourceType, Integer> newStrongbox, int slotToPlace){
        client.sendMessage(new PlaceDevCardRequest(username, expenseDepot, newStrongbox, slotToPlace));
    }

    public void updateGetFromMarket(char getFromRow, int i, ResourceType conversion){
        client.sendMessage(new GetMarketResRequest(username, getFromRow, i, conversion));
    }
    public void updateChosenProdCards(ArrayList<DevCard> chosen){
        client.sendMessage(new AskProductionRequest(username, chosen));
    }

    @Override
    public void update(GeneralMessage msg){
        switch(msg.getMessageType()){
            case LOGIN_REPLY:
                LoginReplyMsg loginMsg= (LoginReplyMsg) msg;
                queue.execute(() -> view.showLoginResult(loginMsg.getUsername(), loginMsg.getGameId(), loginMsg.wasJoined()));
                break;
            case SUCCESSFUL_HOST:
                queue.execute(() -> view.askPlayersNumber());
                break;
            case STARTING_LEADERS:
                queue.execute(() -> view.askLeaderCardToKeep(((StartingLeadersReplyMsg) msg).getLeaderCard()));
                break;
            case RESOURCE_TO_WAREHOUSE:
                ResourceToWarehouseReplyMsg message=((ResourceToWarehouseReplyMsg) msg);
                queue.execute(()-> view.askResourceToWarehouse(message.getResourceToPlace(), message.getAny(), message.getLeaderDepots()));
                break;
            case START_TURN:
                queue.execute(() -> view.askAction());
                break;
            case MAIN_MARBLE:
                queue.execute(() -> view.askMarketLineToGet(((GetMarketResReply) msg).getConversion()));
                break;
            case MAIN_CARD:
                queue.execute(() -> view.askDevCardToBuy());
                break;
            case MAIN_PRODUCTION:
                queue.execute(()-> view.askCardsToActivateProd(((AskProductionReply) msg).getDevCardList()));
                break;
            case PLACE_CARD:
                PlaceDevCardReply placeMsg= (PlaceDevCardReply) msg;
                queue.execute(() ->view.askSlot( placeMsg.getStrongbox(), placeMsg.getCardCost(), placeMsg.getAny(), placeMsg.getAvailableSlots()));
                break;
            case SHOW_MARKET:
                queue.execute(() -> view.showMarket(((ShowMarketMsg) msg).getMarket(),((ShowMarketMsg) msg).getCornerMarble()));
                break;
            case SHOW_FAITH_TRACK:
                queue.execute(() -> view.showCurrentVP(((ShowFaithTrackMsg) msg).getPlayerFaith()));
                break;
            case SHOW_DEV_MARKET:
                queue.execute(()-> view.showDevMarket(((ShowDevMarketMsg) msg).getAvailableCards(),((ShowDevMarketMsg) msg).getRemainingCards()));
                break;
            case SHOW_VICTORY_POINTS:
                queue.execute(() -> view.showCurrentVP(((ShowVictoryPointsMsg) msg).getVictoryPoints()));
                break;
            case SHOW_SLOT:
                queue.execute(() -> view.showSlots(((ShowSlotsMsg) msg).getDevCardSlot(), ((ShowSlotsMsg) msg).getUsername()));
                break;
        }
    }


}
