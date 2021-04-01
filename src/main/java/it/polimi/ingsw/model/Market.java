package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Random;

/**
 * This class is used to contain all the marbles.
 */

public class Market {
    private int[][] marketTray;
    private int cornerMarble;
    private final int N_COL = 4;
    private final int N_ROW = 3;

    /**
     * Market class constructor.
     */
    public Market(int corner){
        this.marketTray = new int[N_COL][N_ROW];
        this.cornerMarble = corner;
    }

    /**
     * This method is called at the beginning of the game and generates the market placing
     * the marbles randomly.
     */
    public void generateMarket(){

        int[] marble = new int[6];

        marble[0]= 2; /* yellow marbles */
        marble[1]= 2; /* grey marbles */
        marble[2]= 2; /* purple marbles */
        marble[3]= 2; /* blue marbles */
        marble[4]= 1; /* red marble */
        marble[5]= 4; /* white marbles */

        for(int i=0; i<N_COL-1; i++){
            for(int j=0; j< N_ROW-1; j++){
                Random n = new Random();
                int d = 1+ n.nextInt(7);
                if(marble[d] > 0) {
                    marketTray[i][j] = d;
                    marble[d]--;
                }
            }
        }

        /**
         * Place the marble in the corner.
         */
        for(int i=0; i<6; i++){
            if(marble[i] > 0){
                cornerMarble = i;
                marble[i]--;
            }
        }
    }

    /**
     * This method is used to take resources from the market.
     * @param res (the indexes are the resources and in the array are there quantities)
     * @param player (the player who is buying at the market)
     */
    public void pickResource(int[] res, Player player){
       HashMap<Integer, Integer> resources = new HashMap<>();
       for(int i=0; i<res.length-1; i++) {
           resources.put(i, res[i]);
       }
       Game.giveMarketRes(resources);
    }
}
