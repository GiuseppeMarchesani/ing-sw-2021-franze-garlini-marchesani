package it.polimi.ingsw.view.GUI;


import it.polimi.ingsw.model.Board.CardMarket;
import it.polimi.ingsw.model.Board.Market;
import it.polimi.ingsw.model.Card.DevCard;
import it.polimi.ingsw.model.Card.LeaderCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.Color;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.observer.ObservableView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;

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
    private Button btmPlayers;
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

    private ArrayList<ImageView> cards = new ArrayList<>();
    private ArrayList<ImageView> market = new ArrayList<>();
    private ResourceType cornerMarket;
    private ArrayList<DevCard> cardMarket = new ArrayList<>();
    private ArrayList <String> numPlayer = new ArrayList<>();
    private ArrayList<LeaderCard> leaderCards = new ArrayList<>();
    private ArrayList<ImageView> imgLeader = new ArrayList<>();
    private String activePlayer;

    @FXML
    public void initialize(){
        updateMarket();
        updateCardMarket();
        updateLeader();
    }


    private void updateCardMarket(){
        cardMarket.addAll(Gui.getCardMarket());
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

        numPlayer = Gui.getPlayerList();
        if(numPlayer.size()==1) {
            btmPlayers.setDisable(true);
            btmPlayers.setVisible(false);
        }

        int i=0;

        for (DevCard devCard : cardMarket){
            Image image = new Image(MainApp.class.getResourceAsStream("/images/devCards/dev_Id" + (devCard.getId()-1) + ".png"));
            cards.get(i).setImage(image);
            cards.get(i).setVisible(true);
            i++;
        }
    }

    private void updateLeader(){
        leaderCards.addAll(Gui.getLeaderCards());
        imgLeader.add(img_leader1);
        imgLeader.add(img_leader2);
        int i=0;
        for(LeaderCard ld: leaderCards){
            Image image = new Image(MainApp.class.getResourceAsStream("/images/leaderCards/leader_Id" + (ld.getId()-1) + ".png"));
            imgLeader.get(i).setImage(image);
            imgLeader.get(i).setVisible(true);
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
                if(Gui.getMarket()[i][j] == ResourceType.COIN){
                    image = new Image(MainApp.class.getResourceAsStream("/images/yellowMarble.png"));
                }
                else if(Gui.getMarket()[i][j] == ResourceType.SERVANT){
                    image = new Image(MainApp.class.getResourceAsStream("/images/purpleMarble.png"));
                }
                else if(Gui.getMarket()[i][j] == ResourceType.SHIELD){
                    image = new Image(MainApp.class.getResourceAsStream("/images/blueMarble.png"));
                }
                else if(Gui.getMarket()[i][j] == ResourceType.STONE){
                    image = new Image(MainApp.class.getResourceAsStream("/images/greyMarble.png"));
                }
                else if (Gui.getMarket()[i][j] == ResourceType.EMPTY){
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
        cornerMarket = Gui.getCornerMarble();
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

}
