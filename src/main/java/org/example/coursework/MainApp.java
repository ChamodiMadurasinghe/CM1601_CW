package org.example.coursework;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        CommContext.initialize();

        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("main-menu.fxml"));
        Parent root = loader.load();

        stage.setTitle("Malabe TukTuk Depot");
        stage.setScene(new Scene(root,720,640));
        stage.show();
    }

    public static void main (String[] args){
        launch(args);
    }
}
