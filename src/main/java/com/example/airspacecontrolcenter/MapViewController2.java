package com.example.airspacecontrolcenter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.List;

public class MapViewController2 implements Initializable {





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

    private JXMapViewer mapViewer;
    private static final int MAP_WIDTH = 1400;
    private static final int MAP_HEIGHT = 650;
    private Ciudad newYork = new Ciudad("Nueva york",40.68063802521456, -74.1796875);
    private Ciudad madrid = new Ciudad("madrid",40.3130432088809, -3.5595703125);
    private Ciudad brasilia = new Ciudad("brasilia",-15.707662769583505, -47.5927734375);
    private Ciudad losAngeles = new Ciudad("losAngeles",33.7243396617476, -117.685546875);
    private Ciudad roma = new Ciudad("roma",41.88592102814744, 12.67822265625);
    private Ciudad dubai = new Ciudad("dubai",23.725011735951796, 54.55810546875);
    private Ciudad tokyo = new Ciudad("tokyo",35.38904996691167, 139.04296875);
    private Ciudad sydney = new Ciudad("sydney",-34.234512362369856, 150.29296875);
    private Ciudad paris = new Ciudad("paris",48.661942846070055, 2.3291015625);
    private Ciudad bangkok = new Ciudad("bangkok",20.385825381874263, 95.009765625);
    private List<Ciudad> ciudades = new ArrayList<>();

    private Random random = new Random();
    private List<DefaultWaypoint> totalAirplanes = new ArrayList<>();

    private List<DefaultWaypoint> airplanes = new ArrayList<>();
    private Timeline timeline;
    private static Timer timer;
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







        /*

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

        */






        SwingNode swingNode = new SwingNode();
        createSwingContent(swingNode);
        panel.getChildren().add(swingNode);



        // Handle mouse click on the map to set the airplane destination



