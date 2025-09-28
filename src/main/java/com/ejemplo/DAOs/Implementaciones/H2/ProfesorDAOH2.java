package com.ejemplo.DAOs.Implementaciones.H2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.ProfesorDAO;
import com.ejemplo.Modelos.Profesor;
import com.ejemplo.infra.SqlErrorDetailer;

public class ProfesorDAOH2 implements ProfesorDAO{

    private final DataSource dataSource;

    public ProfesorDAOH2(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Profesor guardar(Profesor entidad) {

        String sql = "INSERT INTO PROFESOR (id, tipoContrato) VALUES (?, ?)";

        try (var ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setLong(1, (long)entidad.getId());
            ps.setString(2, entidad.getTipoContrato());
            ps.executeUpdate();

            return entidad;

        } catch (Exception e) {
            // Incluye operación, SQL y parámetros que intentaste enviar
            throw SqlErrorDetailer.wrap(e, "INSERT PROFESOR", sql,
                (long) entidad.getId(),
                entidad.getTipoContrato());
        }

    }

    @Override
    public List<Profesor> listar() {
        final String sql =
            "SELECT P.id, P.nombres, P.apellidos, P.email, PR.tipo_contrato " +
            "FROM PERSONA P INNER JOIN PROFESOR PR ON P.id = PR.id " +
            "ORDER BY P.apellidos, P.nombres";

        List<Profesor> lista = new ArrayList<>();
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "SELECT PROFESORES", sql /* sin params */);
        }
        return lista;
    }

    @Override
    public void actualizar(Profesor entidad) {
        final String sql = "UPDATE PROFESOR SET tipo_contrato = ? WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, entidad.getTipoContrato());
            ps.setLong(2, (long)entidad.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "UPDATE PROFESOR", sql,
                entidad.getTipoContrato(), (long) entidad.getId());
        }
    }

    @Override
    public void eliminar(Long id) {
        final String delProfesor = "DELETE FROM PROFESOR WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(delProfesor)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "DELETE PROFESOR", delProfesor, id);
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
