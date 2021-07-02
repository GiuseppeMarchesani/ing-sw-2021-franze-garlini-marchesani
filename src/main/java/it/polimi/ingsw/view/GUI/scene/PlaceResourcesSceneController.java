package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.MainApp;;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This Scene Controller is used to ask the player to choose where he wants place resources in warehouse.
 */
public class PlaceResourcesSceneController extends ObservableView implements GenericSceneController {

    @FXML
    private RadioButton radio_depot1_res1;
    @FXML
    private RadioButton radio_depot2_res1;
    @FXML
    private RadioButton radio_depot3_res1;
    @FXML
    private RadioButton radio_depot1_res2;
    @FXML
    private RadioButton radio_depot2_res2;
    @FXML
    private RadioButton radio_depot3_res2;
    @FXML
    private RadioButton radio_depot1_res3;
    @FXML
    private RadioButton radio_depot2_res3;
    @FXML
    private RadioButton radio_depot3_res3;
    @FXML
    private RadioButton radio_depot1_res4;
    @FXML
    private RadioButton radio_depot2_res4;
    @FXML
    private RadioButton radio_depot3_res4;
    @FXML
    private RadioButton radio1_res1;
    @FXML
    private RadioButton radio2_res1;
    @FXML
    private RadioButton radio3_res1;
    @FXML
    private RadioButton radio0_res1;
    @FXML
    private RadioButton radio1_res2;
    @FXML
    private RadioButton radio2_res2;
    @FXML
    private RadioButton radio3_res2;
    @FXML
    private RadioButton radio0_res2;
    @FXML
    private RadioButton radio1_res3;
    @FXML
    private RadioButton radio2_res3;
    @FXML
    private RadioButton radio3_res3;
    @FXML
    private RadioButton radio0_res3;
    @FXML
    private RadioButton radio1_res4;
    @FXML
    private RadioButton radio2_res4;
    @FXML
    private RadioButton radio3_res4;
    @FXML
    private RadioButton radio0_res4;
    @FXML
    private ImageView img_res1;
    @FXML
    private ImageView img_res2;
    @FXML
    private ImageView img_res3;
    @FXML
    private ImageView img_res4;
    @FXML
    private Label quantity_res1;
    @FXML
    private Label quantity_res2;
    @FXML
    private Label quantity_res3;
    @FXML
    private Label quantity_res4;
    @FXML
    private Button btm_next;
    @FXML
    private ToggleGroup depot_res1;
    @FXML
    private ToggleGroup depot_res2;
    @FXML
    private ToggleGroup depot_res3;
    @FXML
    private ToggleGroup depot_res4;
    @FXML
    private ToggleGroup quantity1;
    @FXML
    private ToggleGroup quantity2;
    @FXML
    private ToggleGroup quantity3;
    @FXML
    private ToggleGroup quantity4;


    private HashMap<ResourceType, Integer> resToPlace = new HashMap<>();
    private ArrayList<Integer> extraDepot = new ArrayList<>();
    private ArrayList<Integer> resQuantity = new ArrayList<>();
    private ArrayList<Image> imgRes = new ArrayList<>();
    private HashMap<Integer, ResourceType> floorResources = new HashMap<>();
    private HashMap<Integer, Integer> floorQuantity = new HashMap<>();
    private ArrayList<Integer> leaderDepotQuantity = new ArrayList<>();
    private int finalDiscard = 0;
    private ArrayList<ResourceType> resType = new ArrayList<>();
    private final Image imgEmpty = new Image(MainApp.class.getResourceAsStream("/images/xblack.png"));
    private HashMap<ResourceType, Integer> extraDepotRes = new HashMap<>();

