package it.polimi.ingsw.network;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ObserverView;
import it.polimi.ingsw.view.View;

import java.io.IOException;
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
        client.sendMessage(new LoginMsg(username, lobby));
    }
    public void updatePlayersNumber(int numPlayers){
        client.sendMessage(new PlayersNumberReply(numPlayers));
    }

    public void updateLeaderDraw(ArrayList<LeaderCard> leaders){
        client.sendMessage(new ChoseLeadersMsg(leaders));
    }

    public void updateBeginningResource(HashMap<ResourceType, Integer> resourceQuantity, HashMap<ResourceType, Integer> resourceDepot ){
        client.sendMessage(new RearrangeReplyMsg(username, lobby, resourceQuantity, resourceDepot, 0));
    }

    public void updateGetFromMarket(char getFromRow, int i, ResourceType conversion){
        client.sendMessage(new GetMarketMsg(getFromRow, i, conversion));
    }

    public void updateDepot(HashMap<ResourceType, Integer> resourceQuantity, HashMap<ResourceType, Integer> resourceDepot, int discard){
        client.sendMessage(new RearrangeReplyMsg(username, lobby, resourceQuantity, resourceDepot, discard));
    }

    public void updateProduction(ArrayList<DevCard> devCards){
        client.sendMessage(new ProductionMsg(devCards));
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
                queue.execute(() -> view.showLoginResult(loginMsg.getUsername(), loginMsg.getGameID(), loginMsg.wasJoined()));
                break;
            case PLAYER_NUMBER:
                queue.execute(() -> view.askPlayersNumber());
                break;
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
