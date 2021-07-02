package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.model.Card.DevCardSlot;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This Scene Controller allows the player to see another player's board.
 */
public class ChoosePlayerSceneController extends ObservableView implements GenericSceneController {

    @FXML
    private TextField chosenPlayer;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player3;
    @FXML
    private Label player4;
    @FXML
    private Button btm_ok;

    private ArrayList<String> playersList = new ArrayList<>();
    private  int faithSpace;
    private HashMap<Integer, ResourceType> depotToResource = new HashMap<>();
    private HashMap<Integer, Integer> depotToQuantity = new HashMap<>();
    private HashMap<ResourceType, Integer> strongbox = new HashMap<>();
    private  DevCardSlot devCardSlot= new DevCardSlot();
    private ArrayList<LeaderCard> playedLeaderCards = new ArrayList<>();
    private String self;
    private ArrayList<Label> players = new ArrayList<>();

    @FXML
    public void initialize(){
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        int count = 0;
        for(String name: playersList){
            players.get(count).setText(name);
            count++;
        }
        while (count == players.size()-1) {
            for (int n = players.size()-1; n >= 0; n--) {
                players.get(n).setVisible(false);
                count++;
            }
        }
        btm_ok.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onOkBtm);
    }

    /**
     * Handles the click on the Ok button.
     * @param event the mouse click event.
     */
    private void onOkBtm(Event event){
        boolean wrongInput = false;
        String name;
        name = chosenPlayer.getText();
        if(!playersList.contains(name) || name.equals(self)){
            wrongInput = true;
        }
        if(wrongInput){
            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Check the players name");
            errorAlert.showAndWait();
        }

        notifyObserver(obs -> obs.updateShowPlayer(name));
    }
    public void setPlayersList(ArrayList<String> playersList){
        this.playersList = playersList;
    }

    public void setDepotToQuantity(HashMap<Integer, Integer> depotToQuantity) {
        this.depotToQuantity = depotToQuantity;
    }

    public void setDepotToResource(HashMap<Integer, ResourceType> depotToResource) {
        this.depotToResource = depotToResource;
    }

    public void setDevCardSlot(DevCardSlot devCardSlot) {
        this.devCardSlot = devCardSlot;
    }

    public void setFaithSpace(int faithSpace) {
        this.faithSpace = faithSpace;
    }

    public void setStrongbox(HashMap<ResourceType, Integer> strongbox) {
        this.strongbox = strongbox;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public void setPlayedLeaderCards(ArrayList<LeaderCard> playedLeaderCards) {
        this.playedLeaderCards = playedLeaderCards;
    }
}
