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
        // 1) Upsert de PERSONA: debe dejar estudiante.id seteado (por GeneratedKeys o lookup por email)
        personaDAO.guardar(conn, estudiante);

        // 2) Fallback: si por alguna razón aún no tiene id, lo recuperamos por email (único)
        if (estudiante.getId() <= 0) {
            try (PreparedStatement q = conn.prepareStatement("SELECT id FROM PERSONA WHERE email = ?")) {
                q.setString(1, estudiante.getEmail());
                try (ResultSet r = q.executeQuery()) {
                    if (r.next()) {
                        estudiante.setId(r.getLong(1));
                    } else {
                        throw new IllegalStateException(
                                "PERSONA no encontrada luego de guardar (email=" + estudiante.getEmail() + ")"
                        );
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error recuperando id de PERSONA por email antes de guardar ESTUDIANTE", e);
            }
        }

        // 3) Upsert de ESTUDIANTE por clave primaria (id) — recomendado dado tu DDL
        final String sql = "MERGE INTO ESTUDIANTE (id, codigo, programa_id, activo, promedio) " +
                "KEY(id) VALUES (?, ?, ?, ?, ?)";

        Long programaId = (estudiante.getPrograma() != null)
                ? (long) estudiante.getPrograma().getID()
                : null;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, (long) estudiante.getId());              // FK a PERSONA(id)
            ps.setLong(2, (long) estudiante.getCodigo());          // UNIQUE (uq_estudiante_codigo)
            if (programaId != null && programaId > 0) {
                ps.setLong(3, programaId);
            } else {
                ps.setNull(3, java.sql.Types.BIGINT);              // puede ser NULL según tu DDL
            }
            ps.setBoolean(4, estudiante.isActivo());
            ps.setDouble(5, estudiante.getPromedio());

            ps.executeUpdate();

        } catch (SQLException e) {
            SQLException next = e.getNextException();
            String nextInfo = (next == null) ? "—"
                    : String.format("SQLState=%s, ErrorCode=%d, Msg=%s",
                    next.getSQLState(), next.getErrorCode(), next.getMessage());

            String detalle = String.format(
                    "Error al guardar ESTUDIANTE. Datos=[id=%d, codigo=%d, programaId=%s, activo=%s, promedio=%.3f]. " +
                            "SQLState=%s, ErrorCode=%d, Msg=%s. SQL=%s. Next=%s",
                    (long) estudiante.getId(),
                    (long) estudiante.getCodigo(),
                    (programaId == null ? "NULL" : programaId.toString()),
                    estudiante.isActivo(),
                    estudiante.getPromedio(),
                    e.getSQLState(), e.getErrorCode(), e.getMessage(),
                    sql, nextInfo
            );
            throw new RuntimeException(detalle, e);
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
