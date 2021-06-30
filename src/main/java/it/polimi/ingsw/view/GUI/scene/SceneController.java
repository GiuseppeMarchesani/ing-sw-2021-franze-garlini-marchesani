package it.polimi.ingsw.view.GUI.scene;

import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.observer.ObserverView;
import it.polimi.ingsw.view.GUI.MainApp;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;

public class SceneController extends ObservableView {

    private static Scene scene;
    private static GenericSceneController activeController;
    private static BoardSceneController mainController;
    private static Scene mainScene;
    private static Parent mainRoot;

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
    public static void changeRootPane(GenericSceneController controller, String fxml) {
        changeRootPane(controller, scene, fxml);
    }

    public static void changeRootPane(GenericSceneController controller, Scene scene, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxml + ".fxml"));

            loader.setController(controller);
            SceneController.activeController = controller;
            Parent root = loader.load();

            SceneController.scene = scene;
            SceneController.scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static Scene getScene() {
        return scene;
    }

    public static BoardSceneController getMainScene(){
        return mainController;
    }

}
