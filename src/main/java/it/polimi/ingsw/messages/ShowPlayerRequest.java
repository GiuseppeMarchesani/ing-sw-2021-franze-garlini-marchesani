package it.polimi.ingsw.messages;

/**
 * This message contains the requested player's username
 */
public class ShowPlayerRequest extends ClientMessage{
    private String player;
    public ShowPlayerRequest(String username, String player){
        super(username, MessageType.SHOW_PLAYER);
        this.player=player;
    }

    public String getPlayer() {
        return player;
    }
}
