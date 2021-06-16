package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.GenericSceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.event.Event;
import java.util.Map;

public class InitSceneController extends ObservableView implements GenericSceneController {

    @FXML
    private TextField serverAddress;
    @FXML
    private TextField serverPort;
    @FXML
    private Button btmNext;

    @FXML
    public void initialize(){
        btmNext.addEventHandler(MouseEvent.MOUSE_CLICKED, this :: onConnectBtm);
    }

    private void onConnectBtm(Event event){
        btmNext.setDisable(true);

        String address = serverAddress.getText();
        Integer port = Integer.parseInt(serverPort.getText());

        new Thread(() -> notifyObserver(obs -> obs.updateConnect(address, port))).start();
        SceneController.changeRootPane(observers, event, "/fxml/lobby_scene.fxml");

    }

}
