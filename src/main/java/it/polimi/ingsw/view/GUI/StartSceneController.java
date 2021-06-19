package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.observer.ObservableView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;

import java.io.IOException;

public class StartSceneController extends ObservableView implements GenericSceneController {

    @FXML
    private Button btmNext;
    @FXML
    public void initialize() {
        btmNext.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onPlayBtnClick);
    }

    private void onPlayBtnClick(Event event){
        btmNext.setDisable(true);

        MainApp.changeRootPane(observers, event,"/fxml/init_scene");


    }
}
