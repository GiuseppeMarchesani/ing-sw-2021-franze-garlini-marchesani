package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.network.ClientMessenger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {



    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Gui view = new Gui();
        ClientMessenger clientMessenger = new ClientMessenger(view);
        view.addObserver(clientMessenger);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainApp.class.getResource("/fxml/start_scene.fxml"));

        Parent root = fxmlLoader.load();

        StartSceneController controller = fxmlLoader.getController();
        controller.addObserver(clientMessenger);


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(732d);
        stage.setHeight(400d);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setFullScreen(false);
        stage.setTitle("Masters of Renaissance Game");
        stage.show();

    }

    @Override
    public void stop(){
        Platform.exit();
        System.exit(0);
    }
}
