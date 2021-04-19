package it.polimi.ingsw.model;

import java.util.*;

/**
 * This class is used to contain all the marbles.
 */

public class Market {
    private Marble[][] marketTray;
    private Marble cornerMarble;
    private final int N_COL = 4;
    private final int N_ROW = 3;
    private HashMap<Marble, Integer> totalMarbles;

    /**
     * Market class constructor.
     */
    public Market(){
        this.marketTray = new Marble[N_COL][N_ROW];
        this.cornerMarble = new Marble();
        this.totalMarbles= new HashMap<>();
        generateMarket();
    }

    /**
     * This method is called at the beginning of the game and generates the market placing
     * the marbles randomly.
     */
    private void generateMarket(){
        int d=0;
        ArrayList<Marble> marbles = new ArrayList<>();
        Collections.shuffle(marbles);

        for(int i=0; i<N_COL; i++){
            for(int j=0; j< N_ROW; j++){
                marketTray[i][j]= marbles.get(d);
                d++;
            }
        }
        cornerMarble= marbles.get(d);
    }

    /**
     *
     * @param a (true column and false row)
     * @param num (which column or which row)
     * @param player (the player who is buying from market)
     */
    public HashMap<ResourceType, Integer> pickResources(Player player, Boolean a, int num) {

        ArrayList<ResourceType> marble = player.getMarbleConversion();
        ArrayList<Marble> boughtResources = new ArrayList<>();
        HashMap<ResourceType, Integer> resources = new HashMap<>();


        if (a) {
            for (int i = 0; i < N_ROW; i++) {
                if (marketTray[num][i].getType().getResource() == 5) {
                    int n = player.choose(marble);
                    if (resources.containsKey(marble.get(n))) {
                        resources.put(marble.get(n), resources.get(marble.get(n)) + 1);
                    } else resources.put(marble.get(n), 1);
                } else if (resources.containsKey(marketTray[num][i].getType())) {
                    resources.put(marketTray[num][i].getType(), resources.get(marketTray[num][i]) + 1);
                } else resources.put(marketTray[num][i].getType(), 1);
            }
        } else {
            for (int i = 0; i < N_COL; i++) {
                if (marketTray[i][num].getType().getResource() == 5) {
                    int n = player.choose(marble);
                    if (resources.containsKey(marble.get(n))) {
                        resources.put(marble.get(n), resources.get(marble.get(n)) + 1);
                    } else resources.put(marble.get(n), 1);
                } else if (resources.containsKey(marketTray[i][num].getType())) {
                    resources.put(marketTray[i][num].getType(), resources.get(marketTray[N_COL][i]) + 1);
                } else resources.put(marketTray[i][num].getType(), 1);
            }
        }
        replace(a, num);
        return player.placeResources(resources);
    }

    private void replace (boolean n, int x) {
        Marble c;
        if(n){
            c=marketTray[x][0];
            for(int i=0; i<N_ROW-1; i++){
                marketTray[x][i]=marketTray[x][i+1];
            }
            marketTray[x][N_ROW]=cornerMarble;
        }
        else {
            c = marketTray[0][x];
            for(int i=0; i<N_COL-1; i++){
                marketTray[i][x] = marketTray[i + 1][x];
            }
            marketTray[N_COL][x] = cornerMarble;
        }
        cornerMarble = c;
    }

}
