package com.ejemplo.DAOs.Universidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import com.ejemplo.Modelos.Universidad.Facultad;
import com.ejemplo.Modelos.Universidad.Programa;

public class ProgramaDAO {

    FacultadDAO facultadDAO = new FacultadDAO();

    public void guardar(Connection conn, Programa programa) {
        
        facultadDAO.guardar(conn, programa.getFacultad());
        
        String sql = "MERGE INTO PROGRAMA (id, nombre, duracion, registro, facultad_id) KEY(id) VALUES (?, ?, ?, ?, ?)";
                
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, (long)programa.getID());
            ps.setString(2, programa.getNombre());
            ps.setDouble(3, programa.getDuracion());
            ps.setDate(4, new java.sql.Date(programa.getRegistro().getTime()));
            ps.setLong(5, (long)programa.getFacultad().getID());
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.err.println("Error al guardar PROGRAMA: " + e.getMessage());
        }
    }

    public Optional<Programa> cargar(Connection conn, int id){

        String sql = "SELECT * FROM PROGRAMA WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                Facultad facultad = facultadDAO.cargar(conn, rs.getInt("facultad_id")).orElse(null);
                
                Programa programa = new Programa();
                programa.setID((double)rs.getLong("id"));
                programa.setNombre(rs.getString("nombre"));
                programa.setDuracion(rs.getDouble("duracion"));
                programa.setRegistro(rs.getDate("registro"));
                programa.setFacultad(facultad);

                return Optional.of(programa);
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error al cargar PROGRAMA: " + e.getMessage());
        }

        return Optional.empty();
    }

    public void eliminar(Connection conn, int id){

        String sql = "DELETE FROM PROGRAMA WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.err.println("Error al eliminar PROGRAMA: " + e.getMessage());
        }
    }

}
