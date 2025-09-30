package com.ejemplo.Vistas;

import java.util.List;

import com.ejemplo.Controladores.ProfesorControlador;
import com.ejemplo.DTOs.Respuesta.ProfesorRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.ProfesorSolicitudDTO;
import com.ejemplo.Fabricas.FabricaInterna.FabricaControladores;


public class VistaConsolaProfesor extends UtilsVistaConsola {

    private FabricaControladores fabrica;

    public VistaConsolaProfesor(FabricaControladores fabrica, java.util.Scanner scanner) {
        super(scanner);
        this.fabrica = fabrica;
    }

    public void mostrarMenu() {
        boolean enMenu = true;

        while (enMenu) {
            limpiarPantalla();
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

            int opcion = Integer.parseInt(scanner.nextLine().trim());

            switch (opcion) {
                case 1 -> crearProfesor();
                case 2 -> listarProfesores();
                case 3 -> actualizarProfesor();
                case 4 -> eliminarProfesor();
                case 0 -> enMenu = false;
                default -> mostrarError("Opción inválida");
            }
        }
    }

    private void crearProfesor() {
        limpiarPantalla();
        System.out.println("➕ CREAR PROFESOR");

        try {
            String nombres = leerTexto("Nombres: ");
            String apellidos = leerTexto("Apellidos: ");
            String email = leerTexto("Email: ");
            String contrato = leerTexto("Tipo de contrato: ");

            ProfesorSolicitudDTO dto = new ProfesorSolicitudDTO(nombres, apellidos, email, contrato);
            ProfesorControlador controlador = fabrica.crearControladorProfesor();
            controlador.crearProfesor(dto);

            mostrarMensaje("✅ Profesor creado correctamente.");
        } catch (Exception e) {
            mostrarError("Error al crear: " + e.getMessage());
        }
        pausar();
    }

    private void listarProfesores() {
        limpiarPantalla();
        System.out.println("📋 LISTA DE PROFESORES");

        try {

            ProfesorControlador controlador = fabrica.crearControladorProfesor();
            controlador.listarProfesores();
            List<ProfesorRespuestaDTO> lista = controlador.listarProfesores();

            if (lista.isEmpty()) {
                System.out.println("🔍 No hay profesores.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (Exception e) {
            mostrarError("Error al listar: " + e.getMessage());
        }
        pausar();
    }

    private void actualizarProfesor() {
        limpiarPantalla();
        System.out.println("✏️ ACTUALIZAR PROFESOR");

        try {
            long id = leerLong("ID del profesor: ");
            String nombres = leerTexto("Nuevos nombres: ");
            String apellidos = leerTexto("Nuevos apellidos: ");
            String email = leerTexto("Nuevo email: ");
            String contrato = leerTexto("Nuevo contrato: ");

            ProfesorSolicitudDTO dto = new ProfesorSolicitudDTO(nombres, apellidos, email, contrato);
            ProfesorControlador controlador = fabrica.crearControladorProfesor();
            controlador.actualizarProfesor(id, dto);

            mostrarMensaje("✅ Profesor actualizado.");
        } catch (Exception e) {
            mostrarError("Error al actualizar: " + e.getMessage());
        }
        pausar();
    }

    private void eliminarProfesor() {
        limpiarPantalla();
        System.out.println("🗑️ ELIMINAR PROFESOR");

        try {
            long id = leerLong("ID del profesor: ");
            String confirm = leerTexto("¿Seguro? (s/N): ");
            if (confirm.equalsIgnoreCase("s")) {
                ProfesorControlador controlador = fabrica.crearControladorProfesor();
                controlador.eliminarProfesor(id);
                mostrarMensaje("✅ Profesor eliminado.");
            } else {
                mostrarMensaje("❌ Cancelado.");
            }
        } catch (Exception e) {
            mostrarError("Error al eliminar: " + e.getMessage());
        }
        pausar();
    }
}
