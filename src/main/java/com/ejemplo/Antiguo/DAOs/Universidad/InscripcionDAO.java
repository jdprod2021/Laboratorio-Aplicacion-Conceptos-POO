package com.ejemplo.DAOs.Universidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ejemplo.Modelos.Personas.Estudiante;
import com.ejemplo.Modelos.Universidad.Curso;
import com.ejemplo.Modelos.Universidad.Inscripcion;

public class InscripcionDAO {

    /* ======================= CREATE / UPSERT ======================= */
    
    public void guardar(Connection conn, Inscripcion ins) {
        final String sql = "MERGE INTO INSCRIPCION (curso_id, anio, semestre, estudiante_id) " +
                           "KEY (curso_id, anio, semestre, estudiante_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, ins.getCurso().getId());
            ps.setInt(2, ins.getAnio());
            ps.setInt(3, ins.getSemestre());
            ps.setLong(4, (long)ins.getEstudiante().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar INSCRIPCION", e);
        }
    }

    /* ======================= READ (uno) ======================= */
    
    public Optional<Inscripcion> cargarPorClave(Connection conn, long cursoId, int anio, int semestre, long estudianteId) {
        final String sql = "SELECT curso_id, anio, semestre, estudiante_id " +
                           "FROM INSCRIPCION WHERE curso_id=? AND anio=? AND semestre=? AND estudiante_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, cursoId);
            ps.setInt(2, anio);
            ps.setInt(3, semestre);
            ps.setLong(4, estudianteId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar INSCRIPCION por clave", e);
        }
        return Optional.empty();
    }

    /* ======================= READ (varios) ======================= */
    public List<Inscripcion> listar(Connection conn) {
        final String sql = "SELECT curso_id, anio, semestre, estudiante_id " +
                           "FROM INSCRIPCION ORDER BY anio, semestre, curso_id, estudiante_id";
        List<Inscripcion> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar INSCRIPCIONES", e);
        }
        return lista;
    }

    
    public List<Inscripcion> listar(Connection conn, int limit, int offset) {
        final String sql = "SELECT curso_id, anio, semestre, estudiante_id " +
                           "FROM INSCRIPCION ORDER BY anio, semestre, curso_id, estudiante_id " +
                           "LIMIT ? OFFSET ?";
        List<Inscripcion> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar paginado INSCRIPCIONES", e);
        }
        return lista;
    }

    
    public List<Inscripcion> listarPorCursoYPeriodo(Connection conn, long cursoId, int anio, int semestre) {
        final String sql = "SELECT curso_id, anio, semestre, estudiante_id " +
                           "FROM INSCRIPCION WHERE curso_id=? AND anio=? AND semestre=? " +
                           "ORDER BY estudiante_id";
        List<Inscripcion> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, cursoId);
            ps.setInt(2, anio);
            ps.setInt(3, semestre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar por curso y periodo", e);
        }
        return lista;
    }

    public List<Inscripcion> listarPorEstudiante(Connection conn, long estudianteId) {
        final String sql = "SELECT curso_id, anio, semestre, estudiante_id " +
                           "FROM INSCRIPCION WHERE estudiante_id=? ORDER BY anio, semestre, curso_id";
        List<Inscripcion> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, estudianteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar por estudiante", e);
        }
        return lista;
    }

    public boolean existe(Connection conn, long cursoId, int anio, int semestre, long estudianteId) {
        final String sql = "SELECT 1 FROM INSCRIPCION WHERE curso_id=? AND anio=? AND semestre=? AND estudiante_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, cursoId);
            ps.setInt(2, anio);
            ps.setInt(3, semestre);
            ps.setLong(4, estudianteId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia en INSCRIPCION", e);
        }
    }

    /* ======================= UPDATE ======================= */
    
    public int actualizarPorClave(Connection conn,
                                  long oldCursoId, int oldAnio, int oldSemestre, long oldEstudianteId,
                                  long newCursoId, int newAnio, int newSemestre, long newEstudianteId) {
        final String sql = "UPDATE INSCRIPCION SET curso_id=?, anio=?, semestre=?, estudiante_id=? " +
                           "WHERE curso_id=? AND anio=? AND semestre=? AND estudiante_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, newCursoId);
            ps.setInt(2, newAnio);
            ps.setInt(3, newSemestre);
            ps.setLong(4, newEstudianteId);

            ps.setLong(5, oldCursoId);
            ps.setInt(6, oldAnio);
            ps.setInt(7, oldSemestre);
            ps.setLong(8, oldEstudianteId);

            return ps.executeUpdate(); // 0 ó 1
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar INSCRIPCION por clave", e);
        }
    }

    /* ======================= DELETE ======================= */
    public int eliminarPorClave(Connection conn, long cursoId, int anio, int semestre, long estudianteId) {
        final String sql = "DELETE FROM INSCRIPCION WHERE curso_id=? AND anio=? AND semestre=? AND estudiante_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, cursoId);
            ps.setInt(2, anio);
            ps.setInt(3, semestre);
            ps.setLong(4, estudianteId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar INSCRIPCION por clave", e);
        }
    }

    public int eliminarPorCursoYPeriodo(Connection conn, long cursoId, int anio, int semestre) {
        final String sql = "DELETE FROM INSCRIPCION WHERE curso_id=? AND anio=? AND semestre=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, cursoId);
            ps.setInt(2, anio);
            ps.setInt(3, semestre);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar INSCRIPCION por curso y periodo", e);
        }
    }

    /* ======================= MAPPER ======================= */
   
    private Inscripcion mapRow(ResultSet rs) throws SQLException {
        Inscripcion ins = new Inscripcion();

        Curso c = new Curso();
        c.setId((int)rs.getLong("curso_id"));
        ins.setCurso(c);

        Estudiante e = new Estudiante();
        e.setId((int)rs.getLong("estudiante_id"));
        ins.setEstudiante(e);

        ins.setAnio(rs.getInt("anio"));
        ins.setSemestre(rs.getInt("semestre"));

        return ins;
    }
}