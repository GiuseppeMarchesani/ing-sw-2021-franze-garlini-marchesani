package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.MainApp;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
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
    private Label lblcards1;
    @FXML
    private Label lblcards2;
    @FXML
    private CheckBox check_card1;
    @FXML
    private CheckBox check_card2;
    @FXML
    private CheckBox check_card3;
    @FXML
    private CheckBox check_card4;
    @FXML
    private CheckBox check_card5;
    @FXML
    private CheckBox check_scroll;
    @FXML
    private Button btn_ok;
    @FXML
    private Button btn_pay;
    @FXML
    private Button btn_next;

    private HashMap<ResourceType, Integer> strongbox;
    private HashMap<ResourceType, Integer> warehouse;
    private HashMap<ResourceType, Integer> price;
    private int anyProduce;

    private final ToggleGroup group = new ToggleGroup();
    private ArrayList<Image> imgRes = new ArrayList<>();
    private ArrayList<Integer> resQuantity = new ArrayList<>();
    private ArrayList<ResourceType> resType = new ArrayList<>();
    private final Image imgEmpty = new Image(MainApp.class.getResourceAsStream("/images/xblack.png"));

    private ArrayList<Label> lblAvaStrong;
    private ArrayList<Label> lblAvaWare;
    private ArrayList<Label> lblResToPay;
    private ArrayList<TextField> textStrongbox;
    private ArrayList<TextField> textWarehouse;
    private ArrayList<ImageView> imgList;

    public void initialize() {
        check_card1.setVisible(false);
        check_card2.setVisible(false);
        check_card3.setVisible(false);
        check_card4.setVisible(false);
        check_card5.setVisible(false);
        check_scroll.setVisible(false);
        lblcards1.setVisible(false);
        lblcards2.setVisible(false);
        btn_ok.setVisible(false);

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

        imgList = new ArrayList<>();
        imgList.add(img_res1);
        imgList.add(img_res2);
        imgList.add(img_res3);
        imgList.add(img_res4);

        if(strongbox.isEmpty()){
            for(TextField textField: textStrongbox) {
                textField.setEditable(false);
                textField.setText("0");
            }
        }

        for(ResourceType res: price.keySet()){
            Image image = new Image(MainApp.class.getResourceAsStream("/images/" + res.toString() + ".png"));
            imgRes.add(image);
            resQuantity.add(price.get(res));
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
    }

    public void onBtnPay(Event event) {
        HashMap<ResourceType, Integer> newStrongbox = new HashMap<>(strongbox);
        HashMap<ResourceType, Integer> paymentWarehouse = new HashMap<>();

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
                    if(newStrongbox.get(resType.get(i))!=null) {
                        newStrongbox.replace(resType.get(i), newStrongbox.get(resType.get(i)) - fromStrong);
                    }
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
        } else{
            if(anyProduce > 0) {
                AnyForProdIncSceneController apisc = new AnyForProdIncSceneController();
                apisc.addAllObservers(observers);
                apisc.setStrongbox(newStrongbox);
                apisc.setWarehouse(paymentWarehouse);
                apisc.setAnyProduce(anyProduce);
                Platform.runLater(() -> SceneController.changeRootPane(apisc, "/fxml/anyForProduction_scene"));
            }
            else notifyObserver(obs -> obs.updateGetProdRes(newStrongbox, paymentWarehouse));
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

    public void setAnyProduce(int anyProduce) {
        this.anyProduce = anyProduce;
    }
}
