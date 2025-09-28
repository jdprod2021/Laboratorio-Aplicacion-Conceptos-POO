package com.ejemplo.Vistas;

import java.util.List;
import java.util.Optional;

import com.ejemplo.Controladores.CursoControlador;
import com.ejemplo.Controladores.EstudianteControlador;
import com.ejemplo.Controladores.FacultadControlador;
import com.ejemplo.Controladores.ProfesorControlador;
import com.ejemplo.Controladores.ProgramaControlador;
import com.ejemplo.DTOs.Solicitud.ProfesorSolicitudDTO;
import com.ejemplo.Fabricas.FabricaInterna.FabricaControladores;
import com.ejemplo.Modelos.Profesor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Implementación de vista con JavaFX para interfaz gráfica moderna
 * Proporciona una experiencia visual rica para la gestión académica
 */
public class VistaGUI extends Application implements InterfaceVista {

    // Componentes principales
    private Stage ventanaPrincipal;
    private BorderPane layoutPrincipal;
    private FabricaControladores fabricaControladores;

    // Estado de la aplicación
    private boolean aplicacionInicializada = false;

    // Instancia singleton para el patrón
    private static VistaGUI instancia;

    public VistaGUI() {
        instancia = this;
    }

    @Override
    public void inicializar() {
        if (!aplicacionInicializada) {
            Platform.runLater(() -> {
                try {
                    launch(); // Esto llamará automáticamente al método start()
                } catch (IllegalStateException e) {
                    // JavaFX ya está inicializado, crear la ventana directamente
                    crearVentanaPrincipal();
                }
            });
            aplicacionInicializada = true;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.ventanaPrincipal = primaryStage;
        crearVentanaPrincipal();
    }

    @Override
    public void setFabricaControladores(FabricaControladores fabricaControladores) {
        this.fabricaControladores = fabricaControladores;
        System.out.println("🔗 Fábrica de controladores configurada en JavaFX.");
    }

    @Override
    public void ejecutar() {
        // JavaFX maneja su propio bucle de eventos
        if (!aplicacionInicializada) {
            inicializar();
        }
    }

    @Override
    public void mostrarMenuPrincipal() {
        Platform.runLater(this::crearMenuPrincipal);
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();
        });
    }

    @Override
    public void mostrarError(String error) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Ha ocurrido un error");
            alert.setContentText(error);
            alert.showAndWait();
        });
    }

    @Override
    public void cerrar() {
        Platform.runLater(() -> {
            if (ventanaPrincipal != null) {
                ventanaPrincipal.close();
            }
            Platform.exit();
        });
    }

    // ===============================
    // MÉTODOS PRIVADOS - CREACIÓN DE UI
    // ===============================

    private void crearVentanaPrincipal() {
        ventanaPrincipal.setTitle("🎓 Sistema de Gestión Académica - JavaFX");
        ventanaPrincipal.setMinWidth(800);
        ventanaPrincipal.setMinHeight(600);

        // Layout principal
        layoutPrincipal = new BorderPane();
        layoutPrincipal.getStyleClass().add("main-layout");

        // Aplicar estilos CSS
        aplicarEstilos();

        // Crear menú principal
        crearMenuPrincipal();

        // Crear escena
        Scene escena = new Scene(layoutPrincipal, 1000, 700);
        escena.getStylesheets().add(getClass().getResource("/estilos.css").toExternalForm());

        ventanaPrincipal.setScene(escena);
        ventanaPrincipal.centerOnScreen();
        ventanaPrincipal.show();

        // Manejar cierre de ventana
        ventanaPrincipal.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    private void crearMenuPrincipal() {
        // Limpiar layout
        layoutPrincipal.setTop(null);
        layoutPrincipal.setCenter(null);
        layoutPrincipal.setBottom(null);

        // Header con título
        Label titulo = new Label("🎓 Sistema de Gestión Académica");
        titulo.getStyleClass().add("main-title");

        VBox headerBox = new VBox(titulo);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(20));
        headerBox.getStyleClass().add("header");

        layoutPrincipal.setTop(headerBox);

        // Panel central con botones del menú
        GridPane menuGrid = new GridPane();
        menuGrid.setAlignment(Pos.CENTER);
        menuGrid.setHgap(20);
        menuGrid.setVgap(20);
        menuGrid.setPadding(new Insets(40));

        // Crear botones del menú principal
        Button btnProfesores = crearBotonMenu("👨‍🏫\nProfesores", "Gestionar profesores del sistema");
        Button btnFacultades = crearBotonMenu("🏛️\nFacultades", "Gestionar facultades");
        Button btnProgramas = crearBotonMenu("📚\nProgramas", "Gestionar programas académicos");
        Button btnCursos = crearBotonMenu("📖\nCursos", "Gestionar cursos");
        Button btnEstudiantes = crearBotonMenu("🎓\nEstudiantes", "Gestionar estudiantes");
        Button btnSalir = crearBotonMenu("🚪\nSalir", "Cerrar la aplicación");

        // Configurar acciones
        btnProfesores.setOnAction(e -> mostrarGestionProfesores());
        btnFacultades.setOnAction(e -> mostrarEnConstruccion("Facultades"));
        btnProgramas.setOnAction(e -> mostrarEnConstruccion("Programas"));
        btnCursos.setOnAction(e -> mostrarEnConstruccion("Cursos"));
        btnEstudiantes.setOnAction(e -> mostrarEnConstruccion("Estudiantes"));
        btnSalir.setOnAction(e -> cerrar());

        // Organizar botones en grid 2x3
        menuGrid.add(btnProfesores, 0, 0);
        menuGrid.add(btnFacultades, 1, 0);
        menuGrid.add(btnProgramas, 2, 0);
        menuGrid.add(btnCursos, 0, 1);
        menuGrid.add(btnEstudiantes, 1, 1);
        menuGrid.add(btnSalir, 2, 1);

        layoutPrincipal.setCenter(menuGrid);

        // Footer con información
        Label footer = new Label("Desarrollado con Patrón Factory | JavaFX Interface");
        footer.getStyleClass().add("footer");

        VBox footerBox = new VBox(footer);
        footerBox.setAlignment(Pos.CENTER);
        footerBox.setPadding(new Insets(10));

        layoutPrincipal.setBottom(footerBox);
    }

    private Button crearBotonMenu(String texto, String tooltip) {
        Button boton = new Button(texto);
        boton.getStyleClass().add("menu-button");
        boton.setPrefSize(150, 100);
        boton.setTooltip(new Tooltip(tooltip));
        return boton;
    }

    private void aplicarEstilos() {
        // Crear archivo CSS dinámicamente si no existe
        String css = """
            .main-layout {
                -fx-background-color: linear-gradient(to bottom, #f0f2f5, #ffffff);
            }
            
            .header {
                -fx-background-color: #2c3e50;
                -fx-background-radius: 0 0 10 10;
            }
            
            .main-title {
                -fx-font-size: 28px;
                -fx-font-weight: bold;
                -fx-text-fill: white;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 1, 1);
            }
            
            .menu-button {
                -fx-background-color: linear-gradient(to bottom, #3498db, #2980b9);
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-background-radius: 10;
                -fx-border-radius: 10;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 2, 2);
            }
            
            .menu-button:hover {
                -fx-background-color: linear-gradient(to bottom, #5dade2, #3498db);
                -fx-cursor: hand;
            }
            
            .menu-button:pressed {
                -fx-background-color: linear-gradient(to bottom, #2980b9, #1f618d);
            }
            
            .footer {
                -fx-font-size: 12px;
                -fx-text-fill: #7f8c8d;
                -fx-font-style: italic;
            }
            
            .section-title {
                -fx-font-size: 20px;
                -fx-font-weight: bold;
                -fx-text-fill: #2c3e50;
            }
            
            .action-button {
                -fx-background-color: #27ae60;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 5;
                -fx-padding: 8 15 8 15;
            }
            
            .action-button:hover {
                -fx-background-color: #2ecc71;
                -fx-cursor: hand;
            }
            
            .danger-button {
                -fx-background-color: #e74c3c;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 5;
                -fx-padding: 8 15 8 15;
            }
            
            .danger-button:hover {
                -fx-background-color: #c0392b;
                -fx-cursor: hand;
            }
            """;
    }

    // ===============================
    // GESTIÓN DE PROFESORES - JavaFX
    // ===============================

    private void mostrarGestionProfesores() {
        // Limpiar layout
        layoutPrincipal.setCenter(null);

        // Panel principal para gestión de profesores
        VBox panelPrincipal = new VBox(20);
        panelPrincipal.setPadding(new Insets(20));

        // Título de sección
        Label titulo = new Label("👨‍🏫 Gestión de Profesores");
        titulo.getStyleClass().add("section-title");

        // Panel de botones de acción
        HBox panelBotones = new HBox(15);
        panelBotones.setAlignment(Pos.CENTER_LEFT);

        Button btnCrear = new Button("➕ Crear Profesor");
        Button btnActualizar = new Button("✏️ Actualizar");
        Button btnEliminar = new Button("🗑️ Eliminar");
        Button btnVolver = new Button("🔙 Volver");

        btnCrear.getStyleClass().add("action-button");
        btnActualizar.getStyleClass().add("action-button");
        btnEliminar.getStyleClass().add("danger-button");

        // Tabla de profesores
        TableView<Profesor> tablaProfesores = crearTablaProfesores();

        // Configurar acciones de botones
        btnCrear.setOnAction(e -> mostrarFormularioCrearProfesor());
        btnActualizar.setOnAction(e -> {
            Profesor seleccionado = tablaProfesores.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                mostrarFormularioActualizarProfesor(seleccionado);
            } else {
                mostrarError("Por favor seleccione un profesor para actualizar.");
            }
        });
        btnEliminar.setOnAction(e -> {
            Profesor seleccionado = tablaProfesores.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                eliminarProfesorConConfirmacion(seleccionado, tablaProfesores);
            } else {
                mostrarError("Por favor seleccione un profesor para eliminar.");
            }
        });
        btnVolver.setOnAction(e -> crearMenuPrincipal());

        panelBotones.getChildren().addAll(btnCrear, btnActualizar, btnEliminar, btnVolver);

        // Cargar datos iniciales
        cargarDatosProfesores(tablaProfesores);

        // Ensamblar panel
        panelPrincipal.getChildren().addAll(titulo, panelBotones, tablaProfesores);

        layoutPrincipal.setCenter(panelPrincipal);
    }

    private TableView<Profesor> crearTablaProfesores() {
        TableView<Profesor> tabla = new TableView<>();
        tabla.setPrefHeight(400);

        // Columnas
        TableColumn<Profesor, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> {
            int id = (int) cellData.getValue().getId(); // Cast double a int
            return new javafx.beans.property.SimpleObjectProperty<>(id);
        });
        colId.setPrefWidth(50);

        TableColumn<Profesor, String> colNombres = new TableColumn<>("Nombres");
        colNombres.setCellValueFactory(cellData -> {
            String nombres = cellData.getValue().getNombres();
            return new javafx.beans.property.SimpleStringProperty(nombres != null ? nombres : "N/A");
        });
        colNombres.setPrefWidth(150);

        TableColumn<Profesor, String> colApellidos = new TableColumn<>("Apellidos");
        colApellidos.setCellValueFactory(cellData -> {
            String apellidos = cellData.getValue().getApellidos();
            return new javafx.beans.property.SimpleStringProperty(apellidos != null ? apellidos : "N/A");
        });
        colApellidos.setPrefWidth(150);

        TableColumn<Profesor, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(cellData -> {
            String email = cellData.getValue().getEmail();
            return new javafx.beans.property.SimpleStringProperty(email != null ? email : "N/A");
        });
        colEmail.setPrefWidth(200);

        TableColumn<Profesor, String> colTipoContrato = new TableColumn<>("Tipo Contrato");
        colTipoContrato.setCellValueFactory(new PropertyValueFactory<>("tipoContrato"));
        colTipoContrato.setPrefWidth(120);

        tabla.getColumns().addAll(colId, colNombres, colApellidos, colEmail, colTipoContrato);

        return tabla;
    }

    private void cargarDatosProfesores(TableView<Profesor> tabla) {
        try {
            // ✅ CORRECTO: Crear controlador solo cuando se necesita
            ProfesorControlador profesorControlador = fabricaControladores.crearControladorProfesor();
            List<Profesor> profesores = profesorControlador.listarProfesores();
            ObservableList<Profesor> datosTabla = FXCollections.observableArrayList(profesores);
            tabla.setItems(datosTabla);
        } catch (Exception e) {
            mostrarError("No se pudieron cargar los profesores: " + e.getMessage());
        }
    }

    private void mostrarFormularioCrearProfesor() {
        // Crear diálogo personalizado
        Dialog<ProfesorSolicitudDTO> dialogo = new Dialog<>();
        dialogo.setTitle("Crear Profesor");
        dialogo.setHeaderText("Ingrese los datos del nuevo profesor");

        // Configurar botones
        ButtonType btnCrear = new ButtonType("Crear", ButtonBar.ButtonData.OK_DONE);
        dialogo.getDialogPane().getButtonTypes().addAll(btnCrear, ButtonType.CANCEL);

        // Crear formulario
        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(10);
        formulario.setPadding(new Insets(20, 150, 10, 10));

        TextField txtNombres = new TextField();
        TextField txtApellidos = new TextField();
        TextField txtEmail = new TextField();
        TextField txtTipoContrato = new TextField();

        txtNombres.setPromptText("Nombres del profesor");
        txtApellidos.setPromptText("Apellidos del profesor");
        txtEmail.setPromptText("email@ejemplo.com");
        txtTipoContrato.setPromptText("Cátedra, Tiempo Completo, etc.");

        formulario.add(new Label("Nombres:"), 0, 0);
        formulario.add(txtNombres, 1, 0);
        formulario.add(new Label("Apellidos:"), 0, 1);
        formulario.add(txtApellidos, 1, 1);
        formulario.add(new Label("Email:"), 0, 2);
        formulario.add(txtEmail, 1, 2);
        formulario.add(new Label("Tipo Contrato:"), 0, 3);
        formulario.add(txtTipoContrato, 1, 3);

        dialogo.getDialogPane().setContent(formulario);

        // Configurar resultado del diálogo
        dialogo.setResultConverter(dialogButton -> {
            if (dialogButton == btnCrear) {
                if (txtNombres.getText().trim().isEmpty() ||
                        txtApellidos.getText().trim().isEmpty() ||
                        txtEmail.getText().trim().isEmpty() ||
                        txtTipoContrato.getText().trim().isEmpty()) {

                    mostrarError("Todos los campos son obligatorios.");
                    return null;
                }

                return new ProfesorSolicitudDTO(
                        txtNombres.getText().trim(),
                        txtApellidos.getText().trim(),
                        txtEmail.getText().trim(),
                        txtTipoContrato.getText().trim()
                );
            }
            return null;
        });

        // Mostrar diálogo y procesar resultado
        Optional<ProfesorSolicitudDTO> resultado = dialogo.showAndWait();

        resultado.ifPresent(profesorDTO -> {
            try {
                // ✅ CORRECTO: Crear controlador solo cuando se necesita
                ProfesorControlador profesorControlador = fabricaControladores.crearControladorProfesor();
                profesorControlador.crearProfesor(profesorDTO);
                mostrarMensaje("✅ Profesor creado exitosamente!");
                mostrarGestionProfesores(); // Refrescar vista
            } catch (Exception e) {
                mostrarError("No se pudo crear el profesor: " + e.getMessage());
            }
        });
    }

    private void mostrarFormularioActualizarProfesor(Profesor profesor) {
        // Crear diálogo personalizado
        Dialog<ProfesorSolicitudDTO> dialogo = new Dialog<>();
        dialogo.setTitle("Actualizar Profesor");
        dialogo.setHeaderText("Modifique los datos del profesor");

        // Configurar botones
        ButtonType btnActualizar = new ButtonType("Actualizar", ButtonBar.ButtonData.OK_DONE);
        dialogo.getDialogPane().getButtonTypes().addAll(btnActualizar, ButtonType.CANCEL);

        // Crear formulario
        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(10);
        formulario.setPadding(new Insets(20, 150, 10, 10));

        // Campos pre-cargados con datos actuales
        TextField txtNombres = new TextField();
        TextField txtApellidos = new TextField();
        TextField txtEmail = new TextField();
        TextField txtTipoContrato = new TextField();

        // Pre-cargar datos actuales
        txtNombres.setText(profesor.getNombres() != null ? profesor.getNombres() : "");
        txtApellidos.setText(profesor.getApellidos() != null ? profesor.getApellidos() : "");
        txtEmail.setText(profesor.getEmail() != null ? profesor.getEmail() : "");
        txtTipoContrato.setText(profesor.getTipoContrato() != null ? profesor.getTipoContrato() : "");

        txtNombres.setPromptText("Nombres del profesor");
        txtApellidos.setPromptText("Apellidos del profesor");
        txtEmail.setPromptText("email@ejemplo.com");
        txtTipoContrato.setPromptText("Cátedra, Tiempo Completo, etc.");

        formulario.add(new Label("Nombres:"), 0, 0);
        formulario.add(txtNombres, 1, 0);
        formulario.add(new Label("Apellidos:"), 0, 1);
        formulario.add(txtApellidos, 1, 1);
        formulario.add(new Label("Email:"), 0, 2);
        formulario.add(txtEmail, 1, 2);
        formulario.add(new Label("Tipo Contrato:"), 0, 3);
        formulario.add(txtTipoContrato, 1, 3);

        dialogo.getDialogPane().setContent(formulario);

        // Configurar resultado del diálogo
        dialogo.setResultConverter(dialogButton -> {
            if (dialogButton == btnActualizar) {
                if (txtNombres.getText().trim().isEmpty() ||
                        txtApellidos.getText().trim().isEmpty() ||
                        txtEmail.getText().trim().isEmpty() ||
                        txtTipoContrato.getText().trim().isEmpty()) {

                    mostrarError("Todos los campos son obligatorios.");
                    return null;
                }

                return new ProfesorSolicitudDTO(
                        txtNombres.getText().trim(),
                        txtApellidos.getText().trim(),
                        txtEmail.getText().trim(),
                        txtTipoContrato.getText().trim()
                );
            }
            return null;
        });

        // Mostrar diálogo y procesar resultado
        Optional<ProfesorSolicitudDTO> resultado = dialogo.showAndWait();

        resultado.ifPresent(profesorDTO -> {
            try {
                // ✅ CORRECTO: Crear controlador solo cuando se necesita
                ProfesorControlador profesorControlador = fabricaControladores.crearControladorProfesor();
                profesorControlador.actualizarProfesor((long)profesor.getId(), profesorDTO);
                mostrarMensaje("✅ Profesor actualizado exitosamente!");
                mostrarGestionProfesores(); // Refrescar vista
            } catch (Exception e) {
                mostrarError("No se pudo actualizar el profesor: " + e.getMessage());
            }
        });
    }

    private void eliminarProfesorConConfirmacion(Profesor profesor, TableView<Profesor> tabla) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Está seguro de eliminar este profesor?");

        String nombreCompleto = "ID: " + (int)profesor.getId();
        String nombres = profesor.getNombres();
        String apellidos = profesor.getApellidos();

        if (nombres != null && apellidos != null) {
            nombreCompleto += " - " + nombres + " " + apellidos;
        }

        confirmacion.setContentText("Profesor: " + nombreCompleto);

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                // ✅ CORRECTO: Crear controlador solo cuando se necesita
                ProfesorControlador profesorControlador = fabricaControladores.crearControladorProfesor();
                profesorControlador.eliminarProfesor((long)profesor.getId());
                mostrarMensaje("✅ Profesor eliminado exitosamente!");
                cargarDatosProfesores(tabla); // Refrescar tabla
            } catch (Exception e) {
                mostrarError("No se pudo eliminar el profesor: " + e.getMessage());
            }
        }
    }

    // ===============================
    // MÉTODOS AUXILIARES
    // ===============================

    private void mostrarEnConstruccion(String modulo) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("En Construcción");
        info.setHeaderText("Módulo " + modulo);
        info.setContentText("🚧 Este módulo está en construcción.\n\nEl controlador está disponible y configurado correctamente.");
        info.showAndWait();
    }

    // Método estático para acceso desde la Factory
    public static VistaGUI obtenerInstancia() {
        if (instancia == null) {
            instancia = new VistaGUI();
        }
        return instancia;
    }
}