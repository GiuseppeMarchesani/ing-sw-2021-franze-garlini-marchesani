package it.polimi.ingsw.network;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ObserverView;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO
/**
 *
 */
public class ClientMessenger implements Observer, ObserverView {

    private String username;
    private View view;
    private ExecutorService queue;
    private ClientSocket client;
    private String lobby;

    //TODO
    /**
     *
     * @param view
     */
    public ClientMessenger(View view) {
        this.view = view;
        queue= Executors.newSingleThreadExecutor();
    }

    //TODO
    /**
     *
     * @param address
     * @param port
     */
    public void updateConnect(String address, int port){
        try {
            client = new ClientSocket(address, port);
            client.add(this);
            client.listen();
            queue.execute(view::askLobby);
        } catch (Exception e) {
            queue.execute(view::askConnect);
        }

    }

    //TODO

    /**
     *
     * @param username
     * @param lobby
     */
    public void updateLobby(String username, String lobby){
        this.username= username;
        this.lobby=lobby;
        client.sendMessage(new LoginRequest(username, lobby));
    }

    //TODO
    /**
     * Updates new username, if a player with the same username was already in the game.
     * @param username
     */
    public void updateNewUsername(String username){
        this.username= username;
    }

    //TODO

    /**
     *
     * @param numPlayers
     */
    public void updatePlayersNumber(int numPlayers){
        client.sendMessage(new PlayersNumberRequest(username, numPlayers));
    }

    //TODO

    /**
     *
     * @param leaders
     */
    public void updateDiscardLeader(ArrayList<LeaderCard> leaders){
        client.sendMessage(new StartingLeadersRequestMsg(username, leaders));
    }

    //TODO

    /**
     *
     * @param depotToResource
     * @param depotToQuantity
     * @param leaderToDepot
     * @param discard
     */
    public void updateWarehouse(HashMap<Integer,ResourceType> depotToResource, HashMap<Integer,Integer> depotToQuantity, ArrayList<Integer> leaderToDepot,int discard){
        client.sendMessage(new ResourceToWarehouseRequestMsg(username, depotToResource, depotToQuantity, leaderToDepot ,discard));
    }

    //TODO

