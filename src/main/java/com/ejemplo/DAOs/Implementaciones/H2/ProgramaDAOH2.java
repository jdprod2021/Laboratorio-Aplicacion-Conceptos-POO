package com.ejemplo.DAOs.Implementaciones.H2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.ProgramaDAO;
import com.ejemplo.Modelos.Facultad;
import com.ejemplo.Modelos.Programa;
import com.ejemplo.infra.SqlErrorDetailer;

public class ProgramaDAOH2 implements ProgramaDAO{

    private final DataSource dataSource;

    public ProgramaDAOH2(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Programa guardar(Programa entidad) {
        final String sql = "INSERT INTO PROGRAMA (nombre, duracion, registro, facultad_id) " +
                           "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, entidad.getNombre());
            ps.setDouble(2, entidad.getDuracion());
            ps.setDate(3, new java.sql.Date(entidad.getRegistro().getTime()));
            ps.setLong(4, (long)(entidad.getFacultad() != null ? entidad.getFacultad().getID() : 0L));
            ps.executeUpdate();

            // Obtener la clave generada
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // Asignar el id generado a la entidad
                    entidad.setID(generatedKeys.getLong(1)); // Asumiendo que el id
                }
            }

            return entidad;

        } catch (SQLException e) {
            Long facId =  (entidad.getFacultad() == null ? null : (long)entidad.getFacultad().getID());
            throw SqlErrorDetailer.wrap(e, "INSERT PROGRAMA", sql,
                entidad.getNombre(), entidad.getDuracion(), entidad.getRegistro(), facId);
        }
    }

    @Override
    public List<Programa> listar() {
        final String sql = "SELECT id, nombre, duracion, registro, facultad_id FROM PROGRAMA ORDER BY nombre";
        List<Programa> lista = new ArrayList<>();
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "SELECT PROGRAMAS", sql);
        }
        return lista;
    }

    @Override
    public void actualizar(Programa entidad) {
        final String sql = "UPDATE PROGRAMA SET nombre = ?, duracion = ?, registro = ?, facultad_id = ? WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, entidad.getNombre());
            ps.setDouble(2, entidad.getDuracion());
            ps.setDate(3, new java.sql.Date(entidad.getRegistro().getTime()));
            ps.setLong(4, (long)(entidad.getFacultad() != null ? entidad.getFacultad().getID() : 0L));
            ps.setLong(5, (long)entidad.getID());
            ps.executeUpdate();
        } catch (SQLException e) {
            Long facId = (entidad.getFacultad() == null ? null : (long)entidad.getFacultad().getID());
            throw SqlErrorDetailer.wrap(e, "UPDATE PROGRAMA", sql,
                entidad.getNombre(), entidad.getDuracion(), entidad.getRegistro(), facId, entidad.getID());
        }
    }

    @Override
    public void eliminar(Long id) {
        final String sql = "DELETE FROM PROGRAMA WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "DELETE PROGRAMA", sql, id);
        }
    }

    @Override
    public Optional<Programa> buscarPorId(Long id) {
        String sql = "SELECT id, nombre, duracion, registro, facultad_id FROM PROGRAMA WHERE id = ?";

        Optional<Programa> programa = Optional.empty();

        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    programa = Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "SELECT PROGRAMA BY ID", sql, id);
        }
        return programa;
    }

    private Programa mapRow(ResultSet rs) throws SQLException {
        Programa p = new Programa();
        p.setID(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        p.setDuracion(rs.getDouble("duracion"));

        java.sql.Date reg = rs.getDate("registro");
        if (reg != null) {
            p.setRegistro(new java.util.Date(reg.getTime()));
        }

        long facId = rs.getLong("facultad_id");
        if (!rs.wasNull()) {
            Facultad f = new Facultad();
            f.setID(facId);     
            p.setFacultad(f);
        }
        return p;
    }
}
