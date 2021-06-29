package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.MainApp;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.HashMap;


public class AskSlotSceneController extends ObservableView implements GenericSceneController {
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

    HashMap<ResourceType, Integer> newStrongbox;
    HashMap<ResourceType, Integer> paymentWarehouse;

    private ArrayList<Label> lblAvaStrong;
    private ArrayList<Label> lblAvaWare;
    private ArrayList<Label> lblResToPay;
    private ArrayList<TextField> textStrongbox;
    private ArrayList<TextField> textWarehouse;
    private ArrayList<RadioButton> radioSlots;
    private ArrayList<ImageView> imgList;

    @FXML
    public void initialize(){
        lblAvaStrong = new ArrayList<>();
        lblAvaStrong.add(ava_strong_res1);
        lblAvaStrong.add(ava_strong_res2);
        lblAvaStrong.add(ava_strong_res3);
        lblAvaStrong.add(ava_strong_res4);

        lblAvaWare = new ArrayList<>();
        lblAvaWare.add(ava_ware_res1);
        lblAvaWare.add(ava_ware_res2);
        lblAvaWare.add(ava_ware_res3);
        lblAvaWare.add(ava_ware_res4);

        lblResToPay = new ArrayList<>();
        lblResToPay.add(num_res1);
        lblResToPay.add(num_res2);
        lblResToPay.add(num_res3);
        lblResToPay.add(num_res4);

        textStrongbox = new ArrayList<>();
        textStrongbox.add(txt_pay_strong1);
        textStrongbox.add(txt_pay_strong2);
        textStrongbox.add(txt_pay_strong3);
        textStrongbox.add(txt_pay_strong4);

        textWarehouse = new ArrayList<>();
        textWarehouse.add(txt_pay_ware1);
        textWarehouse.add(txt_pay_ware2);
        textWarehouse.add(txt_pay_ware3);
        textWarehouse.add(txt_pay_ware4);

        radioSlots = new ArrayList<>();
        radioSlots.add(radio_slot1);
        radioSlots.add(radio_slot2);
        radioSlots.add(radio_slot3);

        imgList = new ArrayList<>();
        imgList.add(img_res1);
        imgList.add(img_res2);
        imgList.add(img_res3);
        imgList.add(img_res4);

        //Disabling radio button for choosing slot
        for(RadioButton radioButton: radioSlots) {
            radioButton.setDisable(true);
            radioButton.setToggleGroup(group);
        }


        if(strongbox.isEmpty()){
            for(TextField textField: textStrongbox) {
                textField.setEditable(false);
                textField.setText("0");
            }

        }

        btn_next.setDisable(true);

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

        int i=0;
        for(Image img: imgRes) {
            imgList.get(i).setImage(img);
            i++;
        }

        i=0;
        for(Label lbl: lblResToPay) {
            lbl.setText(resQuantity.get(i).toString());
            i++;
        }


        for(i=0; i<textStrongbox.size(); i++) {
            if(resType.get(i).equals(ResourceType.EMPTY) || strongbox.get(resType.get(i))==null ) {
                textStrongbox.get(i).setText("0");
                lblAvaStrong.get(i).setText("0");
                textStrongbox.get(i).setDisable(true);
            }
            else lblAvaStrong.get(i).setText(strongbox.get(resType.get(i)).toString());
        }

        for(i=0; i<textWarehouse.size(); i++) {
            if(resType.get(i).equals(ResourceType.EMPTY) || warehouse.get(resType.get(i))==null ) {
                textWarehouse.get(i).setText("0");
                lblAvaWare.get(i).setText("0");
                textWarehouse.get(i).setDisable(true);
            }
            else lblAvaWare.get(i).setText(warehouse.get(resType.get(i)).toString());
        }

        btn_pay.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtnPay);
        btn_next.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtnNext);
    }

    public void onBtnPay(Event event) {
        newStrongbox = new HashMap<>(strongbox);
        paymentWarehouse = new HashMap<>();

        boolean wrongInput = false;
        int fromWare;
        int fromStrong;
        for(int i=0; i<resType.size(); i++) {
            if(!resType.get(i).equals(ResourceType.EMPTY)) {
                try {
                    fromWare = Integer.parseInt(textWarehouse.get(i).getText());
                    fromStrong = Integer.parseInt(textStrongbox.get(i).getText());
                } catch (NumberFormatException e) {
                    wrongInput = true;
                    break;
                }
                if(resQuantity.get(i) == fromWare + fromStrong) {
                    newStrongbox.replace(resType.get(i), newStrongbox.get(resType.get(i)) - fromStrong);
                    paymentWarehouse.put(resType.get(i), fromWare);
                }
                else {
                    wrongInput = true;
                    break;
                }
            }
        }
        if(wrongInput) {
            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Check your payment option!");
            errorAlert.showAndWait();
        }
        else{
            btn_next.setDisable(false);
            for(TextField txt: textStrongbox){
                txt.setDisable(true);
            }
            for(TextField txt: textWarehouse){
                txt.setDisable(true);
            }
            for(int i=0; i<availableSlots.size(); i++) {
                radioSlots.get(availableSlots.get(i)).setDisable(false);
            }
        }
    }

    public void onBtnNext(Event event) {
        int chosenSlot = -1;
        for(RadioButton radio: radioSlots) {
            if(radio.isSelected()) {
                chosenSlot = radioSlots.indexOf(radio);
                break;
            }
        }
        if(chosenSlot==-1) {
            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("You must choose a slot!");
            errorAlert.showAndWait();
        }
        else {
            int finalChosenSlot = chosenSlot;
            notifyObserver(obs -> obs.updatePlaceDevCard(paymentWarehouse, newStrongbox, finalChosenSlot));
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
