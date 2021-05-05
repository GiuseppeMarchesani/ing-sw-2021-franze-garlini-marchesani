package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.AnswerMsg;
import it.polimi.ingsw.messages.CommandMsg;
import it.polimi.ingsw.messages.SessionAnswerMsg;
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
        if (args.length < 2) return;

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        ObjectOutputStream output;
        ObjectInputStream input;

        Scanner scanner = new Scanner(System.in);

        Socket server;
        try {
            server = new Socket(hostname, port);
            output = new ObjectOutputStream(server.getOutputStream());
            input = new ObjectInputStream(server.getInputStream());
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }

        System.out.println("Connected");
        AnswerMsg answer;
        System.out.println("Inserisci il numero di giocatori e l'id della partita composto da soli caratteri. (Esempio: 4GAMES");
        String gameId = scanner.nextLine();
        try {
                output.writeObject((Object) new SessionMsg(gameId));
                while(true) {
                    Object next = input.readObject();
                    answer = (AnswerMsg) next;
                    answer.processMessage(output);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}