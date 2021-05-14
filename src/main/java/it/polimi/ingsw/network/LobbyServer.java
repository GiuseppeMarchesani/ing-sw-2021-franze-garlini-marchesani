package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LobbyServer {
    public Map<String, Lobby> lobbyMap;

    public LobbyServer() {
        lobbyMap = Collections.synchronizedMap(new HashMap<>());
    }
    //TODO: View
    public Lobby joinLobby(String id){
        Lobby lobby= lobbyMap.get(id);
            if(lobby!=null){
               if((!(lobby.status())&& !(lobby.isFull()))||(lobby.hasInactivePLayers())){
                    return lobby;
               }
               return null;
            }
            lobbyMap.put(id, new Lobby());
            return lobbyMap.get(id);
    }
}
