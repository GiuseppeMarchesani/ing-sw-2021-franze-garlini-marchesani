package it.polimi.ingsw.messages;

import it.polimi.ingsw.network.ClientHandler;

import java.io.IOException;

public abstract class CommandMsg extends NetworkMsg
{
    /**
     * Method invoked in the server to process the message.
     */
    public abstract void processMessage(ClientHandler clientHandler) throws IOException;
}