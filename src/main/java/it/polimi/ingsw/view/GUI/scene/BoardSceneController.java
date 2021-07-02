package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.DevCardSlot;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.view.GUI.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This Scene Controller manages the main scene of the game.
 */
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
    private ImageView img_space1;
    @FXML
    private ImageView img_space2;
    @FXML
    private ImageView img_space3;
    @FXML
    private ImageView img_space4;
    @FXML
    private ImageView img_space5;
    @FXML
    private ImageView img_space6;
    @FXML
    private ImageView img_space7;
    @FXML
    private ImageView img_space8;
    @FXML
    private ImageView img_space9;
    @FXML
    private ImageView img_space10;
    @FXML
    private ImageView img_space11;
    @FXML
    private ImageView img_space12;
    @FXML
    private ImageView img_space13;
    @FXML
    private ImageView img_space14;
    @FXML
    private ImageView img_space15;
    @FXML
    private ImageView img_space16;
    @FXML
    private ImageView img_space17;
    @FXML
    private ImageView img_space18;
    @FXML
    private ImageView img_space19;
    @FXML
    private ImageView img_space20;
    @FXML
    private ImageView img_space21;
    @FXML
    private ImageView img_space22;
    @FXML
    private ImageView img_space23;
    @FXML
    private ImageView img_space24;
    @FXML
    private ImageView blackCross1;
    @FXML
    private ImageView blackCross2;
    @FXML
    private ImageView blackCross3;
    @FXML
    private ImageView blackCross4;
    @FXML
    private ImageView blackCross5;
    @FXML
    private ImageView blackCross6;
    @FXML
    private ImageView blackCross7;
    @FXML
    private ImageView blackCross8;
    @FXML
    private ImageView blackCross9;
    @FXML
    private ImageView blackCross10;
    @FXML
    private ImageView blackCross11;
    @FXML
    private ImageView blackCross12;
    @FXML
    private ImageView blackCross13;
    @FXML
    private ImageView blackCross14;
    @FXML
    private ImageView blackCross15;
    @FXML
    private ImageView blackCross16;
    @FXML
    private ImageView blackCross17;
    @FXML
    private ImageView blackCross18;
    @FXML
    private ImageView blackCross19;
    @FXML
    private ImageView blackCross20;
    @FXML
    private ImageView blackCross21;
    @FXML
    private ImageView blackCross22;
    @FXML
    private ImageView blackCross23;
    @FXML
    private ImageView blackCross24;
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
    @FXML
    private ImageView pope1;
    @FXML
    private ImageView pope2;
    @FXML
    private ImageView pope3;
    @FXML
    private ImageView img_slot0_card0;
    @FXML
    private ImageView img_slot0_card1;
    @FXML
    private ImageView img_slot0_card2;
    @FXML
    private ImageView img_slot1_card0;
    @FXML
    private ImageView img_slot1_card1;
    @FXML
    private ImageView img_slot1_card2;
    @FXML
    private ImageView img_slot2_card0;
    @FXML
    private ImageView img_slot2_card1;
    @FXML
    private ImageView img_slot2_card2;
    @FXML
    private ImageView extra_leader1_res1;
    @FXML
    private ImageView extra_leader1_res2;
    @FXML
    private ImageView extra_leader2_res1;
    @FXML
    private ImageView extra_leader2_res2;

    private ArrayList<ImageView> cards = new ArrayList<>();
    private ArrayList<ImageView> market = new ArrayList<>();
    private ResourceType[][] marbleMarket = new ResourceType[4][3];
    private ResourceType cornerMarket;
    private ArrayList<DevCard> cardMarket = new ArrayList<>();
    private HashMap<LeaderCard, Boolean> leaderCards = new HashMap<>();
    private ArrayList<ImageView> imgLeader = new ArrayList<>();
    private HashMap<String, Integer> faithTrack = new HashMap<>();
    private HashMap<Integer, Integer> depotQuantity = new HashMap<>();
    private HashMap<Integer, ResourceType> depotResource = new HashMap<>();
    private HashMap<ResourceType, Integer> strongbox = new HashMap<>();
    private ArrayList<Integer> remainingCards = new ArrayList<>();
    private HashMap<Integer, Boolean> faithZone= new HashMap<>();
    private DevCardSlot devCardSlot = new DevCardSlot();
    private HashMap<ResourceType, Integer> extraDepotRes = new HashMap<>();
    private ArrayList<LeaderCard> discarded = new ArrayList<>();

    @FXML
    public void initialize(){

        btmDevCard.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBuyCardBtm);
        btmResources.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onResourcesBtm);
        btmProduction.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onProductionBtm);
        btmLeader.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onLeaderBtm);
        btmPlayers.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onPlayerBtm);
        end_turn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onEndTurnBtm);
    }

    /**
     * Handles the click on the BuyCard button.
     * @param event the mouse click event.
     */
    public void onBuyCardBtm(Event event){
        notifyObserver(obs -> obs.updateAction(1));
    }

    /**
     * Handles the click on the Resources button.
     * @param event the mouse click event.
     */
    public void onResourcesBtm(Event event){
        notifyObserver(obs -> obs.updateAction(0));
    }

    /**
     * Handles the click on the Production button.
     * @param event the mouse click event.
     */
    public void onProductionBtm(Event event){
        notifyObserver(obs -> obs.updateAction(2));
    }

    /**
     * Handles the click on the Leader button.
     * @param event the mouse click event.
     */
    public void onLeaderBtm(Event event){
        notifyObserver(obs -> obs.updateAction(3));
    }

    /**
     * Handles the click on the Player button.
     * @param event the mouse click event.
     */
    public void onPlayerBtm(Event event){
        notifyObserver(obs -> obs.updateAction(13));
    }

    /**
     * Handles the click on the EndTurn button.
     * @param event the mouse click event.
     */
    public void onEndTurnBtm(Event event){
        btmLeader.setDisable(true);
        btmProduction.setDisable(true);
        btmResources.setDisable(true);
        btmPlayers.setDisable(true);
        btmDevCard.setDisable(true);
        notifyObserver(obs -> obs.updateAction(14));
    }

    /**
     * Updates the Card Market.
     */
    private void updateCardMarket(){
        btmLeader.setDisable(false);
        btmProduction.setDisable(false);
        btmResources.setDisable(false);
        btmPlayers.setDisable(false);
        btmDevCard.setDisable(false);
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

    /**
     * Updates the shown Leader Cards.
     */
    private void updateLeader(){
        btmLeader.setDisable(false);
        btmProduction.setDisable(false);
        btmResources.setDisable(false);
        btmPlayers.setDisable(false);
        btmDevCard.setDisable(false);
        extra_leader1_res1.setVisible(false);
        extra_leader1_res2.setVisible(false);
        extra_leader2_res1.setVisible(false);
        extra_leader2_res2.setVisible(false);

        if(imgLeader.size()!=2) {
            imgLeader.add(img_leader1);
            imgLeader.add(img_leader2);
        }
        int i=0;

        for(LeaderCard ld: leaderCards.keySet()){
            if(imgLeader.get(i).getImage()==null) {
                Image image = new Image(MainApp.class.getResourceAsStream("/images/leaderCards/leader_Id" + (ld.getId() - 1) + ".png"));
                imgLeader.get(i).setImage(image);
                imgLeader.get(i).setVisible(true);
            }
            if(leaderCards.get(ld) && i==0){
                if(discarded.contains(ld)) {
                    state_leader1.setText("DISCARDED");
                    state_leader1.setTextFill(Color.RED);
                }
                else {
                    state_leader1.setText("ACTIVATED");
                    state_leader1.setTextFill(Color.RED);
                    if (ld.getId() >= 53 && ld.getId() <= 56) {
                        if (extraDepotRes.containsKey(ld.getResourceAbility())) {
                            if (extraDepotRes.get(ld.getResourceAbility()) == 1) {
                                if(ld.getResourceAbility().equals(ResourceType.SHIELD)){
                                    Image image = new Image(MainApp.class.getResourceAsStream("/images/shield.png"));
                                    extra_leader1_res1.setImage(image);
                                    extra_leader1_res1.setVisible(true);
                                }
                                else if(ld.getResourceAbility().equals(ResourceType.SERVANT)) {
                                    Image image = new Image(MainApp.class.getResourceAsStream("/images/servant.png"));
                                    extra_leader1_res1.setImage(image);
                                    extra_leader1_res1.setVisible(true);
                                }
                                else if(ld.getResourceAbility().equals(ResourceType.STONE)){
                                    Image image = new Image(MainApp.class.getResourceAsStream("/images/stone.png"));
                                    extra_leader1_res1.setImage(image);
                                    extra_leader1_res1.setVisible(true);
                                }
                                else if(ld.getResourceAbility().equals(ResourceType.COIN)) {
                                    Image image = new Image(MainApp.class.getResourceAsStream("/images/coin.png"));
                                    extra_leader1_res1.setImage(image);
                                    extra_leader1_res1.setVisible(true);
                                }

                            } else if (extraDepotRes.get(ld.getResourceAbility()) == 2) {
                                if(ld.getResourceAbility().equals(ResourceType.SHIELD)){
                                    Image image = new Image(MainApp.class.getResourceAsStream("/images/shield.png"));
                                    extra_leader1_res1.setImage(image);
                                    extra_leader1_res1.setVisible(true);
                                    extra_leader1_res2.setImage(image);
                                    extra_leader1_res2.setVisible(true);
                                }
                                else if(ld.getResourceAbility().equals(ResourceType.SERVANT)) {
                                    Image image = new Image(MainApp.class.getResourceAsStream("/images/servant.png"));
                                    extra_leader1_res1.setImage(image);
                                    extra_leader1_res1.setVisible(true);
                                    extra_leader1_res2.setImage(image);
                                    extra_leader1_res2.setVisible(true);
                                }
                                else if(ld.getResourceAbility().equals(ResourceType.STONE)){
                                    Image image = new Image(MainApp.class.getResourceAsStream("/images/stone.png"));
                                    extra_leader1_res1.setImage(image);
                                    extra_leader1_res1.setVisible(true);
                                    extra_leader1_res2.setImage(image);
                                    extra_leader1_res2.setVisible(true);
                                }
                                else if(ld.getResourceAbility().equals(ResourceType.COIN)) {
                                    Image image = new Image(MainApp.class.getResourceAsStream("/images/coin.png"));
                                    extra_leader1_res1.setImage(image);
                                    extra_leader1_res1.setVisible(true);
                                    extra_leader1_res2.setImage(image);
                                    extra_leader1_res2.setVisible(true);
                                }
                            }
                        }
                    }
                }
            }
            else if(leaderCards.get(ld) && i==1){
                if(discarded.contains(ld)) {
                    state_leader2.setText("DISCARDED");
                    state_leader2.setTextFill(Color.RED);
                }
                else {
                    state_leader2.setText("ACTIVATED");
                    state_leader2.setTextFill(Color.RED);
                    if (ld.getId() >= 53 && ld.getId() <= 56) {
                        if (extraDepotRes.containsKey(ld.getResourceAbility())) {
                            String resource;
                            if(ld.getResourceAbility().equals(ResourceType.COIN)){
                                resource = "COIN";
                            }
                            else if(ld.getResourceAbility().equals(ResourceType.STONE)){
                                resource = "STONE";
                            }
                            else if(ld.getResourceAbility().equals(ResourceType.SERVANT)){
                                resource = "SERVANT";
                            }
                            else{
                                resource = "SHIELD";
                            }
                            Image image1 = new Image(MainApp.class.getResourceAsStream("/images/" + resource + ".png"));
                            if (extraDepotRes.get(ld.getResourceAbility()) == 1) {
                                extra_leader2_res1.setImage(image1);
                                extra_leader2_res1.setVisible(true);
                            } else if (extraDepotRes.get(ld.getResourceAbility()) == 2) {
                                extra_leader2_res1.setImage(image1);
                                extra_leader2_res2.setImage(image1);
                                extra_leader2_res1.setVisible(true);
                                extra_leader2_res2.setVisible(true);
                            }
                        }
                    }
                }
            }
            else if(!leaderCards.get(ld) && i==0){
                if(discarded.contains(ld)) {
                    state_leader1.setText("DISCARDED");
                    state_leader1.setTextFill(Color.RED);
                }
                else {
                    state_leader1.setText("NOT ACTIVATED");
                    state_leader1.setTextFill(Color.GREEN);
                }
            }
            else if(!leaderCards.get(ld) && i==1){
                if(discarded.contains(ld)) {
                    state_leader2.setText("DISCARDED");
                    state_leader2.setTextFill(Color.RED);
                }
                else {
                    state_leader2.setText("NOT ACTIVATED");
                    state_leader2.setTextFill(Color.GREEN);
                }
            }
            i++;

        }
    }

    /**
     * Updates the Market.
     */
    private void updateMarket(){
        btmLeader.setDisable(false);
        btmProduction.setDisable(false);
        btmResources.setDisable(false);
        btmPlayers.setDisable(false);
        btmDevCard.setDisable(false);

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

    /**
     * Updates the players' warehouse.
     */
    private void updateWarehouse(){
        btmLeader.setDisable(false);
        btmProduction.setDisable(false);
        btmResources.setDisable(false);
        btmPlayers.setDisable(false);
        btmDevCard.setDisable(false);

        img_depot1_res1.setImage(null);
        img_depot1_res2.setImage(null);
        img_depot2_res3.setImage(null);
        img_depot2_res1.setImage(null);
        img_depot2_res2.setImage(null);
        img_depot3_res1.setImage(null);
        for (Integer depot : depotResource.keySet()) {
            for (int i = 1; i <= depotQuantity.get(depot); i++) {
                if(depotResource.get(depot).equals(ResourceType.SHIELD)){
                    Image image = new Image(MainApp.class.getResourceAsStream("/images/shield.png"));
                    setImage(depot, depotQuantity.get(depot), image);
                }
                else if(depotResource.get(depot).equals(ResourceType.SERVANT)){
                    Image image = new Image(MainApp.class.getResourceAsStream("/images/servant.png"));
                    setImage(depot, depotQuantity.get(depot), image);
                }
                else if(depotResource.get(depot).equals(ResourceType.STONE)){
                    Image image = new Image(MainApp.class.getResourceAsStream("/images/stone.png"));
                    setImage(depot, depotQuantity.get(depot), image);
                }
                else if(depotResource.get(depot).equals(ResourceType.COIN)){
                    Image image = new Image(MainApp.class.getResourceAsStream("/images/coin.png"));
                    setImage(depot, depotQuantity.get(depot), image);
                }

                if(depotResource.get(depot).equals(ResourceType.SHIELD)){
                    Image image = new Image(MainApp.class.getResourceAsStream("/images/shield.png"));
                    setImage(depot, depotQuantity.get(depot), image);
                }
                else if(depotResource.get(depot).equals(ResourceType.SERVANT)) {
                    Image image = new Image(MainApp.class.getResourceAsStream("/images/servant.png"));
                    setImage(depot, depotQuantity.get(depot), image);
                }
                else if(depotResource.get(depot).equals(ResourceType.STONE)){
                    Image image = new Image(MainApp.class.getResourceAsStream("/images/stone.png"));
                    setImage(depot, depotQuantity.get(depot), image);
                }
                else if(depotResource.get(depot).equals(ResourceType.COIN)) {
                    Image image = new Image(MainApp.class.getResourceAsStream("/images/coin.png"));
                    setImage(depot, depotQuantity.get(depot), image);                }
            }
        }
    }

    /**
     * Used to set the resource's images in the warehouse.
     * @param n number of the depot.
     * @param quantity how many resources to be put.
     * @param img the resource image.
     */
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

    /**
     * Updates the Faith Track on the Board.
     */
    private void updateFaithTrack(){
        btmLeader.setDisable(false);
        btmProduction.setDisable(false);
        btmResources.setDisable(false);
        btmPlayers.setDisable(false);
        btmDevCard.setDisable(false);

        img_space1.setImage(null);
        img_space2.setImage(null);
        img_space3.setImage(null);
        img_space4.setImage(null);
        img_space5.setImage(null);
        img_space6.setImage(null);
        img_space7.setImage(null);
        img_space8.setImage(null);
        img_space9.setImage(null);
        img_space10.setImage(null);
        img_space11.setImage(null);
        img_space12.setImage(null);
        img_space13.setImage(null);
        img_space14.setImage(null);
        img_space15.setImage(null);
        img_space16.setImage(null);
        img_space17.setImage(null);
        img_space18.setImage(null);
        img_space19.setImage(null);
        img_space20.setImage(null);
        img_space21.setImage(null);
        img_space22.setImage(null);
        img_space23.setImage(null);
        img_space24.setImage(null);
        blackCross1.setImage(null);
        blackCross2.setImage(null);
        blackCross3.setImage(null);
        blackCross4.setImage(null);
        blackCross5.setImage(null);
        blackCross6.setImage(null);
        blackCross7.setImage(null);
        blackCross8.setImage(null);
        blackCross9.setImage(null);
        blackCross10.setImage(null);
        blackCross11.setImage(null);
        blackCross12.setImage(null);
        blackCross13.setImage(null);
        blackCross14.setImage(null);
        blackCross15.setImage(null);
        blackCross16.setImage(null);
        blackCross17.setImage(null);
        blackCross18.setImage(null);
        blackCross19.setImage(null);
        blackCross20.setImage(null);
        blackCross21.setImage(null);
        blackCross22.setImage(null);
        blackCross23.setImage(null);
        blackCross24.setImage(null);

        for(String name: faithTrack.keySet()){
            if(name.equals("Lorenzo il Magnifico")) {
                Image image = new Image(getClass().getResourceAsStream("/images/black_cross.png"));
                switch (faithTrack.get(name)) {
                    case 1:
                        blackCross1.setImage(image);
                        blackCross1.setVisible(true);
                        break;
                    case 2:
                        blackCross2.setImage(image);
                        blackCross2.setVisible(true);
                        break;
                    case 3:
                        blackCross3.setImage(image);
                        blackCross3.setVisible(true);
                        break;
                    case 4:
                        blackCross4.setImage(image);
                        blackCross4.setVisible(true);
                        break;
                    case 5:
                        blackCross5.setImage(image);
                        blackCross5.setVisible(true);
                        break;
                    case 6:
                        blackCross6.setImage(image);
                        blackCross6.setVisible(true);
                        break;
                    case 7:
                        blackCross7.setImage(image);
                        blackCross7.setVisible(true);
                        break;
                    case 8:
                        blackCross8.setImage(image);
                        blackCross8.setVisible(true);
                        break;
                    case 9:
                        blackCross9.setImage(image);
                        blackCross9.setVisible(true);
                        break;
                    case 10:
                        blackCross10.setImage(image);
                        blackCross10.setVisible(true);
                        break;
                    case 11:
                        blackCross11.setImage(image);
                        blackCross11.setVisible(true);
                        break;

                    case 12:
                        blackCross12.setImage(image);
                        blackCross12.setVisible(true);
                        break;

                    case 13:
                        blackCross13.setImage(image);
                        blackCross13.setVisible(true);
                        break;

                    case 14:
                        blackCross14.setImage(image);
                        blackCross14.setVisible(true);
                        break;

                    case 15:
                        blackCross15.setImage(image);
                        blackCross15.setVisible(true);
                        break;

                    case 16:
                        blackCross16.setImage(image);
                        blackCross16.setVisible(true);
                        break;

                    case 17:
                        blackCross17.setImage(image);
                        blackCross17.setVisible(true);
                        break;

                    case 18:
                        blackCross18.setImage(image);
                        blackCross18.setVisible(true);
                        break;

                    case 19:
                        blackCross19.setImage(image);
                        blackCross19.setVisible(true);
                        break;

                    case 20:
                        blackCross20.setImage(image);
                        blackCross20.setVisible(true);
                        break;

                    case 21:
                        blackCross21.setImage(image);
                        blackCross21.setVisible(true);
                        break;

                    case 22:
                        blackCross22.setImage(image);
                        blackCross22.setVisible(true);
                        break;

                    case 23:
                        blackCross23.setImage(image);
                        blackCross23.setVisible(true);
                        break;

                    case 24:
                        blackCross24.setImage(image);
                        blackCross24.setVisible(true);
                        break;
                    default:
                        break;
                }
            }
            else{
                Image image2 = new Image(MainApp.class.getResourceAsStream("/images/cross.png"));
                switch (faithTrack.get(name)){
                    case 1:
                        img_space1.setImage(image2);
                        img_space1.setVisible(true);
                        break;
                    case 2:
                        img_space2.setImage(image2);
                        img_space2.setVisible(true);
                        break;
                    case 3:
                        img_space3.setImage(image2);
                        img_space3.setVisible(true);
                        break;
                    case 4:
                        img_space4.setImage(image2);
                        img_space4.setVisible(true);
                        break;
                    case 5:
                        img_space5.setImage(image2);
                        img_space5.setVisible(true);
                        break;
                    case 6:
                        img_space6.setImage(image2);
                        img_space6.setVisible(true);
                        break;
                    case 7:
                        img_space7.setImage(image2);
                        img_space7.setVisible(true);
                        break;
                    case 8:
                        img_space8.setImage(image2);
                        img_space8.setVisible(true);
                        break;
                    case 9:
                        img_space9.setImage(image2);
                        img_space9.setVisible(true);
                        break;
                    case 10:
                        img_space10.setImage(image2);
                        img_space10.setVisible(true);
                        break;
                    case 11:
                        img_space11.setImage(image2);
                        img_space11.setVisible(true);
                        break;

                    case 12:
                        img_space12.setImage(image2);
                        img_space12.setVisible(true);
                        break;

                    case 13:
                        img_space13.setImage(image2);
                        img_space13.setVisible(true);
                        break;

                    case 14:
                        img_space14.setImage(image2);
                        img_space14.setVisible(true);
                        break;

                    case 15:
                        img_space15.setImage(image2);
                        img_space15.setVisible(true);
                        break;

                    case 16:
                        img_space16.setImage(image2);
                        img_space16.setVisible(true);
                        break;

                    case 17:
                        img_space17.setImage(image2);
                        img_space17.setVisible(true);
                        break;

                    case 18:
                        img_space18.setImage(image2);
                        img_space18.setVisible(true);
                        break;

                    case 19:
                        img_space19.setImage(image2);
                        img_space19.setVisible(true);
                        break;

                    case 20:
                        img_space20.setImage(image2);
                        img_space20.setVisible(true);
                        break;

                    case 21:
                        img_space21.setImage(image2);
                        img_space21.setVisible(true);
                        break;

                    case 22:
                        img_space22.setImage(image2);
                        img_space22.setVisible(true);
                        break;

                    case 23:
                        img_space23.setImage(image2);
                        img_space23.setVisible(true);
                        break;

                    case 24:
                        img_space24.setImage(image2);
                        img_space24.setVisible(true);
                        break;
                    default:
                        break;
                }
            }

        }
    }

    /**
     * Updates the player's strongbox.
     */
    private void updateStrongbox(){
        btmLeader.setDisable(false);
        btmProduction.setDisable(false);
        btmResources.setDisable(false);
        btmPlayers.setDisable(false);
        btmDevCard.setDisable(false);

        int n=0;

        for(ResourceType res: strongbox.keySet()){
            if(res.equals(ResourceType.SHIELD)){
                Image image = new Image(MainApp.class.getResourceAsStream("/images/shield.png"));
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
            }
            else if(res.equals(ResourceType.SERVANT)) {
                Image image = new Image(MainApp.class.getResourceAsStream("/images/servant.png"));
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
            }
            else if(res.equals(ResourceType.STONE)){
                Image image = new Image(MainApp.class.getResourceAsStream("/images/stone.png"));
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
            }
            else if(res.equals(ResourceType.COIN)) {
                Image image = new Image(MainApp.class.getResourceAsStream("/images/coin.png"));
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
            }
            n++;
        }
    }

    /**
     * Updates the DevCard slot.
     */
    private void updateSlots(){
        btmLeader.setDisable(false);
        btmProduction.setDisable(false);
        btmResources.setDisable(false);
        btmPlayers.setDisable(false);
        btmDevCard.setDisable(false);

        for(int i=0; i<devCardSlot.getSlotDev().size(); i++){
            if(i==0){
                if(devCardSlot.getSlotDev().get(i).size() == 1){
                    Image image1= new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCardSlot.getSlotDev().get(i).get(0).getId()-1) + ".png"));
                    img_slot0_card0.setImage(image1);
                }
                else if(devCardSlot.getSlotDev().get(i).size() == 2){
                    Image image1= new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCardSlot.getSlotDev().get(i).get(0).getId()-1) + ".png"));
                    img_slot0_card0.setImage(image1);
                    Image image2= new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCardSlot.getSlotDev().get(i).get(1).getId()-1) + ".png"));
                    img_slot0_card1.setImage(image2);
                }
                else if (devCardSlot.getSlotDev().get(i).size() == 3){
                    Image image1= new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCardSlot.getSlotDev().get(i).get(0).getId()-1) + ".png"));
                    img_slot0_card0.setImage(image1);
                    Image image2= new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCardSlot.getSlotDev().get(i).get(1).getId()-1) + ".png"));
                    img_slot0_card1.setImage(image2);
                    Image image3= new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCardSlot.getSlotDev().get(i).get(2).getId()-1) + ".png"));
                    img_slot0_card2.setImage(image3);
                }
            }

            else if(i==1){
                if(devCardSlot.getSlotDev().get(i).size() == 1){
                    Image image1= new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCardSlot.getSlotDev().get(i).get(0).getId()-1) + ".png"));
                    img_slot1_card0.setImage(image1);
                }
                else if(devCardSlot.getSlotDev().get(i).size() == 2){
                    Image image1= new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCardSlot.getSlotDev().get(i).get(0).getId()-1) + ".png"));
                    img_slot1_card0.setImage(image1);
                    Image image2= new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCardSlot.getSlotDev().get(i).get(1).getId()-1) + ".png"));
                    img_slot1_card1.setImage(image2);
                }
                else if (devCardSlot.getSlotDev().get(i).size() == 3){
                    Image image1= new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCardSlot.getSlotDev().get(i).get(0).getId()-1) + ".png"));
                    img_slot1_card0.setImage(image1);
                    Image image2= new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCardSlot.getSlotDev().get(i).get(1).getId()-1) + ".png"));
                    img_slot1_card1.setImage(image2);
                    Image image3= new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCardSlot.getSlotDev().get(i).get(2).getId()-1) + ".png"));
                    img_slot1_card2.setImage(image3);
                }
            }
            else if(i==2){
                if(devCardSlot.getSlotDev().get(i).size() == 1){
                    Image image1= new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCardSlot.getSlotDev().get(i).get(0).getId()-1) + ".png"));
                    img_slot2_card0.setImage(image1);
                }
                else if(devCardSlot.getSlotDev().get(i).size() == 2){
                    Image image1= new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCardSlot.getSlotDev().get(i).get(0).getId()-1) + ".png"));
                    img_slot2_card0.setImage(image1);
                    Image image2= new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCardSlot.getSlotDev().get(i).get(1).getId()-1) + ".png"));
                    img_slot2_card1.setImage(image2);
                }
                else if (devCardSlot.getSlotDev().get(i).size() == 3){
                    Image image1= new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCardSlot.getSlotDev().get(i).get(0).getId()-1) + ".png"));
                    img_slot2_card0.setImage(image1);
                    Image image2= new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCardSlot.getSlotDev().get(i).get(1).getId()-1) + ".png"));
                    img_slot2_card1.setImage(image2);
                    Image image3= new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCardSlot.getSlotDev().get(i).get(2).getId()-1) + ".png"));
                    img_slot2_card2.setImage(image3);
                }
            }
        }
    }

    /**
     * General method for update. It calls all the other update methods.
     * @param market the market.
     * @param cornerMarble the corner marble in the market.
     * @param cardMarket the card market.
     * @param remainingCards the remainingCards in the market.
     * @param activeDepotQ the active depot quantity.
     * @param activeDepotT the active depot type.
     * @param chosenLeader the player's leader.
     * @param discarded the discarded leader cards.
     * @param faithTrack the faith track.
     * @param faithZone the faith zone.
     * @param strongbox the player's strongbox.
     * @param devCardSlot the development card slot.
     * @param extraDepotRes the extra depot resources.
     */
    public void update(ResourceType[][] market, ResourceType cornerMarble, ArrayList<DevCard> cardMarket, ArrayList<Integer> remainingCards, HashMap<Integer, Integer> activeDepotQ, HashMap<Integer, ResourceType> activeDepotT, HashMap<LeaderCard, Boolean> chosenLeader, ArrayList<LeaderCard> discarded, HashMap<String, Integer> faithTrack,
                       HashMap<Integer, Boolean> faithZone, HashMap<ResourceType, Integer> strongbox, DevCardSlot devCardSlot, HashMap<ResourceType, Integer> extraDepotRes){
        this.cornerMarket = cornerMarble;
        this.cardMarket = cardMarket;
        this.depotQuantity = activeDepotQ;
        this.depotResource = activeDepotT;
        this.leaderCards = chosenLeader;
        this.discarded = discarded;
        this.faithTrack = faithTrack;
        this.strongbox = strongbox;
        this.remainingCards = remainingCards;
        this.devCardSlot = devCardSlot;
        this.extraDepotRes = extraDepotRes;
        for(int i=0; i<4; i++){
            for(int j=0; j<3; j++){
                this.marbleMarket[i][j]= market[i][j];
            }
        }
        Image zone1 = new Image(MainApp.class.getResourceAsStream("/images/pope_favor1_front.png"));
        Image zone2 = new Image(MainApp.class.getResourceAsStream("/images/pope_favor2_front.png"));
        Image zone3 = new Image(MainApp.class.getResourceAsStream("/images/pope_favor3_front.png"));
        pope1.setImage(zone1);
        pope2.setImage(zone2);
        pope3.setImage(zone3);
        if(faithZone.get(0)){
            pope1.setVisible(false);
        }
        if (faithZone.get(1)){
            pope2.setVisible(false);
        }
        if (faithZone.get(2)){
            pope3.setVisible(false);
        }
        updateStrongbox();
        updateMarket();
        updateCardMarket();
        updateLeader();
        updateFaithTrack();
        updateWarehouse();
        updateSlots();
    }
}
