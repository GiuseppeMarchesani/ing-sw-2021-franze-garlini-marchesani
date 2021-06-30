package it.polimi.ingsw.messages;

import java.io.Serializable;
/**
 * This interface is provided for all messages sent by the Server
 */
public class ServerMessage extends GeneralMessage implements Serializable {

        public ServerMessage(MessageType msg){
            super(msg);
        }


    }

