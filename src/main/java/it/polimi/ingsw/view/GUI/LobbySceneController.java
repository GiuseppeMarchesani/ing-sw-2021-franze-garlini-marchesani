package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.GenericSceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.event.Event;



public class LobbySceneController extends ObservableView implements GenericSceneController {

    @FXML
    private TextField username;
    @FXML
    private TextField gameId;
    @FXML
    private Button next;

    @FXML
    public void initialize(){
        next.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinBtm);
    }

    private void onJoinBtm(Event event){
        next.setDisable(true);

        String user = username.getText();
        String game = gameId.getText();

        new Thread(()-> notifyObserver(obs -> obs.updateLobby(user, game)));
    }
}
