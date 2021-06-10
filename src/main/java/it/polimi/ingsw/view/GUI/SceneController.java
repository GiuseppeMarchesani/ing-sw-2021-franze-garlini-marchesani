package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.observer.ObserverView;
import it.polimi.ingsw.view.GUI.guiController.GeneralController;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;

import java.io.IOException;
import java.util.List;

public class SceneController extends ObservableView {
    private static Scene activeScene;
    private static GeneralController generalController;

    public Scene getActiveScene() {
        return activeScene;
    }
    public GeneralController generalController(){
        return generalController;
    }

    public static <T> T changeRootPane(List<ObserverView> observerViewList, Scene scene, String fxml){
        T controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            Parent root= loader.load();
            controller = loader.getController();
            ((ObservableView) controller).addAllObservers(observerViewList);

            generalController = (GeneralController) controller;
            activeScene = scene;
            activeScene.setRoot(root);

        } catch (IOException e){

        }
        return controller;
    }

    public static <T> T changeRootPane(List<ObserverView> observerViewList, Event event, String fxml){
        Scene scene = ((Node) event.getSource()).getScene();
        return changeRootPane(observerViewList, scene, fxml);
    }

    public static <T> T changeRootPane(List<ObserverView> observerList, String fxml) {
        return changeRootPane(observerList, activeScene, fxml);
    }

    public static void changeRootPane(GeneralController controller, Scene scene, String fxml){
        try{
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            loader.setController(controller);
            generalController = controller;
            Parent root = loader.load();

            activeScene = scene;
            activeScene.setRoot(root);
        } catch (IOException e){

        }
    }
    public static void changeRootPane(GeneralController controller, Event event, String fxml) {
        Scene scene = ((Node) event.getSource()).getScene();
        changeRootPane(controller, scene, fxml);
    }

    public static void changeRootPane(GeneralController controller, String fxml) {
        changeRootPane(controller, activeScene, fxml);
    }


}