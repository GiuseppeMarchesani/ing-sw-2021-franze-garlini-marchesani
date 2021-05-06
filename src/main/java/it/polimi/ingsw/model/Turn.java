package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Turn {
    private Game gameSession;
    private ArrayList<ClientHandler> players;
    private ClientHandler activePlayer;
    private boolean ongoing;
    private boolean endGame;

    public Turn(){
        players = new ArrayList<ClientHandler>();
        ongoing=false;
        endGame=false;
    }

    public void startGame() {
        try {
            if(players.size()==1) gameSession = new SinglePlayerGame();
            else gameSession = new Game();
            ongoing=true;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        //TODO: Give 4 leader cards per player and let them choose 2 cards
        ArrayList<Player> playersList = gameSession.getPlayersList();
        for(Player p: playersList) {
            ArrayList<LeaderCard> drawnLeaders = drawLeaders();
            //Invocare metodo ClientHandler per far scegliere al player le carte?
        }

    }


    public void drawToken() {
        int endGameCode = ((SinglePlayerGame) gameSession).turnAction();
        if(endGameCode == 0) {
            //No more Development Card in a deck - You lose.
        }
        else if(endGameCode == 1) {
            //Lorenzo reached the last faith space - You lose.
        }
        else if(endGameCode == 2) {
            //You win - Total score:
        }
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
        productionIncome.remove(ResourceType.ANY);

        //Getting the activePlayer associated to the active client handler
        Player activeModelPlayer = gameSession.getPlayersList().get(players.indexOf(activePlayer));

        //Increasing faith spaces
        int position = activeModelPlayer.increaseFaith(productionIncome.get(ResourceType.FAITH));
        productionIncome.remove(ResourceType.FAITH);


        activeModelPlayer.storeResources(productionIncome);

        if(gameSession.updateFaithTrack(position)) {
            //endGame(activeModelPlayer);
        }


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


}
