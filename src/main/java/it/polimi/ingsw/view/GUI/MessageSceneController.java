package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.observer.ObservableView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MessageSceneController extends ObservableView implements GenericSceneController {
    private Stage stage;


    @FXML
    private Label messageTxt;
    @FXML
    private Button btmOk;

    @FXML
    public void initialize(){
        btmOk.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onOkBtm);
    }

    private void onOkBtm(Event event){

    }
}

