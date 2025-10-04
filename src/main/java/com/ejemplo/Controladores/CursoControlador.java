package com.ejemplo.Controladores;

import java.util.ArrayList;
import java.util.List;

import com.ejemplo.DAOs.Interfaces.CursoDAO;
import com.ejemplo.DAOs.Interfaces.ProgramaDAO;
import com.ejemplo.DTOs.Mappers.CursoMapper;
import com.ejemplo.DTOs.Respuesta.CursoRespuestaDTO;
import com.ejemplo.DTOs.Solicitud.CursoSolicitudDTO;
import com.ejemplo.Modelos.Curso;
import com.ejemplo.Modelos.Programa;

public class CursoControlador {

    private CursoDAO cursoDAO;
    private ProgramaDAO programaDAO;
    private static CursoControlador cursoControlador;

    private CursoControlador(CursoDAO cursoDAO, ProgramaDAO programaDAO) {
        this.cursoDAO = cursoDAO;
        this.programaDAO = programaDAO;
    }

    public static CursoControlador crearCursoControlador(CursoDAO cursoDAO, ProgramaDAO programaDAO){
        if(cursoControlador == null){
            synchronized (CursoControlador.class){
                if(cursoControlador == null){
                    cursoControlador = new CursoControlador(cursoDAO , programaDAO);
                }
            }
        }
        return cursoControlador;
    }

    public void crearCurso(CursoSolicitudDTO datosDeCurso) {
        Programa programa = programaDAO.buscarPorId(datosDeCurso.programaId).orElse(null);
        if (programa != null) {
            cursoDAO.guardar(CursoMapper.toEntity(datosDeCurso, programa));
        }
    }

    public List<CursoRespuestaDTO> listarCursos() {

        List<Curso> cursos = cursoDAO.listar();
        List<CursoRespuestaDTO> respuestas = new ArrayList<>();

        for (Curso curso : cursos) {
            respuestas.add(CursoMapper.toDTO(curso));
        }

        return respuestas;
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
