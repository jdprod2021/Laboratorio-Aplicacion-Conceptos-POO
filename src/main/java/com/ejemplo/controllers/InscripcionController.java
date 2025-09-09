package com.ejemplo.controllers;

import com.ejemplo.DTOs.Solicitud.EstudianteSolicitudDTO;
import com.ejemplo.DTOs.Respuesta.EstudianteRespuestaDTO;
import com.ejemplo.DTOs.Respuesta.ProgramaRespuestaDTO;
import com.ejemplo.Servicios.Personas.EstudianteServicio;
import com.ejemplo.Repositorios.Personas.EstudianteRepo;
import com.ejemplo.Repositorios.Universidad.ProgramaRepo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class InscripcionController implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtEmail;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtPromedio;
    @FXML private ComboBox<ProgramaRespuestaDTO> cmbPrograma;
    @FXML private ComboBox<String> comboActivo;
    @FXML private Button btnInscribir;
    @FXML private Button btnCancelar;

    private final EstudianteServicio estudianteServicio;

    public InscripcionController() {
        this.estudianteServicio = new EstudianteServicio(
                new EstudianteRepo(),
                new ProgramaRepo()
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Opciones de "Activo"
        comboActivo.getItems().addAll("Sí", "No");

        // Cargar programas desde ProgramaRepo -> mapear a DTO de respuesta
        ProgramaRepo programaRepo = new ProgramaRepo();
        cmbPrograma.getItems().addAll(
                programaRepo.findAll()
                        .stream()
                        .map(p -> new ProgramaRespuestaDTO((long)p.getID(), p.getNombre()))
                        .collect(Collectors.toList())
        );

        // Mostrar solo el nombre en el combo
        cmbPrograma.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(ProgramaRespuestaDTO prog, boolean empty) {
                super.updateItem(prog, empty);
                setText(empty || prog == null ? "" : prog.nombre);
            }
        });
        cmbPrograma.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(ProgramaRespuestaDTO prog, boolean empty) {
                super.updateItem(prog, empty);
                setText(empty || prog == null ? "" : prog.nombre);
            }
        });
    }

    @FXML
    private void guardarEstudiante(ActionEvent event) {
        try {
            // Construir DTO de solicitud
            EstudianteSolicitudDTO dto = new EstudianteSolicitudDTO();
            dto.nombres = txtNombre.getText();
            dto.apellidos = txtApellidos.getText();
            dto.email = txtEmail.getText();
            dto.codigo = Long.parseLong(txtCodigo.getText());
            dto.promedio = Double.parseDouble(txtPromedio.getText());
            dto.activo = "Sí".equals(comboActivo.getValue());

            ProgramaRespuestaDTO programaSeleccionado = cmbPrograma.getValue();
            if (programaSeleccionado == null) {
                throw new IllegalArgumentException("Debe seleccionar un programa");
            }
            dto.programaId = programaSeleccionado.ID;

            // Llamar al servicio
            EstudianteRespuestaDTO respuesta = estudianteServicio.crear(dto);

            // Mostrar mensaje de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro exitoso");
            alert.setHeaderText("Inscripción completada");
            alert.setContentText("Estudiante: " + respuesta.nombres + " " + respuesta.apellidos
                    + "\nPrograma: " + respuesta.NombrePrograma);
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
        txtCodigo.clear();
        txtPromedio.clear();
        comboActivo.getSelectionModel().clearSelection();
        cmbPrograma.getSelectionModel().clearSelection();
    }

    @FXML
    private void volverMenu(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
