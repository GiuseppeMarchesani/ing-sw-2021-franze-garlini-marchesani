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
     * Default constructor.
     * @param totalMarbles all marbles in the game.
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

    /**
     * Takes the row or column requested by the player and replaces the marbles.
     * @param rowOrCol 'c' stands for column, 'r' stands for row.
     * @param num which column or which row.
     * @param conversion ResourceType associated to the white marble conversion.
     * @return the resources present in the chosen row or column.
     */
    public HashMap<ResourceType, Integer> pickResources(char rowOrCol, int num, ResourceType conversion) throws InvalidParameterException {
        HashMap<ResourceType, Integer> resources = new HashMap<>();

        if (rowOrCol == 'c') {
                    for (int i = 0; i < N_ROW; i++) {
                        if (resources.containsKey(marketTray[num][i])){
                            resources.put((marketTray[num][i]), resources.get(marketTray[num][i]) + 1);
                        } else resources.put(marketTray[num][i], 1);
                    }

        } else if(rowOrCol == 'r'){
                for (int i = 0; i < N_COL; i++) {
                    if (resources.containsKey(marketTray[i][num])){
                        resources.put((marketTray[i][num]), resources.get(marketTray[i][num]) + 1);
                    } else resources.put(marketTray[i][num], 1);
                }
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

    /**
     * Repositions the marbles after the player has taken them from the market.
     * @param rowOrCol 'r' if the player chose row, 'c' if the player chose column.
     * @param index index of the row or column chosen by the player.
     */
    private void replace(char rowOrCol, int index) {
        ResourceType c = null;
        if(rowOrCol == 'c'){
            c=marketTray[index][0];
            for(int i=0; i<N_ROW-1; i++){
                marketTray[index][i]=marketTray[index][i+1];
            }
            marketTray[index][N_ROW-1]=cornerMarble;
        }
        else if(rowOrCol == 'r') {
            c = marketTray[0][index];
            for(int i=0; i<N_COL-1; i++){
                marketTray[i][index] = marketTray[i + 1][i];
            }
            marketTray[N_COL-1][index] = cornerMarble;
        }
        cornerMarble = c;
    }

    public ResourceType[][] getMarketTray() {
        ResourceType[][] market = new ResourceType[N_COL][N_ROW];
        for (int i=0; i<N_COL; i++){
            for(int j=0; j<N_ROW; j++){
                market[i][j]=marketTray[i][j];
            }
        }
        return market;
    }

    public ResourceType getCornerMarble() {
        return cornerMarble;
    }

}
