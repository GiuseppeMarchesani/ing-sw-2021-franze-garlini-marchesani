package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.messages.GeneralMessage;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Lobby {
    private Map<ClientHandler, String> clientHandlerMap;
    private GameController gameController;

    public Lobby() {
        clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        gameController=null;
    }

    //TODO: View
    public void addPlayer(String username, ClientHandler clientHandler){
        int i=1;
        String s=username;
        while(clientHandlerMap.containsKey(s)){
            s=username+"("+i+")";
        }
        clientHandlerMap.put(clientHandler, username);
    }
    public void removePlayer(ClientHandler clientHandler){
        clientHandlerMap.remove(clientHandler);
    }
    public void disconnect(ClientHandler clientHandler){

            gameController.disconnect(clientHandlerMap.get(clientHandler));
            removePlayer(clientHandler);
    }
    public void reconnect(String username, ClientHandler clientHandler){
        clientHandlerMap.put(clientHandler, username);;
        gameController.reconnect(username);
    }
   public boolean status(){
        if(gameController==null){
            return false;
        }
        return gameController.status();
   }
   public boolean isFull() {
       if (clientHandlerMap.size() == 4) {
           return true;
       }
           return false;

   }
    public List<String> getInactivePlayers(){
        return gameController.getInactivePlayers();
    }
    public boolean hasInactivePLayers(){
        if(gameController==null){
            return false;
        }
        return gameController.hasInactivePlayers();
    }
    public void getMessage(GeneralMessage generalMessage){
        gameController.getMessage(generalMessage);

    }
}
