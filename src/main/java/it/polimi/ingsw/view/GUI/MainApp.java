package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.network.ClientMessenger;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.observer.ObserverView;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.List;


public class MainApp  extends Application {

    private static Scene scene;
    private static GenericSceneController activeController;

    @Override
    public void start(Stage stage) throws IOException {
        Gui view = new Gui();
        ClientMessenger clientMessenger = new ClientMessenger(view);
        view.addObserver(clientMessenger);
        scene = new Scene(loadFXML("/fxml/start_scene"));

        StartSceneController newController = new StartSceneController();
        newController.addObserver(clientMessenger);
        stage.setScene(scene);
        stage.setTitle("Masters of Renaissance");
        stage.setMaximized(true);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static <T> T changeRootPane(List<ObserverView> observerViewList, Scene newScene, String fxml) throws IOException {
        T controller = null;
        scene = new Scene(loadFXML(fxml));
        URL url = SceneController.class.getResource(fxml);
        FXMLLoader loader = new FXMLLoader(url);
        controller = loader.getController();
        ((ObservableView) controller).addAllObservers(observerViewList);
        try {
            scene.setRoot(loader.load());
            scene= newScene;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return controller;
    }
    public static void changeRootPane(GenericSceneController controller, Event event, String fxml) {
        Scene scene = ((Node) event.getSource()).getScene();
        changeRootPane(controller, scene, fxml);
    }

    public static void changeRootPane(GenericSceneController newController, Scene newScene, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxml + ".fxml"));
            // Setting the controller BEFORE the load() method.
            loader.setController(newController);
            activeController = newController;

            Parent root = loadFXML(fxml);

            scene = newScene;
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T changeRootPane(List<ObserverView> observerList, String fxml) throws IOException {
        return changeRootPane(observerList, scene, fxml);
    }

    public static void changeRootPane(GenericSceneController controller, String fxml) {
        changeRootPane(controller, scene, fxml);
    }

    private static Parent loadFXML(String fxml) throws IOException {

        URL url = MainApp.class.getResource(fxml + ".fxml");

        FXMLLoader fxmlLoader = new FXMLLoader(url);
        return fxmlLoader.load();
    }


    public static void main(String[] args) {
        launch();
    }
}