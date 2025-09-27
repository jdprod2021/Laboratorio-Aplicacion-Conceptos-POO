package com.ejemplo.DAOs.Implementaciones.Oracle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.CursoDAO;
import com.ejemplo.Modelos.Curso;
import com.ejemplo.Modelos.Programa;

public class CursoDAOOracle implements CursoDAO {
    
    private final DataSource dataSource;

    public CursoDAOOracle(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Curso guardar(Curso entidad) {
        final String sql = "INSERT INTO CURSO (id, nombre, programa_id, activo) " +
                           "VALUES (SEQ_CURSO.NEXTVAL, ?, ?, ?)";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql, 
                           PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entidad.getNombre());
            ps.setLong(2, (long)(entidad.getPrograma() != null ? entidad.getPrograma().getID() : 0L));
            ps.setBoolean(3, entidad.isActivo());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entidad.setId((int)generatedKeys.getLong(1)); // Asumiendo que el id es un Long
                }
            }
            return entidad;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar CURSO", e);
        }
    }

    @Override
    public List<Curso> listar() {
        final String sql = "SELECT id, nombre, programa_id, activo FROM CURSO ORDER BY nombre";
        List<Curso> lista = new ArrayList<>();
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar CURSOS", e);
        }
        return lista;
    }

    @Override
    public void actualizar(Curso entidad) {
        final String sql = "UPDATE CURSO SET nombre = ?, programa_id = ?, activo = ? WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, entidad.getNombre());
            ps.setLong(2, (long)(entidad.getPrograma() != null ? entidad.getPrograma().getID() : 0L));
            ps.setBoolean(3, entidad.isActivo());
            ps.setLong(4, (long)entidad.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar CURSO", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        final String sql = "DELETE FROM CURSO WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar CURSO", e);
        }
    }

    private Curso mapRow(ResultSet rs) throws SQLException {
        Curso c = new Curso();
        c.setId((int)rs.getLong("id"));
        c.setNombre(rs.getString("nombre"));
        c.setActivo(rs.getBoolean("activo"));

        long pid = rs.getLong("programa_id");
        if (!rs.wasNull()) {
            Programa p = new Programa();
            p.setID(pid);
            c.setPrograma(p);
        }
        return c;
    }
}
