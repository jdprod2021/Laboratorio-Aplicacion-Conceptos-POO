package com.ejemplo.DAOs.Universidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ejemplo.Modelos.Universidad.Facultad;
import com.ejemplo.Modelos.Universidad.Programa;

public class ProgramaDAO {

    /* ======================= CREATE / UPSERT ======================= */
    public void guardar(Connection conn, Programa programa) {
        final String sql = "MERGE INTO PROGRAMA (id, nombre, duracion, registro, facultad_id) " +
                           "KEY(id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, (long)programa.getID());
            ps.setString(2, programa.getNombre());
            ps.setDouble(3, programa.getDuracion()); // años/semestres según tu modelo
            // Si programa.getRegistro() es java.util.Date:
            ps.setDate(4, new java.sql.Date(programa.getRegistro().getTime()));
            ps.setLong(5, (long)(programa.getFacultad() != null ? programa.getFacultad().getID() : 0L));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar PROGRAMA", e);
        }
    }

    /* ======================= READ (uno) ======================= */
    public Optional<Programa> cargarPorId(Connection conn, long id) {
        final String sql = "SELECT id, nombre, duracion, registro, facultad_id FROM PROGRAMA WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar PROGRAMA", e);
        }
        return Optional.empty();
    }

    /* ======================= READ (varios) ======================= */
    public List<Programa> listar(Connection conn) {
        final String sql = "SELECT id, nombre, duracion, registro, facultad_id FROM PROGRAMA ORDER BY nombre";
        List<Programa> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar PROGRAMAS", e);
        }
        return lista;
    }


    public List<Programa> listar(Connection conn, int limit, int offset) {
        final String sql = "SELECT id, nombre, duracion, registro, facultad_id FROM PROGRAMA " +
                           "ORDER BY nombre LIMIT ? OFFSET ?";
        List<Programa> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar paginado PROGRAMAS", e);
        }
        return lista;
    }

    public List<Programa> listarPorFacultad(Connection conn, long facultadId) {
        final String sql = "SELECT id, nombre, duracion, registro, facultad_id " +
                           "FROM PROGRAMA WHERE facultad_id = ? ORDER BY nombre";
        List<Programa> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, facultadId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar PROGRAMAS por facultad", e);
        }
        return lista;
    }

    public boolean existe(Connection conn, long id) {
        final String sql = "SELECT 1 FROM PROGRAMA WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de PROGRAMA", e);
        }
    }

    /* ======================= UPDATE ======================= */

    public int actualizar(Connection conn, Programa programa) {
        final String sql = "UPDATE PROGRAMA SET nombre = ?, duracion = ?, registro = ?, facultad_id = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, programa.getNombre());
            ps.setDouble(2, programa.getDuracion());
            ps.setDate(3, new java.sql.Date(programa.getRegistro().getTime()));
            ps.setLong(4, (long)(programa.getFacultad() != null ? programa.getFacultad().getID() : 0L));
            ps.setLong(5, (long)programa.getID());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar PROGRAMA", e);
        }
    }

    /* ======================= DELETE ======================= */
    public int eliminar(Connection conn, long id) {
        final String sql = "DELETE FROM PROGRAMA WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar PROGRAMA", e);
        }
    }

    public int eliminar(Connection conn, Programa programa) {
        return eliminar(conn, (long)programa.getID());
    }

    /* ======================= MAPPER ======================= */
    private Programa mapRow(ResultSet rs) throws SQLException {
        Programa p = new Programa();
        p.setID(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        p.setDuracion(rs.getDouble("duracion"));

        java.sql.Date reg = rs.getDate("registro");
        if (reg != null) {
            p.setRegistro(new java.util.Date(reg.getTime()));
        }

        long facId = rs.getLong("facultad_id");
        if (!rs.wasNull()) {
            Facultad f = new Facultad();
            f.setID(facId);     
            p.setFacultad(f);
        }
        return p;
    }
}
