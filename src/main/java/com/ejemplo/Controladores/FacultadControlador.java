package com.ejemplo.Controladores;

import java.util.List;

import com.ejemplo.DAOs.Interfaces.FacultadDAO;
import com.ejemplo.DAOs.Interfaces.PersonaDAO;
import com.ejemplo.DTOs.Mappers.FacultadMapper;
import com.ejemplo.DTOs.Solicitud.FacultadSolicitudDTO;
import com.ejemplo.Modelos.Facultad;
import com.ejemplo.Modelos.Persona;

public class FacultadControlador {

    private FacultadDAO facultadDAO;
    private PersonaDAO personaDAO;

    public FacultadControlador(FacultadDAO facultadDAO, PersonaDAO personaDAO) {
        this.facultadDAO = facultadDAO;
        this.personaDAO = personaDAO;
    }

    public void crearFacultad(FacultadSolicitudDTO datosDeFacultad) {
        Persona decano = personaDAO.buscarPorId(datosDeFacultad.decanoId).orElse(null);
        if (decano != null) {
            Facultad facultad = FacultadMapper.toEntity(datosDeFacultad, decano);
            facultadDAO.guardar(facultad);
        }
    }

    public List<Facultad> listarFacultades() {
        return facultadDAO.listar();
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
