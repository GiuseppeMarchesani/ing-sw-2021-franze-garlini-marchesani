package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.GuiManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;

/**
 * This Scene Controller is used to start the game.
 */
public class StartSceneController extends ObservableView implements GenericSceneController {

    @FXML
    private Button btmNext;
    @FXML
    public void initialize() {
        btmNext.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onPlayBtnClick);
    }

    /**
     * Handles the click on the Next button.
     * @param event the mouse click event.
     */
    private void onPlayBtnClick(Event event){
        btmNext.setDisable(true);

        GuiManager.changeRootPane(observers, event,"/fxml/init_scene");


    }
}
