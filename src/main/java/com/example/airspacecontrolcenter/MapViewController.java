package com.example.airspacecontrolcenter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class MapViewController implements Initializable {



    @FXML
    private ImageView mapImageView;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Pane panel;

    @FXML
    private Button startButton;

    @FXML
    private Circle clickIndicator;

    @FXML
    private Button stopButton;

    @FXML
    private TableView tablaPrueba;

    @FXML
    private Label airplaneCount;

    @FXML
    private Label totalAirplaneCount;

    private static final int MAP_WIDTH = 1400;
    private static final int MAP_HEIGHT = 650;
    private Ciudad newYork = new Ciudad("Nueva york",274, 181);
    private Ciudad madrid = new Ciudad("madrid",520.0, 183.0);
    private Ciudad brasilia = new Ciudad("brasilia",343.0, 446.0);
    private Ciudad losAngeles = new Ciudad("losAngeles",92.0, 211.0);
    private Ciudad roma = new Ciudad("roma",586.0, 177.0);
    private Ciudad dubai = new Ciudad("dubai",740.0, 258.0);
    private Ciudad tokyo = new Ciudad("tokyo",1057.0, 205.0);
    private Ciudad sydney = new Ciudad("sydney",1095.0, 529.0);
    private Ciudad paris = new Ciudad("paris",539.0, 147.05);
    private Ciudad bangkok = new Ciudad("bangkok",927.0, 307.0);
    private List<Ciudad> ciudades = new ArrayList<>();

    private Random random = new Random();
    private List<Airplane> totalAirplanes = new ArrayList<>();

    private List<Airplane> airplanes = new ArrayList<>();
    private Timeline timeline;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load airplane image (assuming the image file is in resources folder)
        System.out.println("Hola");
        System.out.println("Directorio de trabajo actual: " + System.getProperty("user.dir"));
        File file = new File("src\\main\\java\\com\\example\\airspacecontrolcenter\\recursos\\airplane.png");

        Image image = new Image(file.toURI().toString());



        ciudades.add(madrid);
        ciudades.add(brasilia);
        ciudades.add(losAngeles);
        ciudades.add(roma);
        ciudades.add(dubai);
        ciudades.add(tokyo);
        ciudades.add(sydney);
        ciudades.add(paris);
        ciudades.add(bangkok);
        ciudades.add(newYork);



         Session laSesion = null;
         SessionFactory fabrica = null;

        File file3 = new File("src\\main\\java\\com\\example\\airspacecontrolcenter\\hibernate.cfg.xml");
        Configuration cfg = new Configuration().configure(file3.toURI().toString());


        cfg.addAnnotatedClass(Ciudad.class);
        fabrica = cfg.buildSessionFactory();
        laSesion = fabrica.openSession();


        Ciudad ciudad = new Ciudad("La mama de la mama", 40.7128, -74.0060);
        laSesion.beginTransaction();
        laSesion.persist(ciudad);
        laSesion.getTransaction().commit();
        laSesion.close();



        File file2 = new File("src\\main\\java\\com\\example\\airspacecontrolcenter\\recursos\\mapa.png");

        Image image2 = new Image(file2.toURI().toString());

        mapImageView.setImage(image2);
        mapImageView.setFitWidth(MAP_WIDTH);
        mapImageView.setFitHeight(MAP_HEIGHT);
        // Handle mouse click on the map to set the airplane destination
        mapImageView.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();
            System.out.println("Coordenadas del clic: (" + x + ", " + y + ")");

            // Inicializar el indicador de clic si aún no se ha hecho
            if (clickIndicator == null) {
                clickIndicator = new Circle(5);
                clickIndicator.setFill(Color.RED); // Color rojo para el indicador de clic
                anchorPane.getChildren().add(clickIndicator);
            }

            // Mover el indicador de clic a la posición del clic
            clickIndicator.setCenterX(x);
            clickIndicator.setCenterY(y);
        });


        stopButton.setOnAction(event -> stopAnimation());
        startButton.setOnAction(event -> startAnimation());


    }

    private void startAnimation() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.09), event -> {
                    for (Airplane airplane : totalAirplanes) {
                        // Mueve el avión hacia su destino
                        moveAirplane(airplane, airplane.getDestinationX(), airplane.getDestinationY());
                    }
                    // Verifica si se debe generar un nuevo avión
                    if (Math.random() < 0.09) {
                        generateAirplane();
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void stopAnimation() {
        if (timeline != null) {
            timeline.stop(); // Detener la Timeline si está en ejecución
        }
    }

    private void generateAirplane() {
        File file = new File("src\\main\\java\\com\\example\\airspacecontrolcenter\\recursos\\airplane.png");
        Image image = new Image(file.toURI().toString());


        int ciudadOrigenAleatoria = random.nextInt(ciudades.size());
        Ciudad origen = ciudades.get(ciudadOrigenAleatoria);

        int ciudadDestinoAleatoria;
        Ciudad destino = null;

        do {
            ciudadDestinoAleatoria = random.nextInt(ciudades.size());
            destino = ciudades.get(ciudadDestinoAleatoria);
        } while (ciudadOrigenAleatoria == ciudadDestinoAleatoria);





        // Usa las coordenadas de las ciudades como posición inicial y final de los aviones
        ImageView airplaneImageView = new ImageView(image);


        Airplane air = new Airplane(airplaneImageView, destino.getCoordenadaX() +5, destino.getCoordenadaY() +5, 1);


        air.getImageView().setX(origen.getCoordenadaX() - 6);
        air.getImageView().setY(origen.getCoordenadaY() - 6);
        air.getImageView().setFitWidth(15);
        air.getImageView().setFitHeight(15);
        panel.getChildren().add(air.getImageView());
        totalAirplanes.add(air);
        airplanes.add(air);
        airplaneCount.setText("" + airplanes.size());
        totalAirplaneCount.setText("" + totalAirplanes.size());
    }


    // Method to update airplane position based on animation (replace with your logic)
    private void moveAirplane(Airplane airplane, double destinationX, double destinationY) {
        // La lógica para mover el avión individualmente hacia las coordenadas de destino
        double currentX = airplane.getImageView().getX() + airplane.getImageView().getImage().getWidth() / 2;

        // Coordenada X del centro del avión
        double currentY = airplane.getImageView().getY() + airplane.getImageView().getImage().getHeight() / 2; // Coordenada Y del centro del avión

        // Calcular la distancia entre el avión y el destino
        double deltaX = destinationX - currentX;
        double deltaY = destinationY - currentY;
        double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        // Si la distancia es mayor que un cierto umbral, sigue moviendo el avión hacia el destino
        if (distance > 1) {
            // Calcular el ángulo de rotación
            double angle = Math.atan2(deltaY, deltaX);

            // Calcular el desplazamiento en X y Y basado en la distancia
            double moveX = (deltaX / distance) * airplane.getSpeed();
            double moveY = (deltaY / distance) * airplane.getSpeed();

            // Actualizar la posición del avión
            double newX = currentX + moveX;
            double newY = currentY + moveY;
            airplane.getImageView().setX(newX - airplane.getImageView().getImage().getWidth() / 2);
            airplane.getImageView().setY(newY - airplane.getImageView().getImage().getHeight() / 2);

            // Rotar el avión para que apunte hacia su destino
            airplane.getImageView().setRotate(Math.toDegrees(angle));




        } else {
             airplanes.remove(airplane);
             airplane.getImageView().setVisible(false);

        }
    }

}
