package com.ejemplo.DAOs.Cursos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

import com.ejemplo.DAOs.Personas.ProfesorDAO;
import com.ejemplo.DAOs.Universidad.CursoDAO;
import com.ejemplo.Modelos.Cursos.CursoProfesor;

public class CursoProfesorDAO {

    ProfesorDAO profesorDAO = new ProfesorDAO();
    CursoDAO cursoDAO = new CursoDAO();

    public void guardar(Connection conn, CursoProfesor cursoProfesor) {
        cursoDAO.guardar(conn, cursoProfesor.getCurso());
        profesorDAO.guardar(conn, cursoProfesor.getProfesor());

        String sql = "MERGE INTO CURSO_PROFESOR (profesor_id, anio, semestre, curso_id) KEY(profesor_id, anio, semestre, curso_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, (int)cursoProfesor.getProfesor().getId());
            ps.setInt(2, cursoProfesor.getAnio());
            ps.setInt(3, cursoProfesor.getSemestre());
            ps.setInt(4, (int)cursoProfesor.getCurso().getId());
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.err.println("Error al guardar : CURSO_PROFESOR" + e.getMessage());
        }
    }

    public Optional<CursoProfesor> cargar(Connection conn, int id){

        String sql = "SELECT * FROM CURSO_PROFESOR WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            var rs = ps.executeQuery();
            if (rs.next()) {
                CursoProfesor cursoProfesor = new CursoProfesor();

                cursoProfesor.setCurso(cursoDAO.cargar(conn, rs.getInt("curso_id")).orElse(null));
                cursoProfesor.setProfesor(profesorDAO.cargar(conn, rs.getInt("profesor_id")).orElse(null));
                cursoProfesor.setAnio(rs.getInt("anio"));
                cursoProfesor.setSemestre(rs.getInt("semestre"));

                return Optional.of(cursoProfesor);
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error al cargar CURSO_PROFESOR: " + e.getMessage());
        }

        return Optional.empty();
    }

}
