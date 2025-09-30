package com.ejemplo.Vistas;

import java.util.Scanner;
import com.ejemplo.Controladores.FacultadControlador;
import com.ejemplo.DTOs.Solicitud.FacultadSolicitudDTO;
import com.ejemplo.Fabricas.FabricaInterna.FabricaControladores;

public class VistaConsolaFacultades {
    private Scanner scanner;
    private FabricaControladores fabricaControladores;
    private FacultadControlador facultadControlador;

    public VistaConsolaFacultades(FabricaControladores fabricaControladores, Scanner scanner) {
        this.fabricaControladores = fabricaControladores;
        this.scanner = scanner;
        this.facultadControlador = fabricaControladores.crearControladorFacultad();
    }

    public void mostrarMenu() {
        boolean volver = false;
        while (!volver) {
            mostrarOpciones();
            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    crearFacultad();
                    break;
                case 2:
                    listarFacultades();
                    break;
                case 3:
                    actualizarFacultad();
                    break;
                case 4:
                    eliminarFacultad();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("❌ Opción inválida.");
            }
            if (!volver) {
                pausar();
            }
        }
    }

    private void mostrarOpciones() {
        System.out.println("\n╔═══════════════════════════════╗");
        System.out.println("║     GESTIÓN DE FACULTADES     ║");
        System.out.println("╠═══════════════════════════════╣");
        System.out.println("║ 1. 📝 Crear facultad          ║");
        System.out.println("║ 2. 📋 Listar facultades       ║");
        System.out.println("║ 3. 📋 Actualizar facultad     ║");
        System.out.println("║ 4. 🗑️  Eliminar facultad      ║");
        System.out.println("║ 0. ↩️  Volver al menú         ║");
        System.out.println("╚═══════════════════════════════╝");
        System.out.print("👉 Seleccione una opción: ");
    }

    private void crearFacultad() {

        System.out.println("\n=== CREAR NUEVA FACULTAD ===");
        System.out.print("Nombre de la facultad: ");
        String nombre = scanner.nextLine();
        System.out.print("ID del decano (persona): ");
        long decanoId = scanner.nextLong();
        scanner.nextLine();

        FacultadSolicitudDTO facultadDTO = new FacultadSolicitudDTO(nombre, decanoId);
        facultadControlador.crearFacultad(facultadDTO);
        System.out.println("✅ Facultad creada con éxito.");
    }

    private void listarFacultades() {
        System.out.println("\n=== LISTA DE FACULTADES ===");
        facultadControlador.listarFacultades()
                .forEach(facultad -> System.out.println(facultad.toString()));
    }

    private void actualizarFacultad() {
        System.out.println("\n=== ACTUALIZAR FACULTAD ===");
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
        System.out.println("✅ Facultad actualizada con éxito.");
    }

    private void eliminarFacultad() {
        System.out.println("\n=== ELIMINAR FACULTAD ===");
        System.out.print("Ingrese ID de la facultad a eliminar: ");
        long idEliminar = scanner.nextLong();
        scanner.nextLine();

        facultadControlador.eliminarFacultad(idEliminar);
        System.out.println("🗑️ Facultad eliminada con éxito.");
    }

    private void pausar() {
        System.out.println("\n📋 Presione ENTER para continuar...");
        scanner.nextLine();
    }
}