        stopButton.setOnAction(event -> stopAnimation());
        startButton.setOnAction(event ->  startAnimation());


    }

    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JPanel mapPanel = new JPanel();
                mapPanel.setLayout(new BorderLayout());
                mapPanel.setPreferredSize(new Dimension(1293, 643)); // Set the preferred size

                // Inicializar el JXMapViewer
                mapViewer = new JXMapViewer();
                TileFactoryInfo info = new OSMTileFactoryInfo();
                DefaultTileFactory tileFactory = new DefaultTileFactory(info);
                mapViewer.setTileFactory(tileFactory);


                // EVENTOS Y LISTENERS
                mapViewer.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);

                        // Obtener las coordenadas del clic dentro del JXMapViewer
                        Point clickPoint = e.getPoint();
                        GeoPosition clickedPosition = mapViewer.convertPointToGeoPosition(clickPoint);
                        double latitude = clickedPosition.getLatitude();
                        double longitude = clickedPosition.getLongitude();

                        // Imprimir las coordenadas en la consola
                        System.out.println("Coordenadas del clic: (" + latitude + ", " + longitude + ")");

                        // Aquí puedes realizar las acciones que necesites con las coordenadas obtenidas
                    }
                });


                MouseInputListener mia = new PanMouseInputListener(mapViewer);
                mapViewer.addMouseListener(mia);
                mapViewer.addMouseMotionListener(mia);
                mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));



                // Agregar el JXMapViewer al JPanel
                mapPanel.add(mapViewer, BorderLayout.CENTER);
                generateAirplane();
                // Establecer el contenido del SwingNode como el JPanel
                swingNode.setContent(mapPanel);
            }
        });
    }

    private void startAnimation() {
        GeoPosition gp = new GeoPosition(41.88592102814744, 12.67822265625);
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.09), event -> {

                    for (DefaultWaypoint airplane : totalAirplanes) {
                        moveAirplane(airplane, gp);
                    }

                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


    }

    private void moveAirplane(DefaultWaypoint airplane, GeoPosition destination) {
        // Obtener la posición actual del avión
        GeoPosition currentPosition = airplane.getPosition();

        // Calcular la distancia y el rumbo hacia el destino
        double distance = calculateDistance(currentPosition, destination);
        double bearing = calculateBearing(currentPosition, destination);
        System.out.println("distance " + distance);
        // Velocidad máxima del avión en kilómetros por iteración
        double maxSpeed = 0.4;

        // Definir latDelta y lonDelta fuera de la lógica condicional
        double latDelta;
        double lonDelta;

        // Si la distancia es menor que el umbral, reducir la velocidad gradualmente
        if (distance < 30) {
            // Calcular la velocidad reducida proporcionalmente a la distancia
            double reducedSpeed = maxSpeed * (distance / 30); // La velocidad se reduce a medida que la distancia disminuye
            latDelta = reducedSpeed * Math.cos(Math.toRadians(bearing));
            lonDelta = reducedSpeed * Math.sin(Math.toRadians(bearing));
        } else {
            // Usar la velocidad máxima si la distancia es mayor que el umbral
            latDelta = maxSpeed * Math.cos(Math.toRadians(bearing));
            lonDelta = maxSpeed * Math.sin(Math.toRadians(bearing));
        }

        // Calcular la nueva posición del avión
        double newLat = currentPosition.getLatitude() + latDelta;
        double newLon = currentPosition.getLongitude() + lonDelta;

        // Actualizar la posición del avión
        GeoPosition newPosition = new GeoPosition(newLat, newLon);
        airplane.setPosition(newPosition);

        // Volver a pintar el mapa para reflejar los cambios
        mapViewer.repaint();

        // Verificar si el avión ha llegado a su destino
        if (distance == 0.0) {
            airplanes.remove(airplane);
            removeWaypoint(airplane);
            System.out.println("El avión ha llegado a su destino.");
        }
    }

    private void removeWaypoint(DefaultWaypoint waypoint) {

        // Get the WaypointPainter for the waypoints layer
        WaypointPainter waypointPainter = (WaypointPainter) mapViewer.getOverlayPainter();

// Create a new set of waypoints that does not include the waypoint to remove
        Set<Waypoint> newWaypoints = new HashSet<>(waypointPainter.getWaypoints());
        newWaypoints.remove(waypoint);

// Set the WaypointPainter's set of waypoints to the new set
        waypointPainter.setWaypoints(newWaypoints);

// Update the display
        mapViewer.repaint();
    }

    private double calculateDistance(GeoPosition pos1, GeoPosition pos2) {
        double lat1 = Math.toRadians(pos1.getLatitude());
        double lon1 = Math.toRadians(pos1.getLongitude());
        double lat2 = Math.toRadians(pos2.getLatitude());
        double lon2 = Math.toRadians(pos2.getLongitude());

        // Radio de la Tierra en kilómetros
        double radius = 6371.0;

        // Fórmula de Haversine para calcular la distancia entre dos puntos en la Tierra
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = radius * c;

        return distance;
    }



    private double calculateBearing(GeoPosition pos1, GeoPosition pos2) {
        double lat1 = Math.toRadians(pos1.getLatitude());
        double lon1 = Math.toRadians(pos1.getLongitude());
        double lat2 = Math.toRadians(pos2.getLatitude());
        double lon2 = Math.toRadians(pos2.getLongitude());

        double dLon = lon2 - lon1;

        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon);

        double bearing = Math.atan2(y, x);

        // Convertir el rumbo de radianes a grados
        bearing = Math.toDegrees(bearing);

        // Ajustar el rango de 0 a 360 grados
        bearing = (bearing + 360) % 360;

        return bearing;
    }


    private void stopAnimation() {
        if (timeline != null) {
            timeline.stop(); // Detener la Timeline si está en ejecución
        }
    }

    private void generateAirplane() {
        // Elegir una ciudad de origen aleatoria
        int ciudadOrigenAleatoria = random.nextInt(ciudades.size());
        Ciudad origen = ciudades.get(ciudadOrigenAleatoria);

        // Elegir una ciudad de destino aleatoria diferente a la de origen
        int ciudadDestinoAleatoria;
        Ciudad destino = null;
        do {
            ciudadDestinoAleatoria = random.nextInt(ciudades.size());
            destino = ciudades.get(ciudadDestinoAleatoria);
        } while (ciudadOrigenAleatoria == ciudadDestinoAleatoria);


        DefaultWaypoint df = new DefaultWaypoint(new GeoPosition(madrid.getLatitude(), madrid.getLongitude()));
        WaypointPainter waypointPainter = new WaypointPainter();
        waypointPainter.setWaypoints(Collections.singleton((Waypoint) df));
        mapViewer.setOverlayPainter(waypointPainter);


        totalAirplanes.add(df);
        airplanes.add(df);
        // Actualizar la cantidad de aviones y total de aviones
        // Update the airplane count on the JavaFX application thread
        Platform.runLater(() -> {
            airplaneCount.setText(airplanes.size() + "");
            totalAirplaneCount.setText(totalAirplanes.size() + "");
        });
    }


    // Method to update airplane position based on animation (replace with your logic)


}
