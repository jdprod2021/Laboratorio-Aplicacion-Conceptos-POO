package com.ejemplo.DAOs.Universidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import com.ejemplo.Modelos.Universidad.Curso;
import com.ejemplo.Modelos.Universidad.Programa;

public class CursoDAO {

    ProgramaDAO programaDAO = new ProgramaDAO();

    public void guardar(Connection conn, Curso curso){

        programaDAO.guardar(conn, curso.getPrograma());

        String sql = "MERGE INTO CURSO (id, nombre, programa_id, activo) KEY(id) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, (long)curso.getId());
            ps.setString(2, curso.getNombre());
            ps.setLong(3, (long)curso.getPrograma().getID());
            ps.setBoolean(4, curso.isActivo());
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.err.println("Error al guardar CURSO: " + e.getMessage());
        }
    }

    public Optional<Curso> cargar(Connection conn, int id){

        String sql = "SELECT * FROM CURSO WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                Programa programa = programaDAO.cargar(conn, (int)rs.getLong("programa_id")).orElse(null);

                Curso curso = new Curso();
                curso.setId((int)rs.getLong("id"));
                curso.setNombre(rs.getString("nombre"));
                curso.setActivo(rs.getBoolean("activo"));
                curso.setPrograma(programa);
                
                return Optional.of(curso);
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error al cargar CURSO: " + e.getMessage());
        }

        return Optional.empty();
    }

}
