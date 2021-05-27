package it.polimi.ingsw.network;

import it.polimi.ingsw.messages.GeneralMessage;
import it.polimi.ingsw.messages.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class ClientHandler implements Runnable
{
        private Socket client;
        private ObjectOutputStream output;
        private ObjectInputStream input;
        private String gameId;
        private Lobby lobby;
        private LobbyServer lobbyServer;
        ClientHandler(Socket client, LobbyServer lobbyServer)
        {
            this.client = client;
            this.lobbyServer=lobbyServer;
            lobby=null;
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
                      lobby=(lobbyServer.joinLobby(message.getGameID(), message.getUsername(), this));
                        if(lobby!=null){
                            gameId=(message.getGameID());
                        }
                    }
                    else if(lobby!=null){
                        lobby.getMessage(message);
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
           output.reset();
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
           lobbyServer.leaveLobby(gameId, this);
       }

    }

}
