package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.MainApp;
import it.polimi.ingsw.view.GUI.GuiManager;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class ChooseConversionSceneController extends ObservableView implements GenericSceneController {
    @FXML
    private RadioButton chkConv1;
    @FXML
    private RadioButton chkConv2;
    @FXML
    private ImageView imgConv1;
    @FXML
    private ImageView imgConv2;
    @FXML
    private Button btnNext;

    private ToggleGroup toggle;
    private ArrayList<ResourceType> conversions;
    private ResourceType chosen;

    public void initialize() {

        Image image = new Image(MainApp.class.getResourceAsStream("/images/" + conversions.get(0).toString() + ".png"));
        imgConv1.setImage(image);

        Image image2 = new Image(MainApp.class.getResourceAsStream("/images/" + conversions.get(1).toString() + ".png"));
        imgConv2.setImage(image2);

        chkConv1.setToggleGroup(toggle);
        chkConv2.setToggleGroup(toggle);
        chkConv1.setSelected(true);
        btnNext.addEventHandler(MouseEvent.MOUSE_CLICKED, this:: onNextBtn);
    }

    public void onNextBtn(Event event) {
        RadioButton selected = (RadioButton) toggle.getSelectedToggle();
        if(selected.equals(chkConv1)) {
            chosen = conversions.get(0);
        }
        else chosen = conversions.get(1);
        MarketSceneController msc = new MarketSceneController();
        msc.addAllObservers(observers);
        msc.setConversion(chosen);
        Platform.runLater(() ->
                GuiManager.changeRootPane(msc, "/fxml/market_scene")
        );

    }

    public void setConversions(ArrayList<ResourceType> conversions) {
        this.conversions = conversions;
    }

}
