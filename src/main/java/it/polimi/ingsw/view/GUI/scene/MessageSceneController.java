package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.scene.GenericSceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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

