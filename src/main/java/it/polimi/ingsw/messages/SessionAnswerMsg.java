package it.polimi.ingsw.messages;

import java.util.UUID;

public class SessionAnswerMsg extends AnswerMsg{
    UUID parentIdentifier;
    String msg;
    boolean success;
    /**
     * Initializes the answer message.
     * @param parent The CommandMsg being answered.
     */
    public SessionAnswerMsg(CommandMsg parent , String msg, boolean success)
    {

        super(parent);
        this.msg=msg;
        this.success=success;
    }



    /**
     * Returns the identifier of the message being answered.
     * @return The UUID of the answered message.
     */
    public UUID getParentIdentifier()
    {
        return parentIdentifier;
    }
}
