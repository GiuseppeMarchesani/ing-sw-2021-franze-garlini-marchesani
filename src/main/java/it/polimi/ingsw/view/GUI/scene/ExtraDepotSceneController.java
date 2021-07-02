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

import java.util.ArrayList;
import java.util.HashMap;

public class ExtraDepotSceneController extends ObservableView implements GenericSceneController {

    @FXML
    private Button btn_ok;
    @FXML
    private Label num_coin;
    @FXML
    private Label num_stone;
    @FXML
    private Label num_shield;
    @FXML
    private Label num_servant;
    @FXML
    private TextField txt_coin;
    @FXML
    private TextField txt_stone;
    @FXML
    private TextField txt_shield;
    @FXML
    private TextField txt_servant;

    private ArrayList<ResourceType> extraDepot = new ArrayList<>();
    private HashMap<ResourceType, Integer> resToPlace = new HashMap<>();
    private ArrayList<TextField> chosenQuantity = new ArrayList<>();
    private HashMap<ResourceType, Integer> extraDepotRes = new HashMap<>();

    @FXML
    public void initialize(){
        ArrayList<ResourceType> toRemove = new ArrayList<>();
        for(ResourceType res: resToPlace.keySet()) {
            if(resToPlace.get(res)==0) toRemove.add(res);
        }
        for(ResourceType res: toRemove) {
            resToPlace.remove(res);
        }

        chosenQuantity.add(txt_coin);
        chosenQuantity.add(txt_stone);
        chosenQuantity.add(txt_shield);
        chosenQuantity.add(txt_servant);
        txt_servant.setDisable(true);
        txt_coin.setDisable(true);
        txt_shield.setDisable(true);
        txt_stone.setDisable(true);
        txt_servant.setText("0");
        txt_coin.setText("0");
        txt_shield.setText("0");
        txt_stone.setText("0");
        for(ResourceType res: resToPlace.keySet()){
            if(res.equals(ResourceType.COIN)){
                num_coin.setText(String.valueOf(resToPlace.get(res)));
            }
            else if(res.equals(ResourceType.STONE)){
                num_stone.setText(String.valueOf(resToPlace.get(res)));
            }
            else if(res.equals(ResourceType.SHIELD)){
                num_shield.setText(String.valueOf(resToPlace.get(res)));
            }
            else {
                num_servant.setText(String.valueOf(resToPlace.get(res)));
            }
        }
        for(ResourceType res: extraDepot){
            if(res.equals(ResourceType.COIN)){
                txt_coin.setDisable(false);
            }
            else if(res.equals(ResourceType.SHIELD)){
                txt_shield.setDisable(false);
            }
            else if(res.equals(ResourceType.STONE)){
                txt_stone.setDisable(false);
            }
            else if(res.equals(ResourceType.SERVANT)){
                txt_servant.setDisable(false);
            }
        }
        btn_ok.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onBtnOk);
    }

    private void onBtnOk(Event event){
        boolean wrongInput = false;
        ArrayList<Integer> tmp= new ArrayList<>();
        String num;
        for(ResourceType res : extraDepot){
            if(res.equals(ResourceType.COIN)){
                if(Integer.parseInt(txt_coin.getText())>2){
                    wrongInput=true;
                }
                else if(Integer.parseInt(txt_coin.getText())>resToPlace.get(res)){
                    wrongInput = true;
                }
                else {
                    num = txt_coin.getText();
                    extraDepotRes.put(res, Integer.parseInt(num));
                    tmp.add(Integer.parseInt(num));
                    resToPlace.replace(res, resToPlace.get(res)-Integer.parseInt(num));
                }
            }
            else if(res.equals(ResourceType.STONE)){
                if(Integer.parseInt(txt_stone.getText())>2){
                    wrongInput=true;
                }
                else if(Integer.parseInt(txt_stone.getText())>resToPlace.get(res)){
                    wrongInput = true;
                }
                else {
                    num = txt_stone.getText();
                    extraDepotRes.put(res, Integer.parseInt(num));
                    resToPlace.replace(res, resToPlace.get(res) - Integer.parseInt(num));
                    tmp.add(Integer.parseInt(num));
                }
            }
            else if (res.equals(ResourceType.SHIELD)){
                if(Integer.parseInt(txt_shield.getText())>2){
                    wrongInput=true;
                }
                else if(Integer.parseInt(txt_shield.getText())>resToPlace.get(res)){
                    wrongInput = true;
                }
                else {
                    num = txt_shield.getText();
                    extraDepotRes.put(res, Integer.parseInt(num));
                    resToPlace.replace(res, resToPlace.get(res) - Integer.parseInt(num));
                    tmp.add(Integer.parseInt(num));
                }

            }
            else if(res.equals(ResourceType.SERVANT)){
                if(Integer.parseInt(txt_servant.getText())>2){
                    wrongInput=true;
                }
                else if(Integer.parseInt(txt_servant.getText())>resToPlace.get(res)){
                    wrongInput = true;
                }
                else {
                    num = txt_servant.getText();
                    extraDepotRes.put(res, Integer.parseInt(num));
                    resToPlace.replace(res, resToPlace.get(res) - Integer.parseInt(num));
                    tmp.add(Integer.parseInt(num));
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
            PlaceResourcesSceneController prsc = new PlaceResourcesSceneController();
            prsc.addAllObservers(observers);
            prsc.setResToPlace(resToPlace);
            prsc.setExtraDepot(tmp);
            Platform.runLater(() ->
                    GuiManager.changeRootPane(prsc, "/fxml/place_resources_scene")
            );
        }
    }
    public HashMap<ResourceType, Integer> getExtraDepotRes(){
        return extraDepotRes;
    }
    public void setExtraDepot(ArrayList<ResourceType> extraDepot) {
        this.extraDepot = extraDepot;
    }

    public void setResToPlace(HashMap<ResourceType, Integer> resToPlace) {
        this.resToPlace = resToPlace;
    }
}
