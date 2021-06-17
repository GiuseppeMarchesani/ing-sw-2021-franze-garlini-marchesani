package it.polimi.ingsw.network;

import java.util.*;


//TODO

/**
 *
 */
public class LobbyServer {
    public Map<String, Lobby> lobbyMap;

    //TODO

    /**
     *
     */
    public LobbyServer() {
        lobbyMap = Collections.synchronizedMap(new HashMap<>());
    }
    //TODO: View

    /**
     *
     * @param id
     * @return
     */
    public Lobby getLobby(String id){
        Lobby l = lobbyMap.get(id);
        if (l==null){
            lobbyMap.put(id, new Lobby(id));
        }
        return lobbyMap.get(id);

    }

    //TODO

    /**
     *
     * @param id
     * @param clientHandler
     */
    public void leaveLobby(String id, ClientHandler clientHandler){
        Lobby lobby= lobbyMap.get(id);
        if(lobby.remainingPlayers()==1){
         lobbyMap.remove(id);
        }
        else{
            lobby.onDisconnect(clientHandler);
        }

    }
}
