package com.ejemplo.DAOs.Universidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

import com.ejemplo.DAOs.Personas.EstudianteDAO;
import com.ejemplo.Modelos.Universidad.Inscripcion;

public class InscripcionDAO {

    EstudianteDAO estudianteDAO = new EstudianteDAO();
    CursoDAO cursoDAO = new CursoDAO();

    public void guardar(Connection conn, Inscripcion inscripcion) {
        
        cursoDAO.guardar(conn, inscripcion.getCurso());
        estudianteDAO.guardar(conn, inscripcion.getEstudiante());

        String sql = "MERGE INTO INSCRIPCION (curso_id, anio, semestre, estudiante_id) KEY (curso_id, anio, semestre, estudiante_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ((int)inscripcion.getCurso().getId()));
            ps.setInt(2, inscripcion.getAnio());
            ps.setInt(3, inscripcion.getSemestre());
            ps.setInt(4, (int)inscripcion.getEstudiante().getId());
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.err.println("Error al guardar : INSCRIPCION" + e.getMessage());
        }
    }

    public Optional<Inscripcion> cargar(Connection conn, int id){

        String sql = "SELECT * FROM INSCRIPCION WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            var rs = ps.executeQuery();
            if (rs.next()) {
                Inscripcion inscripcion = new Inscripcion();

                inscripcion.setCurso(cursoDAO.cargar(conn, rs.getInt("curso_id")).orElse(null));
                inscripcion.setEstudiante(estudianteDAO.cargar(conn, rs.getInt("estudiante_id")).orElse(null));
                inscripcion.setAnio(rs.getInt("anio"));
                inscripcion.setSemestre(rs.getInt("semestre"));

                return Optional.of(inscripcion);
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error al cargar INSCRIPCION: " + e.getMessage());
        }

        return Optional.empty();
    }

}
