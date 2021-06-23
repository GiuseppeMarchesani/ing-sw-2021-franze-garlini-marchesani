package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.scene.GenericSceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.event.Event;

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

    private void onConnectBtm(Event event){

        String address = serverAddressField.getText();
        Integer chosenPort = Integer.parseInt(serverPortField.getText());
        btm_next.setDisable(true);

        notifyObserver(obs -> obs.updateConnect(address, chosenPort));
    }

}
