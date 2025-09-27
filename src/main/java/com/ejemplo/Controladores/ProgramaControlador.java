package com.ejemplo.Controladores;

import java.util.List;

import com.ejemplo.DAOs.Interfaces.FacultadDAO;
import com.ejemplo.DAOs.Interfaces.ProgramaDAO;
import com.ejemplo.DTOs.Mappers.ProgramaMapper;
import com.ejemplo.DTOs.Solicitud.ProgramaSolicitudDTO;
import com.ejemplo.Modelos.Facultad;
import com.ejemplo.Modelos.Programa;

public class ProgramaControlador {

    private ProgramaDAO programaDAO;
    private FacultadDAO facultadDAO;

    public ProgramaControlador(ProgramaDAO programaDAO, FacultadDAO facultadDAO) {
        this.programaDAO = programaDAO;
        this.facultadDAO = facultadDAO;
    }

    public void crearPrograma(ProgramaSolicitudDTO datosDePrograma) {
        
        Facultad facultad = facultadDAO.buscarPorId(datosDePrograma.facultadId).orElse(null);
        if (facultad != null) {
            Programa programa = ProgramaMapper.toEntity(datosDePrograma, facultad);
            programaDAO.guardar(programa);
        }
    }

    public List<Programa> listarProgramas() {
        return programaDAO.listar();
    }

    public void actualizarPrograma(long id, ProgramaSolicitudDTO datosDePrograma) {
        Facultad facultad = facultadDAO.buscarPorId(datosDePrograma.facultadId).orElse(null);
        if (facultad != null) {
            Programa programa = ProgramaMapper.toEntity(datosDePrograma, facultad);
            programa.setID((double)id);
            programaDAO.actualizar(programa);
        }
    }

    public void eliminarPrograma(Long id) {
        programaDAO.eliminar(id);
    }

}
