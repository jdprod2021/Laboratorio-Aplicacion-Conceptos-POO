package com.ejemplo.DAOs.Personas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ejemplo.Modelos.Personas.Persona;

public class PersonaDAO {

    /* ================= CREATE/UPDATE ================= */

    public void guardar(Connection conn, Persona persona) {
        String sql = "MERGE INTO PERSONA (id, nombres, apellidos, email) " +
                     "KEY(id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, (long)persona.getId());
            ps.setString(2, persona.getNombres());
            ps.setString(3, persona.getApellidos());
            ps.setString(4, persona.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar PERSONA", e);
        }
    }

    /* ================= READ (uno) ================= */
    public Optional<Persona> cargar(Connection conn, long id) {
        String sql = "SELECT id, nombres, apellidos, email FROM PERSONA WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Persona persona = new Persona();
                    persona.setId(rs.getLong("id"));
                    persona.setNombres(rs.getString("nombres"));
                    persona.setApellidos(rs.getString("apellidos"));
                    persona.setEmail(rs.getString("email"));
                    return Optional.of(persona);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar PERSONA", e);
        }
        return Optional.empty();
    }

    /* ================= READ (varios) ================= */
    public List<Persona> listar(Connection conn) {
        String sql = "SELECT id, nombres, apellidos, email FROM PERSONA ORDER BY apellidos, nombres";
        List<Persona> lista = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Persona persona = new Persona();
                persona.setId(rs.getLong("id"));
                persona.setNombres(rs.getString("nombres"));
                persona.setApellidos(rs.getString("apellidos"));
                persona.setEmail(rs.getString("email"));
                lista.add(persona);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar PERSONAS", e);
        }
        return lista;
    }

    /* ================= UPDATE ================= */
    
    public int actualizar(Connection conn, Persona persona) {
        String sql = "UPDATE PERSONA SET nombres=?, apellidos=?, email=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, persona.getNombres());
            ps.setString(2, persona.getApellidos());
            ps.setString(3, persona.getEmail());
            ps.setLong(4, (long)persona.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar PERSONA", e);
        }
    }

    /* ================= DELETE ================= */
    public int eliminar(Connection conn, long id) {
        String sql = "DELETE FROM PERSONA WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar PERSONA", e);
        }
    }

    
    public int eliminar(Connection conn, Persona persona) {
        return eliminar(conn, (long)persona.getId());
    }
}
