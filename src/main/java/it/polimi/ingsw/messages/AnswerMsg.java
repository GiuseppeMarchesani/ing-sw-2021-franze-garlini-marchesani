package it.polimi.ingsw.messages;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.UUID;

    public abstract class AnswerMsg extends NetworkMsg
    {
        UUID parentIdentifier;

        /**
         * Initializes the answer message.
         * @param parent The CommandMsg being answered.
         */
        public AnswerMsg(CommandMsg parent)
        {

            this.parentIdentifier = parent.getIdentifier();
        }



        /**
         * Returns the identifier of the message being answered.
         * @return The UUID of the answered message.
         */
        public UUID getParentIdentifier()
        {
            return parentIdentifier;
        }
        public abstract void processMessage(ObjectOutputStream output) throws IOException;
    }

