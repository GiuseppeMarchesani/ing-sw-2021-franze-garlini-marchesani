package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Turn {

    private Game gameSession;
    private ArrayList<ClientHandler> players;
    private ClientHandler activePlayer;

    public Turn(){
        gameSession=new Game();
        players=new ArrayList<ClientHandler>();
    }

    public HashMap<ResourceType, Integer> buyMarbles(char rowOrCol, int index){
        if(rowOrCol=='c'){
            return gameSession.pickMarketRes(true, index);
        }
        else{
            return gameSession.pickMarketRes(false, index);
        }

    }
    public DevCard buyDevCard(int color ,int level ){
        return gameSession.pickDevCard(color, level);
    }

    public HashMap<ResourceType, Integer> devIncome(ArrayList<DevCard> list){
        return gameSession.pickProductionRes(list);
    }

    public Game getGameSession() {
        return gameSession;
    }

    public ClientHandler getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(ClientHandler activePlayer) {
        this.activePlayer = activePlayer;
    }

    public ArrayList<ClientHandler> getPlayers() {
        return players;
    }
    public void addPlayer(ClientHandler client){
        players.add(client);
    }
}
