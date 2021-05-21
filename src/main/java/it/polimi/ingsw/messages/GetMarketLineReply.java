package it.polimi.ingsw.messages;

public class GetMarketLineReply extends GeneralMessage{
    private char rowOrCol;
    private int num;
    public GetMarketLineReply(String username, MessageType messageType, String gameID, char rowOrCol, int num) {
        super(username, messageType, gameID);
        this.rowOrCol = rowOrCol;
        this.num = num;
    }

    public char getRowOrCol(){
        return rowOrCol;
    }

    public int getNum() {
        return num;
    }

}
