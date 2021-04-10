import java.util.ArrayList;
import java.util.Collections;

public class CardMarket {
    public ArrayList<ArrayList<ArrayList<DevCard>>> devCardGrid;

    public CardMarket(ArrayList<DevCard> deck) {
        devCardGrid = new ArrayList<ArrayList<ArrayList<DevCard>>>();
        Collections.shuffle(deck);
        for (int i = 0; i < 3; i++) {
            devCardGrid.add(new ArrayList<ArrayList<DevCard>>());
            for (int j = 0; j < 4; j++) {
                devCardGrid.get(i).add(new ArrayList<DevCard>());
            }
        }
        int l;
        int c;
        for (int i = 0; i < deck.size(); i++) {
            l = deck.get(i).getCardType().getLevel();
            c = deck.get(i).getCardType().getColor().getVal();
            devCardGrid.get(l).get(c).add(deck.get(i));
        }

    }
    public ArrayList<DevCard> availableCards(){
        ArrayList<DevCard> available = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                try{
                    available.add(devCardGrid.get(i).get(j).get(devCardGrid.get(i).get(j).size()-1));
                }catch(IndexOutOfBoundsException e){}
            }
        }
        return available;
    }
}