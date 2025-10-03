package com.ejemplo.DAOs.Implementaciones.Oracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.EstudianteDAO;
import com.ejemplo.Modelos.Estudiante;
import com.ejemplo.Modelos.Programa;
import com.ejemplo.infra.SqlErrorDetailer;

public class EstudianteDAOOracle implements EstudianteDAO {

    private final DataSource dataSource;

    public EstudianteDAOOracle(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Estudiante guardar(Estudiante entidad) {
        final String sql = "INSERT INTO ESTUDIANTE (id, codigo, programa_id, activo, promedio) VALUES (?, ?, ?, ?, ?)";

        Long programaId = (entidad.getPrograma() != null)
                ? (long) entidad.getPrograma().getID()
                : null;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, (long) entidad.getId());      // FK PERSONA(id)
            ps.setLong(2, (long) entidad.getCodigo());  // UNIQUE
            if (programaId != null && programaId > 0) {
                ps.setLong(3, programaId);
            } else {
                ps.setNull(3, Types.NUMERIC);
            }
            // Oracle no tiene BOOLEAN SQL: usar NUMBER(1) 0/1
            ps.setInt(4, entidad.isActivo() ? 1 : 0);
            ps.setDouble(5, entidad.getPromedio());
            ps.executeUpdate();

            return entidad;

        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "INSERT ESTUDIANTE (ORACLE)", sql,
                    entidad.getId(),
                    (long) entidad.getCodigo(),
                    programaId,
                    entidad.isActivo(),
                    entidad.getPromedio());
        }
    }

    @Override
    public List<Estudiante> listar() {
        final String sql =
            "SELECT P.id, P.nombres, P.apellidos, P.email, " +
            "       E.codigo, E.programa_id, E.activo, E.promedio " +
            "FROM PERSONA P " +
            "INNER JOIN ESTUDIANTE E ON P.id = E.id " +
            "ORDER BY P.apellidos, P.nombres";

        List<Estudiante> lista = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Estudiante est = new Estudiante();
                est.setId(rs.getLong("id"));
                est.setNombres(rs.getString("nombres"));
                est.setApellidos(rs.getString("apellidos"));
                est.setEmail(rs.getString("email"));
                est.setCodigo(rs.getDouble("codigo"));

                // activo NUMBER(1) -> boolean
                int actNum = rs.getInt("activo");
                est.setActivo(!rs.wasNull() && actNum == 1);

                est.setPromedio(rs.getDouble("promedio"));

                long programaId = rs.getLong("programa_id");
                if (!rs.wasNull() && programaId != 0) {
                    Programa p = new Programa();
                    p.setID(programaId);
                    est.setPrograma(p);
                }
                lista.add(est);
            }
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "SELECT ESTUDIANTES (ORACLE)", sql);
        }
        return lista;
    }

    @Override
    public void actualizar(Estudiante entidad) {
        final String sql = "UPDATE ESTUDIANTE SET codigo=?, programa_id=?, activo=?, promedio=? WHERE id=?";

        Long programaId = (entidad.getPrograma() != null)
                ? (long) entidad.getPrograma().getID()
                : null;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, (long) entidad.getCodigo());
            if (programaId != null && programaId > 0) {
                ps.setLong(2, programaId);
            } else {
                ps.setNull(2, Types.NUMERIC);
            }
            ps.setInt(3, entidad.isActivo() ? 1 : 0);
            ps.setDouble(4, entidad.getPromedio());
            ps.setLong(5, (long) entidad.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "UPDATE ESTUDIANTE (ORACLE)", sql,
                    (long) entidad.getCodigo(),
                    programaId,
                    entidad.isActivo(),
                    entidad.getPromedio(),
                    entidad.getId());
        }
    }

    @Override
    public void eliminar(Long id) {
        final String sql = "DELETE FROM ESTUDIANTE WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "DELETE ESTUDIANTE (ORACLE)", sql, id);
        }
    }
}
