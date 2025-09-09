package com.ejemplo.DAOs.Universidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ejemplo.Modelos.Universidad.Curso;
import com.ejemplo.Modelos.Universidad.Programa;


public class CursoDAO {

    /* ======================= CREATE / UPSERT ======================= */
    public void guardar(Connection conn, Curso curso){
        final String sql = "MERGE INTO CURSO (id, nombre, programa_id, activo) KEY(id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, curso.getId());
            ps.setString(2, curso.getNombre());
            ps.setLong(3, (long)(curso.getPrograma() != null ? curso.getPrograma().getID() : 0L));
            ps.setBoolean(4, curso.isActivo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar CURSO", e);
        }
    }

    /* ======================= READ (uno) ======================= */
    public Optional<Curso> cargar(Connection conn, long id){
        final String sql = "SELECT id, nombre, programa_id, activo FROM CURSO WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar CURSO", e);
        }
        return Optional.empty();
    }

    /* ======================= READ (varios) ======================= */
    public List<Curso> listar(Connection conn){
        final String sql = "SELECT id, nombre, programa_id, activo FROM CURSO ORDER BY nombre";
        List<Curso> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar CURSOS", e);
        }
        return lista;
    }

    
    public List<Curso> listar(Connection conn, int limit, int offset){
        final String sql =
            "SELECT id, nombre, programa_id, activo FROM CURSO " +
            "ORDER BY nombre LIMIT ? OFFSET ?";
        List<Curso> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar paginado CURSOS", e);
        }
        return lista;
    }

    
    public List<Curso> listarPorPrograma(Connection conn, long programaId){
        final String sql = "SELECT id, nombre, programa_id, activo FROM CURSO WHERE programa_id=? ORDER BY nombre";
        List<Curso> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, programaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar CURSOS por programa", e);
        }
        return lista;
    }

    public boolean existe(Connection conn, long id){
        final String sql = "SELECT 1 FROM CURSO WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de CURSO", e);
        }
    }

    /* ======================= UPDATE ======================= */
    
    public int actualizar(Connection conn, Curso curso){
        final String sql = "UPDATE CURSO SET nombre=?, programa_id=?, activo=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, curso.getNombre());
            ps.setLong(2, (long)(curso.getPrograma() != null ? curso.getPrograma().getID() : 0L));
            ps.setBoolean(3, curso.isActivo());
            ps.setLong(4, curso.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar CURSO", e);
        }
    }

    /* ======================= DELETE ======================= */
    public int eliminar(Connection conn, long id){
        final String sql = "DELETE FROM CURSO WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar CURSO", e);
        }
    }

    public int eliminar(Connection conn, Curso curso){
        return eliminar(conn, curso.getId());
    }

    /* ======================= MAPPER ======================= */
    private Curso mapRow(ResultSet rs) throws SQLException {
        Curso c = new Curso();
        c.setId((int)rs.getLong("id"));
        c.setNombre(rs.getString("nombre"));
        c.setActivo(rs.getBoolean("activo"));

        long pid = rs.getLong("programa_id");
        if (!rs.wasNull()) {
           
            Programa p = new Programa();
            p.setID(pid);
            c.setPrograma(p);
        }
        return c;
    }
}
