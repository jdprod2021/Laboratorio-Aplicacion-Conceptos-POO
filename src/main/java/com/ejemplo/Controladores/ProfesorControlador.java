package com.ejemplo.Controladores;

import java.util.ArrayList;
import java.util.List;

import com.ejemplo.DAOs.Interfaces.PersonaDAO;
import com.ejemplo.DAOs.Interfaces.ProfesorDAO;
import com.ejemplo.DTOs.Mappers.ProfesorMapper;
import com.ejemplo.DTOs.Respuesta.ProfesorRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.ProfesorSolicitudDTO;
import com.ejemplo.Modelos.Persona;
import com.ejemplo.Modelos.Profesor;

public class ProfesorControlador {

    private ProfesorDAO profesorDAO;
    private PersonaDAO personaDAO;


    public ProfesorControlador(ProfesorDAO profesorDAO, PersonaDAO personaDAO) {
        this.profesorDAO = profesorDAO;
        this.personaDAO = personaDAO;
    }

    public void crearProfesor(ProfesorSolicitudDTO profesorSolicitudDTO){
        Persona persona = personaDAO.buscarPorCorreo(profesorSolicitudDTO.email).orElse(null);

        if (persona == null) {
            persona = new Persona();
            persona.setNombres(profesorSolicitudDTO.nombres);
            persona.setApellidos(profesorSolicitudDTO.apellidos);
            persona.setEmail(profesorSolicitudDTO.email);
            persona = personaDAO.guardar(persona);
        }else{
            throw new RuntimeException("Ya existe una persona con el correo: " + profesorSolicitudDTO.email);
        }

        Profesor profesor = ProfesorMapper.toEntity(profesorSolicitudDTO, (long) persona.getId());
        profesorDAO.guardar(profesor);
    }

    public List<ProfesorRespuestaDTO> listarProfesores() {

        List<Profesor> profesores = profesorDAO.listar();
        List<ProfesorRespuestaDTO> respuestas = new ArrayList<>();

        for (Profesor profesor : profesores) {
            respuestas.add(ProfesorMapper.toDTO(profesor));
        }

        return respuestas;
    }

    public void actualizarProfesor(long id, ProfesorSolicitudDTO profesorSolicitudDTO) {
        Persona persona = personaDAO.buscarPorId(id).orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + id));

        persona.setNombres(profesorSolicitudDTO.nombres);
        persona.setApellidos(profesorSolicitudDTO.apellidos);
        persona.setEmail(profesorSolicitudDTO.email);
        personaDAO.actualizar(persona);

        Profesor profesor = ProfesorMapper.toEntity(profesorSolicitudDTO, (long) persona.getId());
        profesorDAO.actualizar(profesor);
    }


    public void eliminarProfesor(long id) {
        profesorDAO.eliminar(id);
        personaDAO.eliminar(id);
    }

}
