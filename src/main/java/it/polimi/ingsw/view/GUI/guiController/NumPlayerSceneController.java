package it.polimi.ingsw.view.GUI.guiController;

import it.polimi.ingsw.observer.ObservableView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

import java.awt.event.MouseEvent;

public class NumPlayerSceneController extends ObservableView implements GeneralController {

    @FXML
    RadioButton radioBtm1;
    @FXML
    RadioButton radioBtm2;
    @FXML
    RadioButton radioBtm3;
    @FXML
    RadioButton radioBtm4;
    @FXML
    Button btmNext;

    @FXML
    public void initialize() {
    }

}
