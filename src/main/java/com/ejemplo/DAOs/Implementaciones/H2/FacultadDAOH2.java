package com.ejemplo.DAOs.Implementaciones.H2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.FacultadDAO;
import com.ejemplo.Modelos.Facultad;
import com.ejemplo.Modelos.Persona;

public class FacultadDAOH2 implements FacultadDAO{

    private final DataSource dataSource;

    public FacultadDAOH2(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Facultad guardar(Facultad entidad) {
        final String sql = "INSERT INTO FACULTAD (nombre, decano_id) VALUES (?, ?)";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, entidad.getNombre());
            ps.setLong(2, (long)(entidad.getDecano() != null ? entidad.getDecano().getId() : 0L));
            ps.executeUpdate();

             // Obtener la clave generada
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // Asignar el id generado a la entidad
                    entidad.setID(generatedKeys.getLong(1)); // Asumiendo que el id es un Long
                }
            }
        
            return entidad;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar FACULTAD", e);
        }
    }

    @Override
    public List<Facultad> listar() {
        final String sql = "SELECT id, nombre, decano_id FROM FACULTAD ORDER BY nombre";
        List<Facultad> lista = new ArrayList<>();
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar FACULTADES", e);
        }
        return lista;
    }

    @Override
    public void actualizar(Facultad entidad) {
        final String sql = "UPDATE FACULTAD SET nombre = ?, decano_id = ? WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, entidad.getNombre());
            ps.setLong(2, (long)(entidad.getDecano() != null ? entidad.getDecano().getId() : 0L));
            ps.setLong(3, (long)entidad.getID());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar FACULTAD", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        final String sql = "DELETE FROM FACULTAD WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar FACULTAD", e);
        }
    }

    @Override
    public Optional<Facultad> buscarPorId(Long id) {
        final String sql = "SELECT id, nombre, decano_id FROM FACULTAD WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar FACULTAD por ID", e);
        }
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
