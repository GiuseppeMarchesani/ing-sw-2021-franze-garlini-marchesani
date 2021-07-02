package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.observer.ObservableView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;

/**
 * Allows a player to join a certain lobby providing a username and the name of the game.
 */
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

    /**
     * Handles the click on the Join button.
     * @param event the mouse click event.
     */
    private void onJoinBtm(Event event){
        btnNext.setDisable(true);
        txtGameId.setDisable(true);
        txtUsername.setDisable(true);
        String username = txtUsername.getText();
        String game = txtGameId.getText();

        notifyObserver(obs -> obs.updateLobby(username, game));


    }
}
