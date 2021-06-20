package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.observer.ObservableView;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class ChooseLeaderToKeep extends ObservableView implements GenericSceneController {

    @FXML
    private CheckBox leader1;
    @FXML
    private CheckBox leader2;
    @FXML
    private CheckBox leader3;
    @FXML
    private CheckBox leader4;
    @FXML
    private ImageView imgLeader1;
    @FXML
    private ImageView imgLeader2;
    @FXML
    private ImageView imgLeader3;
    @FXML
    private ImageView imgLeader4;
    @FXML
    private Button btmNext;

    private int leaderCard1;
    private int leaderCard2;
    private int leaderCard3;
    private int leaderCard4;
    private ArrayList<LeaderCard> allLeaders= new ArrayList<>();

    private ObservableSet<CheckBox> chooseCheckBoxes = FXCollections.observableSet();
    private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();
    private int maxNumSelected =  2;
    private IntegerBinding numCheckBoxesSelected = Bindings.size(chooseCheckBoxes);

    @FXML
    public void initialize(){
        Image image1 = new Image(String.valueOf(MainApp.class.getResource("/images/leader_id" + leaderCard1 + ".png")));
        Image image2 = new Image(String.valueOf(MainApp.class.getResource("/images/leader_id" + leaderCard2 + ".png")));
        Image image3 = new Image(String.valueOf(MainApp.class.getResource("/images/leader_id" + leaderCard3 + ".png")));
        Image image4 = new Image(String.valueOf(MainApp.class.getResource("/images/leader_id" + leaderCard4 + ".png")));
        imgLeader1.setImage(image1);
        imgLeader2.setImage(image2);
        imgLeader3.setImage(image3);
        imgLeader4.setImage(image4);

        numCheckBoxesSelected.addListener((obs, oldSelectedCount, newSelectedCount) ->
        {
            if(newSelectedCount.intValue()>=maxNumSelected){
                unselectedCheckBoxes.forEach(cb -> cb.setDisable(true));
            }
            else{
                unselectedCheckBoxes.forEach(cb -> cb.setDisable(false));
            }
        });
        btmNext.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onNextBtm);

    }

    private void onNextBtm(Event event){
        HashMap<LeaderCard, CheckBox> checkBoxes=new HashMap<>();
        ArrayList<LeaderCard> chosenLeader = new ArrayList<>();
        checkBoxes.put(allLeaders.get(0),leader1);
        checkBoxes.put(allLeaders.get(1),leader2);
        checkBoxes.put(allLeaders.get(2), leader3);
        checkBoxes.put(allLeaders.get(3),leader4);
        for(LeaderCard ld: checkBoxes.keySet()){
            if(checkBoxes.get(ld).isSelected()){
               chosenLeader.add(ld);
            }
        }

        notifyObserver(obs -> obs.updateDiscardLeader(chosenLeader));

    }
    public void updateLeader(ArrayList<LeaderCard> leaders){
        allLeaders.addAll(leaders);
        leaderCard1 = leaders.get(0).getId();
        leaderCard2 = leaders.get(1).getId();
        leaderCard3 = leaders.get(2).getId();
        leaderCard4 = leaders.get(3).getId();
    }
}