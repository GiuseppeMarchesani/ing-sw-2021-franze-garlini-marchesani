package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.*;

public interface VirtualController {

    public void pickMarketRes(PickResMsg msg);
    public void rowOrCol(RowOrColMsg msg);
    public void chosenMarbleConversion(WhiteConversionMsg msg);
    public void placeRes(PlaceMsg msg);

    public void pickDevCard(DevCardReplyMessage msg);


    public void playLeader(PlayLeaderMsg msg);

    public void discardLeader(DiscardLeaderMsg msg);



    public void activateProduction(ProductionMsg msg);






}
