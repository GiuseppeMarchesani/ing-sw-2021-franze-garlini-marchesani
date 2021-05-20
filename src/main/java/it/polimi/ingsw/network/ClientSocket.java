package it.polimi.ingsw.network;

import it.polimi.ingsw.messages.GeneralMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientSocket {
    private Socket client;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ClientSocket(String address, int port) throws IOException {
        try {
            this.client = new Socket(address, port);
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(GeneralMessage message) {
        try {
            output.writeObject(message);
            output.reset();
        } catch (IOException e) {
            disconnect();
        }
    }
    public void listen() {
        //Constant listening, Thread implementation needed
    }
    public void disconnect(){
        //Close everything
    }

}
