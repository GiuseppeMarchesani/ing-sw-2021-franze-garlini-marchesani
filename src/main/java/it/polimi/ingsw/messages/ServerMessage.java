package it.polimi.ingsw.messages;

import java.io.Serializable;

public class ServerMessage extends GeneralMessage implements Serializable {

        public ServerMessage(MessageType msg){
            super(msg);
        }


    }

