package it.polimi.ingsw.messages;

public class ShowRemainingLeaderMsg extends ServerMessage {
    private int remaining;
    private String username;
    public ShowRemainingLeaderMsg(String username, int remaining) {
        super(MessageType.SHOW_REMAINING_LEADERS);
        this.username=username;
        this.remaining=remaining;
    }

    public String getUsername() {
        return username;
    }

    public int getRemaining() {
        return remaining;
    }
}
