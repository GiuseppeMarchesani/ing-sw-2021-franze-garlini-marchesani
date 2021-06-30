package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.network.ClientMessenger;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.observer.ObserverView;
import it.polimi.ingsw.view.GUI.scene.BoardSceneController;
import it.polimi.ingsw.view.GUI.scene.GenericSceneController;
import it.polimi.ingsw.view.GUI.scene.MessageSceneController;
import it.polimi.ingsw.view.GUI.scene.StartSceneController;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PopupControl;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.List;


public class MainApp  extends Application {


    @Override
    public void start(Stage stage){
        Gui view = new Gui();
        ClientMessenger clientMessenger = new ClientMessenger(view);
        view.addObserver(clientMessenger);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/start_scene.fxml"));
        Parent rootLayout = null;
        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        StartSceneController controller = loader.getController();
        controller.addObserver(clientMessenger);
        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);

        stage.setResizable(true);
        stage.setMaximized(false);
        stage.setTitle("Masters of Renaissance");
        stage.show();

    }




}