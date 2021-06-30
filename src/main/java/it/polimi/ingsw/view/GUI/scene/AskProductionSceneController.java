package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.ResourceBundle;

public class AskProductionSceneController extends ObservableView implements GenericSceneController{
    @FXML
    private ImageView img_res1;
    @FXML
    private ImageView img_res2;
    @FXML
    private ImageView img_res3;
    @FXML
    private ImageView img_res4;
    @FXML
    private Label num_res1;
    @FXML
    private Label num_res2;
    @FXML
    private Label num_res3;
    @FXML
    private Label num_res4;
    @FXML
    private TextField txt_pay_ware4;
    @FXML
    private TextField txt_pay_ware3;
    @FXML
    private TextField txt_pay_ware2;
    @FXML
    private TextField txt_pay_ware1;
    @FXML
    private TextField txt_pay_strong1;
    @FXML
    private TextField txt_pay_strong2;
    @FXML
    private TextField txt_pay_strong3;
    @FXML
    private TextField txt_pay_strong4;
    @FXML
    private Label ava_ware_res1;
    @FXML
    private Label ava_ware_res2;
    @FXML
    private Label ava_ware_res3;
    @FXML
    private Label ava_ware_res4;
    @FXML
    private Label ava_strong_res1;
    @FXML
    private Label ava_strong_res2;
    @FXML
    private Label ava_strong_res3;
    @FXML
    private Label ava_strong_res4;
    @FXML
    private Button btn_pay;
    @FXML
    private Button btn_next;

    HashMap<ResourceType, Integer> strongbox;
    HashMap<ResourceType, Integer> warehouse;
    HashMap<ResourceType, Integer> price;
    int anyPayment, anyProduce;

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
