package it.polimi.ingsw.messages;

public class ErrorMsg extends ServerMessage{
    private String message;
    public ErrorMsg(String message) {
        super(MessageType.ERROR);
        this.message=message;
    }

    public String getMessage() {
        return message;
    }
}
