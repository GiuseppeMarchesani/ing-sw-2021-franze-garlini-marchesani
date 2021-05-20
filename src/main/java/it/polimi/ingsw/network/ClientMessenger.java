package it.polimi.ingsw.network;

import it.polimi.ingsw.messages.DiscardLeaderMsg;
import it.polimi.ingsw.messages.ProductionMsg;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientMessenger {

    private String username;
    private View view;
    private ExecutorService task;
    private ClientSocket client;
    private String lobby;

    public ClientMessenger(View view) {
        this.view = view;
        task= Executors.newSingleThreadExecutor();
    }

    public void messageConnect(String address, int port, boolean host){
        try {
            client = new ClientSocket(address, port);
            client.listen();
            task.execute(view::askUsername);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateUsername(String username){
        this.username= username;
        taskQueue.execute(view::askGameID);
    }

    public void updateLobby(String lobby){
        this.lobby=lobby;
        taskQueue.execute(view::askGameID);
    }

    public void updateLeaderDraw(ArrayList<LeaderCard> leader){
        client.sendMessage(new DiscardLeaderMsg(username, lobby, leader));
    }

    public void updateProductionMsg(ArrayList<DevCard> devs){
        client.sendMessage(new ProductionMsg(username, lobby, devs));
    }

}
