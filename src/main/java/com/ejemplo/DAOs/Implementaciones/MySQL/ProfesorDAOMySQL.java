package com.ejemplo.DAOs.Implementaciones.MySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.ProfesorDAO;
import com.ejemplo.Modelos.Profesor;
import com.ejemplo.infra.SqlErrorDetailer;

public class ProfesorDAOMySQL implements ProfesorDAO {

    private final DataSource dataSource;

    public ProfesorDAOMySQL(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Profesor guardar(Profesor entidad) {
        final String sql = "INSERT INTO PROFESOR (id, tipo_contrato) VALUES (?, ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, (long)entidad.getId());         // FK PERSONA(id)
            ps.setString(2, entidad.getTipoContrato());
            ps.executeUpdate();
            return entidad;

        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "INSERT PROFESOR", sql,
                    entidad.getId(), entidad.getTipoContrato());
        }
    }

    @Override
    public List<Profesor> listar() {
        final String sql =
            "SELECT P.id, P.nombres, P.apellidos, P.email, PR.tipo_contrato " +
            "FROM PERSONA P INNER JOIN PROFESOR PR ON P.id = PR.id " +
            "ORDER BY P.apellidos, P.nombres";

        List<Profesor> lista = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapRow(rs));
            return lista;

        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "SELECT PROFESORES", sql);
        }
    }

    @Override
    public void actualizar(Profesor entidad) {
        final String sql = "UPDATE PROFESOR SET tipo_contrato = ? WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, entidad.getTipoContrato());
            ps.setLong(2, (long)entidad.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "UPDATE PROFESOR", sql,
                    entidad.getTipoContrato(), entidad.getId());
        }
    }

    @Override
    public void eliminar(Long id) {
        final String sql = "DELETE FROM PROFESOR WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "DELETE PROFESOR", sql, id);
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
