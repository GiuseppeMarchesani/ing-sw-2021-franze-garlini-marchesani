package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Turn {
    private Game gameSession;
    private ArrayList<ClientHandler> players;
    private ClientHandler activePlayer;
    private boolean ongoing;
    public Turn(){
        players = new ArrayList<ClientHandler>();
        ongoing=false;
    }

    public void startGame() {
        try {
            if(players.size()==1) gameSession=new SinglePlayerGame();
            else gameSession=new Game();
            ongoing=true;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        //TODO: Give 4 leader cards per player and let them choose 2 cards
    }

    public HashMap<ResourceType, Integer> buyMarbles(char rowOrCol, int index){
        return gameSession.pickMarketRes(rowOrCol, index);
    }

    public DevCard buyDevCard(Color color ,int level ){
        return gameSession.pickDevCard(color, level);
    }

    //calls the Player's methods in order to store the resources in the Strongbox and increase his faith space..
    public void devIncome(ArrayList<DevCard> list){
        HashMap<ResourceType, Integer> productionIncome = gameSession.pickProductionRes(list);
        //TODO: lanciare choose per ogni any in productionIncome
        //TODO: trovare faith e incrementare faithspaces
        /*int position = player.increaseFaith(faithSpaceToInc);
        gameSession.updateFaithTrack(player, position)
        updateFaithTrack(player, position);
        player.storeResources(resources);  */
    }

    public boolean status(){
        return ongoing;
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

    public ArrayList<LeaderCard> drawLeaders(){
        ArrayList<LeaderCard> cards=new ArrayList<LeaderCard>();
        for(int i=0; i<4;i++){
            cards.add(gameSession.drawCard());
        }
        return cards;
    }
}
