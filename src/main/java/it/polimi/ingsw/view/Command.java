package it.polimi.ingsw.view;

public enum Command {
        MARKET("MARKET"),
        PRODUCTION("PRODUCTION"),
        DEVCARD("DEVCARD"),
        LEADER("LEADER"),
        DISCARD("DISCARD"),
        SHOW_MARKET("SHOW_MARKET"),
        SHOW_FAITH("SHOW_FAITH"),
        SHOW_DEV_MARKET("SHOW_DEV_MARKET"),
        SHOW_RES("SHOW_RES"),
        SHOW_VP("SHOW_VP"),
        SHOW_SLOTS("SHOW_SLOTS"),
        SHOW_INFO("SHOW_INFO"),
        END_TURN("END_TURN");


    private String command;

    Command(String command){
        this.command = command;
    }

    public String getVal(){
        return command;
    }
}
