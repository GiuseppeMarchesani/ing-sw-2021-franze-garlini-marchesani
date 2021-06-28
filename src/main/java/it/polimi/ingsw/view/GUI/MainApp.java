package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.network.ClientMessenger;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.observer.ObserverView;
import it.polimi.ingsw.view.GUI.scene.BoardSceneController;
import it.polimi.ingsw.view.GUI.scene.GenericSceneController;
import it.polimi.ingsw.view.GUI.scene.StartSceneController;
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
    private static BoardSceneController mainController;
    private static Scene mainScene;
    private static Parent mainRoot;

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
        stage.setFullScreen(false);
        stage.setTitle("Masters of Renaissance");
        stage.show();

    }


    public static <T> T changeRootPane(List<ObserverView> observerViewList, Scene newScene, String fxml) {

        T controller = null;

        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxml + ".fxml"));
            Parent root = loader.load();
            controller = loader.getController();
            ((ObservableView) controller).addAllObservers(observerViewList);

            activeController = (GenericSceneController) controller;
            scene = newScene;
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controller;
    }

    public static <T> T changeRootPane(List<ObserverView> observerList, String fxml) {
        return changeRootPane(observerList, scene, fxml);
    }

    public static <T> void changeRootMainScene(List<ObserverView> observerList) {
        T controller;
        if(mainController==null){
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/board_scene.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                controller = loader.getController();
                ((ObservableView) controller).addAllObservers(observerList);
                mainController = (BoardSceneController) controller;
                activeController = (GenericSceneController) controller;
                scene.setRoot(root);
                mainRoot = root;
                mainScene = scene;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else{

            scene = mainScene;
            activeController = mainController;
            scene.setRoot(mainRoot);
        }
    }

    public static <T> T changeRootPane(List<ObserverView> observerList, Event event, String fxml) {
        Scene newScene = ((Node) event.getSource()).getScene();
        return changeRootPane(observerList, newScene, fxml);
    }

    public static void main(String[] args) {
        launch();
    }

    public static Scene getScene() {
        return scene;
    }

    public static BoardSceneController getMainScene(){
        return mainController;
    }
}