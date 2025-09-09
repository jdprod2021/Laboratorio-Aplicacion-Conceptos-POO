package com.ejemplo.DAOs.Personas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ejemplo.Modelos.Personas.Profesor;

public class ProfesorDAO {

    private final PersonaDAO personaDAO = new PersonaDAO();

    /* ======================= CREATE / UPSERT ======================= */
    
    public void guardar(Connection conn, Profesor profesor) {
        
        personaDAO.guardar(conn, profesor);

        
        final String sql = "MERGE INTO PROFESOR (id, tipo_contrato) KEY(id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, (long)profesor.getId());
            ps.setString(2, profesor.getTipoContrato());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar PROFESOR", e);
        }
    }

    /* ======================= READ (uno) ======================= */
    public Optional<Profesor> cargarPorId(Connection conn, long id) {
        final String sql =
            "SELECT P.id, P.nombres, P.apellidos, P.email, PR.tipo_contrato " +
            "FROM PERSONA P INNER JOIN PROFESOR PR ON P.id = PR.id " +
            "WHERE P.id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar PROFESOR", e);
        }
        return Optional.empty();
    }

    /* ======================= READ (varios) ======================= */
    public List<Profesor> listar(Connection conn) {
        final String sql =
            "SELECT P.id, P.nombres, P.apellidos, P.email, PR.tipo_contrato " +
            "FROM PERSONA P INNER JOIN PROFESOR PR ON P.id = PR.id " +
            "ORDER BY P.apellidos, P.nombres";

        List<Profesor> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar PROFESORES", e);
        }
        return lista;
    }

    // Paginado opcional
    public List<Profesor> listar(Connection conn, int limit, int offset) {
        final String sql =
            "SELECT P.id, P.nombres, P.apellidos, P.email, PR.tipo_contrato " +
            "FROM PERSONA P INNER JOIN PROFESOR PR ON P.id = PR.id " +
            "ORDER BY P.apellidos, P.nombres " +
            "LIMIT ? OFFSET ?";

        List<Profesor> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar paginado PROFESORES", e);
        }
        return lista;
    }

    /* ======================= UPDATE ======================= */
   
    public int actualizar(Connection conn, Profesor profesor) {
        int filas = 0;
        filas += personaDAO.actualizar(conn, profesor);

        final String sql = "UPDATE PROFESOR SET tipo_contrato = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, profesor.getTipoContrato());
            ps.setLong(2, (long)profesor.getId());
            filas += ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar PROFESOR", e);
        }
        return filas;
    }

    /* ======================= DELETE ======================= */
    
    public int eliminar(Connection conn, long id) {
        int filas = 0;

        final String delProfesor = "DELETE FROM PROFESOR WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(delProfesor)) {
            ps.setLong(1, id);
            filas += ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar PROFESOR", e);
        }

        filas += personaDAO.eliminar(conn, id);

        return filas;
    }

   
    public int eliminar(Connection conn, Profesor profesor) {
        return eliminar(conn, (long)profesor.getId());
    }

    /* ======================= Utils ======================= */
    public boolean existe(Connection conn, long id) {
        final String sql = "SELECT 1 FROM PROFESOR WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de PROFESOR", e);
        }
    }

    private Profesor mapRow(ResultSet rs) throws SQLException {
        Profesor p = new Profesor();
        p.setId(rs.getLong("id"));
        p.setNombres(rs.getString("nombres"));
        p.setApellidos(rs.getString("apellidos"));
        p.setEmail(rs.getString("email"));
        p.setTipoContrato(rs.getString("tipo_contrato"));
        return p;
    }
}
