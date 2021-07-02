package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.MainApp;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/**
 * This Scene Controller is used to asks the player to choose the cards they want to activate for production.
 */
public class AskCardsForProdSceneController extends ObservableView implements GenericSceneController {

    @FXML
    private Label lblMustPay;
    @FXML
    private GridPane gridPayment;
    @FXML
    private ImageView img_scroll;
    @FXML
    private ImageView img_card1;
    @FXML
    private ImageView img_card2;
    @FXML
    private ImageView img_card3;
    @FXML
    private ImageView img_card4;
    @FXML
    private ImageView img_card5;
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
    private Button btn_next;

    private final Image imgCardBack = new Image(MainApp.class.getResourceAsStream("/images/BackCard.png"));
    private ArrayList<DevCard> devCardList;
    private ArrayList<CheckBox> checkCards;
    private ArrayList<ImageView> imgList;

    @FXML
    public void initialize() {
        gridPayment.setDisable(true);
        btn_next.setDisable(true);
        lblMustPay.setDisable(true);

        checkCards = new ArrayList<>();
        checkCards.add(check_card1);
        checkCards.add(check_card2);
        checkCards.add(check_card3);
        checkCards.add(check_card4);
        checkCards.add(check_card5);


        imgList = new ArrayList<>();
        imgList.add(img_card1);
        imgList.add(img_card2);
        imgList.add(img_card3);
        imgList.add(img_card4);
        imgList.add(img_card5);

        int i=0;
        ArrayList<DevCard> cards = new ArrayList<>(devCardList);
        cards.remove(0);

        for(DevCard devCard: cards){
            if(devCard.getId()!=0 && devCard.getId() <= 48) {
                Image image = new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCard.getId() - 1) + ".png"));
                imgList.get(i).setImage(image);
                checkCards.get(i).setDisable(false);
                i++;
            }
            else if(devCard.getId()!=0 && devCard.getId() > 48) {
                Image image = new Image(MainApp.class.getResourceAsStream("/images/leaderCards/leader_Id" + (devCard.getId() - 1) + ".png"));
                imgList.get(i).setImage(image);
                checkCards.get(i).setDisable(false);
                i++;
            }
        }
        for(;i<imgList.size();i++) {
            imgList.get(i).setImage(imgCardBack);
            checkCards.get(i).setDisable(true);
        }

        btn_ok.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtnOk);
    }

    /**
     * Handles the click on the Ok button.
     * @param event the mouse click event.
     */
    public void onBtnOk(Event event) {
        ArrayList<DevCard> chosenCards = new ArrayList<>();
        if(check_scroll.isSelected()) chosenCards.add(devCardList.get(0));
        devCardList.remove(0);
        for(CheckBox chk: checkCards) {
            if(chk.isSelected()) chosenCards.add(devCardList.get(checkCards.indexOf(chk)));
        }
        if(chosenCards.isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("You must choose a card!");
            errorAlert.showAndWait();
        }
        else{
            check_card1.setDisable(true);
            check_card2.setDisable(true);
            check_card3.setDisable(true);
            check_card4.setDisable(true);
            check_card5.setDisable(true);
            check_scroll.setDisable(true);
            btn_ok.setDisable(true);
            notifyObserver(obs -> obs.updateChosenProdCards(chosenCards));
        }
    }

    public void setDevCardList(ArrayList<DevCard> devCardList) {
        this.devCardList = devCardList;
    }
}
