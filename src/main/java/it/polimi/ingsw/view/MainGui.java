package it.polimi.ingsw.view;

import it.polimi.ingsw.network.ClientMessenger;
import it.polimi.ingsw.view.ScenesController.InitSceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * Gui class that starts the JavaFX application.
 */
public class MainGui extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Gui view = new Gui();
        ClientMessenger clientMessenger = new ClientMessenger(view);
        view.addObserver(clientMessenger);

        //Load root node from fxml file.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/init_scene.fxml"));

        Parent root = loader.load();

        InitSceneController initController = loader.getController();
        initController.addObserver(clientMessenger);

        //Show the init scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(1280d);
        stage.setHeight(720d);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setTitle("Master of Reinassance");
        stage.show();
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
