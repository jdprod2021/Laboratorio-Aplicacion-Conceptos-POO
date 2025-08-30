package com.ejemplo.DAOs.Personas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.ejemplo.Modelos.Personas.Persona;

public class PersonaDAO {

    public void guardar(Connection conn, Persona persona) {
        
        String sql = "MERGE INTO PERSONA (id, nombres, apellidos, email) KEY(id) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, (long)persona.getId());
            pstmt.setString(2, persona.getNombres());
            pstmt.setString(3, persona.getApellidos());
            pstmt.setString(4, persona.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Persona> cargar(Connection conn, int id) {

        Persona persona = new Persona();
        
        String sql = "SELECT * FROM PERSONA WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, (long)id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                persona.setId((double)rs.getLong("id"));
                persona.setNombres(rs.getString("nombres"));
                persona.setApellidos(rs.getString("apellidos"));
                persona.setEmail(rs.getString("email"));
                return Optional.of(persona);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

}
