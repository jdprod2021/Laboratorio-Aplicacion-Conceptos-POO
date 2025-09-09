package com.ejemplo.DAOs.Universidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ejemplo.Modelos.Personas.Persona;
import com.ejemplo.Modelos.Universidad.Facultad;

public class FacultadDAO {

    /* ======================= CREATE / UPSERT ======================= */
    public void guardar(Connection conn, Facultad facultad) {
        final String sql = "MERGE INTO FACULTAD (id, nombre, decano_id) KEY(id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, (long)facultad.getID());
            ps.setString(2, facultad.getNombre());
            ps.setLong(3, (long)(facultad.getDecano() != null ? facultad.getDecano().getId() : 0L));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar FACULTAD", e);
        }
    }

    /* ======================= READ (uno) ======================= */
    public Optional<Facultad> cargar(Connection conn, long id) {
        final String sql = "SELECT id, nombre, decano_id FROM FACULTAD WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar FACULTAD", e);
        }
        return Optional.empty();
    }

    /* ======================= READ (varios) ======================= */
    public List<Facultad> listar(Connection conn) {
        final String sql = "SELECT id, nombre, decano_id FROM FACULTAD ORDER BY nombre";
        List<Facultad> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar FACULTADES", e);
        }
        return lista;
    }

    
    public List<Facultad> listar(Connection conn, int limit, int offset) {
        final String sql =
            "SELECT id, nombre, decano_id FROM FACULTAD " +
            "ORDER BY nombre LIMIT ? OFFSET ?";
        List<Facultad> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar paginado FACULTADES", e);
        }
        return lista;
    }

    
    public List<Facultad> listarPorDecano(Connection conn, long decanoId) {
        final String sql = "SELECT id, nombre, decano_id FROM FACULTAD WHERE decano_id = ? ORDER BY nombre";
        List<Facultad> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, decanoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar FACULTADES por decano", e);
        }
        return lista;
    }

    public boolean existe(Connection conn, long id) {
        final String sql = "SELECT 1 FROM FACULTAD WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de FACULTAD", e);
        }
    }

    /* ======================= UPDATE ======================= */
    public int actualizar(Connection conn, Facultad facultad) {
        final String sql = "UPDATE FACULTAD SET nombre = ?, decano_id = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, facultad.getNombre());
            ps.setLong(2, (long)(facultad.getDecano() != null ? facultad.getDecano().getId() : 0L));
            ps.setLong(3, (long)facultad.getID());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar FACULTAD", e);
        }
    }

    /* ======================= DELETE ======================= */
    public int eliminar(Connection conn, long id) {
        final String sql = "DELETE FROM FACULTAD WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar FACULTAD", e);
        }
    }

    public int eliminar(Connection conn, Facultad facultad) {
        return eliminar(conn, (long)facultad.getID());
    }

    /* ======================= MAPPER ======================= */
    private Facultad mapRow(ResultSet rs) throws SQLException {
        Facultad f = new Facultad();
        f.setID(rs.getLong("id"));              
        f.setNombre(rs.getString("nombre"));

        long decanoId = rs.getLong("decano_id");
        if (!rs.wasNull()) {
            
            Persona decano = new Persona();
            decano.setId(decanoId);
            f.setDecano(decano);
        }
        return f;
    }
}
