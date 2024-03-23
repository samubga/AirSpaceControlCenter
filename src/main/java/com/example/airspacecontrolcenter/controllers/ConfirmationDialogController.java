package com.example.airspacecontrolcenter.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ConfirmationDialogController {

    @FXML
    private Button btnYes;

    @FXML
    private Button btnNo;


    @FXML
    private void handleConfirmButton(ActionEvent event) {
        confirmed = true;
        ((Node)(event.getSource())).getScene().getWindow().hide(); // Cerrar la ventana del diálogo
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide(); // Cerrar la ventana del diálogo
    }
    private boolean confirmed = false;

    @FXML
    private void onYesClicked(ActionEvent event) {
        confirmed = true;
        closeDialog();
    }

    @FXML
    private void onNoClicked(ActionEvent event) {
        confirmed = false;
        closeDialog();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    private void closeDialog() {
        Stage stage = (Stage) btnYes.getScene().getWindow();
        stage.close();
    }
}
