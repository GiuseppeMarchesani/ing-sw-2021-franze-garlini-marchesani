package it.polimi.ingsw.network;

import java.util.*;



/**
 *This class keeps track of all games being played.
 */
public class LobbyServer {
    public Map<String, Lobby> lobbyMap;
    /**
     *Default Constructor
     */
    public LobbyServer() {
        lobbyMap = Collections.synchronizedMap(new HashMap<>());
    }

    /**
     *Gets the lobby associated to a game Id
     * @param id the id of the game
     * @return the associated lobby
     */
    public Lobby getLobby(String id){
        Lobby l = lobbyMap.get(id);
        if (l==null){
            lobbyMap.put(id, new Lobby(id));
        }
        return lobbyMap.get(id);

    }


    /**
     *Makes a user leave a lobby
     * @param id the lobby to leave from
     * @param clientHandler the clienthandler of the client handler that leaves.
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
