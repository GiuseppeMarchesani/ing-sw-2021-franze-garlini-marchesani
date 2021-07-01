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


/**This class gets input from the view and packages it into a message.
 *This class also notifies the view that a certain message has arrived
 */
public class ClientMessenger implements Observer, ObserverView {

    private String username;
    private View view;
    private ExecutorService queue;
    private ClientSocket client;
    private String lobby;


    /**
     * Default Constructor
     * @param view the view to interact with
     */
    public ClientMessenger(View view) {
        this.view = view;
        queue= Executors.newSingleThreadExecutor();
    }


    /**
     *Tries to connect the client to a socket.
     * @param address The ip address to connect to.
     * @param port The port to connect to
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


    /**
     *Tries to log the user to a certain lobby
     * @param username The username of the player trying to log in
     * @param lobby The lobby/game id.
     */
    public void updateLobby(String username, String lobby){
        this.username= username;
        this.lobby=lobby;
        client.sendMessage(new LoginRequest(username, lobby));
    }

    /**
     * Updates new username, if a player with the same username was already in the game.
     * @param username
     */
    public void updateNewUsername(String username){
        this.username= username;
    }

    /**
     *Sends the number of players of the game you are hosting
     * @param numPlayers the player number of the game
     */
    public void updatePlayersNumber(int numPlayers){
        client.sendMessage(new PlayersNumberRequest(username, numPlayers));
    }


    /**
     *Sends the cards that the player keeps after the initial setup draw.
     * @param leaders the cards the player wants to keep
     */
    public void updateDiscardLeader(ArrayList<LeaderCard> leaders){
        client.sendMessage(new StartingLeadersRequestMsg(username, leaders));
    }

    /**
     *Updates the player warehouse after getting resources from market
     * @param depotToResource Map Floor to Resource Type
     * @param depotToQuantity Map Floor to Quantity
     * @param leaderToDepot Array of quantity for every leader depot
     * @param discard number of discarded resources
     */
    public void updateWarehouse(HashMap<Integer,ResourceType> depotToResource, HashMap<Integer,Integer> depotToQuantity, ArrayList<Integer> leaderToDepot,int discard){
        client.sendMessage(new ResourceToWarehouseRequestMsg(username, depotToResource, depotToQuantity, leaderToDepot ,discard));
    }

    /**
     *Requests an action to do
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
            case 12:
                msg= new ActionRequest(username, MessageType.SHOW_PLAYER_FAITH);
                break;
            default:
                msg= new ActionRequest(username, MessageType.END_TURN);

        }
        client.sendMessage(msg);
    }


    /**
     *Asks to buy a card from the market.
     * @param level level of the chosen Development Card.
     * @param color color of the Development Card.
     */
    public void updateBuyDevCard(int level, Color color){
        client.sendMessage(new BuyDevCardRequest(username, level, color));
    }


    /**
     *Spends resources and places a card in a slot.
     * @param expenseDepot the resource that will be spent from the depot
     * @param newStrongbox the new strongbox
     * @param slotToPlace the slot to place the game.
     */
    public void updatePlaceDevCard(HashMap<ResourceType, Integer> expenseDepot, HashMap<ResourceType, Integer> newStrongbox, int slotToPlace){
        client.sendMessage(new PlaceDevCardRequest(username, expenseDepot, newStrongbox, slotToPlace));
    }

    /**
     * Shows a player
     * @param player the player to show
     */
    public void updateShowPlayer(String player){
        client.sendMessage(new ShowPlayerRequest(username, player));
    }
    /**
     *Asks for resources from the resource market
     * @param getFromRow identifies if the player wants a column or a row
     * @param i the index of the column/row
     * @param conversion the conversion chosen for white marbles
     */
    public void updateGetFromMarket(char getFromRow, int i, ResourceType conversion){
        client.sendMessage(new GetMarketResRequest(username, getFromRow, i, conversion));
    }

    /**
     *Sends chosen DevCards to be activated
     * @param chosen the chosen devcards
     */
    public void updateChosenProdCards(ArrayList<DevCard> chosen){
        client.sendMessage(new AskProductionRequest(username, chosen));
    }

    /**
     * Gets production resources by paying their cost
     * @param newStrongbox the new strongbox
     * @param expenseDepot the resource that will be spent from the depot
     */
    public void updateGetProdRes(HashMap<ResourceType, Integer> newStrongbox, HashMap<ResourceType, Integer> expenseDepot){
        client.sendMessage(new GetProductionRequest(username, expenseDepot, newStrongbox));
    }

    /**
     * Plays or discards a leader card
     * @param card the chosen card
     * @param dOrP identifies if the player wants to discard or play the chosen card
     */
    public void updatePlayLeaderCard(LeaderCard card, char dOrP){
        client.sendMessage(new LeaderActionRequest(username, card, dOrP=='p'));
    }


    /**
     *Notifies the view when a message is received
     * @param msg The received message
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
                queue.execute(()-> view.askProduction(paymentMsg.getStrongbox(), paymentMsg.getWarehouse(), paymentMsg.getPrice(), paymentMsg.getAnyPayment(), paymentMsg.getAnyProduce()));
                break;
            case PICK_DEVCARD:
                PlaceDevCardReply placeMsg= (PlaceDevCardReply) msg;
                queue.execute(() ->view.askSlot(placeMsg.getStrongbox(), placeMsg.getWarehouse(), placeMsg.getCardCost(), placeMsg.getAny(), placeMsg.getAvailableSlots()));
                break;
            case SHOW_MARKET:
                queue.execute(() -> view.showMarket(((ShowMarketMsg) msg).getMarket(),((ShowMarketMsg) msg).getCornerMarble()));
                break;
            case SHOW_FAITH_TRACK:
                queue.execute(() -> view.showFaithTrack(((ShowFaithTrackMsg) msg).isZoneActivated(),((ShowFaithTrackMsg) msg).getWhichZone()));
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
                queue.execute(() -> view.askLeaderCardToPlay(((SideLeaderReply) msg).getLeaderCards(), ((SideLeaderReply) msg).getAllLeaderCards()));
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
            case SHOW_LEADER:
                queue.execute(() ->view.showLeaderCards(((ShowLeaderCardsMsg) msg).getCards()));
                break;
            case SHOW_STRONGBOX:
                queue.execute(()-> view.showStrongbox(((ShowStrongboxMsg) msg).getStrongbox(), ((ShowStrongboxMsg) msg).getUsername()));
                break;
            case SHOW_WAREHOUSE:
                queue.execute(()-> view.showWarehouse(((ShowWarehouseMsg)msg).getDepotToQuantity(),((ShowWarehouseMsg)msg).getDepotToResource(),((ShowWarehouseMsg)msg).getUsername()));
                break;
            case SHOW_PLAYED_LEADERS:
                queue.execute(()-> view.showPlayedLeaderCards(((ShowPlayedLeadersMsg) msg).getPlayedLeaderCards(),((ShowPlayedLeadersMsg) msg).getActivePlayer()));
                break;
            case SHOW_PLAYER:
                ShowPlayerReply player=(ShowPlayerReply) msg;
                queue.execute(() -> view.showPlayer(player.getUsername(), player.getFaithSpace(), player.getDepotToResource(), player.getDepotToQuantity(), player.getStrongbox(), player.getDevCardSlot(), player.getPlayedLeaderCards(),player.getRemainingLeaderCards(), username));
                break;
            case SHOW_PLAYER_FAITH:
                queue.execute(()->view.showPlayerFaith(((ShowPlayerFaithMsg) msg).getFaith()));
        }
    }


}
