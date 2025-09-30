package com.ejemplo.DAOs.Implementaciones.H2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.EstudianteDAO;
import com.ejemplo.Modelos.Estudiante;
import com.ejemplo.Modelos.Programa;
import com.ejemplo.infra.SqlErrorDetailer;

public class EstudianteDAOH2 implements EstudianteDAO{

    private final DataSource dataSource;

    public EstudianteDAOH2(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Estudiante guardar(Estudiante entidad) {

        String sql = "INSERT INTO ESTUDIANTE (id, codigo, programa_id, activo, promedio) " +
              "VALUES (?, ?, ?, ?, ?)";

        Long programaId = (entidad.getPrograma() != null)
            ? (long) entidad.getPrograma().getID()
            : null;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setLong(1, (long) entidad.getId());              // FK a PERSONA(id)
            ps.setLong(2, (long) entidad.getCodigo());          // UNIQUE (uq_estudiante_codigo)
            if (programaId != null && programaId > 0) {
                ps.setLong(3, programaId);
            } else {
                ps.setNull(3, java.sql.Types.BIGINT);              // puede ser NULL según tu DDL
            }
            ps.setBoolean(4, entidad.isActivo());
            ps.setDouble(5, entidad.getPromedio());
            ps.executeUpdate();

            return entidad;

        } catch (SQLException e) {
            
            throw SqlErrorDetailer.wrap(e, "INSERT ESTUDIANTE", sql,
                entidad.getId(),
                (long) entidad.getCodigo(),
                programaId,                 // puede ser NULL
                entidad.isActivo(),
                entidad.getPromedio());
        }
    }

    @Override
    public List<Estudiante> listar() {
        String sql = "SELECT P.id, P.nombres, P.apellidos, P.email, " +
                     "E.codigo, E.programa_id, E.activo, E.promedio " +
                     "FROM PERSONA P " +
                     "INNER JOIN ESTUDIANTE E ON P.id = E.id " +
                     "ORDER BY P.apellidos, P.nombres";

        List<Estudiante> lista = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
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

                System.out.println("❌ programaId=" + programaId);
                if (programaId != 0) {
                    Programa programa = new Programa();
                    programa.setID(programaId);
                    estudiante.setPrograma(programa);
                }

                lista.add(estudiante);
            }
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "SELECT ESTUDIANTES", sql);
        }
        return lista;
    }

    @Override
    public void actualizar(Estudiante entidad) {
        String sql = "UPDATE ESTUDIANTE SET codigo=?, programa_id=?, activo=?, promedio=? WHERE id=?";

        Long programaId = (entidad.getPrograma() != null)
            ? (long) entidad.getPrograma().getID()
            : null;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, (long) entidad.getCodigo());
            if (programaId != null && programaId > 0) {
                ps.setLong(2, programaId);
            } else {
                ps.setNull(2, java.sql.Types.BIGINT);              // puede ser NULL según tu DDL
            }
            ps.setBoolean(3, entidad.isActivo());
            ps.setDouble(4, entidad.getPromedio());
            ps.setLong(5, (long) entidad.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "UPDATE ESTUDIANTE", sql,
                (long) entidad.getCodigo(),
                programaId,
                entidad.isActivo(),
                entidad.getPromedio(),
                entidad.getId());
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM ESTUDIANTE WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "DELETE ESTUDIANTE", sql, id);
        }
    }

}
