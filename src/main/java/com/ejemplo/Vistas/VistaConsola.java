package com.ejemplo.Vistas;

import java.util.List;
import java.util.Scanner;

import com.ejemplo.Controladores.CursoControlador;
import com.ejemplo.Controladores.EstudianteControlador;
import com.ejemplo.Controladores.FacultadControlador;
import com.ejemplo.Controladores.ProfesorControlador;
import com.ejemplo.Controladores.ProgramaControlador;
import com.ejemplo.DTOs.Solicitud.ProfesorSolicitudDTO;
import com.ejemplo.Fabricas.FabricaInterna.FabricaControladores;
import com.ejemplo.Modelos.Profesor;

/**
 * Implementación de vista para interfaz de consola
 * Maneja toda la interacción CRUD a través de la línea de comandos
 */
public class VistaConsola implements InterfaceVista {

    private Scanner scanner;
    private FabricaControladores fabricaControladores;

    private boolean aplicacionEjecutandose;

    public VistaConsola() {
        this.scanner = new Scanner(System.in);
        this.aplicacionEjecutandose = true;
    }

    @Override
    public void inicializar() {
        limpiarPantalla();
        mostrarBanner();
        System.out.println("✅ Vista de Consola inicializada correctamente.");
        System.out.println("📋 Presione ENTER para continuar...");
        scanner.nextLine();
    }

    @Override
    public void setFabricaControladores(FabricaControladores fabricaControladores) {
        this.fabricaControladores = fabricaControladores;
        System.out.println("🔗 Fábrica de controladores configurada correctamente.");
    }

    @Override
    public void ejecutar() {
        while (aplicacionEjecutandose) {
            try {
                mostrarMenuPrincipal();
                int opcion = leerOpcionMenu();
                procesarOpcionPrincipal(opcion);
            } catch (Exception e) {
                mostrarError("Error inesperado: " + e.getMessage());
                pausar();
            }
        }
    }

    @Override
    public void mostrarMenuPrincipal() {
        limpiarPantalla();
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    🎓 SISTEMA ACADÉMICO 🎓                    ║");
        System.out.println("║                        MENÚ PRINCIPAL                         ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. 👨‍🏫 Gestión de Profesores                                 ║");
        System.out.println("║  2. 🏛️  Gestión de Facultades                                  ║");
        System.out.println("║  3. 📚 Gestión de Programas                                   ║");
        System.out.println("║  4. 📖 Gestión de Cursos                                      ║");
        System.out.println("║  5. 🎓 Gestión de Estudiantes                                 ║");
        System.out.println("║  0. 🚪 Salir                                                   ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.print("👉 Seleccione una opción: ");
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println("ℹ️  " + mensaje);
    }

    @Override
    public void mostrarError(String error) {
        System.out.println("❌ ERROR: " + error);
    }

    @Override
    public void cerrar() {
        System.out.println("👋 Cerrando aplicación...");
        System.out.println("¡Gracias por usar el Sistema Académico!");
        aplicacionEjecutandose = false;
        scanner.close();
    }

    // ===============================
    // MÉTODOS PRIVADOS - UTILIDADES
    // ===============================

    private void mostrarBanner() {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║  🎓 SISTEMA DE GESTIÓN ACADÉMICA - INTERFAZ DE CONSOLA 🎓    ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Desarrollado con patrón Factory para máxima flexibilidad    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    private void limpiarPantalla() {
        // Simula limpiar pantalla en consola
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    private int leerOpcionMenu() {
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            return opcion;
        } catch (Exception e) {
            scanner.nextLine(); // Limpiar buffer
            mostrarError("Por favor ingrese un número válido.");
            pausar();
            return -1; // Opción inválida
        }
    }

    private String leerTexto(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private long leerLong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                long valor = scanner.nextLong();
                scanner.nextLine(); // Limpiar buffer
                return valor;
            } catch (Exception e) {
                scanner.nextLine(); // Limpiar buffer
                mostrarError("Por favor ingrese un número válido.");
            }
        }
    }

    private void pausar() {
        System.out.println("\n📋 Presione ENTER para continuar...");
        scanner.nextLine();
    }

    // ===============================
    // PROCESAMIENTO DE MENÚS
    // ===============================

