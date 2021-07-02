package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.observer.ObservableView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * This Scene Controller shows the player their lose scene.
 */
public class LoseSceneController extends ObservableView implements GenericSceneController {
    @FXML
    private Button btm_close;

    @FXML
    public void initialize(){
        btm_close.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtmClose);
    }

    /**
     * Handles the click on the Close button.
     * @param event the mouse click event.
     */
    private void onBtmClose(Event event){
        System.exit(0);
    }
}