    @FXML
    public void initialize(){
        ArrayList<ResourceType> toRemove = new ArrayList<>();
        radio1_res1.setSelected(true);
        radio1_res2.setSelected(true);
        radio1_res3.setSelected(true);
        radio1_res4.setSelected(true);
        for(ResourceType res: resToPlace.keySet()) {
            if(resToPlace.get(res)==0) toRemove.add(res);
        }
        for(ResourceType res: toRemove) {
            resToPlace.remove(res);
        }

        for(ResourceType res : resToPlace.keySet()){
            String resources;
            if(res.equals(ResourceType.STONE)){
                resources="STONE";
            }
            else if(res.equals(ResourceType.SERVANT)){
                resources = "SERVANT";
            }
            else if(res.equals(ResourceType.SHIELD)){
                resources = "SHIELD";

            }
            else{
                resources = "COIN";
            }
            Image image = new Image(MainApp.class.getResourceAsStream("/images/" + resources + ".png"));
            imgRes.add(image);

            resQuantity.add(resToPlace.get(res));
            resType.add(res);
        }
        while (imgRes.size()<4){
           imgRes.add(imgEmpty);
           resQuantity.add(0);
           resType.add(ResourceType.EMPTY);
        }
        for (int i=0; i<resQuantity.size(); i++){
            controlQuantity(i, resQuantity.get(i));
        }

        img_res1.setImage(imgRes.get(0));
        img_res2.setImage(imgRes.get(1));
        img_res3.setImage(imgRes.get(2));
        img_res4.setImage(imgRes.get(3));
        quantity_res1.setText(String.valueOf(resQuantity.get(0)));
        quantity_res2.setText(String.valueOf(resQuantity.get(1)));
        quantity_res3.setText(String.valueOf(resQuantity.get(2)));
        quantity_res4.setText(String.valueOf(resQuantity.get(3)));

        int k = 0;
        for(Image image: imgRes){
            if(image.equals(imgEmpty)){
                disableButton(k);
            }
            k++;
        }
        btm_next.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onNextBtm);
        radio_depot1_res1.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onRadioDepot1Res1);
        radio_depot1_res2.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onRadioDepot1Res2);
        radio_depot1_res3.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onRadioDepot1Res3);
        radio_depot1_res4.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onRadioDepot1Res4);
        radio_depot2_res1.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onRadioDepot2Res1);
        radio_depot2_res2.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onRadioDepot2Res2);
        radio_depot2_res3.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onRadioDepot2Res3);
        radio_depot2_res4.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onRadioDepot2Res4);
        radio_depot3_res1.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onRadioDepot3Res1);
        radio_depot3_res2.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onRadioDepot3Res2);
        radio_depot3_res3.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onRadioDepot3Res3);
        radio_depot3_res4.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onRadioDepot3Res4);
        radio0_res1.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onRadio0Res1);
        radio0_res2.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onRadio0Res2);
        radio0_res3.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onRadio0Res3);
        radio0_res4.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onRadio0Res4);
    }

    private void onRadioDepot1Res1(Event event) {
        radio0_res1.setSelected(false);
        radio1_res1.setSelected(true);
        radio_depot1_res2.setDisable(true);
        radio_depot1_res3.setDisable(true);
        radio_depot1_res4.setDisable(true);
        radio_depot1_res2.setSelected(false);
        radio_depot1_res3.setSelected(false);
        radio_depot1_res4.setSelected(false);

        int k=0;
        for(Image image: imgRes) {
            if(!image.equals(imgEmpty)) {
                enableButton(k, 1);
            }
            k++;
        }
    }

    private void onRadioDepot1Res2(Event event) {
        radio0_res2.setSelected(false);
        radio1_res2.setSelected(true);
        radio_depot1_res1.setDisable(true);
        radio_depot1_res3.setDisable(true);
        radio_depot1_res4.setDisable(true);
        radio_depot1_res1.setSelected(false);
        radio_depot1_res3.setSelected(false);
        radio_depot1_res4.setSelected(false);

        int k=0;
        for(Image image: imgRes) {
            if(!image.equals(imgEmpty)) {
                enableButton(k, 1);
            }
            k++;
        }
    }

    private void onRadioDepot1Res3(Event event) {
        radio0_res3.setSelected(false);
        radio1_res3.setSelected(true);
        radio_depot1_res2.setDisable(true);
        radio_depot1_res1.setDisable(true);
        radio_depot1_res4.setDisable(true);
        radio_depot1_res2.setSelected(false);
        radio_depot1_res1.setSelected(false);
        radio_depot1_res4.setSelected(false);

        int k=0;
        for(Image image: imgRes) {
            if(!image.equals(imgEmpty)) {
                enableButton(k, 1);
            }
            k++;
        }
    }

    private void onRadioDepot1Res4(Event event) {
        radio0_res4.setSelected(false);
        radio1_res4.setSelected(true);
        radio_depot1_res2.setDisable(true);
        radio_depot1_res3.setDisable(true);
        radio_depot1_res1.setDisable(true);
        radio_depot1_res2.setSelected(false);
        radio_depot1_res3.setSelected(false);
        radio_depot1_res1.setSelected(false);

        int k=0;
        for(Image image: imgRes) {
            if(!image.equals(imgEmpty)) {
                enableButton(k, 1);
            }
            k++;
        }
    }

    private void onRadioDepot2Res1(Event event) {
        radio0_res1.setSelected(false);
        radio1_res1.setSelected(true);
        radio_depot2_res2.setDisable(true);
        radio_depot2_res3.setDisable(true);
        radio_depot2_res4.setDisable(true);
        radio_depot2_res2.setSelected(false);
        radio_depot2_res3.setSelected(false);
        radio_depot2_res4.setSelected(false);

        int k=0;
        for(Image image: imgRes) {
            if(!image.equals(imgEmpty)) {
                enableButton(k, 2);
            }
            k++;
        }
    }

    private void onRadioDepot2Res2(Event event) {
        radio0_res2.setSelected(false);
        radio1_res2.setSelected(true);
        radio_depot2_res1.setDisable(true);
        radio_depot2_res3.setDisable(true);
        radio_depot2_res4.setDisable(true);
        radio_depot2_res1.setSelected(false);
        radio_depot2_res3.setSelected(false);
        radio_depot2_res4.setSelected(false);

        int k=0;
        for(Image image: imgRes) {
            if(!image.equals(imgEmpty)) {
                enableButton(k, 2);
            }
            k++;
        }
    }

    private void onRadioDepot2Res3(Event event) {
        radio0_res3.setSelected(false);
        radio1_res3.setSelected(true);
        radio_depot2_res2.setDisable(true);
        radio_depot2_res1.setDisable(true);
        radio_depot2_res4.setDisable(true);
        radio_depot2_res2.setSelected(false);
        radio_depot2_res1.setSelected(false);
        radio_depot2_res4.setSelected(false);

        int k=0;
        for(Image image: imgRes) {
            if(!image.equals(imgEmpty)) {
                enableButton(k, 2);
            }
            k++;
        }
    }

    private void onRadioDepot2Res4(Event event) {
        radio0_res4.setSelected(false);
        radio1_res4.setSelected(true);
        radio_depot2_res2.setDisable(true);
        radio_depot2_res1.setDisable(true);
        radio_depot2_res3.setDisable(true);
        radio_depot2_res2.setSelected(false);
        radio_depot2_res1.setSelected(false);
        radio_depot2_res3.setSelected(false);

        int k=0;
        for(Image image: imgRes) {
            if(!image.equals(imgEmpty)) {
                enableButton(k, 2);
            }
            k++;
        }
    }

    private void onRadioDepot3Res1(Event event) {
        radio0_res1.setSelected(false);
        radio1_res1.setSelected(true);
        radio_depot3_res2.setDisable(true);
        radio_depot3_res3.setDisable(true);
        radio_depot3_res4.setDisable(true);
        radio_depot3_res2.setSelected(false);
        radio_depot3_res3.setSelected(false);
        radio_depot3_res4.setSelected(false);

        int k=0;
        for(Image image: imgRes) {
            if(!image.equals(imgEmpty)) {
                enableButton(k, 3);
            }
            k++;
        }
    }

    private void onRadioDepot3Res2(Event event) {
        radio0_res2.setSelected(false);
        radio1_res2.setSelected(true);
        radio_depot3_res1.setDisable(true);
        radio_depot3_res3.setDisable(true);
        radio_depot3_res4.setDisable(true);
        radio_depot3_res1.setSelected(false);
        radio_depot3_res3.setSelected(false);
        radio_depot3_res4.setSelected(false);

        int k=0;
        for(Image image: imgRes) {
            if(!image.equals(imgEmpty)) {
                enableButton(k, 3);
            }
            k++;
        }
    }

    private void onRadioDepot3Res3(Event event) {
        radio0_res3.setSelected(false);
        radio1_res3.setSelected(true);
        radio_depot3_res1.setDisable(true);
        radio_depot3_res2.setDisable(true);
        radio_depot3_res4.setDisable(true);
        radio_depot3_res1.setSelected(false);
        radio_depot3_res2.setSelected(false);
        radio_depot3_res4.setSelected(false);

        int k=0;
        for(Image image: imgRes) {
            if(!image.equals(imgEmpty)) {
                enableButton(k, 3);
            }
            k++;
        }
    }

    private void onRadioDepot3Res4(Event event) {
        radio0_res4.setSelected(false);
        radio1_res4.setSelected(true);
        radio_depot3_res1.setDisable(true);
        radio_depot3_res2.setDisable(true);
        radio_depot3_res3.setDisable(true);
        radio_depot3_res1.setSelected(false);
        radio_depot3_res2.setSelected(false);
        radio_depot3_res3.setSelected(false);

        int k=0;
        for(Image image: imgRes) {
            if(!image.equals(imgEmpty)) {
                enableButton(k, 3);
            }
            k++;
        }
    }

    private void onRadio0Res1(Event event) {
        radio_depot1_res1.setSelected(false);
        radio_depot2_res1.setSelected(false);
        radio_depot3_res1.setSelected(false);
    }

    private void onRadio0Res2(Event event) {
        radio_depot1_res2.setSelected(false);
        radio_depot2_res2.setSelected(false);
        radio_depot3_res2.setSelected(false);
    }

    private void onRadio0Res3(Event event) {
        radio_depot1_res3.setSelected(false);
        radio_depot2_res3.setSelected(false);
        radio_depot3_res3.setSelected(false);
    }

    private void onRadio0Res4(Event event) {
        radio_depot1_res4.setSelected(false);
        radio_depot2_res4.setSelected(false);
        radio_depot3_res4.setSelected(false);
    }

    /**
     * Handles the click on the Next button.
     * @param event the mouse click event.
     */
    private void onNextBtm(Event event){
        boolean invalidInput = false;
        for(int j=0; j<3; j++) {
            floorQuantity.put(j, 0);
            floorResources.put(j, ResourceType.EMPTY);
        }

        for(int i=0; i<imgRes.size(); i++) {

            if(!imgRes.get(i).equals(imgEmpty)) {
                if(i==0 && !radio0_res1.isSelected() && depot_res1.getSelectedToggle()!=null && quantity1.getSelectedToggle()!=null) {
                    RadioButton selectedDepotRes1 = (RadioButton) depot_res1.getSelectedToggle();
                    int numDepot = Integer.parseInt(selectedDepotRes1.getText()) -1;
                    RadioButton selectedQuantityRes1 = (RadioButton) quantity1.getSelectedToggle();
                    int numRes = Integer.parseInt(selectedQuantityRes1.getText());
                    int quantityDep = 3 - numDepot;
                    floorResources.put(numDepot, resType.get(i));
                    floorQuantity.put(numDepot, Math.min(numRes, quantityDep));
                    finalDiscard += Integer.parseInt(quantity_res1.getText()) - Math.min(numRes, quantityDep);
                }
                else if(i==1 && !radio0_res2.isSelected() && depot_res2.getSelectedToggle()!=null && quantity2.getSelectedToggle()!=null) {
                    RadioButton selectedDepotRes2 = (RadioButton) depot_res2.getSelectedToggle();
                    int numDepot = Integer.parseInt(selectedDepotRes2.getText()) -1;
                    RadioButton selectedQuantityRes2 = (RadioButton) quantity2.getSelectedToggle();
                    int numRes = Integer.parseInt(selectedQuantityRes2.getText());
                    int quantityDep = 3 - numDepot;
                    floorResources.put(numDepot, resType.get(i));
                    floorQuantity.put(numDepot, Math.min(numRes, quantityDep));
                    finalDiscard += Integer.parseInt(quantity_res2.getText()) - Math.min(numRes, quantityDep);
                }
                else if(i==2 && !radio0_res3.isSelected() && depot_res3.getSelectedToggle()!=null && quantity3.getSelectedToggle()!=null) {
                    RadioButton selectedDepotRes3 = (RadioButton) depot_res3.getSelectedToggle();
                    int numDepot = Integer.parseInt(selectedDepotRes3.getText()) -1;
                    RadioButton selectedQuantityRes3 = (RadioButton) quantity3.getSelectedToggle();
                    int numRes = Integer.parseInt(selectedQuantityRes3.getText());
                    int quantityDep = 3 - numDepot;
                    floorResources.put(numDepot, resType.get(i));
                    floorQuantity.put(numDepot, Math.min(numRes, quantityDep));
                    finalDiscard += Integer.parseInt(quantity_res3.getText()) - Math.min(numRes, quantityDep);
                }
                else if(i==3 && !radio0_res4.isSelected() && depot_res4.getSelectedToggle()!=null && quantity4.getSelectedToggle()!=null) {
                    RadioButton selectedDepotRes4 = (RadioButton) depot_res4.getSelectedToggle();
                    int numDepot = Integer.parseInt(selectedDepotRes4.getText()) -1;
                    RadioButton selectedQuantityRes4 = (RadioButton) quantity4.getSelectedToggle();
                    int numRes = Integer.parseInt(selectedQuantityRes4.getText());
                    int quantityDep = 3 - numDepot;
                    floorResources.put(numDepot, resType.get(i));
                    floorQuantity.put(numDepot, Math.min(numRes, quantityDep));
                    finalDiscard += Integer.parseInt(quantity_res4.getText()) - Math.min(numRes, quantityDep);
                }
                else if(radio0_res1.isSelected()) {
                    finalDiscard += Integer.parseInt(quantity_res1.getText());
                }
                else if(radio0_res2.isSelected()) {
                    finalDiscard += Integer.parseInt(quantity_res2.getText());
                }
                else if(radio0_res3.isSelected()) {
                    finalDiscard += Integer.parseInt(quantity_res3.getText());
                }
                else if(radio0_res4.isSelected()) {
                    finalDiscard += Integer.parseInt(quantity_res4.getText());
                }
                else {
                    invalidInput = true;
                    Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                    errorAlert.setHeaderText("Input not valid");
                    errorAlert.setContentText("You must choose a slot!");
                    errorAlert.showAndWait();
                }
            }
        }
        if(!invalidInput) notifyObserver(obs -> obs.updateWarehouse(floorResources, floorQuantity, extraDepot, finalDiscard));
    }

    private void enableButton(int k, int i) {
        if(i==1) {
            if (k == 0) {
                radio_depot2_res1.setDisable(false);
                radio_depot3_res1.setDisable(false);
            } else if (k == 1) {
                radio_depot2_res2.setDisable(false);
                radio_depot3_res2.setDisable(false);
            } else if (k == 2) {
                radio_depot2_res3.setDisable(false);
                radio_depot3_res3.setDisable(false);
            } else if (k == 3) {
                radio_depot2_res4.setDisable(false);
                radio_depot3_res4.setDisable(false);
            }
        }
        else if(i==2) {
            if (k == 0) {
                radio_depot1_res1.setDisable(false);
                radio_depot3_res1.setDisable(false);
            } else if (k == 1) {
                radio_depot1_res2.setDisable(false);
                radio_depot3_res2.setDisable(false);
            } else if (k == 2) {
                radio_depot1_res3.setDisable(false);
                radio_depot3_res3.setDisable(false);
            } else if (k == 3) {
                radio_depot1_res4.setDisable(false);
                radio_depot3_res4.setDisable(false);
            }
        }
        else if(i==3) {
            if (k == 0) {
                radio_depot2_res1.setDisable(false);
                radio_depot1_res1.setDisable(false);
            } else if (k == 1) {
                radio_depot2_res2.setDisable(false);
                radio_depot1_res2.setDisable(false);
            } else if (k == 2) {
                radio_depot2_res3.setDisable(false);
                radio_depot1_res3.setDisable(false);
            } else if (k == 3) {
                radio_depot2_res4.setDisable(false);
                radio_depot1_res4.setDisable(false);
            }
        }
    }

    private void disableButton(int k) {
        if(k==0){
            radio0_res1.setDisable(true);
            radio1_res1.setDisable(true);
            radio2_res1.setDisable(true);
            radio3_res1.setDisable(true);
            radio_depot1_res1.setDisable(true);
            radio_depot2_res1.setDisable(true);
            radio_depot3_res1.setDisable(true);
        }
        else if(k==1){
            radio0_res2.setDisable(true);
            radio1_res2.setDisable(true);
            radio2_res2.setDisable(true);
            radio3_res2.setDisable(true);
            radio_depot1_res2.setDisable(true);
            radio_depot2_res2.setDisable(true);
            radio_depot3_res2.setDisable(true);
        }
        else if (k==2){
            radio0_res3.setDisable(true);
            radio1_res3.setDisable(true);
            radio2_res3.setDisable(true);
            radio3_res3.setDisable(true);
            radio_depot1_res3.setDisable(true);
            radio_depot2_res3.setDisable(true);
            radio_depot3_res3.setDisable(true);
        }
        else if(k==3) {
            radio0_res4.setDisable(true);
            radio1_res4.setDisable(true);
            radio2_res4.setDisable(true);
            radio3_res4.setDisable(true);
            radio_depot1_res4.setDisable(true);
            radio_depot2_res4.setDisable(true);
            radio_depot3_res4.setDisable(true);
        }
    }


    private void controlQuantity(int n, int k){
        if(n==0){
            if(k==1){
                radio2_res1.setDisable(true);
                radio3_res1.setDisable(true);
            }
            else if(k==2){
                radio3_res1.setDisable(true);
            }
        }
        else if(n==1){
            if(k==1){
                radio2_res2.setDisable(true);
                radio3_res2.setDisable(true);
            }
            else if(k==2){
                radio3_res2.setDisable(true);
            }
        }
        else if(n==2){
            if(k==1){
                radio2_res3.setDisable(true);
                radio3_res3.setDisable(true);
            }
            else if(k==2){
                radio3_res3.setDisable(true);
            }
        }
        else if(n==3){
            if(k==1){
                radio2_res4.setDisable(true);
                radio3_res4.setDisable(true);
            }
            else if(k==2){
                radio3_res4.setDisable(true);
            }
        }
    }

    public void setResToPlace(HashMap<ResourceType, Integer> resToPlace){
        this.resToPlace=resToPlace;
    }
    public void setExtraDepot(ArrayList<Integer> extraDepot){
        this.extraDepot=extraDepot;
    }

}
