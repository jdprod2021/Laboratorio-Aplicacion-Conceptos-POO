package com.ejemplo.Vistas.Consola;


import com.ejemplo.Controladores.ProfesorControlador;
import com.ejemplo.DTOs.Solicitud.ProfesorSolicitudDTO;
import com.ejemplo.Utils.InputUtils;


public class VistaConsolaProfesor {

    private ProfesorControlador controlador;

   public VistaConsolaProfesor(ProfesorControlador controlador) {
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

    private void mostrarOpciones(){
        System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║            👨‍🏫 GESTIÓN DE PROFESORES           ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║ 1. ➕ Crear Profesor                        ║");
            System.out.println("║ 2. 📋 Listar Profesores                     ║");
            System.out.println("║ 3. ✏️  Actualizar Profesor                   ║");
            System.out.println("║ 4. 🗑️  Eliminar Profesor                     ║");
            System.out.println("║ 0. 🔙 Volver                                ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.print("👉 Seleccione una opción: ");

    }

    private boolean procesarOpcion(int opcion){
        switch (opcion) {
                case 1:
                    crearProfesor();
                case 2:
                    listarProfesores();
                case 3: 
                    actualizarProfesor();
                case 4:
                    eliminarProfesor();
                case 0:
                    return true;
                default:
                    InputUtils.mostrarError("Opción inválida");
        }
                return false;
    }

    private void crearProfesor() {
        InputUtils.limpiarPantalla();
        System.out.println("➕ CREAR PROFESOR");

        try {
            
            System.out.println("=== Crear Nuevo Profesor ===");
            System.out.print("Nombres: ");
            String nombres = InputUtils.readLine();
            
            System.out.print("Apellidos: ");
            String apellidos = InputUtils.readLine();

            System.out.print("Email: ");
            String email = InputUtils.readLine();
            
            System.out.print("Tipo Contrato: ");
            String contrato = InputUtils.readLine();

            ProfesorSolicitudDTO ProfesorSolicitud = new ProfesorSolicitudDTO(nombres, apellidos, email, contrato);
            controlador.crearProfesor(ProfesorSolicitud);

            InputUtils.mostrarMensaje("✅ Profesor creado correctamente.");
        } catch (Exception e) {
            InputUtils.mostrarError("Error al crear: " + e.getMessage());
        }
        InputUtils.pausar();
    }

    private void listarProfesores() {
        InputUtils.limpiarPantalla();
        System.out.println("📋 LISTA DE PROFESORES");

        try {

            controlador.listarProfesores().forEach(profesor->
                    System.out.println(" " + profesor.toString())
            );

            InputUtils.pausar();

        } catch (Exception e) {
            InputUtils.mostrarError("Error al listar: " + e.getMessage());
        }
        InputUtils.pausar();
    }

    private void actualizarProfesor() {
        InputUtils.limpiarPantalla();
        System.out.println("✏️ ACTUALIZAR PROFESOR");

        try {

            System.out.print("🔍 ID del profesor a actualizar: ");
            long id = InputUtils.readLong();

            System.out.println("=== Datos Nuevo Profesor ===");
            System.out.print("Nombres: ");
            String nombres = InputUtils.readLine();
            
            System.out.print("Apellidos: ");
            String apellidos = InputUtils.readLine();

            System.out.print("Email: ");
            String email = InputUtils.readLine();
            
            System.out.print("Tipo Contrato: ");
            String contrato = InputUtils.readLine();

            ProfesorSolicitudDTO ProfesorSolicitud = new ProfesorSolicitudDTO(nombres, apellidos, email, contrato);
            controlador.actualizarProfesor(id, ProfesorSolicitud);
            InputUtils.mostrarMensaje("✅ Profesor actualizado.");

        } catch (Exception e) {
            InputUtils.mostrarError("Error al actualizar: " + e.getMessage());
        }
        InputUtils.pausar();
    }

    private void eliminarProfesor() {
        InputUtils.limpiarPantalla();
        System.out.println("🗑️ ELIMINAR PROFESOR");

        try {

            System.out.print("🔍 ID del profesor a eliminar: ");
            long id = InputUtils.readLong();

            controlador.eliminarProfesor(id);
            System.out.println("✅ Curso eliminado exitosamente.");

           
        } catch (Exception e) {
            InputUtils.mostrarError("Error al eliminar: " + e.getMessage());
        }
        InputUtils.pausar();
    }
}
