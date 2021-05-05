package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.AnswerMsg;
import it.polimi.ingsw.messages.CommandMsg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable
{
        private Socket client;
        private ObjectOutputStream output;
        private ObjectInputStream input;
        private Turn turnHandler;

        ClientHandler(Socket client)
        {
            this.client = client;
        }

        @Override
        public void run()
        {
            try {
                output = new ObjectOutputStream(client.getOutputStream());
                input = new ObjectInputStream(client.getInputStream());
            } catch (IOException e) {
                System.out.println("Connection to " + client.getInetAddress()+ " FAILED.");
                return;
            }

            System.out.println("Connected to " + client.getInetAddress());

            try {
                handleMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        private void handleMessage() throws IOException
        {
            try {
                    Object next = input.readObject();
                    CommandMsg command = (CommandMsg)next;
                    command.processMessage(this);

            } catch (ClassNotFoundException | ClassCastException e) {
                System.out.println("invalid stream from client");
            }
        }



    public void setTurnHandler(Turn turnHandler) {
        this.turnHandler = turnHandler;
    }

    public Turn getTurnHandler() {
        return turnHandler;
    }
    public void sendAnswerMessage(AnswerMsg answerMsg) throws IOException
    {
        output.writeObject((Object)answerMsg);
    }
}
