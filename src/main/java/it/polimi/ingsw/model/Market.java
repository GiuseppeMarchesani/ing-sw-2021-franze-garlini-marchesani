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
    private ArrayList<Marble> totalMarbles;

    /**
     * Market class constructor.
     */
    public Market(ArrayList<Marble> totalMarbles){
        this.marketTray = new Marble[N_COL][N_ROW];
        this.cornerMarble = new Marble();
        this.totalMarbles = totalMarbles;
        generateMarket();
    }

    /**
     * This method is called at the beginning of the game and generates the market placing
     * the marbles randomly.
     */
    private void generateMarket(){
        int d=0;
        Collections.shuffle(totalMarbles);

        for(int i=0; i<N_COL; i++){
            for(int j=0; j< N_ROW; j++){
                marketTray[i][j]= totalMarbles.get(d);
                d++;
            }
        }
        cornerMarble= totalMarbles.get(d);
    }

    /**
     *
     * @param a (true column and false row)
     * @param num (which column or which row)
     * @param player (the player who is buying from market)
     */
    public HashMap<ResourceType, Integer> pickResources(Player player, Boolean a, int num) {

        ArrayList<ResourceType> whiteMarbleRes = player.getMarbleConversion();

        HashMap<ResourceType, Integer> resources = new HashMap<>();

        if (a) {
            for (int i = 0; i < N_ROW; i++) {
                if (marketTray[num][i].getResourceType().getResource() == 5) {
                    //n = -1 if whiteMarbleRes is empty
                    int n = player.choose(whiteMarbleRes);
                    if(n>-1) {
                        if (resources.containsKey(whiteMarbleRes.get(n))) {
                            resources.put(whiteMarbleRes.get(n), resources.get(whiteMarbleRes.get(n)) + 1);
                        } else resources.put(whiteMarbleRes.get(n), 1);
                    }
                } else if (resources.containsKey(marketTray[num][i].getResourceType())) {
                    resources.put(marketTray[num][i].getResourceType(), resources.get(marketTray[num][i].getResourceType()) + 1);
                } else resources.put(marketTray[num][i].getResourceType(), 1);
            }
        } else {
            for (int i = 0; i < N_COL; i++) {
                if (marketTray[i][num].getResourceType().getResource() == 5) {
                    //n = -1 if whiteMarbleRes is empty
                    int n = player.choose(whiteMarbleRes);
                    if(n>-1) {
                        if (resources.containsKey(whiteMarbleRes.get(n))) {
                            resources.put(whiteMarbleRes.get(n), resources.get(whiteMarbleRes.get(n)) + 1);
                        } else resources.put(whiteMarbleRes.get(n), 1);
                    }
                } else if (resources.containsKey(marketTray[i][num].getResourceType())) {
                    resources.put(marketTray[i][num].getResourceType(), resources.get(marketTray[N_COL][i].getResourceType()) + 1);
                } else resources.put(marketTray[i][num].getResourceType(), 1);
            }
        }
        replace(a, num);
        return player.placeResources(resources);
    }

    private void replace (boolean a, int num) {
        Marble c;
        if(a){
            c=marketTray[num][0];
            for(int i=0; i<N_ROW-1; i++){
                marketTray[num][i]=marketTray[num][i+1];
            }
            marketTray[num][N_ROW-1]=cornerMarble;
        }
        else {
            c = marketTray[0][num];
            for(int i=0; i<N_COL-1; i++){
                marketTray[i][num] = marketTray[i + 1][num];
            }
            marketTray[N_COL-1][num] = cornerMarble;
        }
        cornerMarble = c;
    }

}
