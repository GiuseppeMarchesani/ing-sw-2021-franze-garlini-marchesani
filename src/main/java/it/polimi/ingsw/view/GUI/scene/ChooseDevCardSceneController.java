package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.Gui;
import it.polimi.ingsw.view.GUI.MainApp;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class ChooseDevCardSceneController extends ObservableView implements GenericSceneController {

    @FXML
    private ImageView card0x0;
    @FXML
    private ImageView card1x0;
    @FXML
    private ImageView card2x0;
    @FXML
    private ImageView card3x0;
    @FXML
    private ImageView card0x1;
    @FXML
    private ImageView card1x1;
    @FXML
    private ImageView card2x1;
    @FXML
    private ImageView card3x1;
    @FXML
    private ImageView card0x2;
    @FXML
    private ImageView card1x2;
    @FXML
    private ImageView card2x2;
    @FXML
    private ImageView card3x2;
    @FXML
    private Button btm_0x0;
    @FXML
    private Button btm_1x0;
    @FXML
    private Button btm_2x0;
    @FXML
    private Button btm_3x0;
    @FXML
    private Button btm_0x1;
    @FXML
    private Button btm_1x1;
    @FXML
    private Button btm_2x1;
    @FXML
    private Button btm_3x1;
    @FXML
    private Button btm_0x2;
    @FXML
    private Button btm_1x2;
    @FXML
    private Button btm_2x2;
    @FXML
    private Button btm_3x2;

    private ArrayList<DevCard> cardMarket = new ArrayList<>();
    private ArrayList<ImageView> cards = new ArrayList<>();

    @FXML
    public void initialize(){
        cardMarket.addAll(Gui.getCardMarket());
        int i=0;
        cards.add(card0x0);
        cards.add(card0x1);
        cards.add(card0x2);
        cards.add(card1x0);
        cards.add(card1x1);
        cards.add(card1x2);
        cards.add(card2x0);
        cards.add(card2x1);
        cards.add(card2x2);
        cards.add(card3x0);
        cards.add(card3x1);
        cards.add(card3x2);

        for (DevCard devCard : cardMarket){
            if(devCard!= null) {
                Image image = new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCard.getId() - 1) + ".png"));
                cards.get(i).setImage(image);
                cards.get(i).setVisible(true);
            }
            else{
                Image image = new Image(MainApp.class.getResourceAsStream("/images/xblack.png"));
                cards.get(i).setImage(image);
                cards.get(i).setVisible(true);
            }
            i++;
        }

        btm_0x0.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtm0x0);
        btm_0x1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtm0x1);
        btm_0x2.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtm0x2);
        btm_1x0.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtm1x0);
        btm_1x1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtm1x1);
        btm_1x2.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtm1x2);
        btm_2x0.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtm2x0);
        btm_2x1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtm2x1);
        btm_2x2.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtm2x2);
        btm_3x0.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtm3x0);
        btm_3x1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtm3x1);
        btm_3x2.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtm3x2);
    }

    private void onBtm0x0(Event event){
        notifyObserver(obs -> obs.updateBuyDevCard(1, Color.GREEN));
    }

    private void onBtm1x0(Event event){
        notifyObserver(obs -> obs.updateBuyDevCard(1, Color.PURPLE));

    }
    private void onBtm2x0(Event event){
        notifyObserver(obs -> obs.updateBuyDevCard(1, Color.YELLOW));

    }
    private void onBtm3x0(Event event){
        notifyObserver(obs -> obs.updateBuyDevCard(1, Color.BLUE));

    }
    private void onBtm0x1(Event event){
        notifyObserver(obs -> obs.updateBuyDevCard(2, Color.GREEN));

    }
    private void onBtm1x1(Event event){
        notifyObserver(obs -> obs.updateBuyDevCard(2, Color.PURPLE));

    }
    private void onBtm2x1(Event event){
        notifyObserver(obs -> obs.updateBuyDevCard(2, Color.YELLOW));

    }
    private void onBtm3x1(Event event){
        notifyObserver(obs -> obs.updateBuyDevCard(2, Color.BLUE));

    }
    private void onBtm0x2(Event event){
        notifyObserver(obs -> obs.updateBuyDevCard(3, Color.GREEN));

    }
    private void onBtm1x2(Event event){
        notifyObserver(obs -> obs.updateBuyDevCard(3, Color.PURPLE));

    }
    private void onBtm2x2(Event event){
        notifyObserver(obs -> obs.updateBuyDevCard(3, Color.YELLOW));

    }
    private void onBtm3x2(Event event){
        notifyObserver(obs -> obs.updateBuyDevCard(3, Color.BLUE));

    }

}
