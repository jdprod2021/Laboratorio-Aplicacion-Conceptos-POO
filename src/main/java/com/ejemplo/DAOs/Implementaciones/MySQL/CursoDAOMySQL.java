package com.ejemplo.DAOs.Implementaciones.MySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.CursoDAO;
import com.ejemplo.Modelos.Curso;
import com.ejemplo.Modelos.Programa;
import com.ejemplo.Utils.Erros.SqlErrorDetailer;

public class CursoDAOMySQL implements CursoDAO {

    private final DataSource dataSource;
    private static CursoDAOMySQL cursoDAOMySQL;

    private CursoDAOMySQL(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static CursoDAOMySQL creaCursoDAOMySQL(DataSource dataSource){
        if(cursoDAOMySQL == null){
            synchronized (CursoDAOMySQL.class){
                if(cursoDAOMySQL == null){
                    cursoDAOMySQL = new CursoDAOMySQL(dataSource);
                }
            }
        }
        return cursoDAOMySQL;
    }

    @Override
    public Curso guardar(Curso entidad) {
        final String sql = "INSERT INTO CURSO (nombre, programa_id, activo) VALUES (?, ?, ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entidad.getNombre());
            if (entidad.getPrograma() != null) {
                ps.setLong(2, (long)entidad.getPrograma().getID());
            } else {
                ps.setNull(2, Types.BIGINT);
            }
            ps.setBoolean(3, entidad.isActivo());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    entidad.setId((int) keys.getLong(1));
                }
            }
            return entidad;

        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "INSERT CURSO", sql,
                    entidad.getNombre(),
                    (entidad.getPrograma() == null ? null : entidad.getPrograma().getID()),
                    entidad.isActivo());
        }
    }

    @Override
    public List<Curso> listar() {
        final String sql = "SELECT id, nombre, programa_id, activo FROM CURSO ORDER BY nombre";
        List<Curso> lista = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapRow(rs));
            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar CURSOS", e);
        }
    }

    @Override
    public void actualizar(Curso entidad) {
        final String sql = "UPDATE CURSO SET nombre = ?, programa_id = ?, activo = ? WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, entidad.getNombre());
            if (entidad.getPrograma() != null) {
                ps.setLong(2, (long)entidad.getPrograma().getID());
            } else {
                ps.setNull(2, Types.BIGINT);
            }
            ps.setBoolean(3, entidad.isActivo());
            ps.setLong(4, entidad.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "UPDATE CURSO", sql,
                    entidad.getNombre(),
                    (entidad.getPrograma() == null ? null : entidad.getPrograma().getID()),
                    entidad.isActivo(),
                    entidad.getId());
        }
    }

    @Override
    public void eliminar(Long id) {
        final String sql = "DELETE FROM CURSO WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "DELETE CURSO", sql, id);
        }
    }

    private Curso mapRow(ResultSet rs) throws SQLException {
        Curso c = new Curso();
        c.setId((int) rs.getLong("id"));
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
