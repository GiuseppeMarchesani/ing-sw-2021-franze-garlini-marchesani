package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.messages.GeneralMessage;
import it.polimi.ingsw.view.VirtualView;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Lobby {
    private Map<ClientHandler, String> clientHandlerMap;
    private GameController gameController;
    private int players;
    private String gameId;
    public Lobby(int players, String gameId) {
        clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        gameController=new GameController(players);
        this.gameId=gameId;
    }

    //TODO: View
    public void addPlayer(String username, ClientHandler clientHandler){
        VirtualView virtualView=new VirtualView(clientHandler);
            if(!(status())&& !(isFull())){

                int i=1;
                String s=username;
                while(clientHandlerMap.containsKey(s)){
                    s=username+"("+i+")";
                }
                clientHandlerMap.put(clientHandler, username);
                virtualView.showLoginResult(username, gameId, true, players-clientHandlerMap.size());
            }
            else if(hasInactivePLayers()){
                List<String> inactive =getInactivePlayers();
                if(inactive.contains(username)){
                    reconnect(username, clientHandler);
                }
            }
            else{

            }

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
        return gameController.status();
   }
   public boolean isFull() {
       if (clientHandlerMap.size() == players) {
           return true;
       }
           return false;

   }
    public List<String> getInactivePlayers(){
        return gameController.getInactivePlayers();
    }
    public boolean hasInactivePLayers(){

        return gameController.hasInactivePlayers();
    }
    public void getMessage(GeneralMessage generalMessage){
        gameController.getMessage(generalMessage);

    }
}
