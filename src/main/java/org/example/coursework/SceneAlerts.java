package org.example.coursework;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


public class SceneAlerts {
    public static void openWindow (String fxmlFile,String title){
        try{
            FXMLLoader loader = new FXMLLoader(SceneAlerts.class.getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception e){
            showError("Could not open screen: " + e.getMessage());
        }
    }

    public static void showInfo(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR,message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