    private void procesarOpcionPrincipal(int opcion) {
        switch (opcion) {
            case 1:
                menuGestionProfesores();
                break;
            case 2:
                menuGestionFacultades();
                break;
            case 3:
                menuGestionProgramas();
                break;
            case 4:
                menuGestionCursos();
                break;
            case 5:
                menuGestionEstudiantes();
                break;
            case 0:
                cerrar();
                break;
            default:
                if (opcion != -1) { // -1 ya mostró error
                    mostrarError("Opción no válida. Por favor seleccione una opción del 0 al 5.");
                    pausar();
                }
                break;
        }
    }

    // ===============================
    // MENÚ GESTIÓN DE PROFESORES
    // ===============================

    private void menuGestionProfesores() {
        boolean enMenuProfesores = true;

        while (enMenuProfesores) {
            limpiarPantalla();
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                👨‍🏫 GESTIÓN DE PROFESORES 👨‍🏫                ║");
            System.out.println("╠════════════════════════════════════════════════════════╣");
            System.out.println("║  1. ➕ Crear Profesor                                   ║");
            System.out.println("║  2. 📋 Listar Profesores                               ║");
            System.out.println("║  3. ✏️  Actualizar Profesor                             ║");
            System.out.println("║  4. 🗑️  Eliminar Profesor                               ║");
            System.out.println("║  0. 🔙 Volver al Menú Principal                        ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            System.out.print("👉 Seleccione una opción: ");

            int opcion = leerOpcionMenu();

            switch (opcion) {
                case 1:
                    crearProfesor();
                    break;
                case 2:
                    listarProfesores();
                    break;
                case 3:
                    actualizarProfesor();
                    break;
                case 4:
                    eliminarProfesor();
                    break;
                case 0:
                    enMenuProfesores = false;
                    break;
                default:
                    if (opcion != -1) {
                        mostrarError("Opción no válida.");
                        pausar();
                    }
                    break;
            }
        }
    }

    private void crearProfesor() {
        limpiarPantalla();
        System.out.println("═══════════════════════════════════════");
        System.out.println("➕ CREAR NUEVO PROFESOR");
        System.out.println("═══════════════════════════════════════");

        try {
            String nombres = leerTexto("👤 Nombres: ");
            if (nombres.isEmpty()) {
                mostrarError("Los nombres son obligatorios.");
                pausar();
                return;
            }

            String apellidos = leerTexto("👤 Apellidos: ");
            if (apellidos.isEmpty()) {
                mostrarError("Los apellidos son obligatorios.");
                pausar();
                return;
            }

            String email = leerTexto("📧 Email: ");
            if (email.isEmpty()) {
                mostrarError("El email es obligatorio.");
                pausar();
                return;
            }

            String tipoContrato = leerTexto("📋 Tipo de Contrato: ");
            if (tipoContrato.isEmpty()) {
                mostrarError("El tipo de contrato es obligatorio.");
                pausar();
                return;
            }

            // Crear el DTO
            ProfesorSolicitudDTO profesorDTO = new ProfesorSolicitudDTO(nombres, apellidos, email, tipoContrato);

            // ✅ CORRECTO: Crear controlador solo cuando se necesita
            ProfesorControlador profesorControlador = fabricaControladores.crearControladorProfesor();
            profesorControlador.crearProfesor(profesorDTO);

            mostrarMensaje("✅ Profesor creado exitosamente!");

        } catch (Exception e) {
            mostrarError("No se pudo crear el profesor: " + e.getMessage());
        }

        pausar();
    }

    private void listarProfesores() {
        limpiarPantalla();
        System.out.println("═══════════════════════════════════════");
        System.out.println("📋 LISTA DE PROFESORES");
        System.out.println("═══════════════════════════════════════");

        try {
            // ✅ CORRECTO: Crear controlador solo cuando se necesita
            ProfesorControlador profesorControlador = fabricaControladores.crearControladorProfesor();
            List<Profesor> profesores = profesorControlador.listarProfesores();

            if (profesores.isEmpty()) {
                System.out.println("🔍 No hay profesores registrados.");
            } else {
                System.out.println("📊 Total de profesores: " + profesores.size());
                System.out.println();

                // Encabezado de la tabla
                System.out.printf("%-5s %-20s %-20s %-30s %-15s%n",
                        "ID", "NOMBRES", "APELLIDOS", "EMAIL", "TIPO CONTRATO");
                System.out.println("-".repeat(90));

                // Datos de los profesores
                for (Profesor profesor : profesores) {
                    System.out.printf("%-5d %-20s %-20s %-30s %-15s%n",
                            (int)profesor.getId(), // Cast double a int para mostrar
                            profesor.getNombres() != null ? profesor.getNombres() : "N/A",
                            profesor.getApellidos() != null ? profesor.getApellidos() : "N/A",
                            profesor.getEmail() != null ? profesor.getEmail() : "N/A",
                            profesor.getTipoContrato() != null ? profesor.getTipoContrato() : "N/A"
                    );
                }
            }

        } catch (Exception e) {
            mostrarError("No se pudo obtener la lista de profesores: " + e.getMessage());
        }

        pausar();
    }

    private void actualizarProfesor() {
        limpiarPantalla();
        System.out.println("═══════════════════════════════════════");
        System.out.println("✏️ ACTUALIZAR PROFESOR");
        System.out.println("═══════════════════════════════════════");

        try {
            long id = leerLong("🔍 Ingrese el ID del profesor a actualizar: ");

            String nombres = leerTexto("👤 Nuevos nombres: ");
            if (nombres.isEmpty()) {
                mostrarError("Los nombres son obligatorios.");
                pausar();
                return;
            }

            String apellidos = leerTexto("👤 Nuevos apellidos: ");
            if (apellidos.isEmpty()) {
                mostrarError("Los apellidos son obligatorios.");
                pausar();
                return;
            }

            String email = leerTexto("📧 Nuevo email: ");
            if (email.isEmpty()) {
                mostrarError("El email es obligatorio.");
                pausar();
                return;
            }

            String tipoContrato = leerTexto("📋 Nuevo tipo de contrato: ");
            if (tipoContrato.isEmpty()) {
                mostrarError("El tipo de contrato es obligatorio.");
                pausar();
                return;
            }

            // Crear el DTO
            ProfesorSolicitudDTO profesorDTO = new ProfesorSolicitudDTO(nombres, apellidos, email, tipoContrato);

            // ✅ CORRECTO: Crear controlador solo cuando se necesita
            ProfesorControlador profesorControlador = fabricaControladores.crearControladorProfesor();
            profesorControlador.actualizarProfesor(id, profesorDTO);

            mostrarMensaje("✅ Profesor actualizado exitosamente!");

        } catch (Exception e) {
            mostrarError("No se pudo actualizar el profesor: " + e.getMessage());
        }

        pausar();
    }

    private void eliminarProfesor() {
        limpiarPantalla();
        System.out.println("═══════════════════════════════════════");
        System.out.println("🗑️ ELIMINAR PROFESOR");
        System.out.println("═══════════════════════════════════════");

        try {
            long id = leerLong("🔍 Ingrese el ID del profesor a eliminar: ");

            String confirmacion = leerTexto("⚠️ ¿Está seguro de eliminar este profesor? (s/N): ");

            if (confirmacion.toLowerCase().equals("s") || confirmacion.toLowerCase().equals("si")) {
                // ✅ CORRECTO: Crear controlador solo cuando se necesita
                ProfesorControlador profesorControlador = fabricaControladores.crearControladorProfesor();
                profesorControlador.eliminarProfesor(id);
                mostrarMensaje("✅ Profesor eliminado exitosamente!");
            } else {
                mostrarMensaje("❌ Operación cancelada.");
            }

        } catch (Exception e) {
            mostrarError("No se pudo eliminar el profesor: " + e.getMessage());
        }

        pausar();
    }

    // ===============================
    // MENÚS PARA OTRAS ENTIDADES (PLACEHOLDERS)
    // ===============================

    private void menuGestionFacultades() {
        mostrarMensaje("🚧 Menú de Facultades - En construcción");
        mostrarMensaje("El controlador está disponible y configurado.");
        pausar();
    }

    private void menuGestionProgramas() {
        mostrarMensaje("🚧 Menú de Programas - En construcción");
        mostrarMensaje("El controlador está disponible y configurado.");
        pausar();
    }

    private void menuGestionCursos() {
        mostrarMensaje("🚧 Menú de Cursos - En construcción");
        mostrarMensaje("El controlador está disponible y configurado.");
        pausar();
    }

    private void menuGestionEstudiantes() {
        mostrarMensaje("🚧 Menú de Estudiantes - En construcción");
        mostrarMensaje("El controlador está disponible y configurado.");
        pausar();
    }
}