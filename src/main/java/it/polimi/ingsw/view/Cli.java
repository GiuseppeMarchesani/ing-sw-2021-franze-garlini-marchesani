package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Board.Market;
import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.observer.Observer;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Cli extends ObservableView implements View{
    private PrintStream out;
    private Thread inputThread;
    private static final String STR_WRONG_INPUT = "Input error. Try again.";

    /**
     * Cli constructor.
     */
    public Cli() {
        out = System.out;

    }


    /**
     * Reads a line from the starndard input.
     *
     * @return the read string.
     * @throws ExecutionException if the input stream thread is interrupted.
     */
    public String readLine() throws ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(new ReadInput());
        inputThread = new Thread(futureTask);
        inputThread.start();

        String in = null;

        try {
            in = futureTask.get();
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        return in;
    }

    /**
     * Starts the command-line interface.
     */
    public void init() {
        out.println("\n"+
                "    __  __           _                     __   _____      _                                          " + "\n" +
                "	|  \\/  |         | |                   / _| |  __ \\    (_)                                         " + "\n" +
                "	| \\  / | __ _ ___| |_ ___ _ __    ___ | |_  | |__) |___ _ _ __   __ _ ___ ___  __ _ _ __   ___ ___ " + "\n" +
                "	| |\\/| |/ _` / __| __/ _ \\ '__|  / _ \\|  _| |  _  // _ \\ | '_ \\ / _` / __/ __|/ _` | '_ \\ / __/ _ \\ "+ "\n" +
                "	| |  | | (_| \\__ \\ ||  __/ |    | (_) | |   | | \\ \\  __/ | | | | (_| \\__ \\__ \\ (_| | | | | (_|  __/ "+ "\n" +
                "	|_|  |_|\\__,_|___/\\__\\___|_|     \\___/|_|   |_|  \\_\\___|_|_| |_|\\__,_|___/___/\\__,_|_| |_|\\___\\___| "+ "\n");
        out.println("Welcome to Master of Reinassance!");
    }


    @Override
    public void askUsername() {
        out.print("Enter your username: ");
        try {
            String username = readLine();
            notifyObserver(obs -> obs.updateUsername(username));
        } catch (ExecutionException e) {
            out.println(STR_WRONG_INPUT);
        }
    }

    @Override
    public void askGameID() {
        out.print("Enter the gameID: ");
        try {
            int gameID = Integer.parseInt(readLine());
            notifyObserver(obs -> obs.updateGameID(gameID));
        } catch (ExecutionException e) {
            out.println(STR_WRONG_INPUT);
        }
    }

    @Override
    public void askGameCreation() {

    }


    @Override
    public void askPlayersNumber() {
        out.print("Enter the number of players who will join the room: ");
        try {
            int playersNumber = Integer.parseInt(readLine());
            notifyObserver(obs -> obs.updatePlayersNumber(playersNumber));
        } catch (ExecutionException e) {
            out.println(STR_WRONG_INPUT);
        }
    }

    @Override
    public void showLoginResult(boolean usernameAccepted, boolean connectionSuccessful, String username) {
        out.println("Chosen username: " + username);
        if(usernameAccepted) {
            out.println("The username is accepted.");
        }
        else out.println("The username was rejected.");
        if(connectionSuccessful) {
            out.println("The connection succeeded.");
        }
        else out.println("The connection was rejected.");
    }

    @Override
    public void showMessage(String message) {
        out.println(message);
    }

    @Override
    public void showDisconnectionMsg(String disconnectedUser, String message) {
        inputThread.interrupt();
        out.println("\n" + disconnectedUser + message);
        System.exit(1);
    }

    @Override
    public void showMatchInfo(List<String> players, String activePlayer) {
        out.print("\nPlayers: ");
        for(String str: players) {
            out.print(str + " ");
        }
        out.println("\n");
        out.println("Active player: " + activePlayer);
    }

    @Override
    public void showLeaderCards(List<LeaderCard> leaderCards) {
        for(LeaderCard leader: leaderCards) {
            out.println(leader.toString());
        }
    }

    @Override
    public void askChooseLeaderCards(List<LeaderCard> leaderCards) {
        LeaderCard chosenLeader = null;
        int id = -1;
        boolean checkId = false;
        int i=0;

        while(!checkId) {
            if(i>0) out.println(STR_WRONG_INPUT);
            out.println("Choose between one of these Leader Card to discard by typing its id.");
            for(LeaderCard leader: leaderCards) {
                out.println(leader.toString());
            }
            try {
                id = Integer.parseInt(readLine());
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
            }

            for(LeaderCard leaderCard: leaderCards) {
                if(leaderCard.getLeaderID() == id) {
                    checkId = true;
                    chosenLeader = leaderCard;
                    break;
                }
            }
            i++;
        }
        LeaderCard finalChosenLeader = chosenLeader;
        notifyObserver(obs -> obs.updateChooseLeaderCard(finalChosenLeader));


    }

    @Override
    public void askBuyDevCard(List<DevCard> devCardList) {

    }

    @Override
    public void askGetMarketRes() {

    }

    @Override
    public void showMarket(Market market) {

    }

    @Override
    public void askActivateProduction(List<DevCard> devCardList) {

    }

    @Override
    public void showResources(HashMap<ResourceType, Integer> strongbox, Warehouse warehouse) {

    }

    @Override
    public void showErrorMsg(String message) {

    }

    @Override
    public void showFaithTrack(List<Integer> faithTrackState) {

    }

    @Override
    public void showActualVP(int victoryPoints) {

    }

    @Override
    public void askToShowResources() {

    }

    @Override
    public void askToShowFaithTrack() {

    }

    @Override
    public void askToShowActualVP() {

    }

    @Override
    public void chooseResToPay(HashMap<ResourceType, Integer> strongbox, Warehouse warehouse) {

    }

    @Override
    public void askRearrangeDepot() {

    }

    @Override
    public void playLeaderCard(List<LeaderCard> leaderCards) {

    }

    @Override
    public void discardLeaderCard(List<LeaderCard> leaderCards) {

    }

    @Override
    public void showWinMessage(String winnerUser) {
        out.println("Game finished: " + winnerUser + " WINS!");
        System.exit(0);
    }
}
