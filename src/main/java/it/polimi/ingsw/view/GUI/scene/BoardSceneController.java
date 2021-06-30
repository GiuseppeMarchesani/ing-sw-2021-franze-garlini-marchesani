package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.Gui;
import it.polimi.ingsw.view.GUI.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class BoardSceneController extends ObservableView implements GenericSceneController {
    @FXML
    private ImageView corner;
    @FXML
    private ImageView res0x0;
    @FXML
    private ImageView res1x0;
    @FXML
    private ImageView res2x0;
    @FXML
    private ImageView res3x0;
    @FXML
    private ImageView res0x1;
    @FXML
    private ImageView res1x1;
    @FXML
    private ImageView res2x1;
    @FXML
    private ImageView res3x1;
    @FXML
    private ImageView res0x2;
    @FXML
    private ImageView res1x2;
    @FXML
    private ImageView res2x2;
    @FXML
    private ImageView res3x2;
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
    private ImageView img_leader1;
    @FXML
    private ImageView img_leader2;
    @FXML
    private Text name1;
    @FXML
    private Text name2;
    @FXML
    private Text name3;
    @FXML
    private Text name4;
    @FXML
    private Text name5;
    @FXML
    private Text name6;
    @FXML
    private Text name7;
    @FXML
    private Text name8;
    @FXML
    private Text name9;
    @FXML
    private Text name10;
    @FXML
    private Text name11;
    @FXML
    private Text name12;
    @FXML
    private Text name13;
    @FXML
    private Text name14;
    @FXML
    private Text name15;
    @FXML
    private Text name16;
    @FXML
    private Text name17;
    @FXML
    private Text name18;
    @FXML
    private Text name19;
    @FXML
    private Text name20;
    @FXML
    private Text name21;
    @FXML
    private Text name22;
    @FXML
    private Text name23;
    @FXML
    private Text name24;
    @FXML
    private Button end_turn;
    @FXML
    private Button btmResources;
    @FXML
    private Button btmDevCard;
    @FXML
    private Button btmProduction;
    @FXML
    private Button btmLeader;
    @FXML
    private Button btm_vp;
    @FXML
    private Button btmPlayers;
    @FXML
    private ImageView img_depot1_res1;
    @FXML
    private ImageView img_depot2_res3;
    @FXML
    private ImageView img_depot3_res1;
    @FXML
    private ImageView img_depot1_res2;
    @FXML
    private ImageView img_depot2_res2;
    @FXML
    private ImageView img_depot2_res1;
    @FXML
    private ImageView strong_res1;
    @FXML
    private ImageView strong_res2;
    @FXML
    private ImageView strong_res3;
    @FXML
    private ImageView strong_res4;
    @FXML
    private Label strong_quantity1;
    @FXML
    private Label strong_quantity2;
    @FXML
    private Label strong_quantity3;
    @FXML
    private Label strong_quantity4;
    @FXML
    private Label state_leader1;
    @FXML
    private  Label state_leader2;

    private ArrayList<ImageView> cards = new ArrayList<>();
    private ArrayList<ImageView> market = new ArrayList<>();
    private ResourceType[][] marbleMarket = new ResourceType[4][3];
    private ResourceType cornerMarket;
    private ArrayList<DevCard> cardMarket = new ArrayList<>();
    private ArrayList <String> numPlayer = new ArrayList<>();
    private HashMap<LeaderCard, Boolean> leaderCards = new HashMap<>();
    private ArrayList<ImageView> imgLeader = new ArrayList<>();
    private HashMap<String, Integer> faithTrack = new HashMap<>();
    private HashMap<Integer, Integer> depotQuantity = new HashMap<>();
    private HashMap<Integer, ResourceType> depotResource = new HashMap<>();
    private HashMap<ResourceType, Integer> strongbox = new HashMap<>();
    private int leaderAction = 0;
    private ArrayList<Integer> remainingCards = new ArrayList<>();
    @FXML
    public void initialize(){
        end_turn.setDisable(true);
        numPlayer = Gui.getPlayerList();
        if(numPlayer.size()==1) {
            btmPlayers.setDisable(true);
            btmPlayers.setVisible(false);

        }
        btmDevCard.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBuyCardBtm);
        btmResources.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onResourcesBtm);
        btmProduction.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onProductionBtm);
        btmLeader.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onLeaderBtm);
        btm_vp.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onVpBtm);
        btmPlayers.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onPlayerBtm);
        end_turn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onEndTurnBtm);
    }

    public void onBuyCardBtm(Event event){
        notifyObserver(obs -> obs.updateAction(1));
    }
    public void onResourcesBtm(Event event){
        btmProduction.setDisable(true);
        btmResources.setDisable(true);
        btmDevCard.setDisable(true);
        notifyObserver(obs -> obs.updateAction(0));
    }
    public void onProductionBtm(Event event){

        notifyObserver(obs -> obs.updateAction(2));
    }
    public void onLeaderBtm(Event event){
        leaderAction++;
        if(leaderAction==2){
            btmLeader.setDisable(true);
        }
        notifyObserver(obs -> obs.updateAction(3));
    }
    public void onVpBtm(Event event){
        notifyObserver(obs -> obs.updateAction(11));
    }

    public void onPlayerBtm(Event event){

    }
    public void onEndTurnBtm(Event event){
        btmResources.setDisable(false);
        btmDevCard.setDisable(false);
        btmLeader.setDisable(false);
        btmProduction.setDisable(false);
        notifyObserver(obs -> obs.updateAction(13));
    }

    private void updateCardMarket(){

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


        int counter=0;
        int n=0;
        for(int num : remainingCards) {
            Image image;
            if (num > 0) {
                image = new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (cardMarket.get(counter).getId() - 1) + ".png"));
                cards.get(n).setImage(image);
                cards.get(n).setVisible(true);
                counter++;
            } else {
                image = new Image(MainApp.class.getResourceAsStream("/images/BackCard.png"));
                cards.get(n).setImage(image);
                cards.get(n).setVisible(true);
            }
            n++;
            }

        }

    private void updateLeader(){

        imgLeader.add(img_leader1);
        imgLeader.add(img_leader2);
        int i=0;
        for(LeaderCard ld: leaderCards.keySet()){
            Image image = new Image(MainApp.class.getResourceAsStream("/images/leaderCards/leader_Id" + (ld.getId()-1) + ".png"));
            imgLeader.get(i).setImage(image);
            imgLeader.get(i).setVisible(true);

            if(leaderCards.get(ld) && i==0){
                state_leader1.setText("ACTIVATED");
            }
            else if(leaderCards.get(ld) && i==1){
                state_leader2.setText("ACTIVATED");
            }
            else if(!leaderCards.get(ld) && i==0){
                state_leader1.setText("NOT ACTIVATED");
            }
            else if(!leaderCards.get(ld) && i==1){
                state_leader2.setText("NOT ACTIVATED");
            }
            i++;

        }
    }

    private void updateMarket(){
        int n=0;

        market.add(res0x0);
        market.add(res0x1);
        market.add(res0x2);
        market.add(res1x0);
        market.add(res1x1);
        market.add(res1x2);
        market.add(res2x0);
        market.add(res2x1);
        market.add(res2x2);
        market.add(res3x0);
        market.add(res3x1);
        market.add(res3x2);

        for(int i=0; i<4; i++){
            for(int j=0; j<3; j++){
                Image image;
                if(marbleMarket[i][j] == ResourceType.COIN){
                    image = new Image(MainApp.class.getResourceAsStream("/images/yellowMarble.png"));
                }
                else if(marbleMarket[i][j] == ResourceType.SERVANT){
                    image = new Image(MainApp.class.getResourceAsStream("/images/purpleMarble.png"));
                }
                else if(marbleMarket[i][j] == ResourceType.SHIELD){
                    image = new Image(MainApp.class.getResourceAsStream("/images/blueMarble.png"));
                }
                else if(marbleMarket[i][j] == ResourceType.STONE){
                    image = new Image(MainApp.class.getResourceAsStream("/images/greyMarble.png"));
                }
                else if (marbleMarket[i][j] == ResourceType.EMPTY){
                    image = new Image(MainApp.class.getResourceAsStream("/images/whiteMarble.png"));
                }
                else{
                    image = new Image(MainApp.class.getResourceAsStream("/images/redMarble.png"));
                }
                market.get(n).setImage(image);
                market.get(n).setVisible(true);
                n++;

            }
        }

        if(cornerMarket == ResourceType.COIN){
            corner.setImage(new Image("/images/yellowMarble.png"));
        }
        else if(cornerMarket == ResourceType.EMPTY){
            corner.setImage(new Image("/images/whiteMarble.png"));
        }
        else if(cornerMarket == ResourceType.FAITH){
            corner.setImage(new Image("/images/redMarble.png"));
        }
        else if(cornerMarket == ResourceType.SERVANT){
            corner.setImage(new Image("/images/purpleMarble.png"));
        }
        else if(cornerMarket == ResourceType.SHIELD){
            corner.setImage(new Image("/images/blueMarble.png"));
        }
        else{
            corner.setImage(new Image("/images/greyMarble.png"));
        }
    }

    private void updateWarehouse(){
        img_depot1_res1.setImage(null);
        img_depot1_res2.setImage(null);
        img_depot2_res3.setImage(null);
        img_depot2_res1.setImage(null);
        img_depot2_res2.setImage(null);
        img_depot3_res1.setImage(null);
        for (Integer depot : depotResource.keySet()) {
            for (int i = 1; i <= depotQuantity.get(depot); i++) {
                Image image = new Image(MainApp.class.getResourceAsStream("/images/" + depotResource.get(depot).toString() + ".png"));
                setImage(depot, depotQuantity.get(depot), image);
            }
        }
    }
    private void setImage(int n, int quantity, Image img) {
        switch (n) {
            case 0:
                switch (quantity) {

                    case 3:
                        img_depot2_res3.setImage(img);
                    case 2:
                        img_depot1_res2.setImage(img);
                    case 1:
                        img_depot1_res1.setImage(img);
                        break;

                }
                break;
            case 1:
                switch (quantity) {
                    case 2:
                        img_depot2_res2.setImage(img);
                    case 1:
                        img_depot2_res1.setImage(img);
                        break;
                }
                break;
            case 2:
                img_depot3_res1.setImage(img);
                break;

        }
    }
    private void updateFaithTrack(){
        name1.setText("");
        name2.setText("");
        name3.setText("");
        name4.setText("");
        name5.setText("");
        name6.setText("");
        name7.setText("");
        name8.setText("");
        name9.setText("");
        name10.setText("");
        name11.setText("");
        name12.setText("");
        name13.setText("");
        name14.setText("");
        name15.setText("");
        name16.setText("");
        name17.setText("");
        name18.setText("");
        name19.setText("");
        name20.setText("");
        name21.setText("");
        name22.setText("");
        name23.setText("");
        name24.setText("");

        for(String name: faithTrack.keySet()){
            switch (faithTrack.get(name)){
                case 1:
                    name1.setText(name1.getText() + " " + name);
                    break;
                case 2:
                    name2.setText(name2.getText() + " " + name);
                    break;
                case 3:
                    name3.setText(name3.getText() + " " + name);
                    break;
                case 4:
                    name4.setText(name4.getText() + " " + name);
                    break;
                case 5:
                    name5.setText(name5.getText() + " " + name);
                    break;
                case 6:
                    name6.setText(name6.getText() + " " + name);
                    break;
                case 7:
                    name7.setText(name7.getText() + " " + name);
                    break;
                case 8:
                    name8.setText(name8.getText() + " " + name);
                    break;
                case 9:
                    name9.setText(name9.getText() + " " + name);
                    break;
                case 10:
                    name10.setText(name10.getText() + " " + name);
                    break;
                case 11:
                    name11.setText(name11.getText() + " " + name);
                    break;

                case 12:
                    name12.setText(name12.getText() + " " + name);
                    break;

                case 13:
                    name13.setText(name13.getText() + " " + name);
                    break;

                case 14:
                    name14.setText(name14.getText() + " " + name);
                    break;

                case 15:
                    name15.setText(name15.getText() + " " + name);
                    break;

                case 16:
                    name16.setText(name16.getText() + " " + name);
                    break;

                case 17:
                    name17.setText(name17.getText() + " " + name);
                    break;

                case 18:
                    name18.setText(name18.getText() + " " + name);
                    break;

                case 19:
                    name19.setText(name19.getText() + " " + name);
                    break;

                case 20:
                    name20.setText(name20.getText() + " " + name);
                    break;

                case 21:
                    name21.setText(name21.getText() + " " + name);
                    break;

                case 22:
                    name22.setText(name22.getText() + " " + name);
                    break;

                case 23:
                    name23.setText(name23.getText() + " " + name);
                    break;

                case 24:
                    name24.setText(name24.getText() + " " + name);
                    break;
                default:
                    break;
            }
        }
    }

    private void updateStrongbox(){
        int n=0;
        for(ResourceType res: strongbox.keySet()){
            Image image = new Image(MainApp.class.getResourceAsStream("/images/" + res.toString() + ".png"));
            if(n==0){
                strong_res1.setImage(image);
                strong_quantity1.setText(String.valueOf(strongbox.get(res)));
            }
            else if(n==1){
                strong_res2.setImage(image);
                strong_quantity2.setText(String.valueOf(strongbox.get(res)));
            }
            else if(n==2){
                strong_res3.setImage(image);
                strong_quantity3.setText(String.valueOf(strongbox.get(res)));
            }
            else{
                strong_res4.setImage(image);
                strong_quantity4.setText(String.valueOf(strongbox.get(res)));
            }
            n++;
        }
    }

    public void update(ResourceType[][] market, ResourceType cornerMarble, ArrayList<DevCard> cardMarket, ArrayList<Integer> remainingCards, HashMap<Integer, Integer> activeDepotQ, HashMap<Integer, ResourceType> activeDepotT, HashMap<LeaderCard, Boolean> chosenLeader, HashMap<String, Integer> faithTrack,
                       HashMap<ResourceType, Integer> strongbox){
        this.cornerMarket = cornerMarble;
        this.cardMarket = cardMarket;
        this.depotQuantity = activeDepotQ;
        this.depotResource = activeDepotT;
        this.leaderCards = chosenLeader;
        this.faithTrack = faithTrack;
        this.strongbox = strongbox;
        this.remainingCards = remainingCards;
        for(int i=0; i<4; i++){
            for(int j=0; j<3; j++){
                this.marbleMarket[i][j]= market[i][j];
            }
        }
        updateStrongbox();
        updateMarket();
        updateCardMarket();
        updateLeader();
        updateFaithTrack();
        updateWarehouse();
    }
}
