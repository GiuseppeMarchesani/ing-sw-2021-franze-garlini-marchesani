package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.observer.ObserverView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This Scene Controller is used to show at the player the winner and final victory points
  */
public class WinSceneController extends ObservableView implements GenericSceneController {

    @FXML
    private Label username;
    @FXML
    private Label username1;
    @FXML
    private Label username2;
    @FXML
    private Label username3;
    @FXML
    private Label username4;
    @FXML
    private Label vp1;
    @FXML
    private Label vp2;
    @FXML
    private Label vp3;
    @FXML
    private Label vp4;
    @FXML
    private Button btm_close;

    private HashMap<String, Integer> finalVp = new HashMap<>();
    @FXML
    public void initialize(){
        ArrayList<String> usernameList = new ArrayList<>(finalVp.keySet());
        vp2.setVisible(false);
        username2.setVisible(false);
        vp3.setVisible(false);
        username3.setVisible(false);
        vp4.setVisible(false);
        username4.setVisible(false);
        switch(finalVp.size()){
            case 4:
                username4.setText(usernameList.get(3));
                vp4.setText(String.valueOf(finalVp.get(usernameList.get(3))));
                vp4.setVisible(true);
                username4.setVisible(true);
            case 3:
                username3.setText(usernameList.get(2));
                vp3.setText(String.valueOf(finalVp.get(usernameList.get(2))));
                username3.setVisible(true);
                vp3.setVisible(true);
            case 2:
                username2.setText(usernameList.get(1));
                vp2.setText(String.valueOf(finalVp.get(usernameList.get(1))));
                username2.setVisible(true);
                vp2.setVisible(true);
            case 1:
                username.setText(usernameList.get(0));
                username1.setText(usernameList.get(0));
                vp1.setText(String.valueOf(finalVp.get(usernameList.get(0))));
                vp1.setVisible(true);
                username1.setVisible(true);

        }

        btm_close.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onBtmClose);

    }

    /**
     * Handles the click on the close button.
     * @param event the mouse click event.
     */
    private void onBtmClose(Event event){
        notifyObserver(ObserverView::updateDisconnect);

        System.exit(0);
    }
    public void setFinalVp(HashMap<String, Integer> finalVp){
        this.finalVp = finalVp;
    }



}
