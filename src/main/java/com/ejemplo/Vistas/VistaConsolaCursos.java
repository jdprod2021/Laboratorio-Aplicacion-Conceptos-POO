
package com.ejemplo.Vistas;


import java.util.Scanner;

import com.ejemplo.Controladores.CursoControlador;
import com.ejemplo.DTOs.Solicitud.CursoSolicitudDTO;
import com.ejemplo.Fabricas.FabricaInterna.FabricaControladores;



public class VistaConsolaCursos {
    private Scanner scanner;
    private FabricaControladores fabricaControladores;
    private CursoControlador controlador;

    public VistaConsolaCursos(FabricaControladores fabricaControladores, Scanner scanner) {
        this.fabricaControladores = fabricaControladores;
        this.scanner = scanner;
        this.controlador = fabricaControladores.crearControladorCurso();
    }

    public void mostrarMenu() {
        boolean volver = false;
        while (!volver) {
            mostrarOpciones();
            int opcion = leerOpcion();
            volver = procesarOpcion(opcion);
        }
    }

    private void mostrarOpciones() {
        System.out.println("\n╔═══════════════════════════════╗");
        System.out.println("║      📚 MENÚ CURSOS 📚       ║");
        System.out.println("╠═══════════════════════════════╣");
        System.out.println("║ 1. ➕ Crear curso             ║");
        System.out.println("║ 2. 📋 Listar cursos           ║");
        System.out.println("║ 3. 📝 Actualizar curso        ║");
        System.out.println("║ 4. ❌ Eliminar curso          ║");
        System.out.println("║ 0. 🔙 Volver al menú principal║");
        System.out.println("╚═══════════════════════════════╝");
        System.out.print("👉 Seleccione una opción: ");
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private boolean procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                crearCurso();
                break;
            case 2:
                listarCursos();
                break;
            case 3:
                actualizarCurso();
                break;
            case 4:
                eliminarCurso();
                break;
            case 0:
                return true;
            default:
                System.out.println("❌ Opción inválida.");
        }
        return false;
    }

    private void crearCurso() {
        System.out.println("\n=== Crear Nuevo Curso ===");
        System.out.print("📝 Nombre del curso: ");
        String nombre = scanner.nextLine();
        
        System.out.print("🏛️ ID del programa: ");
        long programaId = Long.parseLong(scanner.nextLine());
        
        System.out.print("⚡ ¿Activo? (true/false): ");
        boolean activo = Boolean.parseBoolean(scanner.nextLine().trim());

        CursoSolicitudDTO solicitud = new CursoSolicitudDTO(nombre, programaId, activo);
        controlador.crearCurso(solicitud);
        System.out.println("✅ Curso creado exitosamente.");
        pausar();
    }

    private void listarCursos() {
        System.out.println("\n=== Lista de Cursos ===");
        controlador.listarCursos().forEach(curso -> 
            System.out.println("📚 " + curso.toString())
        );
        pausar();
    }

    private void actualizarCurso() {
        System.out.println("\n=== Actualizar Curso ===");
        System.out.print("🔍 ID del curso a actualizar: ");
        long id = Long.parseLong(scanner.nextLine());

        System.out.print("📝 Nuevo nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("🏛️ Nuevo ID del programa: ");
        long programaId = Long.parseLong(scanner.nextLine());
        
        System.out.print("⚡ ¿Activo? (true/false): ");
        boolean activo = Boolean.parseBoolean(scanner.nextLine().trim());

        CursoSolicitudDTO solicitud = new CursoSolicitudDTO(nombre, programaId, activo);
        controlador.actualizarCurso(id, solicitud);
        System.out.println("✅ Curso actualizado exitosamente.");
        pausar();
    }

    private void eliminarCurso() {
        System.out.println("\n=== Eliminar Curso ===");
        System.out.print("🔍 ID del curso a eliminar: ");
        long id = Long.parseLong(scanner.nextLine());
        
        controlador.eliminarCurso(id);
        System.out.println("✅ Curso eliminado exitosamente.");
        pausar();
    }

    private void pausar() {
        System.out.println("\n📋 Presione ENTER para continuar...");
        scanner.nextLine();
    }
}