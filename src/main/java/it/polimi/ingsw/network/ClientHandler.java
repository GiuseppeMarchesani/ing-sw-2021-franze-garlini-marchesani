package it.polimi.ingsw.network;

import it.polimi.ingsw.messages.ClientMessage;
import it.polimi.ingsw.messages.GeneralMessage;
import it.polimi.ingsw.messages.LoginRequest;
import it.polimi.ingsw.messages.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**This class represents the client on the server's side.
 *Handles the received messages from the client.
 * Sends messages sent by GameController to the client.
 */
public class ClientHandler implements Runnable, ClientHandlerInterface {
    private Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Lobby lobby;
    private String gameId;
    private final Object lockSendMessage;
    private final Object lockHandleMessage;
    private LobbyServer lobbyServer;


    /**
     *Default constructor
     * @param client the client socket to send messages to
     * @param lobbyServer The default lobby server
     */
    public ClientHandler(Socket client, LobbyServer lobbyServer)
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

    /**
     * Execute thread service of reading messages
     */
        @Override
        public void run()
        {
            System.out.println("Connected to " + client.getInetAddress());

                handleMessage();

    }

    /**
     *Handles a message sent from the client.
     */
    private void handleMessage()
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

    /**
     *Sends a message to the client
     * @param message the message to send
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

    /**
     *Disconnect the socket from the server.
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


