package it.polimi.ingsw.messages;

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

