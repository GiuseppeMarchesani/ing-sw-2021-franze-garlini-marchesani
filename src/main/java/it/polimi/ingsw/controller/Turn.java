package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.CommandMsg;
import it.polimi.ingsw.messages.GeneralMessage;
import it.polimi.ingsw.messages.LoginMsg;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.TurnState;
import it.polimi.ingsw.network.ClientHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class Turn {
    private ArrayList<ClientHandler> players;
    private HashMap<ClientHandler, Boolean> activePlayer;
    private ClientHandler playingPlayer;
    private boolean ongoing;
    private boolean endGame;
    private TurnState turnState;
    private GameController gameController;

    public Turn(){
        players = new ArrayList<ClientHandler>();
        ongoing=false;
        endGame=false;
        playingPlayer =  players.get(0);
        turnState= TurnState.LOGIN;
        for(int i=0; i<players.size(); i++){
            activePlayer.put(players.get(i), true);
        }
    }

    public void whichTurnState(CommandMsg msg){
        switch (turnState){
            case LOGIN:
                login((LoginMsg) msg);
            case CREATE_GAME:
                createGame(msg);
            case IN_GAME:
                inGame(msg);
            case END_GAME:
                endGameMethod(msg);

        }

    }

    private void login(LoginMsg msg){

    }

    private void createGame(CommandMsg msg){

    }

    private void inGame(CommandMsg msg){

    }

    private void endGameMethod(CommandMsg msg){

    }


    public void startGame() {
        try {
            if(players.size()==1){
                gameController = new GameController('s');
            }
            else gameController = new GameController('m');
            ongoing=true;
        } catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }

        //TODO: Give 4 leader cards per player and let them choose 2 cards
        ArrayList<Player> playersList = gameController.getGameSession().getPlayersList();
        for(Player p: playersList) {
            ArrayList<LeaderCard> drawnLeaders = drawLeaders();
            //Invocare metodo ClientHandler per far scegliere al player le carte?
        }

    }


    private void drawToken() {
        int endGameCode = ((SinglePlayerGame) gameController.getGameSession()).turnAction();
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

/*
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
 */
    public void addPlayer(ClientHandler client){
        players.add(client);
    }

    public ArrayList<LeaderCard> drawLeaders(){
        ArrayList<LeaderCard> cards=new ArrayList<LeaderCard>();
        for(int i=0; i<4;i++){
            cards.add(gameController.getGameSession().drawCard());
        }
        return cards;
    }

    public void  proxPlayer(){
        int player = players.indexOf(playingPlayer);
        if(player +1 < gameController.getGameSession().getPlayersList().size()){
            player = player +1;
        }
        else {
            player = 0;
        }
        playingPlayer = players.get(player);
    }

    public boolean status(){
        return ongoing;
    }

    public GameController getGameController() {
        return gameController;
    }

    public ClientHandler getActivePlayer() {
        return playingPlayer;
    }

    public void setActivePlayer(ClientHandler activePlayer) {
        this.playingPlayer = activePlayer;
    }

    public ArrayList<ClientHandler> getPlayers() {
        return players;
    }


}

