package it.polimi.ingsw.messages;

/**
 * This message contains the requested player's index
 */
public class ShowPlayerRequest extends ClientMessage{
    private int number;
    public ShowPlayerRequest(String username, int n){
        super(username, MessageType.SHOW_PLAYER);
        number=n;
    }

    public int getNumber() {
        return number;
    }
}
