package com.ejemplo.DAOs.Personas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import com.ejemplo.DAOs.Universidad.ProgramaDAO;
import com.ejemplo.Modelos.Personas.Estudiante;
import com.ejemplo.Modelos.Universidad.Programa;

public class EstudianteDAO {

    ProgramaDAO programaDAO = new ProgramaDAO();
    PersonaDAO personaDAO = new PersonaDAO();


    public void guardar(Connection conn, Estudiante estudiante){

        personaDAO.guardar(conn, estudiante);
        programaDAO.guardar(conn, estudiante.getPrograma());

        String sql = "MERGE INTO ESTUDIANTE (id, codigo, programa_id, activo, promedio) KEY(id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, (long)estudiante.getId());
            ps.setDouble(2, estudiante.getCodigo());
            ps.setLong(3, (long)estudiante.getPrograma().getID());
            ps.setBoolean(4, estudiante.isActivo());
            ps.setDouble(5, estudiante.getPromedio());
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.err.println("Error al guardar ESTUDIANTE: " + e.getMessage());
        }
    }

    public Optional<Estudiante> cargar(Connection conn, int id){

        String sql = "SELECT P.*, E.codigo, E.activo, E.promedio, E.programa_id FROM PERSONA P LEFT JOIN ESTUDIANTE E ON P.id = E.id WHERE P.id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                Programa programa = programaDAO.cargar(conn, (int)rs.getLong("programa_id")).orElse(null);

                Estudiante estudiante = new Estudiante();
                estudiante.setId(rs.getLong("id"));
                estudiante.setNombres(rs.getString("nombre"));
                estudiante.setApellidos(rs.getString("apellido"));
                estudiante.setEmail(rs.getString("email"));
                estudiante.setCodigo(rs.getDouble("codigo"));
                estudiante.setActivo(rs.getBoolean("activo"));
                estudiante.setPromedio(rs.getDouble("promedio"));
                estudiante.setPrograma(programa);
                
                return Optional.of(estudiante);
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error al cargar ESTUDIANTE: " + e.getMessage());
        }
        return Optional.empty();
    }

    public void eliminar(Connection conn, Estudiante estudiante){

        String sql = "DELETE FROM ESTUDIANTE WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, (long)estudiante.getId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Información eliminada correctamente.");
                // Eliminar la parte de Persona
                personaDAO.eliminar(conn, estudiante);
            } else {
                System.out.println("No se encontró el estudiante para eliminar.");
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error al eliminar ESTUDIANTE: " + e.getMessage());
        }
    }

}
