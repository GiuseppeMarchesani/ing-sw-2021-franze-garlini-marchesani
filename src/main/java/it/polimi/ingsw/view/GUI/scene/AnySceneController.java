package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.Gui;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;


public class AnySceneController extends ObservableView implements GenericSceneController {
    private final ToggleGroup group = new ToggleGroup();
    private ResourceType anyRes;
    @FXML
    private RadioButton coin;
    @FXML
    private RadioButton servant;
    @FXML
    private RadioButton shield;
    @FXML
    private RadioButton stone;
    @FXML
    private Button btm_next;
    @FXML
    public void initialize(){
        coin.setToggleGroup(group);
        servant.setToggleGroup(group);
        shield.setToggleGroup(group);
        stone.setToggleGroup(group);
        btm_next.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onNextBtm);
    }

    private void onNextBtm(Event event){
        RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
        String id = selectedRadioButton.getId();
        switch (id){
            case "coin":
                anyRes = ResourceType.COIN;
                break;
            case "servant":
                anyRes = ResourceType.SERVANT;
                break;
            case "shield":
                anyRes = ResourceType.SHIELD;
                break;
            default:
                anyRes = ResourceType.STONE;

                break;
        }
    }

    public ResourceType getAny(){
        return anyRes;
    }
}
