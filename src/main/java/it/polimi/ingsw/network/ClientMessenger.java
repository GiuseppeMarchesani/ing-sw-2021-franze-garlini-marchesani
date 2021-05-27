package it.polimi.ingsw.network;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.DiscardLeaderMsg;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ObserverView;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public void updateConnect(String address, int port, boolean host){
        try {
            client = new ClientSocket(address, port);
            client.listen();
            queue.execute(view::askUsername);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateUsername(String username){
        this.username= username;
        queue.execute(view::askGameID);
    }

    public void updateGameID(String lobby){
        this.lobby=lobby;
        queue.execute(view::askPlayersNumber);
    }
    public void updatePlayersNumber(int numPlayers){
        client.sendMessage(new LoginMsg(username, lobby, numPlayers));
    }

    public void updateLeaderDraw(int id1, int id2){
        client.sendMessage(new DiscardLeaderMsg(username, lobby, id1, id2));
    }

    public void updateBeginningResource(HashMap<ResourceType, Integer> resourceQuantity, HashMap<ResourceType, Integer> resourceDepot ){
        client.sendMessage(new RearrangeReplyMsg(username, lobby, resourceQuantity, resourceDepot, 0));
    }

    public void updateGetFromMarket(char getFromRow, int i, ResourceType conversion){
        client.sendMessage(new GetMarketMsg(username, lobby, getFromRow, i, conversion));
    }

    public void updateDepot(HashMap<ResourceType, Integer> resourceQuantity, HashMap<ResourceType, Integer> resourceDepot, int discard){
        client.sendMessage(new RearrangeReplyMsg(username, lobby, resourceQuantity, resourceDepot, discard));
    }

    public void updateProduction(boolean[] activate){
        client.sendMessage(new ProductionMsg(username, lobby, activate);
    }

    public void updateBuyDevCard(int level, int color, int slot){
        client.sendMessage(new BuyDevCardMsg(username, lobby, level, color, slot));
    }

    public void updateAction(char a){
        client.sendMessage(new PickActionMsg(username, lobby, a));
    }

    public void update(GeneralMessage msg){
        switch(msg.getMessageType()){
            case LOGIN_REPLY:
                LoginReplyMsg loginMsg= (LoginReplyMsg) msg;
                queue.execute(() -> view.showLoginResult(loginMsg.getUsername(), loginMsg.getGameID(),loginMsg.wasCreated(), loginMsg.wasJoined()));
            case START_TURN:
                queue.execute(() -> view.askAction());
                break;
            case SHOW_MARKET:
                queue.execute(() -> view.showMarket(((ShowMarketMsg) msg).getMarket()));
                break;
            case SHOW_DEV_MARKET:
                queue.execute(() -> view.showDevMarket(((ShowDevMarketMsg) msg).getCards());
                break;
            case SHOW_ALL_SLOT:
                queue.execute(() -> view.showSlots(((ShowPlayerCardsMsg) msg).getCards());
                break;
            case SHOW_LEADER:
                queue.execute(() -> view.showLeaderCards(((ShowLeaderCardsMsg) msg).getCards());
                break;
            case SHOW_ALL_RES:
                queue.execute(() -> view.showResources(((ShowResourcesRequest) msg).getStrongbox(),((ShowResourcesRequest) msg).getWarehouse());
                break;

        }
    }


}
