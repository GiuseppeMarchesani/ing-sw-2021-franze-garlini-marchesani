package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.Gui;
import it.polimi.ingsw.view.GUI.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.HashMap;


public class askSlotSceneController extends ObservableView implements GenericSceneController {
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
    @FXML
    private RadioButton radio_slot1;
    @FXML
    private RadioButton radio_slot2;
    @FXML
    private RadioButton radio_slot3;


    private final ToggleGroup group = new ToggleGroup();
    private ArrayList<Image> imgRes = new ArrayList<>();
    private ArrayList<Integer> resQuantity = new ArrayList<>();
    private ArrayList<ResourceType> resType = new ArrayList<>();
    private final Image imgEmpty = new Image(MainApp.class.getResourceAsStream("/images/xblack.png"));
    private HashMap<ResourceType, Integer> strongbox= new HashMap<>();
    private HashMap<ResourceType, Integer> cardCost = new HashMap<>();
    private ArrayList<Integer> availableSlots = new ArrayList<>();
    private HashMap<ResourceType, Integer> warehouse = new HashMap<>();

    @FXML
    public void initialize(){
        radio_slot1.setDisable(true);
        radio_slot2.setDisable(true);
        radio_slot3.setDisable(true);
        radio_slot1.setToggleGroup(group);
        radio_slot2.setToggleGroup(group);
        radio_slot3.setToggleGroup(group);
        if(strongbox.isEmpty()){
            txt_pay_strong1.setEditable(false);
            txt_pay_strong2.setEditable(false);
            txt_pay_strong3.setEditable(false);
            txt_pay_strong4.setEditable(false);
        }

        for(ResourceType res: cardCost.keySet()){
            Image image = new Image(MainApp.class.getResourceAsStream("/images/" + res.toString() + ".png"));
            imgRes.add(image);
            resQuantity.add(cardCost.get(res));
            resType.add(res);
        }
        while (imgRes.size()<4){
            imgRes.add(imgEmpty);
            resQuantity.add(0);
            resType.add(ResourceType.EMPTY);
        }

    }
    public void setStrongbox(HashMap<ResourceType, Integer> strongbox){
        this.strongbox=strongbox;
    }
    public void setCardCost(HashMap<ResourceType, Integer> cardCost){
        this.cardCost = cardCost;
    }
    public void setAvailableSlots(ArrayList<Integer> availableSlots){
        this.availableSlots = availableSlots;
    }
    public void setWarehouse(HashMap<ResourceType, Integer> warehouse){
        this.warehouse = warehouse;
    }
}
