package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.Gui;
import it.polimi.ingsw.view.GUI.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;


public class ShowWarehouseSceneController extends ObservableView implements GenericSceneController {
    @FXML
    private Label username;
    @FXML
    private ImageView res1_depot1;
    @FXML
    private ImageView res2_depot1;
    @FXML
    private ImageView res3_depot1;
    @FXML
    private ImageView res1_depot2;
    @FXML
    private ImageView res2_depot2;
    @FXML
    private ImageView res1_depot3;
    @FXML
    private ImageView depotLeader1_res1;
    @FXML
    private ImageView depotLeader1_res2;
    @FXML
    private ImageView depotLeader2_res2;
    @FXML
    private ImageView depotLeader2_res1;


    private HashMap<Integer, Integer> depotQ = new HashMap<>();
    private HashMap<Integer, ResourceType> depotR = new HashMap<>();

    @FXML
    public void initialize() {
        depotQ.putAll(Gui.getActiveDepotQ());
        depotR.putAll(Gui.getActiveDepotT());
        for (Integer depot : depotR.keySet()) {
            for (int i = 1; i <= depotQ.get(depot); i++) {
                Image image = new Image(MainApp.class.getResourceAsStream("/images/" + depotR.get(depot).toString() + ".png"));
                setImage(depot, depotQ.get(depot), image);
            }
        }
        username.setText(Gui.getActivePlayer());
    }

    private void setImage(int n, int quantity, Image img) {
        switch (n) {
            case 0:
                switch (quantity) {

                    case 3:
                        res3_depot1.setImage(img);
                    case 2:
                        res2_depot1.setImage(img);
                    case 1:
                        res1_depot1.setImage(img);
                        break;

                }
                break;
            case 1:
                switch (quantity) {
                    case 2:
                        res2_depot2.setImage(img);
                    case 1:
                        res1_depot2.setImage(img);
                        break;
                }
                break;
            case 2:
                res1_depot3.setImage(img);
                break;

            case 3:
                switch (quantity) {
                    case 2:
                        depotLeader1_res2.setImage(img);
                    case 1:
                        depotLeader1_res1.setImage(img);
                        break;
                }
                break;
            case 4:
                switch (quantity) {
                    case 2:
                        depotLeader2_res2.setImage(img);
                    case 1:
                        depotLeader2_res1.setImage(img);
                        break;
                }
                break;
        }
    }
}
