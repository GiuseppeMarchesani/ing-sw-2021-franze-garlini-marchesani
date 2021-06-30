package it.polimi.ingsw.messages;

/**
 * This interface is provided for all messages sent by the client
 */
public abstract class ClientMessage extends GeneralMessage{
        private String username;

        public ClientMessage(String username, MessageType msg){
            super(msg);
            this.username=username;


        }

    public String getUsername() {
        return username;
    }


}

