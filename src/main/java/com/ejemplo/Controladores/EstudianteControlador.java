package com.ejemplo.Controladores;

import java.util.ArrayList;
import java.util.List;

import com.ejemplo.DAOs.Interfaces.EstudianteDAO;
import com.ejemplo.DAOs.Interfaces.PersonaDAO;
import com.ejemplo.DAOs.Interfaces.ProgramaDAO;
import com.ejemplo.DTOs.Mappers.EstudianteMapper;
import com.ejemplo.DTOs.Solicitud.EstudianteSolicitudDTO;
import com.ejemplo.Modelos.Estudiante;
import com.ejemplo.Modelos.Persona;
import com.ejemplo.Modelos.Programa;

public class EstudianteControlador {

    private  EstudianteDAO estudianteDAO;
    private  PersonaDAO personaDAO;
    private  ProgramaDAO programaDAO;

    public EstudianteControlador(EstudianteDAO estudianteDAO, PersonaDAO personaDAO, ProgramaDAO programaDAO) {
        this.estudianteDAO = estudianteDAO;
        this.personaDAO = personaDAO;
        this.programaDAO = programaDAO;
    }

    public void crearEstudiante(EstudianteSolicitudDTO datosDeEstudiante) {
        
        Persona persona = personaDAO.buscarPorCorreo(datosDeEstudiante.email).orElse(null);
        
        if (persona == null) {
            persona = new Persona();
            persona.setNombres(datosDeEstudiante.nombres);
            persona.setApellidos(datosDeEstudiante.apellidos);
            persona.setEmail(datosDeEstudiante.email);
            persona = personaDAO.guardar(persona);
        }

        Programa programa = programaDAO.buscarPorId(datosDeEstudiante.programaId).orElse(null);

        Estudiante estudiante = EstudianteMapper.toEntity(datosDeEstudiante, programa, (long)persona.getId());

        estudianteDAO.guardar(estudiante);
    }

    public List<Estudiante> listarEstudiantes() {

        List<Estudiante> estudiantes = estudianteDAO.listar();
        List<Estudiante> respuestas = new ArrayList<>();

        for (Estudiante estudiante : estudiantes) {
            respuestas.add(estudiante);
        }

        return respuestas;
    }

    public void actualizarEstudiante(long id, EstudianteSolicitudDTO datosDeEstudiante) {

        Persona persona = personaDAO.buscarPorId(id).orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + id));

        persona.setNombres(datosDeEstudiante.nombres);
        persona.setApellidos(datosDeEstudiante.apellidos);
        persona.setEmail(datosDeEstudiante.email);
        personaDAO.actualizar(persona);

        Programa programa = programaDAO.buscarPorId(datosDeEstudiante.programaId).orElse(null);
        Estudiante estudiante = EstudianteMapper.toEntity(datosDeEstudiante, programa, (long)persona.getId());
        estudianteDAO.actualizar(estudiante);
    }

    public void eliminarEstudiante(Long id) {
        estudianteDAO.eliminar(id);
        personaDAO.eliminar(id);
    }

}
