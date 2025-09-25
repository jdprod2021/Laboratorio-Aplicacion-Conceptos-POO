package com.ejemplo.DAOs.Cursos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ejemplo.Modelos.Cursos.CursoProfesor;
import com.ejemplo.Modelos.Personas.Profesor;
import com.ejemplo.Modelos.Universidad.Curso;

public class CursoProfesorDAO {

    /* -------------------- CREATE (UPSERT) -------------------- */
    public void guardar(Connection conn, CursoProfesor cp) {
        String sql = "MERGE INTO CURSO_PROFESOR (profesor_id, anio, semestre, curso_id) " +
                     "KEY(profesor_id, anio, semestre, curso_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, (long)cp.getProfesor().getId());
            ps.setInt(2, cp.getAnio());
            ps.setInt(3, cp.getSemestre());
            ps.setLong(4, (long)cp.getCurso().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar CURSO_PROFESOR", e);
        }
    }

    /* -------------------- READ -------------------- */
    public List<CursoProfesor> listar(Connection conn) {
        String sql = "SELECT profesor_id, curso_id, anio, semestre FROM CURSO_PROFESOR ORDER BY anio, semestre, profesor_id";

        List<CursoProfesor> lista = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar CURSO_PROFESOR", e);
        }

        return lista;
    }

    private CursoProfesor mapRow(ResultSet rs) throws SQLException {
        CursoProfesor cp = new CursoProfesor();

        Profesor profesor = new Profesor(rs.getLong("profesor_id"));
        Curso curso = new Curso((int)rs.getLong("curso_id"));

        cp.setProfesor(profesor);
        cp.setCurso(curso);
        cp.setAnio(rs.getInt("anio"));
        cp.setSemestre(rs.getInt("semestre"));

        return cp;
    }

    /* -------------------- DELETE -------------------- */
    public int eliminarPorClave(Connection conn, long profesorId, int anio, int semestre, long cursoId) {
        String sql = "DELETE FROM CURSO_PROFESOR WHERE profesor_id=? AND anio=? AND semestre=? AND curso_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, profesorId);
            ps.setInt(2, anio);
            ps.setInt(3, semestre);
            ps.setLong(4, cursoId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar CURSO_PROFESOR por clave", e);
        }
    }

}
