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

    public Lobby getLobby(String id){
        Lobby l = lobbyMap.get(id);
        if (l==null){
            lobbyMap.put(id, new Lobby(id));
        }
        return lobbyMap.get(id);


    }
    public void leaveLobby(String id, ClientHandler clientHandler){
        Lobby lobby= lobbyMap.get(id);
        if(lobby!=null){
            if (lobby.isGameStarted()){
                lobby.disconnect(clientHandler);
            }
            else{
                lobby.removePlayer(clientHandler);
            }
        }
    }
}
