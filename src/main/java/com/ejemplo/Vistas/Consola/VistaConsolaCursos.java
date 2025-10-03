
package com.ejemplo.Vistas.Consola;

import com.ejemplo.Controladores.CursoControlador;
import com.ejemplo.DTOs.Solicitud.CursoSolicitudDTO;
import com.ejemplo.Utils.InputUtils;

public class VistaConsolaCursos {

    private CursoControlador controlador;

    public VistaConsolaCursos(CursoControlador controlador) {
        this.controlador = controlador;
    }

    public void mostrarMenu() {
        boolean volver = false;
        while (!volver) {
            mostrarOpciones();
            int opcion = InputUtils.readInt();
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
        String nombre = InputUtils.readLine();
        
        System.out.print("🏛️ ID del programa: ");
        long programaId = InputUtils.readLong();
        
        System.out.print("⚡ ¿Activo? (true/false): ");
        boolean activo = InputUtils.readBoolean();

        CursoSolicitudDTO CursoSolicitud = new CursoSolicitudDTO(nombre, programaId, activo);
        controlador.crearCurso(CursoSolicitud);
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
        long id = InputUtils.readLong();

        System.out.print("📝 Nuevo nombre: ");
        String nombre = InputUtils.readLine();
        
        System.out.print("🏛️ Nuevo ID del programa: ");
        long programaId = InputUtils.readLong();
        
        System.out.print("⚡ ¿Activo? (true/false): ");
        boolean activo = InputUtils.readBoolean();

        CursoSolicitudDTO solicitud = new CursoSolicitudDTO(nombre, programaId, activo);
        controlador.actualizarCurso(id, solicitud);
        System.out.println("✅ Curso actualizado exitosamente.");
        pausar();
    }

    private void eliminarCurso() {
        System.out.println("\n=== Eliminar Curso ===");
        System.out.print("🔍 ID del curso a eliminar: ");
        long id = InputUtils.readLong();
        
        controlador.eliminarCurso(id);
        System.out.println("✅ Curso eliminado exitosamente.");
        pausar();
    }

    private void pausar() {
        System.out.println("\n📋 Presione ENTER para continuar...");
        InputUtils.readLine();
    }
}