package it.polimi.ingsw.view;

public enum Command {
        MARKET("MARKET"),
        PRODUCTION("PRODUCTION"),
        DEVCARD("DEVCARD"),
        LEADER("LEADER"),
        SHOW_LEADER("SHOW_LEADER"),
        SHOW_MARKET("SHOW_MARKET"),
        SHOW_DEV_MARKET("SHOW_DEV_MARKET"),
        SHOW_FAITH("SHOW_FAITH"),
        SHOW_DEVCARDS("SHOW_DEVCARDS"),
        SHOW_RES("SHOW_RES"),
        SHOW_VP("SHOW_VP"),
        END_TURN("END_TURN");

    private String command;

    Command(String command){
        this.command = command;
    }

    public String getVal(){
        return command;
    }
}
