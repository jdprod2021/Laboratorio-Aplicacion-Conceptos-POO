package com.ejemplo.ViewConsole;

import java.util.List;
import java.util.Optional;

import com.ejemplo.DTOs.Respuesta.EstudianteRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.EstudianteSolicitudDTO;
import com.ejemplo.Servicios.Personas.EstudianteServicio;
import com.ejemplo.Utils.InputUtils;

public class EstudianteView implements MenuView {

    private final EstudianteServicio servicio = new EstudianteServicio();

    @Override
    public void show() {
        System.out.println("---- ESTUDIANTE ----");
        System.out.println("1. Registrar Estudiante");
        System.out.println("2. Listar Estudiantes");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Actualizar Estudiante");
        System.out.println("5. Eliminar Estudiante");
        System.out.println("0. Volver");
    }

    @Override
    public void handle() {
        while (true) {
            show();
            int op = InputUtils.readInt("Opción (Estudiante): ");
            switch (op) {
                case 1 -> crear();
                case 2 -> listar();
                case 3 -> buscarPorId();
                case 4 -> actualizar();
                case 5 -> eliminar();
                case 0 -> { System.out.println("Volviendo..."); return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    /* ===================== Operaciones ===================== */

    private void crear() {
        EstudianteSolicitudDTO dto = leerSolicitud();
        try {
            servicio.crear(dto);
            System.out.println("✅ Estudiante registrado:");
            //System.out.println(render(out));
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void listar() {
        List<EstudianteRespuestaDTO> lista = servicio.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        System.out.println("===== LISTA DE ESTUDIANTES =====");
        for (EstudianteRespuestaDTO e : lista) {
            System.out.println(renderLinea(e));
        }
    }

    private void buscarPorId() {
        long id = InputUtils.readLong("ID del estudiante: ");
        Optional<EstudianteRespuestaDTO> opt = servicio.obtenerPorId(id);
        if (opt.isPresent()) {
            System.out.println(render(opt.get()));
        } else {
            System.out.println("No se encontró el estudiante con ID " + id);
        }
    }

    private void actualizar() {
        long id = InputUtils.readLong("ID del estudiante a actualizar: ");
        EstudianteSolicitudDTO dto = leerSolicitud();
        try {
            servicio.actualizar(id, dto);
            System.out.println("✅ Estudiante actualizado:");
            //System.out.println(render(out));
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        long id = InputUtils.readLong("ID del estudiante a eliminar: ");
        try {
            servicio.eliminar(id);
            System.out.println("✅ Estudiante eliminado correctamente.");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /* ===================== Auxiliares ===================== */

    private EstudianteSolicitudDTO leerSolicitud() {
        EstudianteSolicitudDTO dto = new EstudianteSolicitudDTO();
        dto.nombres   = InputUtils.readLine("Nombres: ");
        dto.apellidos = InputUtils.readLine("Apellidos: ");
        dto.email     = InputUtils.readLine("Email: ");
        dto.codigo    = InputUtils.readLong("Código (numérico): ");
        dto.activo    = InputUtils.readBoolean("¿Activo? (s/n): ");
        dto.promedio  = InputUtils.readDouble("Promedio (0.0 - 5.0): ");
        dto.programaId= InputUtils.readLong("ID del Programa (FK): ");
        return dto;
    }

    private String render(EstudianteRespuestaDTO e) {
        return "ID: " + e.ID + "\n" +
               "Nombre: " + e.nombres + " " + e.apellidos + "\n" +
               "Email: " + e.email + "\n" +
               "Programa: " + (e.NombrePrograma != null ? e.NombrePrograma : "—");
    }

    private String renderLinea(EstudianteRespuestaDTO e) {
        String prog = (e.NombrePrograma != null ? e.NombrePrograma : "—");
        return " " + e.ID + " | " + e.nombres + " | " + e.apellidos + " | " + prog + " | " + e.email;
    }
}