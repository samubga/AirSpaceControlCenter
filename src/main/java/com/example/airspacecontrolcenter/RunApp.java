package com.example.airspacecontrolcenter;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.io.IOException;


public class RunApp extends Application {



    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view2.fxml"));
        Pane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root); // Aumentamos la altura para acomodar los botones
        primaryStage.setScene(scene);
        primaryStage.setTitle("Air Traffic Control");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
