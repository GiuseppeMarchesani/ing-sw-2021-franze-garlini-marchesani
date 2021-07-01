package it.polimi.ingsw.view;
//TODO
/**
 *
 */
public enum Command {
        MARKET("MARKET"),
        DEVCARD("DEVCARD"),
        PRODUCTION("PRODUCTION"),
        LEADER("LEADER"),
        SHOW_LEADER("SHOW_LEADER"),
        SHOW_MARKET("SHOW_MARKET"),
        SHOW_DEV_MARKET("SHOW_DEV_MARKET"),
        SHOW_FAITH_TRACK("SHOW_FAITH_TRACK"),
        SHOW_SLOT("SHOW_SLOT"),
        SHOW_WAREHOUSE("SHOW_WAREHOUSE"),
        SHOW_STRONGBOX("SHOW_STRONGBOX"),
        SHOW_VP("SHOW_VP"),
        SHOW_FAITH("SHOW_FAITH"),
        SHOW_PLAYER("SHOW_PLAYER"),
        END_TURN("END_TURN");

    private String command;

    Command(String command){
        this.command = command;
    }

    public String getVal(){
        return command;
    }
}
