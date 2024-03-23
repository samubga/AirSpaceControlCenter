package com.example.airspacecontrolcenter.oldversion;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class AirTrafficControl extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crear una imagen de avión
        File file = new File("src\\main\\java\\com\\example\\airspacecontrolcenter\\recursos\\airplane.png");

        Image avionImage = new Image(file.toURI().toString());
        ImageView avionImageView = new ImageView(avionImage);
        avionImageView.setFitWidth(50);
        avionImageView.setFitHeight(50);

        // Crear el camino (ruta) del avión
        Path path = new Path();
        path.getElements().add(new MoveTo(50, 50)); // Inicio del camino
        path.getElements().add(new LineTo(500, 50)); // Fin del camino

        // Crear la transición de la ruta
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(5)); // Duración del desplazamiento (en segundos)
        pathTransition.setNode(avionImageView);
        pathTransition.setPath(path);
        pathTransition.setCycleCount(PathTransition.INDEFINITE); // Repetir la animación indefinidamente
        pathTransition.setAutoReverse(true); // Reverse animation

        // Crear el grupo que contendrá la imagen del avión
        Group root = new Group(avionImageView);

        // Crear la escena
        Scene scene = new Scene(root, 600, 200);

        // Mostrar la ventana
        primaryStage.setScene(scene);
        primaryStage.setTitle("Control de Tráfico Aéreo");
        primaryStage.show();

        // Iniciar la animación
        pathTransition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}