package com.ejemplo.controllers;

import com.ejemplo.Modelos.Personas.Estudiante;
import com.ejemplo.Repositorios.Personas.EstudianteRepo;
import com.ejemplo.Servicios.Impl.Personas.EstudianteImpl;
import com.ejemplo.Servicios.Personas.EstudianteServicio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InscripcionController implements Initializable {
    @FXML
    private Button btnInscribir;
    @FXML
    private Button btnCancelar;
    @FXML
    private ComboBox seleccionarRol;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelApellidos;
    @FXML
    private Label labelPrograma;
    @FXML
    private TextField txtPrograma;
    @FXML
    private Label labelEmail;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtPromedio;
    @FXML
    private ComboBox<String> comboActivo;

    private final EstudianteServicio estudianteServicio;


    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void seleccionarRol(ActionEvent actionEvent) throws IOException {




    }
    public InscripcionController() {
        this.estudianteServicio = new EstudianteImpl(new EstudianteRepo());
    }

    @FXML
    private void guardarEstudiante(ActionEvent event){

    try {
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String email = txtEmail.getText();
        long codigo = Long.parseLong(txtCodigo.getText());
        double promedio = Double.parseDouble(txtPromedio.getText());
        boolean activo = "Sí".equals(comboActivo.getValue());
        Long programaID = Long.parseLong(txtPrograma.getText());


        Estudiante estudiante = new Estudiante();
        estudiante.setNombres(nombre);
        estudiante.setApellidos(apellidos);
        estudiante.setEmail(email);
        estudiante.setCodigo(codigo);
        estudiante.setPromedio(promedio);
        estudiante.setActivo(activo);
        estudiante.setPrograma();

        String resultado = estudianteServicio.inscribir(estudiante);
        System.out.println(resultado);

    } catch (Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error al inscribir");
        alert.setContentText("Verifique los datos: " + e.getMessage());
        alert.showAndWait();

    }

    }





    @FXML
    private void volverMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ejemplo/ui/view/MainMenu.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
