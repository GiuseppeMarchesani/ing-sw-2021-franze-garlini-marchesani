package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.PhaseTurn;
import it.polimi.ingsw.model.enumeration.TurnState;
import it.polimi.ingsw.network.ClientHandler;

import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Turn {

    private HashMap<String, Boolean> activePlayer;
    private View view;
    private String playingPlayer;
    private boolean ongoing;
    private boolean endGame;
    private TurnState turnState;
    private GameController gameController;
    private ArrayList<GameController> gameList;
    private PhaseTurn phaseTurn;

    public Turn(GameController gameController){
        ongoing=false;
        endGame=false;
        this.gameController=gameController;
        playingPlayer =  gameController.getPlayers().get(0);
        gameList = GameList.getInstance().getActiveGames();
        turnState= TurnState.LOGIN;
        phaseTurn = PhaseTurn.ALL_ACTION;
        for(int i=0; i<gameController.getPlayers().size(); i++){
            activePlayer.put(gameController.getPlayers().get(i), true);
        }
    }
/*
    public void whichTurnState(GeneralMessage msg){
        switch (){
            case YOUR_TURN:
                login((LoginMsg) msg);
            case CREATE_GAME:
                createGame((NewGameRequest) msg);
            case IN_GAME:
                inGame(msg);
            case END_GAME:
                endGameMethod(msg);

        }

    }

 */
/*
    private void login(LoginMsg msg){
        if(gameController(msg.getGameID()) && !this.turnState.equals(TurnState.IN_GAME)){
            gameList.get(msg.getGameID()).getGameSession().getPlayersList().add(new Player(msg.getUsername()));


            gameController.getGameSession().getPlayersList().add(new Player(msg.getUsername()));
        }
        else {
           // TODO: view.askGameCreation();
        }

    }

 */
/*
    private void createGame(NewGameRequest msg){
        if(!gameList.containsKey(msg.getGameID())){
            gameList.put(msg.getGameID(), new Turn());
            if(msg.getNumOfPlayers()>1){
                gameController=new GameController();
                 new Game();
                gameController.setMaxPlayers(msg.getNumOfPlayers());
            }
            else if(msg.getNumOfPlayers()==1){
                gameController = new GameController('s');
                gameController.setMaxPlayers(msg.getNumOfPlayers());
            }
            else {
                //messaggio di errore
            }


        }
        else{

        }
    }

 */

    private void inGame(GeneralMessage msg){
        switch (phaseTurn) {
            case ALL_ACTION:
                allAction(msg);
            case END_TURN:
                endTurn(msg);

        }

    }

    private void allAction(GeneralMessage msg){
        gameController.getMessage(msg);
    }

    private void endTurn(GeneralMessage msg){
        if(gameController.getGameSession().getPlayersList().size() == 1){

        }
    }

    private void endGameMethod(GeneralMessage msg){

    }


    public void startGame() {
        ongoing=true;


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
        gameController.getPlayers().add(client);
    }

    public ArrayList<LeaderCard> drawLeaders(){
        ArrayList<LeaderCard> cards=new ArrayList<LeaderCard>();
        for(int i=0; i<4;i++){
            cards.add(gameController.getGameSession().drawCard());
        }
        return cards;
    }

    public void  proxPlayer(){
        int player = gameController.getPlayers().indexOf(playingPlayer);
        if(player +1 < gameController.getGameSession().getPlayersList().size()){
            player = player +1;
        }
        else {
            player = 0;
        }
        playingPlayer = gameController.getPlayers().get(player);
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
        return gameController.getPlayers();
    }

    public void setPhaseTurn(PhaseTurn phaseTurn) {
        this.phaseTurn = phaseTurn;
    }
    public void disconnect(String username){
        activePlayer.put(username,false);
    }
    //TODO: Testing if it works
    public boolean hasInactivePlayers(){
        return(inactivePlayers()!=null);
    }
    public Map<String, Boolean> inactivePlayers(){
        return activePlayer.entrySet().stream().filter(entry -> (!entry.getValue()))
                .collect(Collectors.toMap(entry-> entry.getKey(), entry -> entry.getValue()));
    }
}

