package com.ejemplo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
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
        if(event.getSource() == btnEstudiantes){
            lblMainStatus.setText("Gestión de Estudiantes");
            lblStatus.setText("Listado de estudiantes");

        }
        else if(event.getSource() == btnProfesores){
            lblMainStatus.setText("Gestión de  Profesores");
            lblStatus.setText("Listado de Profesores");
        }
        else if (event.getSource() == btnCursos) {
            lblMainStatus.setText("Gestión de Cursos");
            lblStatus.setText("Listado de Cursos");
        }
        else if (event.getSource() == btnMoreInfo) {
            lblMainStatus.setText("En desarrollo");
            lblStatus.setText("Estamos desarrollando");
        }


    }
}
