package it.polimi.ingsw.messages;

public enum MessageType {
    //game's messages
    LOGIN,
    LOGIN_REPLY,
    PLAYER_NUMBER,

    //Server Connection Messages
    SUCCESSFUL_HOST,
    STRING_MESSAGE,

    //Turn messages
    START_TURN,
    END_TURN,


    //Setup
    STARTING_LEADERS,

    //Action Requests
    MAIN_CARD, MAIN_PRODUCTION, SIDE_LEADER, MAIN_MARBLE,
    SHOW_LEADER,
    SHOW_MARKET,
    SHOW_DEV_MARKET,
    SHOW_DEV_CARDS,
    SHOW_RES,
    SHOW_FAITH_TRACK,
    SHOW_VICTORY_POINTS,
    SHOW_STRONGBOX,
    SHOW_WAREHOUSE,
    SHOW_REMAINING_LEADERS,
    SHOW_SLOT,

    //action
    LEADER_ACTION,
    PICK_DEVCARD,
    PICK_MARKETRES,


    //intermediate message
    ACTIVATE_PRODUCTION,
    RESOURCE_TO_WAREHOUSE,
    CHECK_PRODUCTION,
    REARRANGE_REQUEST,
    PLACE_CARD,

    ERROR,
    WIN,
    LOSE;
}
