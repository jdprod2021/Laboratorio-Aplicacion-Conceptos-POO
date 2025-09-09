package com.ejemplo.DAOs.Personas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ejemplo.DAOs.Universidad.ProgramaDAO;
import com.ejemplo.Modelos.Personas.Estudiante;
import com.ejemplo.Modelos.Universidad.Programa;

public class EstudianteDAO {

    private final PersonaDAO personaDAO = new PersonaDAO();
    private final ProgramaDAO programaDAO = new ProgramaDAO();

    /* =============== CREATE/UPDATE =============== */
    public void guardar(Connection conn, Estudiante estudiante) {
        
        personaDAO.guardar(conn, estudiante);

        
        String sql = "MERGE INTO ESTUDIANTE (codigo, programa_id, activo, promedio) " +
                     "KEY(codigo) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, estudiante.getCodigo());
            ps.setLong(2, (long)(estudiante.getPrograma() != null ? estudiante.getPrograma().getID() : 0L));
            ps.setBoolean(3, estudiante.isActivo());
            ps.setDouble(4, estudiante.getPromedio());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar ESTUDIANTE", e);
        }
    }

    /* =============== READ =============== */
    public Optional<Estudiante> cargar(Connection conn, long id) {
        String sql = "SELECT P.id, P.nombres, P.apellidos, P.email, " +
                     "E.codigo, E.programa_id, E.activo, E.promedio " +
                     "FROM PERSONA P " +
                     "LEFT JOIN ESTUDIANTE E ON P.id = E.id " +
                     "WHERE P.id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setId(rs.getLong("id"));
                    estudiante.setNombres(rs.getString("nombres"));
                    estudiante.setApellidos(rs.getString("apellidos"));
                    estudiante.setEmail(rs.getString("email"));
                    estudiante.setCodigo(rs.getDouble("codigo"));
                    estudiante.setActivo(rs.getBoolean("activo"));
                    estudiante.setPromedio(rs.getDouble("promedio"));

                    long programaId = rs.getLong("programa_id");
                    if (programaId != 0) {
                        Programa programa = programaDAO.cargarPorId(conn, (long)programaId).orElse(null);
                        estudiante.setPrograma(programa);
                    }

                    return Optional.of(estudiante);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar ESTUDIANTE", e);
        }
        return Optional.empty();
    }

    public List<Estudiante> listar(Connection conn) {
        String sql = "SELECT P.id, P.nombres, P.apellidos, P.email, " +
                     "E.codigo, E.programa_id, E.activo, E.promedio " +
                     "FROM PERSONA P " +
                     "INNER JOIN ESTUDIANTE E ON P.id = E.id " +
                     "ORDER BY P.apellidos, P.nombres";

        List<Estudiante> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Estudiante estudiante = new Estudiante();
                estudiante.setId(rs.getLong("id"));
                estudiante.setNombres(rs.getString("nombres"));
                estudiante.setApellidos(rs.getString("apellidos"));
                estudiante.setEmail(rs.getString("email"));
                estudiante.setCodigo(rs.getDouble("codigo"));
                estudiante.setActivo(rs.getBoolean("activo"));
                estudiante.setPromedio(rs.getDouble("promedio"));

                long programaId = rs.getLong("programa_id");
                if (programaId != 0) {
                    Programa programa = programaDAO.cargarPorId(conn, (long)programaId).orElse(null);
                    estudiante.setPrograma(programa);
                }

                lista.add(estudiante);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar ESTUDIANTES", e);
        }
        return lista;
    }

    /* =============== DELETE =============== */
    public void eliminar(Connection conn, long id) {
        
        String sql = "DELETE FROM ESTUDIANTE WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar ESTUDIANTE", e);
        }

       
        personaDAO.eliminar(conn, id);
    }
}
