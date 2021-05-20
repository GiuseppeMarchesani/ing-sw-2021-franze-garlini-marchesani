package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Board.Market;
import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.DevCardSlot;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.observer.Observer;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Cli extends ObservableView implements View{
    private PrintStream out;
    private Thread inputThread;
    private static final String STR_WRONG_INPUT = "Input error. Try again.";
    private List<String> commandList;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK = "\u001B[30m";


    /**
     * Cli constructor.
     */
    public Cli() {
        out = System.out;
    }


    /**
     * Reads a line from the standard input.
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
        for(Command command: Command.values()) {
            commandList.add(command.getVal());
        }

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
        //TODO: askGameCreation()
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
    public void askAction() {
        out.print("What action do you want to do?");

        try {
            while(true) {
                String command = readLine();
                if(commandList.contains(command.toUpperCase())) {
                    notifyObserver(obs -> obs.updateAction(command));
                    break;
                }
                else out.println(STR_WRONG_INPUT);
            }
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
    public void askDevCardToBuy(List<DevCard> devCardList) {
        DevCard chosenDevCard = null;
        int id = -1;
        boolean checkId = false;
        int i=0;

        while(!checkId) {
            if(i>0) out.println(STR_WRONG_INPUT);
            out.println("Choose between one of these Development Card to buy by typing its id.");
            for(DevCard devCard: devCardList) {
                out.println(devCard.toString());
            }
            try {
                id = Integer.parseInt(readLine());
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
            }

            for(DevCard devCard: devCardList) {
                if(devCard.getId() == id) {
                    checkId = true;
                    chosenDevCard = devCard;
                    break;
                }
            }
            i++;
        }
        DevCard finalChosenDevCard = chosenDevCard;
        notifyObserver(obs -> obs.updateBuyDevCard(finalChosenDevCard));
    }

    @Override
    public void askMarketLineToGet() {
        char rowOrCol = '0';

        out.println("Do you want to pick a ROW or a COL?");
        try {
            while(true) {
                String input = readLine();
                if(input.toUpperCase().equals("ROW")) {
                    rowOrCol = 'r';
                    break;
                }
                else if(input.toUpperCase().equals("COL")) {
                    rowOrCol = 'c';
                    break;
                }
                else out.println(STR_WRONG_INPUT);
            }
        } catch (ExecutionException e) {
            out.println(STR_WRONG_INPUT);
        }
        out.println("Which one?");
        try {
            while(true) {
                int num = Integer.parseInt(readLine());
                if((rowOrCol=='c' && num > 0 && num < 5) || (rowOrCol=='r' && num > 0 && num < 4)) {
                    char finalRowOrCol = rowOrCol;
                    notifyObserver(obs -> obs.updateGetMarketRes(finalRowOrCol, num));
                    break;
                }
                else out.println(STR_WRONG_INPUT);
            }
        } catch (ExecutionException e) {
            out.println(STR_WRONG_INPUT);
        }
    }

    @Override
    public void showMarket(Market market) {
        for(int i=0; i<4; i++) {
            for(int j=0; j<3; j++) {
                ResourceType res = market.getMarketTray()[i][j];
                if(res.equals(ResourceType.COIN)) out.print(ANSI_YELLOW + " @ " + ANSI_RESET);
                else if(res.equals(ResourceType.SERVANT)) out.print(ANSI_PURPLE+ " @ " + ANSI_RESET);
                else if(res.equals(ResourceType.FAITH)) out.print(ANSI_RED+ " @ " + ANSI_RESET);
                else if(res.equals(ResourceType.EMPTY)) out.print(ANSI_WHITE+ " @ " + ANSI_RESET);
                else if(res.equals(ResourceType.STONE)) out.print(ANSI_BLACK+ " @ " + ANSI_RESET);
                else if(res.equals(ResourceType.SHIELD)) out.print(ANSI_BLUE+ " @ " + ANSI_RESET);
            }
            out.println("\n");
        }
    }

    @Override
    public void askCardsToActivateProd(List<DevCard> availableCards) {
        List<DevCard> chosenCards = new ArrayList<>();
        String ids = "";
        boolean checkId = false;
        int idCounter;
        String[] stringIdList;
        int[] intIdList;

        for(DevCard devCard: availableCards) {
            out.println(devCard.toString());
        }

        while(!checkId) {
            chosenCards.clear();
            try {
                ids = readLine();
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
            }


            stringIdList = ids.trim().split("\\s+");//remove any leading, trailing white spaces and split the string from rest of the white spaces

            intIdList = new int[stringIdList.length];//create a new int array to store the int values

            for (int i = 0; i < stringIdList.length; i++) {
                intIdList[i] = Integer.parseInt(stringIdList[i]);//parse the integer value and store it in the int array
            }

            idCounter = intIdList.length;

            for (int i = 0; i < intIdList.length; i++) {
                for (DevCard devCard : availableCards) {
                    if (devCard.getId() == intIdList[i]) {
                        idCounter--;
                        chosenCards.add(devCard);
                        break;
                    }
                }
            }
            if (idCounter == 0) {
                checkId = true;
            }
        }
        notifyObserver(obs -> obs.updateActivateProduction(chosenCards));
    }

    @Override
    public void showResources(HashMap<ResourceType, Integer> strongbox, Warehouse warehouse) {
        String ansiColor = null;

        out.println("Warehouse: ");
        for(int i=0; i<warehouse.getDepotList().size(); i++) {
            if(warehouse.getDepotList().get(i).getResourceType().equals(ResourceType.COIN)) ansiColor = ANSI_YELLOW;
            else if(warehouse.getDepotList().get(i).getResourceType().equals(ResourceType.SERVANT)) ansiColor = ANSI_PURPLE;
            else if(warehouse.getDepotList().get(i).getResourceType().equals(ResourceType.SHIELD)) ansiColor = ANSI_BLUE;
            else if(warehouse.getDepotList().get(i).getResourceType().equals(ResourceType.STONE)) ansiColor = ANSI_BLACK;
            out.println(ansiColor + warehouse.getDepotList().get(i).getResourceType().toString() + ANSI_RESET + ": (" + warehouse.getDepotList().get(i).getResourceQuantity() + "/" + warehouse.getDepotList().get(i).getSize() + ")");
        }
        out.println("\nStrongbox: ");
        for(ResourceType res: strongbox.keySet()) {
            if(res.equals(ResourceType.COIN)) ansiColor = ANSI_YELLOW;
            else if(res.equals(ResourceType.SERVANT)) ansiColor = ANSI_PURPLE;
            else if(res.equals(ResourceType.SHIELD)) ansiColor = ANSI_BLUE;
            else if(res.equals(ResourceType.STONE)) ansiColor = ANSI_BLACK;
            out.println(ansiColor + res.toString() + ANSI_RESET + ": " + strongbox.get(res));
        }
    }

    @Override
    public void showErrorMsg(String message) {
        out.println(message);
    }

    @Override
    public void showFaithTrack(HashMap<String, Integer> faithTrackState) {
        for(String username: faithTrackState.keySet()) {
            out.println(username + ": " + faithTrackState.get(username));
        }
    }

    @Override
    public void showCurrentVP(int victoryPoints) {
        out.println("Your current Victory Points: " + victoryPoints);
    }

    @Override
    public void showSlots(DevCardSlot devCardSlot) {
        int slotNumber = 0;
        for(int i=0; i<devCardSlot.getSlotDev().size(); i++) {
            slotNumber = i+1;
            out.println("Slot #" + slotNumber);
            for(int j=0; j<devCardSlot.getSlotDev().get(i).size(); j++) {
                out.println(devCardSlot.getSlotDev().get(i).get(j).toString());
                out.println("\n");
            }
        }
    }

    @Override
    public void askChooseResToPay(HashMap<ResourceType, Integer> strongbox, Warehouse warehouse) {
        int resQuantity = 0;
        String ansiColor = null;
        boolean any = false;
        HashMap<ResourceType, Integer> paymentWarehouse = new HashMap<>();
        paymentWarehouse.put(ResourceType.COIN, 0);
        paymentWarehouse.put(ResourceType.SERVANT, 0);
        paymentWarehouse.put(ResourceType.SHIELD, 0);
        paymentWarehouse.put(ResourceType.STONE, 0);

        showResources(strongbox, warehouse);
        out.println("\nSelecting resources from Warehouse, all the other resources will be taken from the strongbox:");
        for(int i=0; i<warehouse.getDepotList().size(); i++) {
            if(warehouse.getDepotList().get(i).getResourceType().equals(ResourceType.COIN)) ansiColor = ANSI_YELLOW;
            else if(warehouse.getDepotList().get(i).getResourceType().equals(ResourceType.SERVANT)) ansiColor = ANSI_PURPLE;
            else if(warehouse.getDepotList().get(i).getResourceType().equals(ResourceType.SHIELD)) ansiColor = ANSI_BLUE;
            else if(warehouse.getDepotList().get(i).getResourceType().equals(ResourceType.STONE)) ansiColor = ANSI_BLACK;
            else if(warehouse.getDepotList().get(i).getResourceType().equals(ResourceType.ANY)) any = true;
            if(!any) out.println(ansiColor + warehouse.getDepotList().get(i).getResourceType().toString() + ANSI_RESET + ": ");
            try {
                resQuantity = Integer.parseInt(readLine());
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
            }
            paymentWarehouse.put(warehouse.getDepotList().get(i).getResourceType(), resQuantity);
        }
        notifyObserver(obs -> obs.updateResToPay(paymentWarehouse));
    }

    @Override
    public void askDepotToRearrange() {
        int depot1=0, depot2=0;

        out.println("Which depots do you want to swap? (ex. \"1 3\")");
        try {
            depot1 = Integer.parseInt(readLine());
        } catch (ExecutionException e) {
            out.println(STR_WRONG_INPUT);
        }
        try {
            depot2 = Integer.parseInt(readLine());
        } catch (ExecutionException e) {
            out.println(STR_WRONG_INPUT);
        }
        depot1--;
        depot2--;
        int firstDepot = depot1;
        int secondDepot = depot2;
        notifyObserver(obs -> obs.updateRearrange(firstDepot, secondDepot));
    }

    @Override
    public void askLeaderCardToPlay(List<LeaderCard> leaderCards) {
        LeaderCard chosenLeader = null;
        int id = -1;
        boolean checkId = false;
        int i=0;

        while(!checkId) {
            if(i>0) out.println(STR_WRONG_INPUT);
            out.println("Choose between one of these Leader Card to play by typing its id.");
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
        notifyObserver(obs -> obs.updatePlayLeaderCard(finalChosenLeader));
    }

    @Override
    public void askLeaderCardToDiscard(List<LeaderCard> leaderCards) {
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
        notifyObserver(obs -> obs.updateDiscardLeader(finalChosenLeader));
    }

    @Override
    public void showWinMessage(String winnerUser) {
        out.println("Game finished: " + winnerUser + " WINS!");
        System.exit(0);
    }
}
