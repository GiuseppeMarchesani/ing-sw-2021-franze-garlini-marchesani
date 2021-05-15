package it.polimi.ingsw.network;

import it.polimi.ingsw.messages.AnswerMsg;
import it.polimi.ingsw.messages.CommandMsg;
import it.polimi.ingsw.controller.Turn;
import it.polimi.ingsw.messages.GeneralMessage;
import it.polimi.ingsw.messages.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable
{
        private Socket client;
        private ObjectOutputStream output;
        private ObjectInputStream input;
        private String lobby;
        private LobbyServer lobbyServer;
        ClientHandler(Socket client, LobbyServer lobbyServer)
        {
            this.client = client;
            this.lobbyServer=lobbyServer;
            try {
                output = new ObjectOutputStream(client.getOutputStream());
                input = new ObjectInputStream(client.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run()
        {
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
                while(true) {
                    GeneralMessage message = (GeneralMessage) input.readObject();

                    if(message.getMessageType()== MessageType.LOGIN) {
                      lobbyServer.joinLobby(message.getGameID(), message.getUsername(), this);
                    }
                }
            } catch (ClassNotFoundException | ClassCastException e) {
                System.out.println("invalid stream from client");
            }
        }


    public void sendAnswerMessage(GeneralMessage message) throws IOException
    {
       try{
           output.writeObject((Object)message);
       }
       catch(IOException e){
           e.printStackTrace();
           try {
               if (!client.isClosed()) {
                   client.close();
               }
           } catch (IOException ee) {
               ee.printStackTrace();
           }
           Thread.currentThread().interrupt();
           lobbyServer.leaveLobby(lobby, this);
       }

    }

}
