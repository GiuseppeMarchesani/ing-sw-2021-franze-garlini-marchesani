package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Board.Market;
import it.polimi.ingsw.model.enumeration.ResourceType;

/**
 * This message contains the chosen row or column of the marble market and the chosen conversion
 */
public class GetMarketResRequest extends ClientMessage{
        private char rowOrCol;
        private int num;
        private ResourceType conversion;
    public GetMarketResRequest(String username, char rowOrCol, int num, ResourceType conversion) {
            super(username, MessageType.PICK_MARKETRES);
            this.rowOrCol = rowOrCol;
            this.num = num;
            this.conversion=conversion;
        }

        public char getRowOrCol(){
            return rowOrCol;
        }

        public int getNum() {
            return num;
        }
        public ResourceType getConversion(){ return conversion;}

}
