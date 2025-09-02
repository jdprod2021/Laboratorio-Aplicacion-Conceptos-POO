package com.ejemplo.DAOs.Personas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.ejemplo.Modelos.Personas.Profesor;

public class ProfesorDAO {

    PersonaDAO personaDAO = new PersonaDAO();

    public void guardar(Connection conn, Profesor profesor) {
        // Primero guarda la parte de Persona
        personaDAO.guardar(conn, profesor);

        // Luego guarda la parte específica de Profesor
        String sql = "MERGE INTO PROFESOR (id, tipo_contrato) KEY(id) VALUES (?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, (long)profesor.getId());
            pstmt.setString(2, profesor.getTipoContrato());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            
            e.printStackTrace();

        }
    }

    public Optional<Profesor> cargar(Connection conn, int id){

        String sql = "SELECT P.*, PR.tipo_contrato FROM PERSONA P LEFT JOIN PROFESOR PR ON P.id = PR.id WHERE P.id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, (long)id);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Profesor profesor = new Profesor();
                profesor.setId((double)rs.getLong("id"));
                profesor.setNombres(rs.getString("nombres"));
                profesor.setApellidos(rs.getString("apellidos"));
                profesor.setEmail(rs.getString("email"));
                profesor.setTipoContrato(rs.getString("tipo_contrato"));
                return Optional.of(profesor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }

    public void eliminar(Connection conn, Profesor profesor) {
        String sql = "DELETE FROM PROFESOR WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, (long)profesor.getId());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Información eliminada correctamente.");
            } else {
                System.out.println("No se encontró el profesor para eliminar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Luego elimina la parte de Persona
        personaDAO.eliminar(conn, profesor);
    }
}
