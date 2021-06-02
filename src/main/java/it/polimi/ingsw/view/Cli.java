package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board.Market;
import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.DevCardSlot;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

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
        for(Command command: Command.values()) {
            commandList.add(command.getVal());
        }
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
        out.println("\n" +
                "    __  __           _                     __   _____      _                                          " + "\n" +
                "	|  \\/  |         | |                   / _| |  __ \\    (_)                                         " + "\n" +
                "	| \\  / | __ _ ___| |_ ___ _ __    ___ | |_  | |__) |___ _ _ __   __ _ ___ ___  __ _ _ __   ___ ___ " + "\n" +
                "	| |\\/| |/ _` / __| __/ _ \\ '__|  / _ \\|  _| |  _  // _ \\ | '_ \\ / _` / __/ __|/ _` | '_ \\ / __/ _ \\ " + "\n" +
                "	| |  | | (_| \\__ \\ ||  __/ |    | (_) | |   | | \\ \\  __/ | | | | (_| \\__ \\__ \\ (_| | | | | (_|  __/ " + "\n" +
                "	|_|  |_|\\__,_|___/\\__\\___|_|     \\___/|_|   |_|  \\_\\___|_|_| |_|\\__,_|___/___/\\__,_|_| |_|\\___\\___| " + "\n");
        out.println("Welcome to Master of Reinassance!");
                askConnect();

    }

    @Override
    public void askConnect() {
        do {
            try{
                out.print("Insert a valid IP Address: ");
                String ipAddress = readLine();
                out.print("Insert a valid port: ");
                int port = Integer.parseInt(readLine());
                notifyObserver(obs -> obs.updateConnect(ipAddress, port));
                break;
            }
            catch (ExecutionException e){
                out.print("Invalid Input. ");
            }

        }while(true);
        askLobby();
    }

    @Override
    public void askLobby() {
        try {
            out.print("Enter your username: ");
            String username = readLine();
            out.print("Enter the gameID: ");
            String gameID = readLine();
            notifyObserver(obs -> obs.updateLobby(username, gameID));
        } catch (ExecutionException e) {
            out.println(STR_WRONG_INPUT);

        }
    }



    @Override
    public void askPlayersNumber() {
        int playersNumber;
        do {
            out.print("Enter the number of players who will join the room (1-4): ");
            try {
                playersNumber = Integer.parseInt(readLine());
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
                playersNumber=0;
            }

        } while(playersNumber>4||playersNumber<=0);
        int finalPlayersNumber = playersNumber;
        notifyObserver(obs -> obs.updatePlayersNumber(finalPlayersNumber));
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
    public void showLoginResult(String username,String gameID,boolean wasJoined) {
        if (wasJoined){
            notifyObserver(obs -> obs.updateNewUsername(username));
        }
        else {
            out.println("Game "+gameID+ " not available.");
            askLobby();
        }

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
    public void showLeaderCards(List<LeaderCard> leaderCards, String username) {
        out.println(username);
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
            out.println("Choose among one of these Development Card to buy by typing its id.");
            showDevMarket(devCardList);
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
    public void askMarketLineToGet(ResourceType[][] market, ResourceType corner) {
        //agg conversione marble
        char rowOrCol = '0';
        showMarket(market, corner);
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
    public void askResourceToWarehouse(HashMap<ResourceType, Integer> resToPlace, int numAny, ArrayList<ResourceType> extraDepot) {
        HashMap<ResourceType, Integer> convertedAny = askAnyResource(numAny);
        ArrayList<ResourceType> allResources = new ArrayList<>();
        boolean invalidInput = false;
        String strResource = "";
        int resNumToPut = 0;

        HashMap<Integer, ResourceType> floorResources = new HashMap<>();
        HashMap<Integer, Integer> floorQuantity  = new HashMap<>();
        ArrayList<Integer> leaderDepotQuantity = new ArrayList<>();
        int discarded = 0;


        //Adding resources swapped with Any to the HashMap resToPlace.
        for (ResourceType res : convertedAny.keySet()) {
            allResources.add(res);
            if (resToPlace.get(res) != null) {
                resToPlace.replace(res, resToPlace.get(res) + convertedAny.get(res));
            } else {
                resToPlace.put(res, convertedAny.get(res));
            }
        }

        //Showing the resources to the player.
        showResources(resToPlace);

        //Placing resources in Leader Depot
        for(ResourceType res: extraDepot){
            out.println("How many " + getAnsiColor(res) + res.toString() + ANSI_RESET + " do you want to place in Leader Depot? (0/1/2)");
            try {
                resNumToPut = Integer.parseInt(readLine());
            } catch (ExecutionException e) {
                resNumToPut = 0;
            }
            leaderDepotQuantity.add(resNumToPut);
            discarded += resToPlace.get(res) - resNumToPut;
            resToPlace.put(res, resToPlace.get(res) - resNumToPut);
        }

        int numDepot = 3 + extraDepot.size();
        for(int i=0; i<numDepot; i++) {
            if(i<3) {
                out.println("What resource do you want to put in the " + (3-i) + "slotted depot?");
                do {
                    invalidInput = false;
                    try {
                        strResource = readLine();
                    } catch (ExecutionException e) {
                        out.println(STR_WRONG_INPUT);
                        invalidInput = true;
                    }
                    for(ResourceType res: allResources) {
                        if(invalidInput) {
                            break;
                        }
                        if(res.toString().toUpperCase().equals(strResource.toUpperCase())){
                            out.println("How many?");
                            try {
                                resNumToPut = Integer.parseInt(readLine());
                            } catch (ExecutionException e) {
                                resNumToPut = 0;
                            }
                            if(resToPlace.get(res) < resNumToPut) {
                                resNumToPut = resToPlace.get(res);
                            }
                            discarded += (resToPlace.get(res) - resNumToPut);
                            floorQuantity.put(i, resNumToPut);
                            floorResources.put(i, res);
                            invalidInput = false;
                            break;
                        }
                    }
                } while(invalidInput);
            }
        }
        int finalDiscarded = discarded;
        notifyObserver(obs -> obs.updateWarehouse(floorResources, floorQuantity, leaderDepotQuantity, finalDiscarded));
    }

    @Override
    public void askResourceToStrongbox( int numAny) {
        HashMap<ResourceType, Integer> convertedAny = askAnyResource(numAny);


        notifyObserver(obs -> obs.updateStrongbox(convertedAny));
    }

    /**
     * This method is used for the Any Resource conversion.
     * @return The resources converted from Any.
     */
    private HashMap<ResourceType, Integer> askAnyResource(int numAny) {
        int i=0;
        String strResource = null;
        boolean invalidInput = false;
        HashMap<ResourceType, Integer> convertedAny = new HashMap<>();

        out.println("You have " + numAny + " ANY to be converted");
        while(i < numAny) {
            out.println("Please choose the resource to be converted with ANY: (COIN/STONE/SHIELD/SERVANT): ");
            try {
                strResource = readLine();
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
                invalidInput = true;
            }
            if(!invalidInput) {
                for(ResourceType res: ResourceType.values()) {
                    if(strResource.toUpperCase().equals(res.toString().toUpperCase())) {
                        if(convertedAny.get(res) != null) {
                            convertedAny.replace(res, convertedAny.get(res)+1);
                        } else {
                            convertedAny.put(res, 1);
                        }
                    }
                    else{
                        out.println(STR_WRONG_INPUT);
                        i--;
                    }
                }
                i++;
            }
        }
        return convertedAny;
    }

    @Override
    public void showMarket(ResourceType[][] market, ResourceType corner) {
        out.println("           " + getAnsiColor(corner) + "@");
        for(int i=0; i<3; i++) {
            for(int j=0; j<4; j++) {
                ResourceType res = market[i][j];
                out.print(getAnsiColor(res) + " @ " + ANSI_RESET);
            }
            out.println("\n");
        }

    }

    @Override
    public void showDevMarket(List<DevCard> availableCards) {
        for(DevCard devCard: availableCards) {
            out.println(devCard.toString());
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
    public void showResources(HashMap<ResourceType, Integer> strongbox, Warehouse warehouse, String username) {
        String ansiColor = null;
        out.println("Player: " + username);
        out.println("Warehouse: ");
        for(int i=0; i<warehouse.getDepotList().size(); i++) {
            out.println(getAnsiColor(warehouse.getDepotList().get(i).getResourceType()) + warehouse.getDepotList().get(i).getResourceType().toString() + ANSI_RESET + ": (" + warehouse.getDepotList().get(i).getResourceQuantity() + "/" + warehouse.getDepotList().get(i).getSize() + ")");
        }
        out.println("\nStrongbox: ");
        for(ResourceType res: strongbox.keySet()) {
            out.println(getAnsiColor(res) + res.toString() + ANSI_RESET + ": " + strongbox.get(res));
        }
    }

    public void showResources(HashMap<ResourceType, Integer> strongbox, Warehouse warehouse) {
        String ansiColor = null;
        out.println("Your Warehouse: ");
        for(int i=0; i<warehouse.getDepotList().size(); i++) {
            out.println(getAnsiColor(warehouse.getDepotList().get(i).getResourceType()) + warehouse.getDepotList().get(i).getResourceType().toString() + ANSI_RESET + ": (" + warehouse.getDepotList().get(i).getResourceQuantity() + "/" + warehouse.getDepotList().get(i).getSize() + ")");
        }
        out.println("\nYour Strongbox: ");
        for(ResourceType res: strongbox.keySet()) {
            out.println(getAnsiColor(res) + res.toString() + ANSI_RESET + ": " + strongbox.get(res));
        }
    }

    @Override
    public void showResources(HashMap<ResourceType, Integer> resources) {
        String ansiColor = null;
        for(ResourceType res: resources.keySet()) {
            out.println(getAnsiColor(res) + res.toString() + ANSI_RESET + ": " + resources.get(res));
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
    public void showCurrentVP(int victoryPoints, String username) {
        out.println("Player: " + username);
        out.println("Victory Points: " + victoryPoints);
    }

    @Override
    public void showSlots(DevCardSlot devCardSlot, String username) {
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
    public void askSlot(ArrayList<Integer> availableSlots) {
        int chosenSlot = -1;
        boolean checkSlot = false;

        while(!checkSlot) {
            try {
                chosenSlot = Integer.parseInt(readLine());
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
            }
            if(availableSlots.contains(chosenSlot-1)) {
                checkSlot = true;
                int finalChosenSlot = chosenSlot;
                notifyObserver(obs -> obs.updateAskSlot(finalChosenSlot -1));
            }
        }
    }

    @Override
    public void askChooseMarbleConversion(ArrayList<ResourceType> availableConversions) {
        boolean checkChose = false;
        String chosenResource ="";

        out.println("Choose the resource type you want to exchange the white marble with: ");
        while(!checkChose) {
            try {
                chosenResource = readLine();
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
            }
            for(ResourceType res: availableConversions) {
                if(res.toString().toLowerCase().equals(chosenResource.toLowerCase())) {
                    checkChose = true;
                    notifyObserver(obs -> obs.updateChooseMarbleConv(res));
                    break;
                }
            }

        }
    }

    @Override
    public void askChooseResToPay(HashMap<ResourceType, Integer> strongbox, Warehouse warehouse, ResourceType resource) {
        char destination ='0';
        String ansiColor = null;
        boolean checkChar = false;


        while(!checkChar) {
            out.println("\nChoose were to get " + getAnsiColor(resource) + resource.toString() + ANSI_RESET + ". Type 'w' for warehouse, 's' for strongbox.");
            showResources(strongbox, warehouse);
            try {
                destination = readLine().toLowerCase().charAt(0);
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
            }
            if(destination=='s') {
                checkChar = true;
                notifyObserver(obs -> obs.updateResToPay("strongbox"));
            }
            else if(destination=='w') {
                checkChar = true;
                notifyObserver(obs -> obs.updateResToPay("warehouse"));
            }
        }

    }

    @Override
    public void askChooseOneRes(ArrayList<String> resources, String message) {
        String resource = "";
        boolean checkRes = false;

        out.println(message);
        while(!checkRes) {
            try {
                resource = readLine();
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
            }
            for(String res: resources) {
                if(resource.equals(res)) {
                    checkRes = true;
                    for(ResourceType resourceType: ResourceType.values()) {
                        if(resourceType.toString().equals(resource)) {
                            notifyObserver(obs -> obs.updateChooseOneRes(resourceType));
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void askChooseFloor(Warehouse warehouse, ResourceType resToPlace) {
        boolean checkDepot = false;
        int chosenDepot = -1;

        out.println("Your Warehouse: ");
        for(int i=0; i<warehouse.getDepotList().size(); i++) {
            out.println(getAnsiColor(warehouse.getDepotList().get(i).getResourceType()) + warehouse.getDepotList().get(i).getResourceType().toString() + ANSI_RESET + ": (" + warehouse.getDepotList().get(i).getResourceQuantity() + "/" + warehouse.getDepotList().get(i).getSize() + ")");
        }
        out.println("\nChoose in which depot you want to place " + getAnsiColor(resToPlace) + resToPlace.toString() + ANSI_RESET + ".");
        while(!checkDepot) {
            try {
                chosenDepot = Integer.parseInt(readLine());
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
            }
            if(chosenDepot >= 1 && chosenDepot <= 3 && (warehouse.getDepotList().get(chosenDepot-1).getResourceType().toString().equals(ResourceType.ANY) || warehouse.getDepotList().get(chosenDepot-1).getResourceType().toString().equals(resToPlace.toString())) && warehouse.getDepotList().get(chosenDepot-1).getResourceQuantity() < warehouse.getDepotList().get(chosenDepot-1).getSize()) {
                checkDepot = true;
                int finalChosenDepot = chosenDepot;
                notifyObserver(obs -> obs.updateChooseFloor(finalChosenDepot -1));
            }
        }
    }

    @Override
    public void askRearrange(Warehouse warehouse, HashMap<ResourceType, Integer> resources) {
        String reply = "";
        boolean checkReply = false;
        boolean checkDepot = false;

        out.println("Do you want to rearrange? (Yes/No)");
        while(!checkReply) {
            try {
                reply = readLine();
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
            }
            if(reply.toLowerCase().equals("yes")) {
                checkReply = true;
                int depot1=0, depot2=0;
                while(!checkDepot) {
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

                    if(depot1>=1 && depot1<=3 && depot2>=1 && depot2<=3 && depot1!=depot2) {
                        depot1--;
                        depot2--;
                        int firstDepot = depot1;
                        int secondDepot = depot2;
                        checkDepot = true;
                        notifyObserver(obs -> obs.updateRearrange(true, firstDepot, secondDepot));
                        break;
                    }
                }
            } else if(reply.toLowerCase().equals("no")) {
                checkReply = true;
                notifyObserver(obs -> obs.updateRearrange(false, -1, -1));
                break;
            }
        }
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
    public void askLeaderCardToKeep(ArrayList<LeaderCard> leaderCards) {
        int id = -1;
        boolean checkId = false;
        int i=0;
        out.println("Choose two of these Leader Card to discard by typing their Id, one at a time.");
        while(i!=2) {
            out.println("Choose card No. "+ (i+1));
            for(LeaderCard leader: leaderCards) {
                out.println(leader.getLeaderID());
            }
            try {
                id = Integer.parseInt(readLine());
            } catch (ExecutionException e) {
                id=-1;
            }

            for(LeaderCard leaderCard: leaderCards) {
                if(leaderCard.getLeaderID() == id) {
                    leaderCards.remove(leaderCard);
                    i++;
                    checkId=true;
                    break;
                }
            }
            if(!checkId){
                out.println("Wrong input!");
            }
            checkId=false;
        }
        notifyObserver(obs -> obs.updateDiscardLeader(leaderCards));
    }

    @Override
    public void showWinMessage(String winnerUser) {
        out.println("Game finished: " + winnerUser + " WINS!");
        System.exit(0);
    }

    public String getAnsiColor(ResourceType resourceType) {
        if(resourceType.equals(ResourceType.COIN)) return ANSI_YELLOW;
        else if(resourceType.equals(ResourceType.SERVANT)) return ANSI_PURPLE;
        else if(resourceType.equals(ResourceType.SHIELD)) return ANSI_BLUE;
        else if(resourceType.equals(ResourceType.STONE)) return ANSI_BLACK;
        else if(resourceType.equals(ResourceType.FAITH)) return ANSI_RED;
        else return ANSI_RESET;
    }
}
