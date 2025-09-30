package com.ejemplo.Vistas;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.ejemplo.Controladores.ProgramaControlador;
import com.ejemplo.DTOs.Respuesta.ProgramaRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.ProgramaSolicitudDTO;
import com.ejemplo.Fabricas.FabricaInterna.FabricaControladores;




public class VistaConsolaPrograma {
    private Scanner scanner;
    private FabricaControladores fabricaControladores;

    public VistaConsolaPrograma(FabricaControladores fabricaControladores, Scanner scanner) {
        this.fabricaControladores = fabricaControladores;
        this.scanner = scanner;
    }

    public void mostrarMenu() {
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
        System.out.println("\n📋 Presione ENTER para continuar...");
        scanner.nextLine();
    }
}