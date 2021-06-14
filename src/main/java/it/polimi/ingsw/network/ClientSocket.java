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

public class ClientSocket extends Observable {
    private Socket client;

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ExecutorService queue;

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

    public void sendMessage(ClientMessage message) {
        try {
            output.writeObject(message);
            output.reset();
        } catch (IOException e) {
            disconnect();
        }
    }
    public void listen() {
        queue.execute(() ->
        {
            while (!queue.isShutdown()) {
                ServerMessage message;
                try {
                    message = (ServerMessage) input.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                    message = new StringMessage("Connection lost.");
                    disconnect();
                }
                notifyObserver(message);
            }
        });
    }
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
