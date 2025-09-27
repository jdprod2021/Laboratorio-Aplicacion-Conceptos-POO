package com.ejemplo.Controladores;

import java.util.List;

import com.ejemplo.DAOs.Interfaces.CursoDAO;
import com.ejemplo.DAOs.Interfaces.ProgramaDAO;
import com.ejemplo.DTOs.Mappers.CursoMapper;
import com.ejemplo.DTOs.Solicitud.CursoSolicitudDTO;
import com.ejemplo.Modelos.Curso;
import com.ejemplo.Modelos.Programa;

public class CursoControlador {

    private CursoDAO cursoDAO;
    private ProgramaDAO programaDAO;

    public CursoControlador(CursoDAO cursoDAO, ProgramaDAO programaDAO) {
        this.cursoDAO = cursoDAO;
        this.programaDAO = programaDAO;
    }

    public void crearCurso(CursoSolicitudDTO datosDeCurso) {
        Programa programa = programaDAO.buscarPorId(datosDeCurso.programaId).orElse(null);
        if (programa != null) {
            cursoDAO.guardar(CursoMapper.toEntity(datosDeCurso, programa));
        }
    }

    public List<Curso> listarCursos() {
        return cursoDAO.listar();
    }

    public void actualizarCurso(long id, CursoSolicitudDTO datosDeCurso) {
        Programa programa = programaDAO.buscarPorId(datosDeCurso.programaId).orElse(null);
        if (programa != null) {
            Curso curso = CursoMapper.toEntity(datosDeCurso, programa);
            curso.setId((int)id);
            cursoDAO.actualizar(curso);
        }
    }

    public void eliminarCurso(Long id) {
        cursoDAO.eliminar(id);
    }
}
