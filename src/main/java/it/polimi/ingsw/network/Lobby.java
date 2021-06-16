package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.messages.ClientMessage;
import it.polimi.ingsw.messages.GeneralMessage;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.view.VirtualView;

import java.util.*;

public class Lobby {
    private Map<ClientHandler, String> clientHandlerMap;
    private GameController gameController;
    private String gameId;
    public Lobby(String gameId) {
        clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        gameController=new GameController();
        this.gameId=gameId;
    }

    public void addPlayer(String username, ClientHandler clientHandler){
        VirtualView virtualView=new VirtualView(clientHandler);
            if(!(isGameStarted())){

                int i=1;
                String s=new String(username);
                if (s.equals("Lorenzo Il Magnifico")){
                        s="Lorenzo L'Imitazione";
                }
                while (clientHandlerMap.containsValue(s)) {
                    s = username + "(" + i + ")";
                    System.out.println(s);
                    i++;
                }
                clientHandlerMap.put(clientHandler, s);
                    gameController.newPlayer(s, gameId, virtualView);
            }
            else if(hasInactivePLayers()){
                List<String> inactive =getInactivePlayers();
                if(inactive.contains(username)){
                    reconnect(username, clientHandler, virtualView);
                }
            }
            else{
                virtualView.showLoginResult(username, gameId, false);
            }

    }
    public void removePlayer(ClientHandler clientHandler){
        clientHandlerMap.remove(clientHandler);
    }
    public void disconnect(ClientHandler clientHandler){

            gameController.disconnect(clientHandlerMap.get(clientHandler));
            removePlayer(clientHandler);
    }
    public void reconnect(String username, ClientHandler clientHandler,VirtualView virtualView){
        clientHandlerMap.put(clientHandler, username);
        gameController.reconnect(username, virtualView);

    }
   public boolean isGameStarted(){
        return gameController.isGameStarted();
   }

    public List<String> getInactivePlayers(){
        return gameController.getInactivePlayers();
    }
    public boolean hasInactivePLayers(){

        return gameController.hasInactivePlayers();
    }
    public void getMessage(ClientMessage clientMessage){
        gameController.getMessage(clientMessage);

    }
    public void onDisconnect(ClientHandler clientHandler){
        if (gameController.isGameStarted()){
            disconnect(clientHandler);
        }
        else{
            removePlayer(clientHandler);
        }
    }
    public int remainingPlayers(){
        return clientHandlerMap.size();
    }
}
