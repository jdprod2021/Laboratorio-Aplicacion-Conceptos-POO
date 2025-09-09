package com.ejemplo.ViewConsole;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.ejemplo.DTOs.Respuesta.ProgramaRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.ProgramaSolicitudDTO;
import com.ejemplo.Servicios.Universidad.ProgramaServicio;
import com.ejemplo.Utils.InputUtils;

public class ProgramaView implements MenuView {

    private final ProgramaServicio servicio = new ProgramaServicio();

    @Override
    public void show() {
        System.out.println("---- PROGRAMA ----");
        System.out.println("1. Crear Programa");
        System.out.println("2. Listar Programas");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Actualizar Programa");
        System.out.println("5. Eliminar Programa");
        System.out.println("0. Volver");
    }

    @Override
    public void handle() {
        while (true) {
            int op = InputUtils.readInt("Opción (Programa): ");
            switch (op) {
                case 1 -> crear();
                case 2 -> listar();
                case 3 -> buscarPorId();
                case 4 -> actualizar();
                case 5 -> eliminar();
                case 0 -> { System.out.println("Volviendo al menú principal..."); return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    /* ===================== Operaciones ===================== */

    private void crear() {
        ProgramaSolicitudDTO dto = leerSolicitud();
        try {
            ProgramaRespuestaDTO out = servicio.crear(dto);
            System.out.println("✅ Programa creado:\n" + render(out));
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void listar() {
        List<ProgramaRespuestaDTO> lista = servicio.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay programas registrados.");
            return;
        }
        System.out.println("===== LISTA DE PROGRAMAS =====");
        for (ProgramaRespuestaDTO p : lista) {
            System.out.println(renderLinea(p));
        }
    }

    private void buscarPorId() {
        long id = InputUtils.readLong("ID del programa: ");
        Optional<ProgramaRespuestaDTO> opt = servicio.obtenerPorId(id);
        if (opt.isPresent()) {
            System.out.println(render(opt.get()));
        } else {
            System.out.println("No se encontró el programa con ID " + id);
        }
    }

    private void actualizar() {
        long id = InputUtils.readLong("ID del programa a actualizar: ");
        ProgramaSolicitudDTO dto = leerSolicitud();
        try {
            ProgramaRespuestaDTO out = servicio.actualizar(id, dto);
            System.out.println("✅ Programa actualizado:\n" + render(out));
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        long id = InputUtils.readLong("ID del programa a eliminar: ");
        try {
            servicio.eliminar(id);
            System.out.println("✅ Programa eliminado correctamente.");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /* ===================== Auxiliares ===================== */

    private ProgramaSolicitudDTO leerSolicitud() {
        ProgramaSolicitudDTO dto = new ProgramaSolicitudDTO();
        dto.nombre = InputUtils.readLine("Nombre del programa: ");
        dto.duracion = InputUtils.readInt("Duración (en semestres): ");

        dto.registro = Date.valueOf(LocalDate.now());

        dto.facultadId = InputUtils.readLong("ID de la facultad: ");
        return dto;
    }

    private String render(ProgramaRespuestaDTO p) {
        return "ID: " + p.ID + "\n" +
               "Nombre: " + p.nombre;
    }

    private String renderLinea(ProgramaRespuestaDTO p) {
        return "(" + p.ID + ") " + p.nombre;
    }
}
