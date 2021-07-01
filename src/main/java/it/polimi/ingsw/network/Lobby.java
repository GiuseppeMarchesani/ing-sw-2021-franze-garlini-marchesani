package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.messages.ClientMessage;
import it.polimi.ingsw.messages.GeneralMessage;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.view.VirtualView;

import java.util.*;
import java.util.function.ToDoubleBiFunction;

/**
 *This class keeps track of all the connected clients to the server that have the same game in common
 */
public class Lobby {
    private Map<ClientHandler, String> clientHandlerMap;
    private GameController gameController;
    private String gameId;

    /**
     *Default constructor
     * @param gameId
     */
    public Lobby(String gameId) {
        clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        gameController=new GameController();
        this.gameId=gameId;
    }

    /**
     *Adds a new player to the lobby
     * @param username the username of the player
     * @param clientHandler the clienthandler of the player
     */
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


    /**
     *Removes a player from the lobby.
     * @param clientHandler the associated clienthandler
     */
    public void removePlayer(ClientHandler clientHandler){
        clientHandlerMap.remove(clientHandler);
    }


    /**
     * Disconnects a player from the lobby.
     * @param clientHandler the associated client handler
     */
    public void disconnect(ClientHandler clientHandler){

            gameController.disconnect(clientHandlerMap.get(clientHandler));
            removePlayer(clientHandler);
    }

    /**
     *Reconnects a player to the lobby
     * @param username the username of the player
     * @param clientHandler the associated clienthandler
     * @param virtualView the associated virtual view
     */
    public void reconnect(String username, ClientHandler clientHandler,VirtualView virtualView){
        clientHandlerMap.put(clientHandler, username);
        gameController.reconnect(username, virtualView);

    }


    /**
     *Checks if the game is idle or ongoing
     * @return if the game has already started
     */
    public boolean isGameStarted(){
        return gameController.isGameStarted();
    }

    /**
     *Returns a list of inactive players.
     * @return list of inactive players.
     */
    public List<String> getInactivePlayers(){
        return gameController.getInactivePlayers();
    }


    /**
     *Checks if the game has inactive players
     * @return true if the game has inactive players
     */
    public boolean hasInactivePLayers(){

        return gameController.hasInactivePlayers();
    }

    /**
     * passes a message to the gamecontroller
     * @param clientMessage a message from the client
     */
    public void getMessage(ClientMessage clientMessage){
        gameController.getMessage(clientMessage);

    }


    /**
     *Handles a disconnection from the client
     * @param clientHandler the clienthandler of the disconnected client
     */
    public void onDisconnect(ClientHandler clientHandler){
        if (gameController.isGameStarted()){
            disconnect(clientHandler);
        }
        else{
            removePlayer(clientHandler);
        }
    }
    /**
     *Checks how many players are connected
     * @return the number of connected players
     */
    public int remainingPlayers(){
        return clientHandlerMap.size();
    }
}
