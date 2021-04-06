package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;
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
    public Market(){
        this.marketTray = new int[N_COL][N_ROW];
        this.cornerMarble = 0;
        generateMarket();
    }

    /**
     * This method is called at the beginning of the game and generates the market placing
     * the marbles randomly.
     */
    private void generateMarket(){

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

        for(int i=0; i<6; i++){
            if(marble[i] > 0){
                cornerMarble = i;
                marble[i]--;
            }
        }
    }

    /**
     *
     * @param a (true column and false row)
     * @param num (which column or which row)
     * @param player (the player who is buying from market)
     */
    public HashMap<ResourceType, Integer> pickResource(boolean a, int num, Player player){

        ArrayList<ResourceType> marble = player.getMarbleConversion();
        HashMap<ResourceType,Integer> resources = new HashMap<>();

        int[] res;
        if(a){
            res = new int[N_ROW];
            System.arraycopy(marketTray[num], 0, res, 0, N_ROW - 1);
            for(int i=0; i<res.length-1; i++){
                if(res[i] == 5){
                  int n = Turn.whichMarbleConversion(marble);
                  resources.put(new ResourceType(i), res[n]);
                }
                else resources.put(new ResourceType(i), res[i]);
            }
        }
        else{
            res = new int[N_COL];
            for(int i=0; i<N_COL-1; i++){
                res[i] = marketTray[i][num];
            }
            for(int i=0; i<res.length-1; i++){
                if(res[i]==5){
                    int n = Turn.whichMarbleConversion(marble);
                    resources.put(new ResourceType(i), res[n]);
                }
                else resources.put(new ResourceType(i), res[i]);
            }
        }
        replace(a, num);
        return resources;

    }

    private void replace(boolean n, int x){
        int c;
        if(n){
            c = marketTray[x][0];
            System.arraycopy(marketTray[x], 1, marketTray[x], 0, N_ROW - 2);
            marketTray[x][N_ROW] = cornerMarble;
        }
        else {
            c = marketTray[0][x];
            for(int i=0; i<N_COL-2; i++){
                marketTray[i][x] = marketTray[i+1][x];
            }
            marketTray[N_COL][x] = cornerMarble;

        }
        cornerMarble = c;
    }
}
