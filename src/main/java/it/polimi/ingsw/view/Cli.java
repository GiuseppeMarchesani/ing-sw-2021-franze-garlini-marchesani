package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.DevCardSlot;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.observer.ObserverView;

import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * This class implements the Command Line Interface.
 */
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
    public static final String ANSI_WHITE = "\u001b[37;1m";
    public static final String ANSI_BLACK = "\u001B[30;1m";


    /**
     * Cli constructor.
     */
    public Cli() {
        out = System.out;
        commandList=new ArrayList<String>();
        for(Command command: Command.values()) {
            commandList.add(command.getVal());
        }
    }


    /**
     * Reads a line from the standard input.
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
        out.println(ANSI_YELLOW + "\n" +
                "    __   __           _                     __   _____                                                " + "\n" +
                "	|  \\/  |         | |                   / _| |  __ \\    (_)                                         " + "\n" +
                "	| \\  / | __ _ ___| |_ ___ _ __    ___ | |_  | |__) |___ _ _ __   __ _ ___ ___  __ _ _ __   ___ ___ " + "\n" +
                "	| |\\/| |/ _` / __| __/ _ \\ '__|  / _ \\|  _| |  _  // _ \\ | '_ \\ / _` / __/ __|/ _` | '_ \\ / __/ _ \\ " + "\n" +
                "	| |  | | (_| \\__ \\ ||  __/ |    | (_) | |   | | \\ \\  __/ | | | | (_| \\__ \\__ \\ (_| | | | | (_|  __/ " + "\n" +
                "	|_|  |_|\\__,_|___/\\__\\___|_|     \\___/|_|   |_|  \\_\\___|_|_| |_|\\__,_|___/___/\\__,_|_| |_|\\___\\___| " + "\n");
        out.println(ANSI_RESET + "Welcome to Master of Reinassance!");
                askConnect();

    }

    @Override
    public void askConnect() {
        boolean did;
        do {

            try{
                did=false;
                out.print("Insert a valid IP Address: ");
                String ipAddress = readLine();
                out.print("Insert a valid port: ");
                int port = Integer.parseInt(readLine());
                notifyObserver(obs -> obs.updateConnect(ipAddress, port));
            }
            catch (Exception e){
                out.print("Invalid Input. ");
                did=true;
            }

        }while(did);

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
            } catch (Exception e) {
                out.println(STR_WRONG_INPUT);
                playersNumber=0;
            }

        } while(playersNumber>4||playersNumber<=0);
        int finalPlayersNumber = playersNumber;
        notifyObserver(obs -> obs.updatePlayersNumber(finalPlayersNumber));
    }

    @Override
    public void askAction() {
        out.print("\nWhat action do you want to do? ");

        try {
            while(true) {
                String command = readLine();
                if(commandList.contains(command.toUpperCase())) {
                    notifyObserver(obs -> obs.updateAction(commandList.indexOf(command.toUpperCase())));
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
            out.println("\nYou joined game: "+gameID+ " as "+ username);
        }
        else {
            out.println("\nGame "+gameID+ " not available.");
            askLobby();
        }

    }

    @Override
    public void showMessage(String message) {
        out.println("\n" + message);
    }

    @Override
    public void askDevCardToBuy() {
        boolean checkColor = false;
        boolean checkLevel = false;
        String strColor = "";
        Color finalColor = null;
        int level = 0;


        while(!checkColor) {
            out.print("\nPlease insert the color of the chosen Development Card (GREEN/PURPLE/YELLOW/BLUE): ");
            try {
                strColor = readLine();
                for(Color color: Color.values()) {
                    if(color.toString().toUpperCase().equals(strColor.toUpperCase())) {
                        finalColor = color;
                        checkColor = true;
                        break;
                    }
                }
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
            }
        }

        while(!checkLevel) {
            out.print("\nPlease insert the level of the chosen Development Card (1/2/3): ");
            try {
                level = Integer.parseInt(readLine());
                if(level > 0 && level <= 3) {
                    checkLevel = true;
                    break;
                }
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
            }
        }

        Color finalColor1 = finalColor;
        int finalLevel = level;
        notifyObserver(obs -> obs.updateBuyDevCard(finalLevel, finalColor1));
    }

    @Override
    public void askMarketLineToGet(ArrayList<ResourceType> conversion) {
        int num = 0;
        char rowOrCol = '0';
        out.print("\nDo you want to pick a ROW or a COL? ");
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
        out.print("\nWhich one? ");
        try {
            while(true) {
                num = Integer.parseInt(readLine());
                if((rowOrCol=='c' && num > 0 && num < 5) || (rowOrCol=='r' && num > 0 && num < 4)) {
                    break;
                }
                else out.println(STR_WRONG_INPUT);
            }
        } catch (ExecutionException e) {
            out.println(STR_WRONG_INPUT);
        }

        ResourceType chosenConversion = null;
        if(conversion.size()>1) {
            out.print("\nAvailable marble conversions: ");
            for(ResourceType res: conversion) {
                out.print(res.toString() + " ");
            }
            out.print("\nChoose the resource type you want to exchange the white marble with: ");
            try {
                while(true) {
                    String input = readLine();
                    for(ResourceType res: conversion) {
                        if(input.toUpperCase().equals(res.toString())) {
                            chosenConversion = res;
                            break;
                        }
                    }
                    if(chosenConversion != null) {
                        break;
                    }
                    else out.println(STR_WRONG_INPUT);
                }
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
            }
        }
        else if(conversion.size()==1) chosenConversion = conversion.get(0);

        char finalRowOrCol = rowOrCol;
        int finalNum = num -1;
        ResourceType finalChosenConversion = chosenConversion;
        notifyObserver(obs -> obs.updateGetFromMarket(finalRowOrCol, finalNum, finalChosenConversion));
    }

    @Override
    public void askResourceToWarehouse(HashMap<ResourceType, Integer> resToPlace, int numAny, ArrayList<ResourceType> extraDepot) {
        HashMap<ResourceType, Integer> convertedAny = new HashMap<>();
        if(numAny>0) convertedAny = askAnyResource(numAny);
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
            if(resToPlace.keySet().contains(res)) {
                do {
                    out.print("\nHow many " + getAnsiColor(res) + res.toString() + ANSI_RESET + " do you want to place in Leader Depot? (0/1/2) ");
                    invalidInput=false;
                    try {
                        resNumToPut = Integer.parseInt(readLine());
                        if (resNumToPut > resToPlace.get(res) || resNumToPut < 0 || resNumToPut > 2) throw new Exception();
                    } catch (Exception e) {
                        invalidInput = true;
                        out.println(STR_WRONG_INPUT);
                    }
                    if(!invalidInput){
                        leaderDepotQuantity.add(resNumToPut);
                        resToPlace.replace(res, resToPlace.get(res) - resNumToPut);
                        if(resToPlace.get(res)==0) resToPlace.remove(res);
                    }
                } while(invalidInput);
            }
        }

        for(int i=0; i<3; i++) {
                do {
                    out.print("\nWhat resource do you want to put in the " + (3-i) + " slotted depot? ");
                    invalidInput = false;
                    try {
                        strResource = readLine();
                    } catch (ExecutionException e) {
                        out.println("STR_WRONG_INPUT");
                        invalidInput = true;
                    }
                    if(strResource.equalsIgnoreCase("EMPTY")) {
                        floorQuantity.put(i, 0);
                        floorResources.put(i, ResourceType.EMPTY);
                        break;
                    }

                    for(ResourceType res: ResourceType.values()) {
                        if (strResource.toUpperCase().equals(res.toString().toUpperCase()) && !strResource.toUpperCase().equals("ANY") && !strResource.toUpperCase().equals("FAITH") && !strResource.toUpperCase().equals("EMPTY")) {
                            if(resToPlace.containsKey(res)) {
                                out.print("\nHow many? ");
                                try {
                                    resNumToPut = Integer.parseInt(readLine());
                                    if (resNumToPut>resToPlace.get(res)||resNumToPut<=0||resNumToPut>(3-i))throw new Exception();
                                } catch (Exception e) {
                                    out.println(STR_WRONG_INPUT);
                                    invalidInput = true;
                                    break;
                                }
                                discarded += (resToPlace.get(res) - resNumToPut);
                                floorQuantity.put(i, resNumToPut);
                                floorResources.put(i, res);
                                resToPlace.remove(res);
                                invalidInput=false;
                                break;
                            }
                            else  invalidInput = true;
                            break;
                        }
                        invalidInput = true;
                    }
                } while(invalidInput);
            }
        for(ResourceType res: resToPlace.keySet()){
            discarded+=resToPlace.get(res);
        }
        int finalDiscarded = discarded;
        notifyObserver(obs -> obs.updateWarehouse(floorResources, floorQuantity, leaderDepotQuantity, finalDiscarded));
    }

    @Override
    public void askProduction(HashMap<ResourceType, Integer> strongbox, HashMap<ResourceType, Integer> warehouse, HashMap<ResourceType, Integer> price, int anyPayment, int anyProduce) {
        out.println("\nConverting ANY Resource in production cost...");
        HashMap<ResourceType, Integer> anyToPay = askAnyResource(anyPayment);
        boolean invalidInput = false;

        //Payment
        int resNumToGet = 0;
        HashMap<ResourceType, Integer> paymentWarehouse = new HashMap<>();
        HashMap<ResourceType, Integer> newStrongbox = new HashMap<>(strongbox);


        //Putting ANY resources converted in price
        if(anyToPay != null) {
            for (ResourceType res : anyToPay.keySet()) {
                if (price.get(res) != null) {
                    price.replace(res, price.get(res) + anyToPay.get(res));
                } else {
                    price.put(res, anyToPay.get(res));
                }
            }
        }
        if(!strongbox.isEmpty()) {
            for (ResourceType res : price.keySet()) {
                out.println("\nYou must pay " + price.get(res) + " " + getAnsiColor(res) + res.toString() + ANSI_RESET + ".");
                if (newStrongbox.keySet().contains(res) && newStrongbox.get(res) > 0) {
                    do {
                        invalidInput=false;
                        out.print("Found " + newStrongbox.get(res) + " in strongbox. How many of it you want to use for payment?: ");
                        try {
                            resNumToGet = 0;
                            resNumToGet = Integer.parseInt(readLine());
                        } catch (ExecutionException e) {
                            invalidInput = true;
                        }
                        if(!invalidInput) {
                            if (resNumToGet < 0 || resNumToGet > price.get(res) || resNumToGet > newStrongbox.get(res) || price.get(res) > (resNumToGet + (warehouse.get(res)!=null ? warehouse.get(res) : 0)))
                                invalidInput = true;
                            else invalidInput = false;
                            if (!invalidInput) {
                                newStrongbox.replace(res, newStrongbox.get(res) - resNumToGet);
                                if (newStrongbox.get(res) == 0) newStrongbox.remove(res);
                            }
                        }
                        if(invalidInput) out.println("\nInvalid input or invalid payment.");
                    } while (invalidInput);
                }
                paymentWarehouse.put(res, (price.get(res) - resNumToGet));
            }
            out.println("\nAll the other resources will be taken in the warehouse.");
        }
        else{
            for(ResourceType res: price.keySet()){
                paymentWarehouse.put(res, price.get(res));
            }
            out.println("\nAll the resources will be taken in the warehouse");
        }

        out.println("\nConverting ANY resource in production income...");
        HashMap<ResourceType, Integer> anyToIncome = askAnyResource(anyProduce);

        //Putting ANY resource income in strongbox
        for(ResourceType res: anyToIncome.keySet()) {
            if(newStrongbox.get(res) != null) {
                newStrongbox.replace(res, newStrongbox.get(res) + anyToIncome.get(res));
            } else {
                newStrongbox.put(res, anyToIncome.get(res));
            }
        }
        notifyObserver(obs -> obs.updateGetProdRes(newStrongbox, paymentWarehouse));
    }

    /**
     * This method is used for the Any Resource conversion.
     * @return the resources converted from Any.
     */
    private HashMap<ResourceType, Integer> askAnyResource(int numAny) {
        int i=0;
        String strResource = null;
        boolean invalidInput = false;
        HashMap<ResourceType, Integer> convertedAny = new HashMap<>();
        boolean validStr = false;

        out.println("\nYou have " + numAny + " ANY to be converted");
        while(i < numAny) {
            out.print("Please choose the resource to be converted with ANY: (COIN/STONE/SHIELD/SERVANT): ");
            try {
                strResource = readLine();
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
                invalidInput = true;
            }
            if(!invalidInput) {
                for(ResourceType res: ResourceType.values()) {
                    if(strResource.toUpperCase().equals(res.toString().toUpperCase()) && !(strResource.toUpperCase().equals("ANY")) && !(strResource.toUpperCase().equals("FAITH")) && !(strResource.toUpperCase().equals("EMPTY"))) {
                        if(convertedAny.get(res) != null) {
                            convertedAny.replace(res, convertedAny.get(res)+1);
                        } else {
                            convertedAny.put(res, 1);
                        }
                        validStr = true;
                        i++;
                        break;
                    }
                }
                if(validStr=false) {
                    out.println("\n" + STR_WRONG_INPUT);
                }
            }
        }
        return convertedAny;
    }

    @Override
    public void showMarket(ResourceType[][] market, ResourceType corner) {
        out.println("\nThis is the marble market:");
        out.println("               " + getAnsiColor(corner) + "@");
        for(int i=0; i<3; i++) {
            for(int j=0; j<4; j++) {
                ResourceType res = market[j][i];
                out.print(getAnsiColor(res) + " @ " + ANSI_RESET);
            }
            out.println("\n");
        }

    }

    @Override
    public void showDevMarket(ArrayList<DevCard> availableCards, ArrayList<Integer> remainingCards) {
        int counter = 0;
        for(int i: remainingCards) {
            out.println("\n" + ANSI_BLUE +"Cards in the stack: (" + (i) + "/4)" + ANSI_RESET + "\n");
            if(i>0) {
                out.println(availableCards.get(counter).toString());
                out.println("");
                counter++;
            }
        }
    }

    @Override
    public void showStrongbox(HashMap<ResourceType, Integer> strongbox, String username) {
        String ansiColor = null;
        out.println("\n" + username + "'s strongbox:");
        if(strongbox.keySet().isEmpty()) out.println("No resources in strongbox.");
        else {
            for (ResourceType res : strongbox.keySet()) {
                out.println(getAnsiColor(res) + res.toString() + ANSI_RESET + ": " + strongbox.get(res));
            }
        }
    }

    @Override
    public void showWarehouse(HashMap<Integer, Integer> depotToQuantity, HashMap<Integer, ResourceType> depotToResource, String username) {
        String ansiColor = null;
        out.println("\n" + username + "'s warehouse:");
        for(Integer i: depotToQuantity.keySet()) {
            if(i<3) out.println(getAnsiColor(depotToResource.get(i)) + depotToResource.get(i).toString() + ANSI_RESET + ": (" + depotToQuantity.get(i).toString() + "/" + (3-i)  + ")");
            else{
                out.println("\nLeader Depot:");
                out.println(getAnsiColor(depotToResource.get(i)) + depotToResource.get(i).toString() + ANSI_RESET + ": (" + depotToQuantity.get(i).toString() + "/2)");
            }
        }
    }

    @Override
    public void askCardsToActivateProd(ArrayList<DevCard> devCardList) {
        ArrayList<DevCard> chosenCards = new ArrayList<>();
        String ids = "";
        boolean checkId = false;
        int idCounter;
        String[] stringIdList;
        int[] intIdList;

        out.println("\nAvailable cards for production: \n");
        for(DevCard devCard: devCardList) {
            out.println(devCard.toString());
        }

        out.print("\nInsert the id of the cards you want to activate: (ex. 11 9 23) ");

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
                for (DevCard devCard : devCardList) {
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
        notifyObserver(obs -> obs.updateChosenProdCards(chosenCards));
    }

    /**
     * Shows the player an amount of generic resources.
     * @param resources the amount of resources to be shown.
     */
    private void showResources(HashMap<ResourceType, Integer> resources) {
        for(ResourceType res: resources.keySet()) {
            out.println(getAnsiColor(res) + res.toString() + ANSI_RESET + ": " + resources.get(res));
        }
    }

    @Override
    public void showErrorMsg(String message) {
        out.println("\n" + message);
    }

    @Override
    public void showFaithTrack( boolean wasZoneActivated, int whichZone) {
        if(wasZoneActivated) out.println("\nFaithZone " + whichZone + " has been activated.");
        else out.println("\nNo new FaithZone was activated.");
    }

    @Override
    public void showCurrentVP(HashMap<String, Integer> victoryPoints) {
        out.println("\nCurrent Victory Points:");
        for(String username: victoryPoints.keySet()) {
            out.println(username + ": " + victoryPoints.get(username));
        }
    }

    @Override
    public void showSlots(DevCardSlot devCardSlot, String username) {
        int slotNumber = 0;
        for(int i=0; i<devCardSlot.getSlotDev().size(); i++) {
            slotNumber = i+1;
            out.println("\n" + ANSI_BLUE + "Slot #" + slotNumber + ANSI_RESET);
            for(int j=0; j<devCardSlot.getSlotDev().get(i).size(); j++) {
                out.println(devCardSlot.getSlotDev().get(i).get(j).toString());
                out.println("\n");
            }
        }
    }
    @Override
    public void showPlayerFaith(ArrayList<Integer> faith){
        out.println("Your Faith: "+ faith.get(0));
        if(faith.size()>1){
            out.println("Lorenzo's Faith: "+ faith.get(1));
        }
    }


    @Override
    public void showPlayedLeaderCards(ArrayList<LeaderCard> leaderCards, String username){
        if(leaderCards!=null && leaderCards.size()>0) out.println("\nPlayer " + username + " has played a Leader Card. ");
        for(LeaderCard leader: leaderCards) {
            out.println(leader.toString());
        }

    }
    @Override
    public void askSlot(HashMap<ResourceType, Integer> strongbox, HashMap<ResourceType, Integer> warehouse, HashMap<ResourceType, Integer> cardCost, int numAny, ArrayList<Integer> availableSlots) {
        //Not ANY-resources payment
        boolean invalidInput=false;
        int resNumToGet = 0;
        HashMap<ResourceType, Integer> paymentWarehouse = new HashMap<>();
        HashMap<ResourceType, Integer> newStrongbox = new HashMap<>(strongbox);

        if(!strongbox.isEmpty()) {
            for (ResourceType res : cardCost.keySet()) {
                out.println("\nYou must pay " + cardCost.get(res) + " " + getAnsiColor(res) + res.toString() + ANSI_RESET + ".");
                if (newStrongbox.keySet().contains(res) && newStrongbox.get(res) > 0) {
                    do {
                        invalidInput=false;
                        out.print("Found " + newStrongbox.get(res) + " in strongbox. How many of it you want to use for payment?: ");
                        try {
                            resNumToGet = 0;
                            resNumToGet = Integer.parseInt(readLine());
                        } catch (ExecutionException e) {
                            invalidInput = true;
                        }
                        if(!invalidInput) {
                            if (resNumToGet < 0 || resNumToGet > cardCost.get(res) || resNumToGet > newStrongbox.get(res) || cardCost.get(res) > (resNumToGet + (warehouse.get(res)!=null ? warehouse.get(res) : 0)))
                                invalidInput = true;
                            else invalidInput = false;
                            if (!invalidInput) {
                                newStrongbox.replace(res, newStrongbox.get(res) - resNumToGet);
                                if (newStrongbox.get(res) == 0) newStrongbox.remove(res);
                            }
                        }
                        if(invalidInput) out.println("\nInvalid input or invalid payment.");
                    } while (invalidInput);
                }
                paymentWarehouse.put(res, (cardCost.get(res) - resNumToGet));
            }
            out.println("\nAll the other resources will be taken in the warehouse.");
        }
        else{
            for(ResourceType res: cardCost.keySet()){
                paymentWarehouse.put(res, cardCost.get(res));
            }
            out.println("\nAll the resources will be taken in the warehouse");
        }


        //ANY-resource payment
        HashMap<ResourceType, Integer> anyToPay = askAnyResource(numAny);

        if(!strongbox.isEmpty()) {
            for (ResourceType res : anyToPay.keySet()) {
                out.println("\nYou must pay " + anyToPay.get(res) + " " + getAnsiColor(res) + res.toString() + ANSI_RESET + ".");
                if (newStrongbox.keySet().contains(res) && newStrongbox.get(res) > 0) {
                    do {
                        invalidInput=false;
                        out.print("Found " + newStrongbox.get(res) + " in strongbox. How many of it you want to use for payment?: ");
                        try {
                            resNumToGet = 0;
                            resNumToGet = Integer.parseInt(readLine());
                        } catch (ExecutionException e) {
                            invalidInput=true;
                        }
                        if(!invalidInput) {
                            if (resNumToGet < 0 || resNumToGet > anyToPay.get(res) || resNumToGet > newStrongbox.get(res) || anyToPay.get(res) > (resNumToGet + (warehouse.get(res)!=null ? warehouse.get(res) : 0)))
                                invalidInput = true;
                            else invalidInput = false;
                            if (!invalidInput) {
                                newStrongbox.replace(res, newStrongbox.get(res) - resNumToGet);
                                if (newStrongbox.get(res) == 0) newStrongbox.remove(res);
                            }
                        }
                        if(invalidInput) out.println("\nInvalid input or invalid payment.");
                    } while(invalidInput);
                }
                paymentWarehouse.put(res, (anyToPay.get(res) - resNumToGet));
            }
            out.println("\nAll the other resources will be taken in the warehouse.");
        }
        else{
            for(ResourceType res: anyToPay.keySet()){
                paymentWarehouse.put(res, anyToPay.get(res));
            }
            out.println("\nAll the resources will be taken in the warehouse");
        }


        //Asking slot
        int chosenSlot = -1;
        boolean checkSlot = false;

        while(!checkSlot) {
            out.println("\nAvailable slot: ");
            for(int i: availableSlots) {
                out.println("Slot #" + (i+1));
            }
            out.print("\nChoose the slot among those available: ");
            try {
                chosenSlot = Integer.parseInt(readLine());
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
            }
            if(availableSlots.contains(chosenSlot-1)) {
                checkSlot = true;
                int finalChosenSlot = chosenSlot;
                notifyObserver(obs -> obs.updatePlaceDevCard(paymentWarehouse, newStrongbox, finalChosenSlot -1));
            }
        }
    }

    @Override
    public void askLeaderCardToPlay(ArrayList<LeaderCard> leaderCards) {
        LeaderCard chosenLeader = null;
        int id = -1;
        boolean checkId =true;

        do {
            if(!checkId) out.println(STR_WRONG_INPUT);
            checkId=false;
            out.println("\nChoose between one of these Leader Card by typing its id.\n");
            for(LeaderCard leader: leaderCards) {
                out.println(leader.toString());
            }
            out.print("Choose card: ");
            try {
                id = Integer.parseInt(readLine());
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
            }

            for(LeaderCard leaderCard: leaderCards) {
                if(leaderCard.getId() == id) {
                    checkId = true;
                    chosenLeader = leaderCard;
                    break;
                }
            }
        }while(!checkId);

        out.print("\nDo you want to play or discard it? (PLAY/DISCARD): ");
        char action = '0';
        try {
            while(true) {
                String input = readLine();
                if(input.toUpperCase().equals("PLAY")) {
                    action = 'p';
                    break;
                }
                else if(input.toUpperCase().equals("DISCARD")) {
                    action = 'd';
                    break;
                }
                else out.println(STR_WRONG_INPUT);
            }
        } catch (ExecutionException e) {
            out.println(STR_WRONG_INPUT);
        }

        LeaderCard finalChosenLeader = chosenLeader;
        char finalAction = action;
        notifyObserver(obs -> obs.updatePlayLeaderCard(finalChosenLeader, finalAction));
    }

    @Override
    public void askLeaderCardToKeep(ArrayList<LeaderCard> leaderCards) {
        int id = -1;
        boolean checkId = false;
        int i=0;
        out.println("\nChoose two of these Leader Card to discard by typing their Id, one at a time.\n");
        while(i<2) {

            for(LeaderCard leader: leaderCards) {
                out.println(leader.toString() + "\n");
            }
            out.print("\nChoose card No. "+ (i+1) + ": ");
            try {
                id = Integer.parseInt(readLine());
            } catch (ExecutionException e) {
                id=-1;
            }

            for(LeaderCard leaderCard: leaderCards) {
                if(leaderCard.getId() == id) {
                    leaderCards.remove(leaderCard);
                    i++;
                    checkId=true;
                    break;
                }
            }
            if(!checkId){
                out.println(STR_WRONG_INPUT);
            }
            checkId=false;
        }
        notifyObserver(obs -> obs.updateDiscardLeader(leaderCards));
    }

    @Override
    public void showWinMessage(LinkedHashMap<String, Integer> finalPoints) {
        ArrayList<String> users = new ArrayList<>(finalPoints.keySet());
        //Printing results
        out.println("\nFinal results.");
        for(String s: users ) {

                    out.println(s + "'s Victory Points: " + finalPoints.get(s) + (s.equals(users.get(0)) ? " - WINNER." : ""));

        }

        out.print("\nPress Enter key to exit.");
        try {
            int key = System.in.read();
            notifyObserver(ObserverView::updateDisconnect);
        } catch(Exception e)
        {

        }
    }

    @Override
    public void showLoseMessage() {
        out.println("\nGame Over. You lose!");
        out.print("\nPress Enter key to exit.");
        try {
            int key = System.in.read();
            System.exit(0);
        } catch(Exception e)
        {

        }
    }

    @Override
    public void showLeaderCards(HashMap<LeaderCard, Boolean> leaderCards) {
        out.println("\n");
        for(LeaderCard leader: leaderCards.keySet()) {
            out.println(leader.toString());
            if(leaderCards.get(leader)) out.println(ANSI_RED + "activated" + ANSI_RESET+"\n\n");
            else out.println(ANSI_YELLOW + "not activated" + ANSI_RESET + "\n\n");
        }
        if(leaderCards.isEmpty()) out.println("\nYou have no leader cards!");
    }

    /**
     * Returns the Ansi Color associated to a ResourceType.
     * @param resourceType the resource type associated to an ansi color.
     * @return the ansi color.
     */
    private String getAnsiColor(ResourceType resourceType) {
        if(resourceType.equals(ResourceType.COIN)) return ANSI_YELLOW;
        else if(resourceType.equals(ResourceType.SERVANT)) return ANSI_PURPLE;
        else if(resourceType.equals(ResourceType.SHIELD)) return ANSI_BLUE;
        else if(resourceType.equals(ResourceType.STONE)) return ANSI_BLACK;
        else if(resourceType.equals(ResourceType.FAITH)) return ANSI_RED;
        else if(resourceType.equals(ResourceType.EMPTY)) return ANSI_WHITE;
        else return ANSI_RESET;
    }


    @Override
    public void showPlayerList(ArrayList<String> players){
        String s;
        while(true){
            out.println("Insert a player's username. The following users are available: ");
            for(String x: players){
                out.println(x);
            }
            try {
                s=readLine();
                if(players.contains(s)){
                    String finalS = s;
                    notifyObserver(obs -> obs.updateShowPlayer(finalS));
                    break;
                }
                else out.println(STR_WRONG_INPUT);
            } catch (ExecutionException e) {
                out.println(STR_WRONG_INPUT);
            }

        }

    }
    @Override
    public void showPlayer(String username, int faithSpace, HashMap<Integer, ResourceType> depotToResource, HashMap<Integer, Integer> depotToQuantity, HashMap<ResourceType, Integer> strongbox, DevCardSlot devCardSlot, ArrayList<LeaderCard> playedLeaderCards, int remainingLeaderCards, String user){
        out.println("\nShowing player " + username);
        out.println("\nTheir faith is: " + faithSpace);
        showWarehouse(depotToQuantity, depotToResource, username);
        showStrongbox(strongbox, username);
        showSlots(devCardSlot,username);
        showPlayedLeaderCards(playedLeaderCards,username);
        out.println("\nThey have " + remainingLeaderCards+" leader cards left.");
        if(!(username.equals(user))){


            out.println("\nPress Enter to show your data and continue...");
            try {
                String s=readLine();
            } catch (ExecutionException e) {
            }
            notifyObserver(obs -> obs.updateShowPlayer(user));
        }

    }
}
