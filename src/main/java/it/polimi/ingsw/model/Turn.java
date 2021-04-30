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

    public void startGame() {
        ArrayList<LeaderCard> leaderCardDeck = gameSession.start();
        //TODO: Give 4 leader cards per player and let them choose 2 cards
    }

    public HashMap<ResourceType, Integer> buyMarbles(char rowOrCol, int index){
        return gameSession.pickMarketRes(rowOrCol, index);
    }

    public DevCard buyDevCard(int color ,int level ){
        return gameSession.pickDevCard(color, level);
    }

    public void devIncome(ArrayList<DevCard> list){
        HashMap<ResourceType, Integer> productionIncome = gameSession.pickProductionRes(list);
        //TODO: lanciare choose per ogni any in productionIncome
        //TODO: trovare faith e incrementare faithspaces
        /*int position = player.increaseFaith(faithSpaceToInc);
        gameSession.updateFaithTrack(player, position)
        updateFaithTrack(player, position);
        player.storeResources(resources);  */
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
