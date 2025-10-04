package com.ejemplo.Controladores;

import java.util.ArrayList;
import java.util.List;

import com.ejemplo.DAOs.Interfaces.FacultadDAO;
import com.ejemplo.DAOs.Interfaces.PersonaDAO;
import com.ejemplo.DTOs.Mappers.FacultadMapper;
import com.ejemplo.DTOs.Respuesta.FacultadRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.FacultadSolicitudDTO;
import com.ejemplo.Modelos.Facultad;
import com.ejemplo.Modelos.Persona;

public class FacultadControlador {

    private FacultadDAO facultadDAO;
    private PersonaDAO personaDAO;
    private static FacultadControlador facultadControlador;

    private FacultadControlador(FacultadDAO facultadDAO, PersonaDAO personaDAO) {
        this.facultadDAO = facultadDAO;
        this.personaDAO = personaDAO;
    }

    public static FacultadControlador crearControlador(FacultadDAO facultadDAO, PersonaDAO personaDAO){
        if(facultadControlador == null){
           synchronized (FacultadControlador.class){
                if(facultadControlador == null){
                    facultadControlador = new FacultadControlador(facultadDAO, personaDAO);
                }
           } 
        }
        return facultadControlador;
    }

    public void crearFacultad(FacultadSolicitudDTO datosDeFacultad) {
        Persona decano = personaDAO.buscarPorId(datosDeFacultad.decanoId).orElse(null);
        if (decano != null) {
            Facultad facultad = FacultadMapper.toEntity(datosDeFacultad, decano);
            facultadDAO.guardar(facultad);
        }
    }

    public List<FacultadRespuestaDTO> listarFacultades() {

        List<Facultad> facultades = facultadDAO.listar();
        List<FacultadRespuestaDTO> respuestas = new ArrayList<>();

        for (Facultad facultad : facultades) {
            respuestas.add(FacultadMapper.toDTO(facultad));
        }
        return respuestas;
    }

    public void actualizarFacultad(long id, FacultadSolicitudDTO datosDeFacultad) {
        Persona decano = personaDAO.buscarPorId(datosDeFacultad.decanoId).orElse(null);
        if (decano != null) {
            Facultad facultad = FacultadMapper.toEntity(datosDeFacultad, decano);
            facultad.setID((double)id);
            facultadDAO.actualizar(facultad);
        }
    }

    public void eliminarFacultad(Long id) {
        facultadDAO.eliminar(id);
    }

}
