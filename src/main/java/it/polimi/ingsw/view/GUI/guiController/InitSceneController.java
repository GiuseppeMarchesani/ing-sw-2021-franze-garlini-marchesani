package it.polimi.ingsw.view.GUI.guiController;

import it.polimi.ingsw.observer.ObservableView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.event.Event;
import java.util.Map;

public class InitSceneController extends ObservableView implements GeneralController {

    @FXML
    private TextField serverAddress;
    @FXML
    private TextField serverPort;
    @FXML
    private Button next;

    @FXML
    public void initialize(){
        next.addEventHandler(MouseEvent.MOUSE_CLICKED, this :: onConnectBtm);
    }

    private void onConnectBtm(Event event){
        next.setDisable(true);

        String address = serverAddress.getText();
        Integer port = Integer.parseInt(serverPort.getText());

        new Thread(() -> notifyObserver(obs -> obs.updateConnect(address, port))).start();

    }

}