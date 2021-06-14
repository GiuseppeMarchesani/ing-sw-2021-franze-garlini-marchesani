package it.polimi.ingsw.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BootServer {
    //RIVEDERE


    BootServer(){}

    public static void main(String args[]) {
        int port;
        if (args.length < 1) {
            System.out.println("Error: Input port missing. Using default port 4000.");
            port=4000;
        }
        else {
            port = Integer.parseInt(args[0]);
        }
        ServerSocket socket;
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }
        LobbyServer lobbyServer= new LobbyServer();
        while (true) {
            try {
                Socket client = socket.accept();
                ClientHandler clientHandler = new ClientHandler(client, lobbyServer);
                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }

    }
}
