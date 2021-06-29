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


public class AnySceneController extends ObservableView implements GenericSceneController {
    private final ToggleGroup group = new ToggleGroup();
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
                if(Gui.getAnyRes().containsKey(ResourceType.COIN)){
                    Gui.getAnyRes().put(ResourceType.COIN, Gui.getAnyRes().get(ResourceType.COIN)+1);
                }
                else{
                    Gui.getAnyRes().put(ResourceType.COIN, 1);
                }
                break;
            case "servant":
                if(Gui.getAnyRes().containsKey(ResourceType.SERVANT)){
                    Gui.getAnyRes().put(ResourceType.SERVANT, Gui.getAnyRes().get(ResourceType.SERVANT)+1);
                }
                else{
                    Gui.getAnyRes().put(ResourceType.SERVANT, 1);
                }
                break;
            case "shield":
                if(Gui.getAnyRes().containsKey(ResourceType.SHIELD)){
                    Gui.getAnyRes().put(ResourceType.SHIELD, Gui.getAnyRes().get(ResourceType.SHIELD)+1);
                }
                else{
                    Gui.getAnyRes().put(ResourceType.SHIELD, 1);
                }
                break;
            default:
                if(Gui.getAnyRes().containsKey(ResourceType.STONE)){
                    Gui.getAnyRes().put(ResourceType.STONE, Gui.getAnyRes().get(ResourceType.STONE)+1);
                }
                else{
                    Gui.getAnyRes().put(ResourceType.STONE, 1);
                }

                break;
        }
    }
}
