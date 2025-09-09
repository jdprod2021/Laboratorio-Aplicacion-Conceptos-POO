package com.ejemplo.ViewConsole;

import java.util.List;
import java.util.Optional;

import com.ejemplo.DTOs.Respuesta.ProfesorRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.ProfesorSolicitudDTO;
import com.ejemplo.Servicios.Personas.ProfesorServicio;
import com.ejemplo.Utils.InputUtils;

public class ProfesorView implements MenuView {

    private final ProfesorServicio servicio = new ProfesorServicio();

    @Override
    public void show() {
        System.out.println("---- PROFESOR ----");
        System.out.println("1. Registrar Profesor");
        System.out.println("2. Listar Profesores");
        System.out.println("3. Buscar Profesor por ID");
        System.out.println("4. Actualizar Profesor");
        System.out.println("5. Eliminar Profesor");
        System.out.println("0. Volver");
    }

    @Override
    public void handle() {
        while (true) {
            int op = InputUtils.readInt("Opción (Profesor): ");
            switch (op) {
                case 1 -> registrar();
                case 2 -> listar();
                case 3 -> buscarPorId();
                case 4 -> actualizar();
                case 5 -> eliminar();
                case 0 -> { System.out.println("Volviendo..."); return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    /* ====================== MÉTODOS ====================== */

    private void registrar() {
        ProfesorSolicitudDTO dto = leerDatosProfesor();
        try {
            ProfesorRespuestaDTO creado = servicio.crear(dto);
            System.out.println("Profesor registrado con éxito: " + creado);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listar() {
        List<ProfesorRespuestaDTO> lista = servicio.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay profesores registrados.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void buscarPorId() {
        long id = InputUtils.readInt("Ingrese ID del profesor: ");
        Optional<ProfesorRespuestaDTO> opt = servicio.obtenerPorId(id);
        System.out.println(opt.map(Object::toString).orElse("Profesor no encontrado."));
    }

    private void actualizar() {
        long id = InputUtils.readInt("Ingrese ID del profesor a actualizar: ");
        ProfesorSolicitudDTO dto = leerDatosProfesor();
        try {
            ProfesorRespuestaDTO actualizado = servicio.actualizar(id, dto);
            System.out.println("Profesor actualizado: " + actualizado);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        long id = InputUtils.readInt("Ingrese ID del profesor a eliminar: ");
        try {
            servicio.eliminar(id);
            System.out.println("Profesor eliminado correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /* ====================== AUXILIAR ====================== */
    private ProfesorSolicitudDTO leerDatosProfesor() {
        ProfesorSolicitudDTO dto = new ProfesorSolicitudDTO();
        dto.nombres = InputUtils.readLine("Nombres: ");
        dto.apellidos = InputUtils.readLine("Apellidos: ");
        dto.email = InputUtils.readLine("Email: ");
        dto.TipoContrato = InputUtils.readLine("Tipo de contrato: ");
        return dto;
    }
}
