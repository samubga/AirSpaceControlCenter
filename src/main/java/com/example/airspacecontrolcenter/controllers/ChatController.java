package com.example.airspacecontrolcenter.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    private ListView<String> chatListView;

    @FXML
    private TextField messageTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                        }
                    }
                };
            }
        });
    }

    @FXML
    private void sendMessage() {
        String message = messageTextField.getText();
        if (!message.isEmpty()) {
            chatListView.getItems().add("You: " + message);
            messageTextField.clear();
            // Simular un retraso de 2-3 segundos antes de que la IA responda
            PauseTransition pause = new PauseTransition(Duration.seconds(2 + Math.random())); // 2-3 segundos
            pause.setOnFinished(event -> {
                // Responder usando la IA después del retraso
                String response = simulatePilotAI(message);
                chatListView.getItems().add("Piloto: " + response);  // Agregar la respuesta de la IA al ListView
            });
            pause.play();
        }
    }

    // Método para simular la IA del piloto
    private String simulatePilotAI(String message) {
        if (message.contains("despegar")) {
            return "Recibido.";
        } else if (message.contains("aterrizar")) {
            return "Entendido. Preparándome para aterrizar.";
        } else if (message.contains("clima")) {
            return "El clima en Madrid es soleado.";}
            else if(message.contains("hola")){
                return "Buenos días";

        } else {
            return "Mensaje no reconocido. Por favor, repita.";
        }
    }
}
