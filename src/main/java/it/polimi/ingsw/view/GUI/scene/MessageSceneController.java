package it.polimi.ingsw.view.GUI.scene;

import javafx.scene.control.Alert;

public class MessageSceneController {

    public static void display(String title, String message){
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.setTitle(title);
        errorAlert.showAndWait();
    }
}

