package it.polimi.ingsw.view.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Gui class that starts the JavaFX application.
 */
public class LauncherApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Load root layout from fxml file.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/menu_scene.fxml"));
        Parent rootLayout = null;




        // FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/init_Scene.fxml"));
        Parent root = loader.load();
        SceneController controller = loader.getController();
    }



    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
