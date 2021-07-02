package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.model.enumeration.ResourceType;
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

/**
 * This Scene allow the player to get resources from market.
 */
public class MarketSceneController extends ObservableView implements GenericSceneController {

    @FXML
    private ImageView res0x0;
    @FXML
    private ImageView res0x1;
    @FXML
    private ImageView res0x2;
    @FXML
    private ImageView res1x0;
    @FXML
    private ImageView res1x1;
    @FXML
    private ImageView res1x2;
    @FXML
    private ImageView res2x0;
    @FXML
    private ImageView res2x1;
    @FXML
    private ImageView res2x2;
    @FXML
    private ImageView res3x0;
    @FXML
    private ImageView res3x1;
    @FXML
    private ImageView res3x2;
    @FXML
    private ImageView corner;
    @FXML
    private Button btm_col0;
    @FXML
    private Button btm_col1;
    @FXML
    private Button btm_col2;
    @FXML
    private Button btm_col3;
    @FXML
    private Button btm_row0;
    @FXML
    private Button btm_row1;
    @FXML
    private Button btm_row2;

    private final ArrayList<ImageView> market = new ArrayList<>();
    private ResourceType conversion;

    @FXML
    public void initialize(){
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
        ResourceType cornerMarket = Gui.getCornerMarble();
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

        btm_col0.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onCol0Btm);
        btm_col1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onCol1Btm);
        btm_col2.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onCol2Btm);
        btm_col3.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onCol3Btm);
        btm_row0.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onRow0Btm);
        btm_row1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onRow1Btm);
        btm_row2.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onRow2Btm);
    }

    private void onCol0Btm(Event event){
        btm_row0.setDisable(true);
        btm_row1.setDisable(true);
        btm_row2.setDisable(true);
        btm_col0.setDisable(true);
        btm_col1.setDisable(true);
        btm_col2.setDisable(true);
        btm_col3.setDisable(true);

        notifyObserver(obs -> obs.updateGetFromMarket('c',0, conversion));

    }
    private void onCol1Btm(Event event){
        btm_row0.setDisable(true);
        btm_row1.setDisable(true);
        btm_row2.setDisable(true);
        btm_col0.setDisable(true);
        btm_col1.setDisable(true);
        btm_col2.setDisable(true);
        btm_col3.setDisable(true);

        notifyObserver(obs -> obs.updateGetFromMarket('c',1, conversion));

    }
    private void onCol2Btm(Event event){
        btm_row0.setDisable(true);
        btm_row1.setDisable(true);
        btm_row2.setDisable(true);
        btm_col0.setDisable(true);
        btm_col1.setDisable(true);
        btm_col2.setDisable(true);
        btm_col3.setDisable(true);

        notifyObserver(obs -> obs.updateGetFromMarket('c',2, conversion));

    }
    private void onCol3Btm(Event event){
        btm_row0.setDisable(true);
        btm_row1.setDisable(true);
        btm_row2.setDisable(true);
        btm_col0.setDisable(true);
        btm_col1.setDisable(true);
        btm_col2.setDisable(true);
        btm_col3.setDisable(true);

        notifyObserver(obs -> obs.updateGetFromMarket('c',3, conversion));

    }
    private void onRow0Btm(Event event){
        btm_row0.setDisable(true);
        btm_row1.setDisable(true);
        btm_row2.setDisable(true);
        btm_col0.setDisable(true);
        btm_col1.setDisable(true);
        btm_col2.setDisable(true);
        btm_col3.setDisable(true);

        notifyObserver(obs -> obs.updateGetFromMarket('r',0, conversion));

    }
    private void onRow1Btm(Event event){
        btm_row0.setDisable(true);
        btm_row1.setDisable(true);
        btm_row2.setDisable(true);
        btm_col0.setDisable(true);
        btm_col1.setDisable(true);
        btm_col2.setDisable(true);
        btm_col3.setDisable(true);

        notifyObserver(obs -> obs.updateGetFromMarket('r',1, conversion));

    }
    private void onRow2Btm(Event event){
        btm_row0.setDisable(true);
        btm_row1.setDisable(true);
        btm_row2.setDisable(true);
        btm_col0.setDisable(true);
        btm_col1.setDisable(true);
        btm_col2.setDisable(true);
        btm_col3.setDisable(true);

        notifyObserver(obs -> obs.updateGetFromMarket('r',2, conversion));
    }
    public void setConversion(ResourceType conversion){
        this.conversion=conversion;
    }
}
