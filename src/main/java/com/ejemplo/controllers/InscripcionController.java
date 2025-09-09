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
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;

    private final EstudianteServicio estudianteServicio;

    // Guardamos el ID del estudiante si se está editando
    private Long estudianteEditandoId = null;

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

    /* ==========================================================
       CREAR O ACTUALIZAR SEGÚN EL CASO
       ========================================================== */
    @FXML
    private void guardarEstudiante(ActionEvent event) {
        try {
            EstudianteSolicitudDTO dto = construirDTO();

            EstudianteRespuestaDTO respuesta;
            if (estudianteEditandoId == null) {
                // Crear
                respuesta = estudianteServicio.crear(dto);
                mostrarAlertaInfo("Registro exitoso", "Inscripción completada",
                        "Estudiante: " + respuesta.nombres + " " + respuesta.apellidos +
                                "\nPrograma: " + respuesta.NombrePrograma);
            } else {
                // Actualizar
                respuesta = estudianteServicio.actualizar(estudianteEditandoId, dto);
                mostrarAlertaInfo("Actualización exitosa", "Datos modificados",
                        "Estudiante: " + respuesta.nombres + " " + respuesta.apellidos +
                                "\nPrograma: " + respuesta.NombrePrograma);
            }

            limpiarFormulario();

        } catch (Exception e) {
            mostrarAlertaError("Error al guardar", e.getMessage());
        }
    }

    /* ==========================================================
       ACTUALIZAR: carga los datos de un estudiante en el formulario
       ========================================================== */
    public void cargarParaEditar(EstudianteRespuestaDTO est) {
        estudianteEditandoId = est.ID;
        txtNombre.setText(est.nombres);
        txtApellidos.setText(est.apellidos);
        txtEmail.setText(est.email);
        txtCodigo.setText(String.valueOf(est.codigo));
        txtPromedio.setText(String.valueOf(est.promedio));
        comboActivo.setValue(est.activo ? "Sí" : "No");

        // Seleccionar programa en el ComboBox
        cmbPrograma.getItems().stream()
                .filter(p -> p.ID == est.programaId)
                .findFirst()
                .ifPresent(cmbPrograma::setValue);
    }

    /* ==========================================================
       ELIMINAR
       ========================================================== */
    @FXML
    private void eliminarEstudiante(ActionEvent event) {
        if (estudianteEditandoId == null) {
            mostrarAlertaError("Error", "No hay estudiante seleccionado para eliminar.");
            return;
        }

        try {
            estudianteServicio.eliminar(estudianteEditandoId);
            mostrarAlertaInfo("Eliminado", "El estudiante fue eliminado correctamente.", "");
            limpiarFormulario();
        } catch (Exception e) {
            mostrarAlertaError("Error al eliminar", e.getMessage());
        }
    }

    /* ==========================================================
       UTILS
       ========================================================== */
    private EstudianteSolicitudDTO construirDTO() {
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

        return dto;
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtApellidos.clear();
        txtEmail.clear();
        txtCodigo.clear();
        txtPromedio.clear();
        comboActivo.getSelectionModel().clearSelection();
        cmbPrograma.getSelectionModel().clearSelection();
        estudianteEditandoId = null;
    }

    private void mostrarAlertaInfo(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void mostrarAlertaError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void volverMenu(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
