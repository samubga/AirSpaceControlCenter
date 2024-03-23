package com.example.airspacecontrolcenter.util;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class NotificationDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crear una escena
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 400, 300);

        // Mostrar la escena
        primaryStage.setScene(scene);
        primaryStage.show();

        // Mostrar la notificación
        showNotification(root, "Advertencia","¡Notificación!");
    }

    public static void showNotification(StackPane root, String title, String message) {
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD,16));
        titleLabel.setTextFill(Color.BLACK);
        File file = new File("src/main/java/com/example/airspacecontrolcenter/recursos/warning.png");

        Image image = new Image(file.toURI().toString());
        ImageView warningIcon = new ImageView(image);
        warningIcon.setFitWidth(20);
        warningIcon.setFitHeight(20);

        VBox vbox = new VBox(1);
        vbox.setAlignment(Pos.CENTER);

        // Crear un HBox para el icono y el título
        HBox iconTitleBox = new HBox(5);
        iconTitleBox.setAlignment(Pos.CENTER); // Alinear a la izquierda
        iconTitleBox.getChildren().addAll(warningIcon, titleLabel); // Añadir el icono y el título al HBox

        vbox.getChildren().addAll(iconTitleBox); // Añadir el HBox al VBox

        Label messageLabel = new Label(message);
        messageLabel.setFont(Font.font("Arial", 14));
        messageLabel.setTextFill(Color.BLACK);

        VBox notificationBox = new VBox(10, vbox, messageLabel);
        notificationBox.setStyle("-fx-background-color: #f1c40f; -fx-padding: 5px; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");
        notificationBox.setAlignment(Pos.CENTER);



        Timeline fadeInTimeline = new Timeline();
        fadeInTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), new KeyValue(notificationBox.opacityProperty(), 1)));

        Timeline fadeOutTimeline = new Timeline();
        fadeOutTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(5000), new KeyValue(notificationBox.opacityProperty(), 0)));
        notificationBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Abre una nueva ventana
                openNewWindow();
            }
        });


        root.getChildren().add(notificationBox);
        StackPane.setAlignment(notificationBox, Pos.TOP_CENTER);
        fadeInTimeline.play();
        fadeOutTimeline.playFromStart();
    }

    private static void openNewWindow() {
        // Crea una nueva ventana
        Stage newStage = new Stage();
        VBox root = new VBox();
        Scene scene = new Scene(root, 300, 200);
        Label label = new Label("¡Nueva ventana!");
        root.getChildren().add(label);
        newStage.setScene(scene);
        newStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