    /**
     *
     * @param actionCode the action code of the action the player wants to do.
     */
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
                msg= new ActionRequest(username, MessageType.SHOW_SLOT);
                break;
            case 9:
                msg= new ActionRequest(username, MessageType.SHOW_WAREHOUSE);
                break;
            case 10:
                msg= new ActionRequest(username, MessageType.SHOW_STRONGBOX);
                break;
            case 11:
                msg= new ActionRequest(username, MessageType.SHOW_VICTORY_POINTS);
                break;
            default:
                msg= new ActionRequest(username, MessageType.END_TURN);

        }
        client.sendMessage(msg);
    }


    //TODO

    /**
     *
     * @param level level of the chosen Development Card.
     * @param color color of the Development Card.
     */
    public void updateBuyDevCard(int level, Color color){
        client.sendMessage(new BuyDevCardRequest(username, level, color));
    }

    //TODO

    /**
     *
     * @param expenseDepot
     * @param newStrongbox
     * @param slotToPlace
     */
    public void updatePlaceDevCard(HashMap<ResourceType, Integer> expenseDepot, HashMap<ResourceType, Integer> newStrongbox, int slotToPlace){
        client.sendMessage(new PlaceDevCardRequest(username, expenseDepot, newStrongbox, slotToPlace));
    }

    //TODO

    /**
     *
     * @param getFromRow
     * @param i
     * @param conversion
     */
    public void updateGetFromMarket(char getFromRow, int i, ResourceType conversion){
        client.sendMessage(new GetMarketResRequest(username, getFromRow, i, conversion));
    }

    //TODO

    /**
     *
     * @param chosen
     */
    public void updateChosenProdCards(ArrayList<DevCard> chosen){
        client.sendMessage(new AskProductionRequest(username, chosen));
    }
    public void updateGetProdRes(HashMap<ResourceType, Integer> newStrongbox, HashMap<ResourceType, Integer> expenseDepot){
        client.sendMessage(new GetProductionRequest(username, expenseDepot, newStrongbox));
    }
    public void updatePlayLeaderCard(LeaderCard card, char dOrP){
        client.sendMessage(new LeaderActionRequest(username, card, dOrP=='P'));
    }

    //TODO

    /**
     *
     * @param msg
     */
    @Override
    public void update(GeneralMessage msg){
        switch(msg.getMessageType()){
            case LOGIN_REPLY:
                LoginReply loginMsg= (LoginReply) msg;
                queue.execute(() -> view.showLoginResult(loginMsg.getUsername(), loginMsg.getGameId(), loginMsg.wasJoined()));
                break;
            case SUCCESSFUL_HOST:
                queue.execute(() -> view.askPlayersNumber());
                break;
            case STARTING_LEADERS:
                queue.execute(() -> view.askLeaderCardToKeep(((StartingLeadersReplyMsg) msg).getLeaderCard()));
                break;
            case RESOURCE_TO_WAREHOUSE:
                ResourceToWarehouseReply message=((ResourceToWarehouseReply) msg);
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
            case ACTIVATE_PRODUCTION:
                GetProductionReply paymentMsg= (GetProductionReply) msg;
                queue.execute(()-> view.askProduction(paymentMsg.getStrongbox(), paymentMsg.getPrice(), paymentMsg.getAnyPayment(), paymentMsg.getAnyProduce()));
                break;
            case PICK_DEVCARD:
                PlaceDevCardReply placeMsg= (PlaceDevCardReply) msg;
                queue.execute(() ->view.askSlot( placeMsg.getStrongbox(), placeMsg.getCardCost(), placeMsg.getAny(), placeMsg.getAvailableSlots()));
                break;
            case SHOW_MARKET:
                queue.execute(() -> view.showMarket(((ShowMarketMsg) msg).getMarket(),((ShowMarketMsg) msg).getCornerMarble()));
                break;
            case SHOW_FAITH_TRACK:
                queue.execute(() -> view.showFaithTrack(((ShowFaithTrackMsg) msg).getPlayerFaith(),((ShowFaithTrackMsg) msg).isZoneActivated(),((ShowFaithTrackMsg) msg).getWhichZone()));
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
            case SIDE_LEADER:
                queue.execute(() -> view.askLeaderCardToPlay(((SideLeaderReply) msg).getLeaderCards()));
                break;
            case WIN:
                queue.execute(() -> view.showWinMessage(((WinMessage) msg).getFinalPoints()));
                client.disconnect();
                break;
            case ERROR:
                queue.execute(()-> view.showErrorMsg(((ErrorMsg) msg).getMessage()));
                break;
            case STRING_MESSAGE:
                queue.execute(()-> view.showMessage(((StringMessage) msg).getMessage()));
                break;
            case LOSE:
                queue.execute(()-> view.showLoseMessage());
                client.disconnect();
                break;
            case SHOW_REMAINING_LEADERS:
                queue.execute(() -> view.showRemainingLeaderCards(((ShowRemainingLeaderMsg) msg).getUsername(),((ShowRemainingLeaderMsg) msg).getRemaining()));
                break;
            case SHOW_LEADER:
                queue.execute(() ->view.showLeaderCards(((ShowLeaderCardsMsg) msg).getCards()));
                break;
            case SHOW_STRONGBOX:
                queue.execute(()-> view.showStrongbox(((ShowStrongboxMsg) msg).getStrongbox(), ((ShowStrongboxMsg) msg).getUsername()));
                break;
            case SHOW_WAREHOUSE:
                queue.execute(()-> view.showWarehouse(((ShowWarehouseMsg)msg).getDepotToQuantity(),((ShowWarehouseMsg)msg).getDepotToResource(),((ShowWarehouseMsg)msg).getUsername()));
        }
    }


}
