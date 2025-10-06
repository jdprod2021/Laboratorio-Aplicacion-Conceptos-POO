package com.ejemplo.Vistas.Consola;

import java.sql.Date;

import com.ejemplo.Controladores.ProgramaControlador;
import com.ejemplo.DTOs.Solicitud.ProgramaSolicitudDTO;
import com.ejemplo.Utils.InputUtils;

public class VistaConsolaPrograma {


    private ProgramaControlador controlador;

    public VistaConsolaPrograma(ProgramaControlador programaControlador) {
        this.controlador = programaControlador;
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
        System.out.println("\n===== MENÚ PROGRAMAS =====");
        System.out.println("1. Crear programa");
        System.out.println("2. Listar programas");
        System.out.println("3. Actualizar programa");
        System.out.println("4. Eliminar programa");
        System.out.println("0. Volver al menú principal");
        System.out.print("👉 Seleccione una opción: ");
    }

    private boolean procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                crearPrograma();
                break;
            case 2:
                listarProgramas();
                break;
            case 3:
                actualizarPrograma();
                break;
            case 4:
                eliminarPrograma();
                break;
            case 0:
                return true;
            default:
                InputUtils.mostrarError("Opción inválida");
        }

        return false;
    }

    private void crearPrograma(){

        InputUtils.limpiarPantalla();
        System.out.println("➕ CREAR PROGRAMAS-");

        System.out.print("Nombre: ");
        String nombre = InputUtils.readLine();

        System.out.print("Duración (años): ");
        double duracion = InputUtils.readDouble();

        System.out.print("ID de la facultad: ");
        long facultadId = InputUtils.readLong();

        Date registro = new Date(System.currentTimeMillis());

        ProgramaSolicitudDTO solicitud = new ProgramaSolicitudDTO(nombre, duracion, registro, facultadId);
        controlador.crearPrograma(solicitud);
        InputUtils.mostrarMensaje("✅ Programa creado con éxito.");
        InputUtils.pausar();

    }


    private void listarProgramas(){
        InputUtils.limpiarPantalla();
        System.out.println("📋 LISTA DE PROGRAMAS");

        try {

            controlador.listarProgramas().forEach(programa->
                    System.out.println(" " + programa.toString())
            );

            InputUtils.pausar();

        } catch (Exception e) {
            InputUtils.mostrarError("Error al listar: " + e.getMessage());
        }
        InputUtils.pausar();

    }

    private void actualizarPrograma(){

        InputUtils.limpiarPantalla();
        System.out.println("✏️ ACTUALIZAR PROGRAMAS");

        System.out.print("🔍 ID del programa a actualizar: ");
        long id = InputUtils.readLong();

        System.out.println("=== Datos Nuevo Programa ===");

        System.out.print("Nombre: ");
        String nombre = InputUtils.readLine();

        System.out.print("Duración (años): ");
        double duracion = InputUtils.readDouble();

        System.out.print("ID de la facultad: ");
        long facultadId = InputUtils.readLong();

        Date registro = new Date(System.currentTimeMillis());

        ProgramaSolicitudDTO solicitud = new ProgramaSolicitudDTO(nombre, duracion, registro, facultadId);
        controlador.actualizarPrograma(id, solicitud);
        InputUtils.pausar();

    }

    private void eliminarPrograma(){

        InputUtils.limpiarPantalla();
        System.out.println("✏️ ELIMINAR PROGRAMA");

        System.out.print("🔍 ID del programa a eliminar: ");
        long id = InputUtils.readLong();

        controlador.eliminarPrograma(id);
        System.out.println("✅ Curso eliminado exitosamente.");
        InputUtils.pausar();

    }

}