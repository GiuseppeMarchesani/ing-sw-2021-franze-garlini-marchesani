package it.polimi.ingsw.messages;

/**
 * This message sends a generic string to signal an error or unavailable action
 */
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
