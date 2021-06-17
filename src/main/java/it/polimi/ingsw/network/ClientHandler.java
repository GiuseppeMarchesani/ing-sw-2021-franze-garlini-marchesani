package it.polimi.ingsw.network;

import it.polimi.ingsw.messages.ClientMessage;
import it.polimi.ingsw.messages.GeneralMessage;
import it.polimi.ingsw.messages.LoginRequest;
import it.polimi.ingsw.messages.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//TODO
/**
 *
 */
public class ClientHandler implements Runnable {
    private Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Lobby lobby;
    private String gameId;
    private final Object lockSendMessage;
    private final Object lockHandleMessage;
    private LobbyServer lobbyServer;


    /**
     *
     * @param client
     * @param lobbyServer
     */
    ClientHandler(Socket client, LobbyServer lobbyServer)
        {
            this.client = client;
            this.lobbyServer=lobbyServer;
            lockHandleMessage=new Object();
            lockSendMessage=new Object();
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
                disconnect();
            }

    }

    //TODO
    /**
     *
     * @throws IOException
     */
    private void handleMessage() throws IOException
    {
        try {

                while (!Thread.currentThread().isInterrupted()) {
                    synchronized (lockHandleMessage) {
                    ClientMessage message = (ClientMessage) input.readObject();

                    if (message.getMessageType() == MessageType.LOGIN) {
                        LoginRequest loginMsg = (LoginRequest) message;
                        lobby = (lobbyServer.getLobby(loginMsg.getGameId()));
                        gameId=loginMsg.getGameId();
                        lobby.addPlayer(loginMsg.getUsername(), this);
                    } else if (lobby != null) {
                        lobby.getMessage(message);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("invalid stream from client");
            disconnect();
        }
    }

    //TODO
    /**
     *
     * @param message
     */
    public void sendMessage(GeneralMessage message)
    {
       try{
           synchronized (lockSendMessage) {
               output.writeObject((Object) message);
               output.reset();
           }
       }
       catch(IOException e){
           Thread.currentThread().interrupt();
           disconnect();
       }

    }
    //TODO

    /**
     *
     */
    public void disconnect() {
            try {
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.currentThread().interrupt();
            lobbyServer.leaveLobby(gameId, this);
        }
    }


