package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.GuiManager;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;


public class AnyForProdPaySceneController extends ObservableView implements GenericSceneController  {
    @FXML
    private TextField txtCoin;
    @FXML
    private TextField txtStone;
    @FXML
    private TextField txtServant;
    @FXML
    private TextField txtShield;
    @FXML
    private Button btn_next;
    @FXML
    private Label lblNumAny;
    @FXML
    private Label lblReason;

    private HashMap<ResourceType, Integer> strongbox;
    private HashMap<ResourceType, Integer> warehouse;
    private HashMap<ResourceType, Integer> price;
    private int anyPayment, anyProduce;

    private HashMap<ResourceType, TextField> txtRes;

    public void initialize() {
        lblNumAny.setText("" + anyPayment);
        txtRes = new HashMap<>();
        txtRes.put(ResourceType.STONE, txtStone);
        txtRes.put(ResourceType.SHIELD, txtShield);
        txtRes.put(ResourceType.COIN, txtCoin);
        txtRes.put(ResourceType.SERVANT, txtServant);

        btn_next.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtnNext);
    }

    public void onBtnNext(Event event) {
        if(anyPayment == Integer.parseInt(txtCoin.getText()) + Integer.parseInt(txtShield.getText()) + Integer.parseInt(txtServant.getText()) + Integer.parseInt(txtStone.getText())) {
            for(ResourceType res: txtRes.keySet()) {
                if(price.get(res)!=null) price.replace(res, price.get(res) + Integer.parseInt(txtRes.get(res).getText()));
                else price.put(res, Integer.parseInt(txtRes.get(res).getText()));
            }
            btn_next.setDisable(true);
            txtCoin.setDisable(true);
            txtServant.setDisable(true);
            txtShield.setDisable(true);
            txtStone.setDisable(true);
            AskProductionSceneController apsc = new AskProductionSceneController();
            apsc.addAllObservers(observers);
            apsc.setStrongbox(strongbox);
            apsc.setWarehouse(warehouse);
            apsc.setPrice(price);
            apsc.setAnyProduce(anyProduce);
            Platform.runLater(() -> GuiManager.changeRootPane(apsc, "/fxml/chooseCard_production_scene"));
        }
        else {
            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("The sum of the inserted values must be equal to the number of any to be converted!");
            errorAlert.showAndWait();
        }
    }

    public void setStrongbox(HashMap<ResourceType, Integer> strongbox) {
        this.strongbox = strongbox;
    }

    public void setPrice(HashMap<ResourceType, Integer> price) {
        this.price = price;
    }

    public void setWarehouse(HashMap<ResourceType, Integer> warehouse) {
        this.warehouse = warehouse;
    }

    public void setAnyPayment(int anyPayment) {
        this.anyPayment = anyPayment;
    }

    public void setAnyProduce(int anyProduce) {
        this.anyProduce = anyProduce;
    }

}
