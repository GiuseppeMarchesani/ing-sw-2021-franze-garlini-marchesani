package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;

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
