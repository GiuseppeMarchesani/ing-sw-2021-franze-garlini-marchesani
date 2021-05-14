package it.polimi.ingsw.model.Card;


import it.polimi.ingsw.model.Player;

public interface LeaderCard {

    public void activateAbility(Player player);

    public int getLeaderID();

}