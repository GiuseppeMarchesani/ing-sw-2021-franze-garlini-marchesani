package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.observer.ObservableView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class StartSceneController extends ObservableView implements GenericSceneController {
    @FXML
    public AnchorPane rootPane;

    @FXML
    private Button btmNext;

    @FXML
    public void initialize() {
        btmNext.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onPlayBtnClick);
    }


    private void onPlayBtnClick(Event event) {
        SceneController.changeRootPane(observers, event, "/fxml/init_scene.fxml");
    }
}
