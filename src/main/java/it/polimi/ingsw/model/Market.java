package it.polimi.ingsw.model;

import java.util.*;

/**
 * This class is used to contain all the marbles.
 */

public class Market{
    private ResourceType[][] marketTray;
    private ResourceType cornerMarble;
    private final int N_COL = 4;
    private final int N_ROW = 3;

    /**
     * Market class constructor.
     */
    public Market(ArrayList<ResourceType> totalMarbles){
        this.marketTray = new ResourceType[N_COL][N_ROW];

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
     */
    public HashMap<ResourceType, Integer> pickResources(Boolean a, int num) {

        HashMap<ResourceType, Integer> resources = new HashMap<>();

        if (a) {
            for (int i = 0; i < N_ROW; i++) {
                if (resources.containsKey(marketTray[num][i])){
                    resources.put((marketTray[num][i]), resources.get(marketTray[num][i]) + 1);
                } else resources.put(marketTray[num][i], 1);
            }
        } else {
            for (int i = 0; i < N_COL; i++) {
                if (resources.containsKey(marketTray[i][num])){
                    resources.put((marketTray[i][num]), resources.get(marketTray[i][num]) + 1);
                } else resources.put(marketTray[i][num], 1);
            }
        }
        replace(a, num);
        return resources;
    }

    private void replace (boolean n, int x) {
        ResourceType c;
        if(n){
            c=marketTray[x][0];
            for(int i=0; i<N_ROW-1; i++){
                marketTray[x][i]=marketTray[x][i+1];
            }
            marketTray[x][N_ROW-1]=cornerMarble;
        }
        else {
            c = marketTray[0][x];
            for(int i=0; i<N_COL-1; i++){
                marketTray[i][x] = marketTray[i + 1][x];
            }
            marketTray[N_COL-1][x] = cornerMarble;
        }
        cornerMarble = c;
    }

}
