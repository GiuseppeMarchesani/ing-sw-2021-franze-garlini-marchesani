package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.messages.GeneralMessage;
import it.polimi.ingsw.model.Game;

import java.util.*;

public class LobbyServer {
    public Map<String, Lobby> lobbyMap;

    public LobbyServer() {
        lobbyMap = Collections.synchronizedMap(new HashMap<>());
    }
    //TODO: View

    public Lobby joinLobby(String id, String username, ClientHandler clientHandler){
        Lobby lobby= lobbyMap.get(id);
            if(lobby!=null){
               if(!(lobby.status())&& !(lobby.isFull())){
                   lobby.addPlayer(username, clientHandler);
                    return lobby;
               }
               else if(lobby.hasInactivePLayers()){
                   List<String> inactive =lobby.getInactivePlayers();
                   if(inactive.contains(username)){
                       lobby.reconnect(username, clientHandler);
                       return lobby;
                   }
               }
               return null;
            }
            lobbyMap.put(id, new Lobby());
            lobby.addPlayer(username, clientHandler);
            return lobby;
    }
    public void leaveLobby(String id, ClientHandler clientHandler){
        Lobby lobby= lobbyMap.get(id);
        if(lobby!=null){
            if (lobby.status()){
                lobby.disconnect(clientHandler);
            }
            else{
                lobby.removePlayer(clientHandler);
            }
        }
    }
}
