package it.polimi.ingsw.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BootServer {


    /**
     * Boots the Server.
     * @param args parameters ip and port.
     */
    public static void main(String args[]) {
        int port;
        String address;
        if (args.length < 2) {
            System.out.println("Error: Arguments missing. Using default port 4000 and localhost as default.");
            port=4000;
            address="localhost";
        }

        else {
            port = Integer.parseInt(args[0]);
            address = args[1];
        }
        ServerSocket socket;
        try {
            socket = new ServerSocket(port,100, InetAddress.getByName(address));
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            e.printStackTrace();
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
