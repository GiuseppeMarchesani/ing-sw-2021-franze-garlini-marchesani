package it.polimi.ingsw.view.GUI;


import it.polimi.ingsw.observer.ObservableView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;
import javafx.scene.control.MenuButton;

import java.util.List;

public class BoardSceneController extends ObservableView implements GenericSceneController {
    @FXML
    private MenuButton menu_player1;
    @FXML
    private MenuButton menu_player2;
    @FXML
    private MenuButton menu_player3;
    @FXML
    private Button btm_showLeader;
    @FXML
    public void initialize(){
        btm_showLeader.addEventHandler(MouseEvent.MOUSE_CLICKED, this :: onLeaderBtm);
    }
    private void onLeaderBtm(Event event){

    }

    public void updateMatchInfo(List<String> players, String activePlayer){
        if(players.size()==1){
            menu_player1.setDisable(true);
            menu_player1.setVisible(false);
            menu_player2.setDisable(true);
            menu_player2.setVisible(false);
            menu_player3.setDisable(true);
            menu_player3.setVisible(false);


        }
        else if(players.size()==2){
            menu_player2.setDisable(true);
            menu_player2.setVisible(false);
            menu_player3.setDisable(true);
            menu_player3.setVisible(false);
            menu_player1.setText(players.get(0));
        }
        else if(players.size()==3){
            menu_player3.setDisable(true);
            menu_player3.setVisible(false);
        }
    }
}
