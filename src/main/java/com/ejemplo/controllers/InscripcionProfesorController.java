package com.ejemplo.controllers;

import com.ejemplo.DTOs.Solicitud.ProfesorSolicitudDTO;
import com.ejemplo.DTOs.Respuesta.ProfesorRespuestaDTO;
import com.ejemplo.Servicios.Personas.ProfesorServicio;
import com.ejemplo.Repositorios.Personas.ProfesorRepo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.net.URL;
import java.util.ResourceBundle;

public class InscripcionProfesorController implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtEmail;
    @FXML private ComboBox<String> cmbContrato;
    @FXML private Button btnInscribir;
    @FXML private Button btnCancelar;

    private final ProfesorServicio profesorServicio;

    public InscripcionProfesorController() {
        this.profesorServicio = new ProfesorServicio();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Cargar tipos de contrato
        cmbContrato.getItems().addAll("Tiempo Completo", "Cátedra", "Medio Tiempo");
    }

    @FXML
    private void guardarProfesor(ActionEvent event) {
        try {
            ProfesorSolicitudDTO dto = new ProfesorSolicitudDTO();
            dto.nombres = txtNombre.getText();
            dto.apellidos = txtApellidos.getText();
            dto.email = txtEmail.getText();
            dto.TipoContrato = cmbContrato.getValue();

            if (dto.TipoContrato == null) {
                throw new IllegalArgumentException("Debe seleccionar un tipo de contrato");
            }

            ProfesorRespuestaDTO respuesta = profesorServicio.crear(dto);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro exitoso");
            alert.setHeaderText("Inscripción completada");
            alert.setContentText("Profesor: " + respuesta.nombres + " " + respuesta.apellidos
                    + "\nContrato: " + respuesta.TipoContrato);
            alert.showAndWait();

            limpiarFormulario();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al inscribir");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtApellidos.clear();
        txtEmail.clear();
        cmbContrato.getSelectionModel().clearSelection();
    }

    @FXML
    private void volverMenu(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
