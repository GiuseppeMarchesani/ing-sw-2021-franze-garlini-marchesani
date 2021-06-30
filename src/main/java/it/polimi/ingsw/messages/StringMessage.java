package it.polimi.ingsw.messages;

/**
 * this message contains a generic string.
 */
public class StringMessage extends ServerMessage{
    private String message;
    public StringMessage(String message){
        super(MessageType.STRING_MESSAGE);
        this.message=message;
    }

    public String getMessage() {
        return message;
    }
}
