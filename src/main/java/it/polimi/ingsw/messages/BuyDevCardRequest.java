package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.enumeration.Color;

/**
 * This message identifies which color and level of card the player wants to buy
 */
public class BuyDevCardRequest extends ClientMessage {
    private int level;
    private Color color;
    public BuyDevCardRequest(String username, int level, Color color) {
        super(username, MessageType.PICK_DEVCARD);
        this.color=color;
        this.level=level;
    }

    public Color getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }
}
