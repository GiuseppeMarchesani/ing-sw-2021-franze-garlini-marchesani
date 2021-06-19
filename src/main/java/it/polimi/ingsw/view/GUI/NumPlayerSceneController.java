package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.observer.ObservableView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;

public class NumPlayerSceneController extends ObservableView implements GenericSceneController {

    private final ToggleGroup group = new ToggleGroup();
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
        RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
        String id= selectedRadioButton.getId();
        Integer numPlayer;
        switch (id) {
            case "choose1":
                numPlayer = 1;
                break;
            case "choose2":
                numPlayer = 2;
                break;
            case "choose3":
                numPlayer = 3;
                break;
            default:
                numPlayer = 4;
                break;
        }
        btmNext.setDisable(true);
        notifyObserver(obs -> obs.updatePlayersNumber(numPlayer));
    }

}
