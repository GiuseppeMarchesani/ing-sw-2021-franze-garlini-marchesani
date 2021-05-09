package it.polimi.ingsw.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TurnServer {
    //RIVEDERE


    TurnServer(){}

    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("Error: Input port missing");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        ServerSocket socket;
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }

        while (true) {
            try {
                Socket client = socket.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }

    }
}
