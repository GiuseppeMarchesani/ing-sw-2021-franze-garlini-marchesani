package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.network.ClientMessenger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {



    private static Scene scene;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Gui view = new Gui();
        ClientMessenger clientMessenger= new ClientMessenger(view);
        view.addObserver(clientMessenger);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/start_scene.fxml"));
        Parent rootLayout = null;
        try{
            rootLayout= loader.load();
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);

        }
        InitSceneController isc = loader.getController();
        isc.addObserver(clientMessenger);

        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);
        stage.setWidth(1280d);
        stage.setHeight(720d);
        stage.setResizable(true);
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
