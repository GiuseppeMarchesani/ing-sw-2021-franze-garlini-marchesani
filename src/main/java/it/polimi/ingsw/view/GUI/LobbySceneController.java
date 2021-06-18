package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.network.BootServer;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.LobbyServer;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.GenericSceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;

import java.net.Socket;


public class LobbySceneController extends ObservableView implements GenericSceneController {

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtGameId;
    @FXML
    private Button btnNext;

    @FXML
    private void initialize(){
        btnNext.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinBtm);
    }
    private void onJoinBtm(Event event){
        btnNext.setDisable(true);

        String username = txtUsername.getText();
        String game = txtGameId.getText();

        new Thread(() -> notifyObserver(obs -> obs.updateLobby(username, game))).start();


    }
}
