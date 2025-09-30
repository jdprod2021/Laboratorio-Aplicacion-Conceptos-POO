package com.ejemplo.Vistas;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.ejemplo.Controladores.*;
import com.ejemplo.DTOs.Respuesta.EstudianteRespuestaDTO;
import com.ejemplo.DTOs.Respuesta.ProfesorRespuestaDTO;
import com.ejemplo.DTOs.Respuesta.ProgramaRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.*;
import com.ejemplo.Fabricas.FabricaInterna.FabricaControladores;
import com.ejemplo.Modelos.Estudiante;

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
                e.printStackTrace();
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
        System.out.println("║  1. 👨‍🏫 Gestión de Profesores                                ║");
        System.out.println("║  2. 🏛️  Gestión de Facultades                                  ║");
        System.out.println("║  3. 📚 Gestión de Programas                                   ║");
        System.out.println("║  4. 📖 Gestión de Cursos                                      ║");
        System.out.println("║  5. 🎓 Gestión de Estudiantes                                 ║");
        System.out.println("║  0. 🚪 Salir                                                  ║");
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
        System.out.println("║  🎓 SISTEMA DE GESTIÓN ACADÉMICA - INTERFAZ DE CONSOLA 🎓     ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Desarrollado con patrón Factory para máxima flexibilidad     ║");
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
                new VistaConsolaProfesor(fabricaControladores, scanner).mostrarMenu();
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

    }

    // ===============================
    // MENÚS PARA OTRAS ENTIDADES (PLACEHOLDERS)
    // ===============================

    private void menuGestionFacultades() {
        FacultadControlador facultadControlador = fabricaControladores.crearControladorFacultad();

        boolean volver = false;
        while (!volver) {
            System.out.println("\n===== MENÚ FACULTADES =====");
            System.out.println("1. Crear facultad");
            System.out.println("2. Listar facultades");
            System.out.println("3. Actualizar facultad");
            System.out.println("4. Eliminar facultad");
            System.out.println("0. Volver al menú principal");
            System.out.print("👉 Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Nombre de la facultad: ");
                    String nombre = scanner.nextLine();
                    System.out.print("ID del decano (persona): ");
                    long decanoId = scanner.nextLong();
                    scanner.nextLine();

                    FacultadSolicitudDTO facultadDTO = new FacultadSolicitudDTO(nombre, decanoId);
                    facultadControlador.crearFacultad(facultadDTO);
                    System.out.println("✅ Facultad creada con éxito.");
                    break;

                case 2:
                    System.out.println("\n📋 LISTA DE FACULTADES:");
                    facultadControlador.listarFacultades()
                            .forEach(System.out::println);
                    break;

                case 3:
                    System.out.print("Ingrese ID de la facultad a actualizar: ");
                    long idActualizar = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print("Nuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();
                    System.out.print("Nuevo ID del decano: ");
                    long nuevoDecanoId = scanner.nextLong();
                    scanner.nextLine();

                    FacultadSolicitudDTO actSolicitud = new FacultadSolicitudDTO(nuevoNombre, nuevoDecanoId);
                    facultadControlador.actualizarFacultad(idActualizar, actSolicitud);
                    System.out.println("✅ Facultad actualizada.");
                    break;

                case 4:
                    System.out.print("Ingrese ID de la facultad a eliminar: ");
                    long idEliminar = scanner.nextLong();
                    scanner.nextLine();

                    facultadControlador.eliminarFacultad(idEliminar);
                    System.out.println("🗑️ Facultad eliminada.");
                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println("❌ Opción inválida.");
            }
        }
        pausar();
    }

    private void menuGestionProgramas() {
            ProgramaControlador controlador = fabricaControladores.crearControladorPrograma();

            boolean volver = false;
            while (!volver) {
                System.out.println("\n===== MENÚ PROGRAMAS =====");
                System.out.println("1. Crear programa");
                System.out.println("2. Listar programas");
                System.out.println("3. Actualizar programa");
                System.out.println("4. Eliminar programa");
                System.out.println("0. Volver al menú principal");
                System.out.print("👉 Seleccione una opción: ");

                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Duración (años): ");
                        double duracion = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("ID de la facultad: ");
                        long facultadId = scanner.nextLong();
                        scanner.nextLine();

                        Date registro = new Date(System.currentTimeMillis());
                        ProgramaSolicitudDTO solicitud = new ProgramaSolicitudDTO(nombre, duracion, registro, facultadId);
                        controlador.crearPrograma(solicitud);
                        System.out.println("✅ Programa creado con éxito.");
                        break;

                    case 2:
                        List<ProgramaRespuestaDTO> programas = controlador.listarProgramas();
                        System.out.println("\n📋 LISTA DE PROGRAMAS:");
                        for (ProgramaRespuestaDTO p : programas) {
                            System.out.println(p.toString());
                        }
                        break;

                    case 3:
                        System.out.print("Ingrese ID del programa a actualizar: ");
                        long idActualizar = scanner.nextLong();
                        scanner.nextLine();

                        System.out.print("Nombre: ");
                        String nuevoNombre = scanner.nextLine();
                        System.out.print("Duración (años): ");
                        double nuevaDuracion = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("ID de la facultad: ");
                        long nuevaFacultadId = scanner.nextLong();
                        scanner.nextLine();

                        Date nuevoRegistro = new Date(System.currentTimeMillis());
                        ProgramaSolicitudDTO actSolicitud = new ProgramaSolicitudDTO(nuevoNombre, nuevaDuracion, nuevoRegistro, nuevaFacultadId);
                        controlador.actualizarPrograma(idActualizar, actSolicitud);
                        System.out.println("✅ Programa actualizado.");
                        break;

                    case 4:
                        System.out.print("Ingrese ID del programa a eliminar: ");
                        long idEliminar = scanner.nextLong();
                        scanner.nextLine();

                        controlador.eliminarPrograma(idEliminar);
                        System.out.println("🗑️ Programa eliminado.");
                        break;

                    case 0:
                        volver = true;
                        break;

                    default:
                        System.out.println("❌ Opción inválida.");
                }
            }
        pausar();
    }

    private void menuGestionCursos() {
        CursoControlador controlador = fabricaControladores.crearControladorCurso();

        boolean volver = false;
        while (!volver) {
            System.out.println("\n===== MENÚ CURSOS =====");
            System.out.println("1. Crear curso");
            System.out.println("2. Listar cursos");
            System.out.println("3. Actualizar curso");
            System.out.println("4. Eliminar curso");
            System.out.println("0. Volver al menú principal");
            System.out.print("👉 Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Nombre del curso: ");
                    String nombre = scanner.nextLine();
                    System.out.print("ID del programa: ");
                    long programaId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("¿Activo? (true/false): ");
                    boolean activo = Boolean.parseBoolean(scanner.nextLine().trim());

                    CursoSolicitudDTO solicitud = new CursoSolicitudDTO(nombre, programaId, activo);
                    controlador.crearCurso(solicitud);
                    System.out.println("✅ Curso creado con éxito.");
                    break;

                case 2:
                    System.out.println("\n📋 LISTA DE CURSOS:");
                    controlador.listarCursos()
                            .forEach(System.out::println);
                    break;

                case 3:
                    System.out.print("Ingrese ID del curso a actualizar: ");
                    long idActualizar = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print("Nuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();
                    System.out.print("Nuevo ID del programa: ");
                    long nuevoProgramaId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("¿Activo? (true/false): ");
                    boolean nuevoActivo = Boolean.parseBoolean(scanner.nextLine().trim());

                    CursoSolicitudDTO actSolicitud = new CursoSolicitudDTO(nuevoNombre, nuevoProgramaId, nuevoActivo);
                    controlador.actualizarCurso(idActualizar, actSolicitud);
                    System.out.println("✅ Curso actualizado.");
                    break;

                case 4:
                    System.out.print("Ingrese ID del curso a eliminar: ");
                    long idEliminar = scanner.nextLong();
                    scanner.nextLine();

                    controlador.eliminarCurso(idEliminar);
                    System.out.println("🗑️ Curso eliminado.");
                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println("❌ Opción inválida.");
            }
        }
    }

    private void menuGestionEstudiantes() {
        EstudianteControlador controlador = fabricaControladores.crearControladorEstudiante();

        boolean volver = false;
        while (!volver) {
            System.out.println("\n===== MENÚ ESTUDIANTES =====");
            System.out.println("1. Crear estudiante");
            System.out.println("2. Listar estudiantes");
            System.out.println("3. Actualizar estudiante");
            System.out.println("4. Eliminar estudiante");
            System.out.println("0. Volver al menú principal");
            System.out.print("👉 Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Nombres: ");
                    String nombres = scanner.nextLine();
                    System.out.print("Apellidos: ");
                    String apellidos = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Edad: ");
                    long edad = Long.parseLong(scanner.nextLine());
                    System.out.print("¿Está activo? (true/false): ");
                    boolean activo = Boolean.parseBoolean(scanner.nextLine().trim());
                    System.out.print("Promedio: ");
                    double promedio = Double.parseDouble(scanner.nextLine());
                    System.out.print("Programa: ");
                    long programaId = Long.parseLong(scanner.nextLine());

                    EstudianteSolicitudDTO solicitud = new EstudianteSolicitudDTO(nombres, apellidos, email, edad, activo, promedio, programaId);
                    controlador.crearEstudiante(solicitud);
                    System.out.println("✅ Estudiante creado con éxito.");
                    break;

                case 2:
                    List<EstudianteRespuestaDTO> estudiantes = controlador.listarEstudiantes();
                    System.out.println("\n📋 LISTA DE ESTUDIANTES:");
                    for (EstudianteRespuestaDTO e : estudiantes) {
                        System.out.println(e.toString());
                    }
                    break;

                case 3:
                    System.out.print("Ingrese ID del estudiante a actualizar: ");
                    long idActualizar = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print("Nombres: ");
                    String n = scanner.nextLine();
                    System.out.print("Apellidos: ");
                    String a = scanner.nextLine();
                    System.out.print("Email: ");
                    String em = scanner.nextLine();
                    System.out.print("Edad: ");
                    long edadActualizar = scanner.nextLong();
                    System.out.print("¿Está activo? (true/false): ");
                    boolean estaActivo = scanner.nextBoolean();
                    System.out.print("Promedio: ");
                    double promedioActualizado = scanner.nextDouble();
                    System.out.print("Programa: ");
                    long programaIdActualizado = scanner.nextLong();
                    scanner.nextLine(); // Limpiar buffer

                    EstudianteSolicitudDTO actSolicitud = new EstudianteSolicitudDTO(n, a, em, edadActualizar,  estaActivo, promedioActualizado, programaIdActualizado);
                    controlador.actualizarEstudiante(idActualizar, actSolicitud);
                    System.out.println("✅ Estudiante actualizado.");
                    break;

                case 4:
                    System.out.print("Ingrese ID del estudiante a eliminar: ");
                    long idEliminar = scanner.nextLong();
                    scanner.nextLine();

                    controlador.eliminarEstudiante(idEliminar);
                    System.out.println("🗑️ Estudiante eliminado.");
                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println("❌ Opción inválida.");
            }
        }
        pausar();
    }
}