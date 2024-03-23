package com.example.airspacecontrolcenter;

import com.example.airspacecontrolcenter.model.Ciudad;
import com.example.airspacecontrolcenter.model.InfoAirplane;
import com.example.airspacecontrolcenter.controllers.ConfirmationDialogController;
import com.example.airspacecontrolcenter.model.Routes;
import com.example.airspacecontrolcenter.util.NotificationDemo;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.util.Pair;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.*;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
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
    private Button stopButton;

    @FXML
    private TableView tablaPrueba;

    @FXML
    private Label airplaneCount;

    @FXML
    private Label totalAirplaneCount;

    @FXML
    private TextArea textoInfo;

    @FXML
    private StackPane notificationPane;

    @FXML
    private MenuItem menuDespegue;


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

    private Ciudad santaCruzDasFlores = new Ciudad("santaCruzDasFlores", 39.30029918615029, -31.46484375);
    private Ciudad monrovia = new Ciudad("Monrovia", 6.730075707109153, -10.546875);

    private Ciudad sanJose = new Ciudad("San jose", 9.958029972336439, -84.056396484375);

    private Ciudad vietNam = new Ciudad("Vietnam", 14.477234210156519, 108.3251953125);

    private Ciudad reykjavik = new Ciudad("Reykjavik", 64.07219957867282, -21.796875);
    private Ciudad nairobi = new Ciudad("Nairobi", -1.1864386394452024, 36.8701171875);
    private Ciudad pekin = new Ciudad("Pekin", 36.52729481454624, 94.39453125);

    private List<Ciudad> ciudades = new ArrayList<>();

    private Random random = new Random();
    private List<InfoAirplane> totalAirplanes = new ArrayList<>();

    private List<InfoAirplane> airplanes = new ArrayList<>();
    private Timeline timeline;
    private WaypointPainter waypointPainter = new WaypointPainter();
    private int cont = 0;

    private Map<InfoAirplane, MouseListener> airplaneListeners = new HashMap<>();

    private Map<Pair<Ciudad, Ciudad>, Routes> rutasPredefinidas = new HashMap<>();


    @FXML
    public void handleMenuDespegue(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("despegues.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Crear una nueva escena
        Scene scene = new Scene(root);

        // Crear una nueva ventana y configurarla
        Stage stage = new Stage();
        stage.setTitle("Chat");
        stage.setScene(scene);

        // Mostrar la ventana
        stage.show();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load airplane image (assuming the image file is in resources folder)
        System.out.println("Hola");
        System.out.println("Directorio de trabajo actual: " + System.getProperty("user.dir"));
        File file = new File("src\\main\\java\\com\\example\\airspacecontrolcenter\\recursos\\airplane.png");

        Image image = new Image(file.toURI().toString());



        ciudades.add(madrid);

        ciudades.add(roma);
        ciudades.add(newYork);
        /*ciudades.add(dubai);
        ciudades.add(tokyo);
        ciudades.add(sydney);
        ciudades.add(paris);
        ciudades.add(bangkok);
        ciudades.add(losAngeles);
        ciudades.add(santaCruzDasFlores);
        ciudades.add(monrovia);
        ciudades.add(sanJose);
        ciudades.add(vietNam);
        ciudades.add(reykjavik);
        ciudades.add(nairobi);
        ciudades.add(pekin);*/

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

        GeoPosition pos1 = new GeoPosition(41.44272637767212, -54.84375);
        GeoPosition pos2 = new GeoPosition(39.436192999314095, -31.2890625);
        Routes ruta = new Routes(newYork, madrid);
        ruta.agregarPosicionEntreOrigenYDestino(pos1);
        ruta.agregarPosicionEntreOrigenYDestino(pos2);
        rutasPredefinidas.put(new Pair<>(newYork, madrid), ruta);



        Routes ruta3 = new Routes(newYork, roma);
        ruta3.agregarPosicionEntreOrigenYDestino(new GeoPosition(41.44272637767212, -54.84375));
        ruta3.agregarPosicionEntreOrigenYDestino(new GeoPosition(39.436192999314095, -31.2890625));
        rutasPredefinidas.put(new Pair<>(newYork, roma), ruta3);

        //===================================================================================================



        Routes ruta5 = new Routes(madrid, newYork);
        ruta5.agregarPosicionEntreOrigenYDestino(new GeoPosition(39.470125122358176, -31.1572265625));
        ruta5.agregarPosicionEntreOrigenYDestino(new GeoPosition(39.57182223734374, -56.0302734375));
        rutasPredefinidas.put(new Pair<>(madrid, newYork), ruta5);





        //===================================================================================================




        Routes ruta8 = new Routes(roma, newYork);
        ruta8.agregarPosicionEntreOrigenYDestino(new GeoPosition(39.470125122358176, -31.1572265625));
        ruta8.agregarPosicionEntreOrigenYDestino(new GeoPosition(39.57182223734374, -56.0302734375));
        rutasPredefinidas.put(new Pair<>(roma, newYork), ruta8);

        //===================================================================================================






        SwingNode swingNode = new SwingNode();
        createSwingContent(swingNode);
        panel.getChildren().add(swingNode);

        stopButton.setOnAction(event -> stopAnimation());
        startButton.setOnAction(event ->  startAnimation());


    }

    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JPanel mapPanel = new JPanel();
                mapPanel.setLayout(new BorderLayout());
                mapPanel.setPreferredSize(new Dimension(1293, 608)); // Set the preferred size

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

                mapViewer.setZoom(16);
                mapViewer.setAddressLocation(new GeoPosition(madrid.getLatitude(), madrid.getLongitude()));



                // Agregar el JXMapViewer al JPanel
                mapPanel.add(mapViewer, BorderLayout.CENTER);
                // Establecer el contenido del SwingNode como el JPanel
                swingNode.setContent(mapPanel);


            }
        });
    }



    private void startAnimation() {


            // Iniciar la animación
            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.05), event -> {
                        if (airplanes != null) {
                            List<InfoAirplane> airplanesCopy = new ArrayList<>(airplanes);
                            for (InfoAirplane airplane : airplanesCopy) {
                               /*
                                double latitud = airplane.getDf().getPosition().getLatitude();
                                double longitud = airplane.getDf().getPosition().getLongitude();

                                if(longitud <  5.2734375 && longitud > -11.53564453125
                                && latitud < 44.715513732021336 && latitud > 35.65729624809628){
                                    //System.out.println("Avion: " + airplane.getNombre() + " con origen de " + airplane.getCiudadOrigen().getNombre() + " esta en territorio español");
                                }
                                //System.out.println("Coordenadas " +  airplane.getRuta().getListaCoordenadas());

                                 */
                                if(airplane.getRuta() == null){
                                        moveAirplaneNoRoute(airplane, new GeoPosition(airplane.getDestinationLat(), airplane.getDestinationLong()));
                                }else {
                                    moveAirplane(airplane);
                                }
                            }
                        }
                        if (Math.random() < 0.01) {
                            generateAirplane();
                        }
                    })
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();

    }



    private void moveAirplane(InfoAirplane airplane) {




        List<GeoPosition> coordenadas = airplane.getCoordenadas();
        // Obtener la posición actual del avión
        GeoPosition currentPosition = airplane.getDf().getPosition();


        if (coordenadas.isEmpty()) {

            return;
        }

        //System.out.println(airplane.getNombre() + "Destino " + destination);
        //System.out.println(airplane.getNombre() + "Origen " + airplane.getDf().getPosition());


        GeoPosition destination = coordenadas.get(0);
        System.out.println("Siguiente coordenada arriba: " + destination);
        // Calcular la distancia y el rumbo hacia el destino
        double distance = calculateDistance(currentPosition, destination);
        System.out.println("distancia " + distance);
        double bearing = calculateBearing(currentPosition, destination);
        // Velocidad máxima del avión en kilómetros por iteración
        double maxSpeed = airplane.getSpeed();

        // Definir latDelta y lonDelta fuera de la lógica condicional
        double latDelta;
        double lonDelta;

        if(coordenadas.size() == 1){

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
        }else{
            latDelta = maxSpeed * Math.cos(Math.toRadians(bearing));
            lonDelta = maxSpeed * Math.sin(Math.toRadians(bearing));
        }




        // Calcular la nueva posición del avión
        double newLat = currentPosition.getLatitude() + latDelta;
        double newLon = currentPosition.getLongitude() + lonDelta;

        // Actualizar la posición del avión
        GeoPosition newPosition = new GeoPosition(newLat, newLon);
        airplane.getDf().setPosition(newPosition);

        // Volver a pintar el mapa para reflejar los cambios
        mapViewer.repaint();


        if(coordenadas.size() == 1){
            if (distance <= 0.01) {
                // Verificar si el avión ha llegado a su destino
                coordenadas.removeFirst(); // Remover el destino actual de la lista de coordenadas de la ruta
                System.out.println("coordenada removido");
                // Verificar si todavía hay más destinos en la ruta
                if (!coordenadas.isEmpty()) {
                    // Actualizar el próximo destino en la ruta
                    destination = coordenadas.get(0);
                } else {
                    // Si no hay más destinos en la ruta, remover el avión
                    airplanes.remove(airplane);
                    removeWaypoint(airplane.getDf());
                    return;
                }
            }
        }else{
            if (distance <= 10.01) {
                // Verificar si el avión ha llegado a su destino
                coordenadas.removeFirst(); // Remover el destino actual de la lista de coordenadas de la ruta
                System.out.println("tamaño " + coordenadas.size());
                // Verificar si todavía hay más destinos en la ruta
                if (!coordenadas.isEmpty()) {
                    // Actualizar el próximo destino en la ruta
                    destination = coordenadas.get(0);
                    System.out.println("siguiente coordenada " + destination);
                } else {
                    // Si no hay más destinos en la ruta, remover el avión
                    airplanes.remove(airplane);
                    removeWaypoint(airplane.getDf());
                    return;
                }
            }

        }
    }


    private void moveAirplaneNoRoute(InfoAirplane airplane, GeoPosition destination) {


        // Obtener la posición actual del avión
        GeoPosition currentPosition = airplane.getDf().getPosition();


        //System.out.println(airplane.getNombre() + "Destino " + destination);
        //System.out.println(airplane.getNombre() + "Origen " + airplane.getDf().getPosition());


        // Calcular la distancia y el rumbo hacia el destino
        double distance = calculateDistance(currentPosition, destination);
        double bearing = calculateBearing(currentPosition, destination);
        // Velocidad máxima del avión en kilómetros por iteración
        double maxSpeed = airplane.getSpeed();

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
        airplane.getDf().setPosition(newPosition);

        // Volver a pintar el mapa para reflejar los cambios
        mapViewer.repaint();

        // Verificar si el avión ha llegado a su destino
        if (distance <= 0.01) {
            airplanes.remove(airplane);
            removeWaypoint(airplane.getDf());
            Platform.runLater(() -> {

                airplaneCount.setText(airplanes.size() + "");
                totalAirplaneCount.setText(totalAirplanes.size() + "");
            });
        }
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


        DefaultWaypoint df = new DefaultWaypoint(new GeoPosition(origen.getLatitude(), origen.getLongitude()));
        df.setPosition(new GeoPosition(origen.getLatitude(), origen.getLongitude())); // Set the position of the DefaultWaypoint object
        Set<Waypoint> newWaypoints = new HashSet<>(waypointPainter.getWaypoints());
        newWaypoints.add(df);
        waypointPainter.setWaypoints(newWaypoints);
        cont++;
        Routes ruta = obtenerRutaPredefinida(origen,destino);
        InfoAirplane airplane = new InfoAirplane("BJK" + cont, df, destino.getLatitude(), destino.getLongitude(), 0.11, origen, destino, ruta);
        try{
            System.out.println("Avion creado con la siguiente cantidad de coordenadas: " + ruta.getListaCoordenadas());
            List<GeoPosition> coordenadas = new ArrayList<>(ruta.getListaCoordenadas());
            airplane.setCoordenadas(coordenadas);
        }catch(NullPointerException e){

        }


        waypointPainter.setRenderer(new WaypointRenderer<Waypoint>() {
            @Override
            public void paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint wp) {
                // Obtener la posición del waypoint
                GeoPosition pos = wp.getPosition();

                // Convertir la posición del waypoint a coordenadas de píxeles en el mapa
                Point2D point = map.getTileFactory().geoToPixel(pos, map.getZoom());

                // Dibujar la imagen del waypoint en la posición calculada
                ImageIcon icon = new ImageIcon("src\\main\\java\\com\\example\\airspacecontrolcenter\\recursos\\airplane.png"); // Ruta a la imagen
                g.drawImage(icon.getImage(), (int) point.getX(), (int) point.getY(), map);



            }
        });

        mapViewer.setOverlayPainter(waypointPainter);// Add the MouseListener to the JXMapViewer object

        if (airplane.getCiudadOrigen().getNombre().equals("madrid")) {
            NotificationDemo.showNotification(notificationPane,"Esperando permiso para despegar","Avión " + airplane.getNombre() + " con origen de " + airplane.getCiudadOrigen().getNombre() + "\ny destino en " + airplane.getCiudadDestino().getNombre());
            //openChat(airplane);
            totalAirplanes.add(airplane);
            airplanes.add(airplane);
        }
        else{
            totalAirplanes.add(airplane);
            airplanes.add(airplane);
        }




        String info = "Avion: " + airplane.getNombre() + " Origen: " + airplane.getCiudadOrigen().getNombre() + " Destino: " + airplane.getCiudadDestino().getNombre() + "\n";

        // Obtener el texto actual del TextArea y agregar la nueva información
        String currentText = textoInfo.getText();
        String updatedText = currentText + info;


        // Actualizar la cantidad de aviones y total de aviones
        // Update the airplane count on the JavaFX application thread
        Platform.runLater(() -> {
            textoInfo.setText(updatedText);

            airplaneCount.setText(airplanes.size() + "");
            textoInfo.positionCaret(updatedText.length());
            totalAirplaneCount.setText(totalAirplanes.size() + "");
        });
    }


    private Routes obtenerRutaPredefinida(Ciudad origen, Ciudad destino) {
        Pair<Ciudad, Ciudad> key = new Pair<>(origen, destino);
        return rutasPredefinidas.get(key);
    }





    private void openChat(InfoAirplane airplane){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Crear una nueva escena
        Scene scene = new Scene(root);

        // Crear una nueva ventana y configurarla
        Stage stage = new Stage();
        stage.setTitle("Chat");
        stage.setScene(scene);

        // Mostrar la ventana
        stage.show();
    }




    private void openConfirmationDialog(InfoAirplane airplane) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfirmationDialog.fxml"));
            Parent root = loader.load();

            ConfirmationDialogController controller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Agregar un listener para verificar si se confirmó o canceló la acción
            stage.setOnHidden(e -> {
                if (controller.isConfirmed()) {
                    airplanes.add(airplane);
                    totalAirplanes.add(airplane); // Agregar el avión a la lista si se confirma
                } else {
                    System.out.println("Cancelado...");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void removeAirplane(InfoAirplane airplane) {
        // Elimina el avión del mapa y de la lista de aviones
        airplanes.remove(airplane);

        // Obtén el MouseListener asociado al avión y elimínalo del JXMapViewer
        MouseListener listener = airplaneListeners.get(airplane);
        mapViewer.removeMouseListener(listener);

        // Elimina la entrada correspondiente del mapa
        airplaneListeners.remove(airplane);
    }


    private void removeWaypoint(DefaultWaypoint waypoint) {


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



}
