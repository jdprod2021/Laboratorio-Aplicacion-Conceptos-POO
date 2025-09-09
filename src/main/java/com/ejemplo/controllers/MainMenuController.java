package com.ejemplo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAñadir;

    @FXML
    private Button btnCursos;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEstudiantes;

    @FXML
    private Button btnMoreInfo;

    @FXML
    private Button btnProfesores;

    @FXML
    private Label lblMainStatus;

    @FXML
    private Label lblStatus;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void handleClicks(ActionEvent event) {
        if (event.getSource() == btnEstudiantes) {
            lblMainStatus.setText("Gestión de Estudiantes");
            lblStatus.setText("Listado de estudiantes");

        } else if (event.getSource() == btnProfesores) {
            lblMainStatus.setText("Gestión de  Profesores");
            lblStatus.setText("Listado de Profesores");
        } else if (event.getSource() == btnCursos) {
            lblMainStatus.setText("Gestión de Cursos");
            lblStatus.setText("Listado de Cursos");
        } else if (event.getSource() == btnMoreInfo) {
            lblMainStatus.setText("En desarrollo");
            lblStatus.setText("Estamos desarrollando");
        }
    }

    @FXML
    private void añadirInscripcion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ejemplo/ui/view/Inscripcion.fxml"));
        Parent root = loader.load();

        Stage nuevaVentana = new Stage();
        nuevaVentana.setScene(new Scene(root));
        nuevaVentana.setTitle("Inscripción de personas");
        nuevaVentana.show();

    }

}

