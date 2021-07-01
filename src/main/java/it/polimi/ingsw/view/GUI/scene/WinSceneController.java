package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.Gui;
import it.polimi.ingsw.view.GUI.scene.GenericSceneController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.HashMap;

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

    private HashMap<String, Integer> finalVp = new HashMap<>();
    @FXML
    public void initialize(){
        ArrayList<String> usernameList = new ArrayList<>(finalVp.keySet());

        if(finalVp.size() == 1){
            username.setText(usernameList.get(0));
            username1.setText(usernameList.get(0));
            vp1.setText(String.valueOf(finalVp.get(usernameList.get(0))));
            vp2.setVisible(false);
            username2.setVisible(false);
            vp3.setVisible(false);
            username3.setVisible(false);
            vp4.setVisible(false);
            username4.setVisible(false);
        }
        else if(finalVp.size() == 2){
            username.setText(usernameList.get(0));
            username1.setText(usernameList.get(0));
            vp1.setText(String.valueOf(finalVp.get(usernameList.get(0))));
            username2.setText(usernameList.get(1));
            vp2.setText(String.valueOf(finalVp.get(usernameList.get(1))));
            vp3.setVisible(false);
            username3.setVisible(false);
            vp4.setVisible(false);
            username4.setVisible(false);
        }
        else if(finalVp.size() == 3){
            username.setText(usernameList.get(0));
            username1.setText(usernameList.get(0));
            vp1.setText(String.valueOf(finalVp.get(usernameList.get(0))));
            username2.setText(usernameList.get(1));
            vp2.setText(String.valueOf(finalVp.get(usernameList.get(1))));
            username3.setText(usernameList.get(2));
            vp3.setText(String.valueOf(finalVp.get(usernameList.get(3))));
            vp4.setVisible(false);
            username4.setVisible(false);
        }
        else if(finalVp.size() == 4){
            username.setText(usernameList.get(0));
            username1.setText(usernameList.get(0));
            vp1.setText(String.valueOf(finalVp.get(usernameList.get(0))));
            username2.setText(usernameList.get(1));
            vp2.setText(String.valueOf(finalVp.get(usernameList.get(1))));
            username3.setText(usernameList.get(2));
            vp3.setText(String.valueOf(finalVp.get(usernameList.get(3))));
            username4.setText(usernameList.get(3));
            vp4.setText(String.valueOf(finalVp.get(usernameList.get(4))));
        }
    }

    public void setFinalVp(HashMap<String, Integer> finalVp){
        this.finalVp = finalVp;
    }



}
