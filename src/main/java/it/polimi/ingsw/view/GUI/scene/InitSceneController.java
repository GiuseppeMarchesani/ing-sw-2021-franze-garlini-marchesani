package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.observer.ObservableView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.event.Event;

/**
 * Allows a player to connect to a server.
 */
public class InitSceneController extends ObservableView implements GenericSceneController {

    @FXML
    private TextField serverAddressField;
    @FXML
    private TextField serverPortField;
    @FXML
    private Button btm_next;

    @FXML
    public void initialize(){
        btm_next.addEventHandler(MouseEvent.MOUSE_CLICKED, this :: onConnectBtm);
    }

    /**
     * Handles the click on the Connect button.
     * @param event the mouse click event.
     */
    private void onConnectBtm(Event event){
        serverAddressField.setDisable(true);
        serverPortField.setDisable(true);
        String address = serverAddressField.getText();
        Integer chosenPort = Integer.parseInt(serverPortField.getText());
        btm_next.setDisable(true);

        notifyObserver(obs -> obs.updateConnect(address, chosenPort));
    }

}
