package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.messages.ClientMessage;
import it.polimi.ingsw.messages.GeneralMessage;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.view.VirtualView;

import java.util.*;
import java.util.function.ToDoubleBiFunction;
//TODO
/**
 *
 */
public class Lobby {
    private Map<ClientHandler, String> clientHandlerMap;
    private GameController gameController;
    private String gameId;
    //TODO
    /**
     *
     * @param gameId
     */
    public Lobby(String gameId) {
        clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        gameController=new GameController();
        this.gameId=gameId;
    }
    //TODO

    /**
     *
     * @param username
     * @param clientHandler
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

    //TODO

    /**
     *
     * @param clientHandler
     */
    public void removePlayer(ClientHandler clientHandler){
        clientHandlerMap.remove(clientHandler);
    }

    //TODO

    /**
     *
     * @param clientHandler
     */
    public void disconnect(ClientHandler clientHandler){

            gameController.disconnect(clientHandlerMap.get(clientHandler));
            removePlayer(clientHandler);
    }
    //TODO

    /**
     *
     * @param username
     * @param clientHandler
     * @param virtualView
     */
    public void reconnect(String username, ClientHandler clientHandler,VirtualView virtualView){
        clientHandlerMap.put(clientHandler, username);
        gameController.reconnect(username, virtualView);

    }

    //TODO

    /**
     *
     * @return
     */
    public boolean isGameStarted(){
        return gameController.isGameStarted();
    }


    public List<String> getInactivePlayers(){
        return gameController.getInactivePlayers();
    }

    //TODO

    /**
     *
     * @return
     */
    public boolean hasInactivePLayers(){

        return gameController.hasInactivePlayers();
    }

    public void getMessage(ClientMessage clientMessage){
        gameController.getMessage(clientMessage);

    }

    //TODO

    /**
     *
     * @param clientHandler
     */
    public void onDisconnect(ClientHandler clientHandler){
        if (gameController.isGameStarted()){
            disconnect(clientHandler);
        }
        else{
            removePlayer(clientHandler);
        }
    }

    //TODO

    /**
     *
     * @return
     */
    public int remainingPlayers(){
        return clientHandlerMap.size();
    }
}
