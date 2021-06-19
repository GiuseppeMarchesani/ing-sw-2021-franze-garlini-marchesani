package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.observer.ObservableView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;

public class NumPlayerSceneController extends ObservableView implements GenericSceneController {

    private ToggleGroup group = new ToggleGroup();
    @FXML
    private RadioButton choose1;
    @FXML
    private RadioButton choose2;
    @FXML
    private RadioButton choose3;
    @FXML
    private RadioButton choose4;
    @FXML
    private Button btmNext;

    @FXML
    public void initialize(){

        choose1.setToggleGroup(group);
        choose2.setToggleGroup(group);
        choose3.setToggleGroup(group);
        choose4.setToggleGroup(group);
        btmNext.addEventHandler(MouseEvent.MOUSE_CLICKED, this :: onConnectBtm);
    }

    private void onConnectBtm(Event event){
        Integer numPlayer =Integer.parseInt(group.getSelectedToggle().getUserData().toString());
        btmNext.setDisable(true);
        notifyObserver(obs -> obs.updatePlayersNumber(numPlayer));

    }

}
