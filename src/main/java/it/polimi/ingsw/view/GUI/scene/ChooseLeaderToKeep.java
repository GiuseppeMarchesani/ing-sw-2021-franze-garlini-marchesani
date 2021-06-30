package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.Gui;
import it.polimi.ingsw.view.GUI.MainApp;
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
    private ImageView imgLeader1;
    @FXML
    private ImageView imgLeader2;
    @FXML
    private ImageView imgLeader3;
    @FXML
    private ImageView imgLeader4;
    @FXML
    private CheckBox leader1;
    @FXML
    private CheckBox leader2;
    @FXML
    private CheckBox leader3;
    @FXML
    private CheckBox leader4;
    @FXML
    private Button btmNext;

    private int leaderCard1;
    private int leaderCard2;
    private int leaderCard3;
    private int leaderCard4;
    private ArrayList<LeaderCard> allLeaders = new ArrayList<>();
    private HashMap<LeaderCard, Boolean> restLeader = new HashMap<>();

    private ObservableSet<CheckBox> chooseCheckBoxes = FXCollections.observableSet();
    private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();
    private IntegerBinding numCheckBoxesSelected = Bindings.size(chooseCheckBoxes);
    private Image image1;
    private Image image2;
    private Image image3;
    private Image image4;

    @FXML
    public void initialize() {

        leaderCard1 = allLeaders.get(0).getId() - 1;
        leaderCard2 = allLeaders.get(1).getId() - 1;
        leaderCard3 = allLeaders.get(2).getId() - 1;
        leaderCard4 = allLeaders.get(3).getId() - 1;
        image1 = new Image(MainApp.class.getResourceAsStream("/images/leaderCards/leader_Id" + leaderCard1 + ".png"));
        image2 = new Image(MainApp.class.getResourceAsStream("/images/leaderCards/leader_Id" + leaderCard2 + ".png"));
        image3 = new Image(MainApp.class.getResourceAsStream("/images/leaderCards/leader_Id" + leaderCard3 + ".png"));
        image4 = new Image(MainApp.class.getResourceAsStream("/images/leaderCards/leader_Id" + leaderCard4 + ".png"));
        imgLeader1.setImage(image1);
        imgLeader2.setImage(image2);
        imgLeader3.setImage(image3);
        imgLeader4.setImage(image4);
        imgLeader1.setVisible(true);
        imgLeader2.setVisible(true);
        imgLeader3.setVisible(true);
        imgLeader4.setVisible(true);


        configureCheckBox(leader1);
        configureCheckBox(leader2);
        configureCheckBox(leader3);
        configureCheckBox(leader4);


        numCheckBoxesSelected.addListener((obs, oldSelectedCount, newSelectedCount) ->
        {
            if (newSelectedCount.intValue() >= 2) {
                unselectedCheckBoxes.forEach(cb -> cb.setDisable(true));
            } else {
                unselectedCheckBoxes.forEach(cb -> cb.setDisable(false));
            }
        });
        btmNext.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onNextBtm);

    }

    private void onNextBtm(Event event) {
        leader1.setDisable(true);
        leader2.setDisable(true);
        leader3.setDisable(true);
        leader4.setDisable(true);
        HashMap<LeaderCard, CheckBox> checkBoxes = new HashMap<>();
        ArrayList<LeaderCard> chosenLeader = new ArrayList<>();
        checkBoxes.put(allLeaders.get(0), leader1);
        checkBoxes.put(allLeaders.get(1), leader2);
        checkBoxes.put(allLeaders.get(2), leader3);
        checkBoxes.put(allLeaders.get(3), leader4);
        for (LeaderCard ld : checkBoxes.keySet()) {
            if (checkBoxes.get(ld).isSelected()) {
                chosenLeader.add(ld);
            }
        }
        allLeaders.removeAll(chosenLeader);
        for(LeaderCard ld: allLeaders){
            restLeader.put(ld, false);
        }
        notifyObserver(obs -> obs.updateDiscardLeader(chosenLeader));

    }

    private void configureCheckBox(CheckBox checkBox) {

        if (checkBox.isSelected()) {
            chooseCheckBoxes.add(checkBox);
        } else {
            unselectedCheckBoxes.add(checkBox);
        }

        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                unselectedCheckBoxes.remove(checkBox);
                chooseCheckBoxes.add(checkBox);
            } else {
                chooseCheckBoxes.remove(checkBox);
                unselectedCheckBoxes.add(checkBox);
            }

        });
    }
    public void setAllLeaders(ArrayList<LeaderCard> allLeaders){
        this.allLeaders= allLeaders;
    }

    public HashMap<LeaderCard, Boolean> getRestLeader(){
        return restLeader;
    }

}