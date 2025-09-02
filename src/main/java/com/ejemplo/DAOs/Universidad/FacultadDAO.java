package com.ejemplo.DAOs.Universidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import com.ejemplo.DAOs.Personas.PersonaDAO;
import com.ejemplo.Modelos.Personas.Persona;
import com.ejemplo.Modelos.Universidad.Facultad;

public class FacultadDAO {

    PersonaDAO personaDAO = new PersonaDAO();

    

    public void guardar(Connection conn, Facultad facultad) {
        
        personaDAO.guardar(conn, facultad.getDecano());

        String sql = "MERGE INTO FACULTAD (id, nombre, decano_id) KEY(id) VALUES (?, ?, ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, (long)facultad.getID());
            ps.setString(2, facultad.getNombre());
            ps.setLong(3, (long)facultad.getDecano().getId());
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.err.println("Error al guardar FACULTAD: " + e.getMessage());
        }
    }

    public Optional<Facultad> cargar(Connection conn, int id){

        String sql = "SELECT * FROM FACULTAD WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                Persona decano = personaDAO.cargar(conn, rs.getInt("decano_id")).orElse(null);

                Facultad facultad = new Facultad();
                facultad.setID((double)rs.getLong("id"));
                facultad.setNombre(rs.getString("nombre"));
                facultad.setDecano(decano);

                return Optional.of(facultad);
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error al cargar FACULTAD: " + e.getMessage());
        }

        return Optional.empty();
    }

    public void eliminar(Connection conn, int id) {
        String sql = "DELETE FROM FACULTAD WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.err.println("Error al eliminar FACULTAD: " + e.getMessage());
        }
    }
}
