package it.polimi.ingsw.network;

import it.polimi.ingsw.messages.ClientMessage;
import it.polimi.ingsw.messages.GeneralMessage;
import it.polimi.ingsw.messages.ServerMessage;
import it.polimi.ingsw.messages.StringMessage;
import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *This class implements the connection between the client and the server, client-side
 */
public class ClientSocket extends Observable {
    private Socket client;

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ExecutorService queue;



    /**
     *Default Constructor
     * @param address the ip address to connect to
     * @param port the port to connect to
     * @throws IOException when the player isnt able to connect
     */
    public ClientSocket(String address, int port) throws IOException {
        try {
            this.client = new Socket(address, port);
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
            this.queue = Executors.newSingleThreadExecutor();

        } catch (Exception e) {
            throw new IOException();
        }
    }
    /**
     *Sends a message to the server
     * @param message The message to send
     */
    public void sendMessage(ClientMessage message) {
        try {
            output.writeObject(message);
            output.reset();
        } catch (IOException e) {
            disconnect();
        }
    }


    /**
     * Listens for messages from the server
     */
    public void listen() {
        queue.execute(() ->
        {
            while (!queue.isShutdown()) {
                ServerMessage message;
                try {
                    message = (ServerMessage) input.readObject();
                } catch (Exception e) {
                    message = new StringMessage("Connection lost.");
                    disconnect();
                }
                notifyObserver(message);
            }
        });
    }


    /**
     *Disconnects the client from the server
     */
    public void disconnect(){
            if (!client.isClosed()) {
                queue.shutdownNow();
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

}
