package com.ejemplo.DAOs.Implementaciones.Oracle;

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

public class ProgramaDAOOracle implements ProgramaDAO {

    private final DataSource dataSource;

    public ProgramaDAOOracle(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Programa guardar(Programa entidad) {
        final String sql = "INSERT INTO PROGRAMA (id, nombre, duracion, registro, facultad_id) " +
                           "VALUES (SEQ_PROGRAMA.NEXTVAL, ?, ?, ?, ?)";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, entidad.getNombre());
            ps.setDouble(2, entidad.getDuracion());
            ps.setDate(3, new java.sql.Date(entidad.getRegistro().getTime()));
            ps.setLong(4, (long)(entidad.getFacultad() != null ? entidad.getFacultad().getID() : 0L));
            ps.executeUpdate();

            // Obtener el ID generado de la secuencia
            String seqSql = "SELECT SEQ_PROGRAMA.CURRVAL FROM DUAL";
            try (PreparedStatement psSeq = dataSource.getConnection().prepareStatement(seqSql);
                 ResultSet rs = psSeq.executeQuery()) {
                if (rs.next()) {
                    entidad.setID(rs.getLong(1));
                }
            }
            return entidad;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar PROGRAMA", e);
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
            throw new RuntimeException("Error al listar PROGRAMAS", e);
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
            throw new RuntimeException("Error al actualizar PROGRAMA", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        final String sql = "DELETE FROM PROGRAMA WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar PROGRAMA", e);
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
            throw new RuntimeException("Error al buscar PROGRAMA por nombre", e);
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
