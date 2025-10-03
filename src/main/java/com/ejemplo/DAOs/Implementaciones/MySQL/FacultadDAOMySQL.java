package com.ejemplo.DAOs.Implementaciones.MySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.FacultadDAO;
import com.ejemplo.Modelos.Facultad;
import com.ejemplo.Modelos.Persona;
import com.ejemplo.Utils.Erros.SqlErrorDetailer;

public class FacultadDAOMySQL implements FacultadDAO {

    private final DataSource dataSource;

    public FacultadDAOMySQL(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Facultad guardar(Facultad entidad) {
        final String sql = "INSERT INTO FACULTAD (nombre, decano_id) VALUES (?, ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entidad.getNombre());
            if (entidad.getDecano() != null) {
                ps.setLong(2, (long)entidad.getDecano().getId());
            } else {
                ps.setNull(2, Types.BIGINT);
            }
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) entidad.setID(keys.getLong(1));
            }
            return entidad;

        } catch (SQLException e) {
            Long decanoId = (entidad.getDecano() == null ? null : (long)entidad.getDecano().getId());
            throw SqlErrorDetailer.wrap(e, "INSERT FACULTAD", sql, entidad.getNombre(), decanoId);
        }
    }

    @Override
    public List<Facultad> listar() {
        final String sql = "SELECT id, nombre, decano_id FROM FACULTAD ORDER BY nombre";
        List<Facultad> lista = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "SELECT FACULTADES", sql);
        }
        return lista;
    }

    @Override
    public void actualizar(Facultad entidad) {
        final String sql = "UPDATE FACULTAD SET nombre = ?, decano_id = ? WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, entidad.getNombre());
            if (entidad.getDecano() != null) {
                ps.setLong(2, (long)entidad.getDecano().getId());
            } else {
                ps.setNull(2, Types.BIGINT);
            }
            ps.setLong(3, (long)entidad.getID());
            ps.executeUpdate();

        } catch (SQLException e) {
            Long decanoId = (entidad.getDecano() == null ? null : (long)entidad.getDecano().getId());
            throw SqlErrorDetailer.wrap(e, "UPDATE FACULTAD", sql, entidad.getNombre(), decanoId, entidad.getID());
        }
    }

    @Override
    public void eliminar(Long id) {
        final String sql = "DELETE FROM FACULTAD WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "DELETE FACULTAD", sql, id);
        }
    }

    @Override
    public Optional<Facultad> buscarPorId(Long id) {
        final String sql = "SELECT id, nombre, decano_id FROM FACULTAD WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "SELECT FACULTAD BY ID", sql, id);
        }
        return Optional.empty();
    }

    private Facultad mapRow(ResultSet rs) throws SQLException {
        Facultad f = new Facultad();
        f.setID(rs.getLong("id"));
        f.setNombre(rs.getString("nombre"));

        long decanoId = rs.getLong("decano_id");
        if (!rs.wasNull()) {
            Persona decano = new Persona();
            decano.setId(decanoId);
            f.setDecano(decano);
        }
        return f;
    }
}
