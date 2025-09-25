package com.ejemplo.ViewConsole;

import java.util.List;
import java.util.Optional;

import com.ejemplo.DTOs.Respuesta.FacultadRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.FacultadSolicitudDTO;
import com.ejemplo.Repositorios.Personas.InscripcionesPersonas;
import com.ejemplo.Repositorios.Universidad.FacultadRepo;
import com.ejemplo.Servicios.Universidad.FacultadServicio;
import com.ejemplo.Utils.InputUtils;

public class FacultadView implements MenuView {

    // Inyección simple de dependencias (manual)
    private final FacultadServicio servicio =
            new FacultadServicio(new FacultadRepo(), new InscripcionesPersonas());

    @Override
    public void show() {
        System.out.println("---- FACULTAD ----");
        System.out.println("1. Crear Facultad");
        System.out.println("2. Listar Facultades");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Actualizar Facultad");
        System.out.println("5. Eliminar Facultad");
        System.out.println("0. Volver");
    }

    @Override
    public void handle() {
        
        while (true) {
            show();
            int op = InputUtils.readInt("Opción (Facultad): ");
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
        FacultadSolicitudDTO dto = leerSolicitud();
        try {
            servicio.crear(dto);
            System.out.println("✅ Facultad creada");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void listar() {
        List<FacultadRespuestaDTO> lista = servicio.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay facultades registradas.");
            return;
        }
        System.out.println("===== LISTA DE FACULTADES =====");
        for (FacultadRespuestaDTO f : lista) {
            System.out.println(renderLinea(f));
        }
    }

    private void buscarPorId() {
        long id = InputUtils.readLong("ID de la facultad: ");
        Optional<FacultadRespuestaDTO> opt = servicio.obtenerPorId(id);
        if (opt.isPresent()) {
            System.out.println(render(opt.get()));
        } else {
            System.out.println("No se encontró la facultad con ID " + id);
        }
    }

    private void actualizar() {
        long id = InputUtils.readLong("ID de la facultad a actualizar: ");
        FacultadSolicitudDTO dto = leerSolicitud();
        try {
            servicio.actualizar(id, dto);
            System.out.println("✅ Facultad actualizada");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        long id = InputUtils.readLong("ID de la facultad a eliminar: ");
        try {
            servicio.eliminar(id);
            System.out.println("✅ Facultad eliminada correctamente.");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /* ===================== Auxiliares ===================== */

    private FacultadSolicitudDTO leerSolicitud() {
        FacultadSolicitudDTO dto = new FacultadSolicitudDTO();
        dto.nombre = InputUtils.readLine("Nombre de la facultad: ");
        dto.decanoId = InputUtils.readLong("ID del decano (FK Persona): ");
        return dto;
    }

    private String render(FacultadRespuestaDTO f) {
    return "ID: " + f.ID + "\n" +
           "Nombre: " + f.nombre;
    }

    private String renderLinea(FacultadRespuestaDTO f) {
        return "  " + f.ID + " | " + f.nombre;
    }
}