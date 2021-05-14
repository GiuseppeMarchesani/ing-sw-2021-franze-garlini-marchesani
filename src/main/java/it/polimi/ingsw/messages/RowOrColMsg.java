package it.polimi.ingsw.messages;

public class RowOrColMsg extends GeneralMessage{
    private char rowOrCol;
    private int num;
    public RowOrColMsg(String username, MessageType messageType, char rowOrCol, int num) {
        super(username, messageType);
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
