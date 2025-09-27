package com.ejemplo.controllers;

import com.ejemplo.DTOs.Respuesta.EstudianteRespuestaDTO;
import com.ejemplo.DTOs.Respuesta.ProfesorRespuestaDTO;
import com.ejemplo.Repositorios.Personas.EstudianteRepo;
import com.ejemplo.Repositorios.Universidad.ProgramaRepo;
import com.ejemplo.Servicios.Personas.EstudianteServicio;
import com.ejemplo.Servicios.Personas.ProfesorServicio;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML private Button btnActualizar;
    @FXML private Button btnAñadir;
    @FXML private Button btnCursos;
    @FXML private Button btnEliminar;
    @FXML private Button btnEstudiantes;
    @FXML private Button btnMoreInfo;
    @FXML private Button btnProfesores;
    @FXML private Label lblMainStatus;
    @FXML private Label lblStatus;
    @FXML private TableView<Object> tblDatos;  // se usará dinámico para estudiantes/profesores
    @FXML private TableColumn<Object, String> colNombre;
    @FXML private TableColumn<Object, String> colApellido;
    @FXML private TableColumn<Object, String> colPrograma;
    @FXML private TableColumn<Object, String> colEmail;
    //@FXML private TableColumn<Object, String> colPromedio;

    // Guardar la sección activa
    private String seccionActiva = "estudiantes"; // valor por defecto

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // inicialización si necesitas
    }

    // MENÚ LATERAL
    @FXML
    private void handleClicks(ActionEvent event) {
        if (event.getSource() == btnEstudiantes) {
            seccionActiva = "estudiantes";
            lblMainStatus.setText("Gestión de Estudiantes");
            lblStatus.setText("Listado de estudiantes");
            mostrarEstudiantes();

        } else if (event.getSource() == btnProfesores) {
            seccionActiva = "profesores";
            lblMainStatus.setText("Gestión de Profesores");
            lblStatus.setText("Listado de Profesores");
            mostrarProfesores();

        } else if (event.getSource() == btnCursos) {
            seccionActiva = "cursos";
            lblMainStatus.setText("Gestión de Cursos");
            lblStatus.setText("Listado de Cursos");
            //MostrarCursos();

        } else if (event.getSource() == btnMoreInfo) {
            seccionActiva = "info";
            lblMainStatus.setText("En desarrollo");
            lblStatus.setText("Estamos desarrollando");
        }
    }

    // BOTÓN AÑADIR
    @FXML
        private void añadir(ActionEvent event) throws IOException {
        switch (seccionActiva) {
            case "estudiantes":
                abrirFormulario("/com/ejemplo/ui/view/Inscripcion.fxml", "Inscripción de estudiantes");
                break;
            case "profesores":
                abrirFormulario("/com/ejemplo/ui/view/InscripcionProfesor.fxml", "Inscripción de profesores");
                break;
            case "cursos":
                abrirFormulario("/com/ejemplo/ui/view/InscripcionCurso.fxml", "Registro de cursos");
                break;
            default:
                mostrarAlerta("Funcionalidad en desarrollo");
        }
    }

    // BOTÓN ACTUALIZAR
    @FXML
    private void actualizar(ActionEvent event) throws IOException {
        switch (seccionActiva) {
            case "estudiantes":
                mostrarAlerta("Actualizar estudiante seleccionado");
                abrirFormulario("/com/ejemplo/ui/view/Inscripcion.fxml", "Inscripción de estudiantes");
                break;

            case "profesores":
                mostrarAlerta("Actualizar profesor seleccionado");
                abrirFormulario("/com/ejemplo/ui/view/Inscripcion.fxml", "Inscripción de estudiantes");
                break;

            case "cursos":
                mostrarAlerta("Actualizar curso seleccionado");
                break;
            default:
                mostrarAlerta("Funcionalidad en desarrollo");
        }
    }

    // BOTÓN ELIMINAR
    @FXML
    private void eliminar(ActionEvent event) {
        switch (seccionActiva) {
            case "estudiantes":
                mostrarAlerta("Eliminar estudiante seleccionado");
                break;
            case "profesores":
                mostrarAlerta("Eliminar profesor seleccionado");
                break;
            case "cursos":
                mostrarAlerta("Eliminar curso seleccionado");
                break;
            default:
                mostrarAlerta("Funcionalidad en desarrollo");
        }
    }

    // MÉTODO REUTILIZABLE PARA ABRIR FORMULARIOS
    private void abrirFormulario(String fxmlPath, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Stage nuevaVentana = new Stage();
        nuevaVentana.setScene(new Scene(root));
        nuevaVentana.setTitle(titulo);
        nuevaVentana.show();
    }

    @FXML
    private void mostrarEstudiantes() {
        // Configurar columnas
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(((EstudianteRespuestaDTO)c.getValue()).nombres));
        colApellido.setCellValueFactory(c -> new SimpleStringProperty(((EstudianteRespuestaDTO)c.getValue()).apellidos));
        colPrograma.setCellValueFactory(c -> new SimpleStringProperty(((EstudianteRespuestaDTO)c.getValue()).NombrePrograma));
        colEmail.setCellValueFactory(c -> new SimpleStringProperty(((EstudianteRespuestaDTO)c.getValue()).email));


        // Llenar tabla
        EstudianteServicio servicio = new EstudianteServicio(new EstudianteRepo(), new ProgramaRepo());
        tblDatos.getItems().setAll(servicio.listar());
    }

    @FXML
    private void mostrarProfesores() {
        // Configurar columnas
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(((ProfesorRespuestaDTO)c.getValue()).nombres));
        colApellido.setCellValueFactory(c -> new SimpleStringProperty(((ProfesorRespuestaDTO)c.getValue()).apellidos));
        colPrograma.setCellValueFactory(c -> new SimpleStringProperty(((ProfesorRespuestaDTO)c.getValue()).TipoContrato));
        colEmail.setCellValueFactory(c -> new SimpleStringProperty(((ProfesorRespuestaDTO)c.getValue()).email));
        //colFacultad.setCellValueFactory(c -> new SimpleStringProperty("N/A")); // no aplica

        // Llenar tabla
        ProfesorServicio servicio = new ProfesorServicio();
        tblDatos.getItems().setAll(servicio.listar());
    }



    // ALERTA RÁPIDA
    private void mostrarAlerta(String mensaje) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Acción");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
