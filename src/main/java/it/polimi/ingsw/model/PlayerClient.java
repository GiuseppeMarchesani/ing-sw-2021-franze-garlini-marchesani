package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.SessionMsg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class PlayerClient {


    public static void main(String[] args) {
        Player playerData;
        ObjectOutputStream output;
        ObjectInputStream input;

        playerData=new Player();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Insert Server IP Address: ");
        String ip = scanner.nextLine();

        Socket server;
        try {
            server = new Socket(ip, TurnServer.SOCKET_PORT);
            output = new ObjectOutputStream(server.getOutputStream());
            input = new ObjectInputStream(server.getInputStream());
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }

        System.out.println("Connected");

        try {
            System.out.println("Inserisci ID della partita:");
            int gameId= scanner.nextInt();
            output.writeObject((Object)new SessionMsg(gameId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}