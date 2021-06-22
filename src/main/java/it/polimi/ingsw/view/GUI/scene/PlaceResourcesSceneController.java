package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.Gui;
import it.polimi.ingsw.view.GUI.MainApp;;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class PlaceResourcesSceneController extends ObservableView implements GenericSceneController {


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
    private RadioButton radio2_extra_res4;
    @FXML
    private RadioButton radio1_extra_res4;
    @FXML
    private RadioButton radio0_extra_res4;
    @FXML
    private RadioButton radio2_extra_res3;
    @FXML
    private RadioButton radio1_extra_res3;
    @FXML
    private RadioButton radio0_extra_res3;
    @FXML
    private RadioButton radio2_extra_res2;
    @FXML
    private RadioButton radio1_extra_res2;
    @FXML
    private RadioButton radio0_extra_res2;
    @FXML
    private RadioButton radio2_extra_res1;
    @FXML
    private RadioButton radio1_extra_res1;
    @FXML
    private RadioButton radio0_extra_res1;
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
    @FXML
    private ToggleGroup extraDepot1;
    @FXML
    private ToggleGroup extraDepot2;
    @FXML
    private ToggleGroup extraDepot3;
    @FXML
    private ToggleGroup extraDepot4;


    private HashMap<ResourceType, Integer> resToPlace = new HashMap<>();
    private ArrayList<ResourceType> extraDepot = new ArrayList<>();
    private ArrayList<Integer> resQuantity = new ArrayList<>();
    private ArrayList<Image> imgRes = new ArrayList<>();
    @FXML
    private void initialize(){
        resToPlace.putAll(Gui.getResToPlace());
        extraDepot.addAll(Gui.getExtraDepot());

        for(ResourceType res : resToPlace.keySet()){
           Image image = new Image(MainApp.class.getResourceAsStream("/images/" + res.toString() + ".png"));
           imgRes.add(image);
           resQuantity.add(resToPlace.get(res));
        }
        while (imgRes.size()<4){
           imgRes.add(new Image(MainApp.class.getResourceAsStream("/images/xblack.png")));
           resQuantity.add(0);
        }

        img_res1.setImage(imgRes.get(0));
        img_res2.setImage(imgRes.get(1));
        img_res3.setImage(imgRes.get(2));
        img_res4.setImage(imgRes.get(3));
        quantity_res1.setText(String.valueOf(resQuantity.get(0)));
        quantity_res2.setText(String.valueOf(resQuantity.get(1)));
        quantity_res3.setText(String.valueOf(resQuantity.get(2)));
        quantity_res4.setText(String.valueOf(resQuantity.get(3)));

        if(extraDepot.size()==0){
            radio0_extra_res4.setDisable(true);
            radio1_extra_res4.setDisable(true);
            radio2_extra_res4.setDisable(true);
            radio0_extra_res3.setDisable(true);
            radio1_extra_res3.setDisable(true);
            radio2_extra_res3.setDisable(true);
            radio0_extra_res2.setDisable(true);
            radio1_extra_res2.setDisable(true);
            radio2_extra_res2.setDisable(true);
            radio0_extra_res1.setDisable(true);
            radio1_extra_res1.setDisable(true);
            radio2_extra_res1.setDisable(true);
        }
        else if(extraDepot.size()==1){
            for(ResourceType res: resToPlace.keySet()){
                if(res.equals(extraDepot.get(0))){
                    //TODO
                }
            }
        }
        btm_next.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onNextBtm);
    }

    private void onNextBtm(Event event){
        RadioButton chooseQuantity1 = (RadioButton) quantity1.getSelectedToggle();
        RadioButton chooseQuantity2 = (RadioButton) quantity2.getSelectedToggle();
        RadioButton chooseQuantity3 = (RadioButton) quantity3.getSelectedToggle();
        RadioButton chooseQuantity4 = (RadioButton) quantity4.getSelectedToggle();
        RadioButton chooseDepot1 = (RadioButton) depot_res1.getSelectedToggle();
        RadioButton chooseDepot2 = (RadioButton) depot_res2.getSelectedToggle();
        RadioButton chooseDepot3 = (RadioButton) depot_res3.getSelectedToggle();
        RadioButton chooseDepot4 = (RadioButton) depot_res4.getSelectedToggle();
        RadioButton chooseExtraDepot1 = (RadioButton) extraDepot1.getSelectedToggle();
        RadioButton chooseExtraDepot2 = (RadioButton) extraDepot2.getSelectedToggle();
        RadioButton chooseExtraDepot3 = (RadioButton) extraDepot3.getSelectedToggle();
        RadioButton chooseExtraDepot4 = (RadioButton) extraDepot4.getSelectedToggle();
        String qt1 = chooseQuantity1.getId();
        String qt2 = chooseQuantity2.getId();
        String qt3= chooseQuantity3.getId();
        String qt4= chooseQuantity4.getId();
        


    }
}
