package com.ejemplo.DAOs.Implementaciones.Oracle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.ProfesorDAO;
import com.ejemplo.Modelos.Profesor;

public class ProfesorDAOOracle implements ProfesorDAO {

    private final DataSource dataSource;

    public ProfesorDAOOracle(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Profesor guardar(Profesor entidad) {
        String sql = "INSERT INTO PROFESOR (id, tipo_contrato) VALUES (SEQ_PROFESOR.NEXTVAL, ?)";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql, 
                           PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entidad.getTipoContrato());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entidad.setId(generatedKeys.getLong(1)); // Obtener el ID generado
                }
            }

            return entidad;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar PROFESOR", e);
        }
    }

    @Override
    public List<Profesor> listar() {
        String sql = "SELECT P.id, P.nombres, P.apellidos, P.email, PR.tipo_contrato " +
                     "FROM PERSONA P INNER JOIN PROFESOR PR ON P.id = PR.id " +
                     "ORDER BY P.apellidos, P.nombres";

        List<Profesor> lista = new ArrayList<>();
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar PROFESORES", e);
        }
        return lista;
    }

    @Override
    public void actualizar(Profesor entidad) {
        String sql = "UPDATE PROFESOR SET tipo_contrato = ? WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, entidad.getTipoContrato());
            ps.setLong(2, (long) entidad.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar PROFESOR", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM PROFESOR WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar PROFESOR", e);
        }
    }

    private Profesor mapRow(ResultSet rs) throws SQLException {
        Profesor p = new Profesor();
        p.setId(rs.getLong("id"));
        p.setNombres(rs.getString("nombres"));
        p.setApellidos(rs.getString("apellidos"));
        p.setEmail(rs.getString("email"));
        p.setTipoContrato(rs.getString("tipo_contrato"));
        return p;
    }
}
