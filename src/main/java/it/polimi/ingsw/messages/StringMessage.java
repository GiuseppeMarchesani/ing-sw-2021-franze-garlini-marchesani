package it.polimi.ingsw.messages;

public class StringMessage extends ServerMessage{
    private String message;
    public StringMessage(String message){
        super(MessageType.STRING_MESSAGE);
        this.message=message;
    }
}
