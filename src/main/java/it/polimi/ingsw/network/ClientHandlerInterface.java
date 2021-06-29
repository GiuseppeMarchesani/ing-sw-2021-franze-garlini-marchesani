package it.polimi.ingsw.network;

import it.polimi.ingsw.messages.ClientMessage;
import it.polimi.ingsw.messages.GeneralMessage;
import it.polimi.ingsw.messages.LoginRequest;
import it.polimi.ingsw.messages.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public interface ClientHandlerInterface {

        /**
         *Sends a message to the client
         * @param message the message to send
         */
        public void sendMessage(GeneralMessage message);

        /**
         *Disconnect the socket from the server.
         */
        public void disconnect();



}
