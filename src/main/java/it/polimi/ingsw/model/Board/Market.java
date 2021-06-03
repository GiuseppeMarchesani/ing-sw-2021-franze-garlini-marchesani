package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.enumeration.ResourceType;

import java.security.InvalidParameterException;
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
            for(int j=0; j< N_ROW; j++) {
                marketTray[i][j] = totalMarbles.get(d);
                d++;
            }
        }
        cornerMarble= totalMarbles.get(d);
    }

    public ResourceType getCornerMarble() {
        return cornerMarble;
    }


    public ResourceType[][] getMarketTray() {
        ResourceType[][] market= new ResourceType[N_COL][N_ROW];
        for (int i=0; i<N_COL; i++){
            for(int j=0; j<N_ROW; j++){
                market[i][j]=marketTray[i][j];
            }
        }
        return market;
    }

    /**
     *
     * @param rowOrCol 'c' stands for column, 'r' stands for row
     * @param num which column or which row
     */
    public HashMap<ResourceType, Integer> pickResources(char rowOrCol, int num, ResourceType conversion) throws InvalidParameterException {

        HashMap<ResourceType, Integer> resources = new HashMap<>();

        if (rowOrCol == 'c') {
                if(num<N_ROW){
                    for (int i = 0; i < N_ROW; i++) {
                        if (resources.containsKey(marketTray[num][i])){
                            resources.put((marketTray[num][i]), resources.get(marketTray[num][i]) + 1);
                        } else resources.put(marketTray[num][i], 1);
                    }
                }
                else throw new InvalidParameterException();



        } else if(rowOrCol == 'r'){
            if(num<N_COL){
                for (int i = 0; i < N_COL; i++) {
                    if (resources.containsKey(marketTray[i][num])){
                        resources.put((marketTray[i][num]), resources.get(marketTray[i][num]) + 1);
                    } else resources.put(marketTray[i][num], 1);
                }
            }
            else throw new InvalidParameterException();
        }
        if (resources.get(ResourceType.EMPTY)!=null){
            if(conversion!= null){
                int change=resources.get(ResourceType.EMPTY);

                if(resources.get(conversion)!=null){
                    change+=resources.get(conversion);
                }
                resources.put(conversion,change);
            }
            resources.remove(ResourceType.EMPTY);
        }
        replace(rowOrCol, num);
        return resources;
    }

    private void replace (char rowOrCol, int x) {
        ResourceType c = null;
        if(rowOrCol == 'c'){
            c=marketTray[x][0];
            for(int i=0; i<N_ROW-1; i++){
                marketTray[x][i]=marketTray[x][i+1];
            }
            marketTray[x][N_ROW-1]=cornerMarble;
        }
        else if(rowOrCol == 'r') {
            c = marketTray[0][x];
            for(int i=0; i<N_COL-1; i++){
                marketTray[i][x] = marketTray[i + 1][x];
            }
            marketTray[N_COL-1][x] = cornerMarble;
        }
        cornerMarble = c;
    }

}
