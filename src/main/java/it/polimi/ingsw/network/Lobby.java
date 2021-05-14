package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Lobby {
    private Map<String, ClientHandler> clientHandlerMap;
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
        clientHandlerMap.put(username, clientHandler);
    }
    public void removePlayer(String username){
        clientHandlerMap.remove(username);
    }
    public void disconnect(ClientHandler clientHandler){
            String username = clientHandlerMap.entrySet().stream().filter(entry -> clientHandler.equals(entry.getValue())).map(Map.Entry::getKey).findFirst().orElse(null);
            gameController.disconnect(username);
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
   public Map<String, Boolean> inactivePlayers(){
       if(gameController==null){
           return null;
       }
        return gameController.inactivePlayers();
   }

    public boolean hasInactivePLayers(){
        if(gameController==null){
            return false;
        }
        return gameController.hasInactivePlayers();
    }
}
