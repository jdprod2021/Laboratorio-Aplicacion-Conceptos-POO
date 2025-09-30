package com.ejemplo.Vistas;


import java.util.List;
import java.util.Scanner;

import com.ejemplo.Controladores.EstudianteControlador;
import com.ejemplo.DTOs.Respuesta.EstudianteRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.EstudianteSolicitudDTO;
import com.ejemplo.Fabricas.FabricaInterna.FabricaControladores;



public class VistaConsolaEstudiante {

    private Scanner scanner;
    private FabricaControladores fabricaControladores;

    public VistaConsolaEstudiante(FabricaControladores fabricaControladores, Scanner scanner) {
        this.fabricaControladores = fabricaControladores;
        this.scanner = scanner;
    }

    public void mostrarMenu() {
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
                    scanner.nextLine();

                    EstudianteSolicitudDTO actSolicitud = new EstudianteSolicitudDTO(n, a, em, edadActualizar, estaActivo, promedioActualizado, programaIdActualizado);
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
        System.out.println("\n📋 Presione ENTER para continuar...");
        scanner.nextLine();
    }

}
