package com.ejemplo.DAOs.Implementaciones.MySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.ProgramaDAO;
import com.ejemplo.Modelos.Facultad;
import com.ejemplo.Modelos.Programa;
import com.ejemplo.Utils.Erros.SqlErrorDetailer;

public class ProgramaDAOMySQL implements ProgramaDAO {

    private final DataSource dataSource;
    private static ProgramaDAOMySQL programaDAOMySQL;

    private ProgramaDAOMySQL(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ProgramaDAOMySQL crearProgramaDAOMySQL(DataSource dataSource){
        if(programaDAOMySQL == null){
            synchronized (ProgramaDAOMySQL.class){
                if(programaDAOMySQL == null){
                    programaDAOMySQL = new ProgramaDAOMySQL(dataSource);
                }
            }
        }
        return programaDAOMySQL;
    }

    @Override
    public Programa guardar(Programa entidad) {
        final String sql = "INSERT INTO PROGRAMA (nombre, duracion, registro, facultad_id) VALUES (?, ?, ?, ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entidad.getNombre());
            ps.setDouble(2, entidad.getDuracion());
            ps.setDate(3, new java.sql.Date(entidad.getRegistro().getTime()));

            if (entidad.getFacultad() != null) {
                ps.setLong(4, (long)entidad.getFacultad().getID());
            } else {
                ps.setNull(4, Types.BIGINT);
            }

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entidad.setID(generatedKeys.getLong(1));
                }
            }
            return entidad;

        } catch (SQLException e) {
            Long facId = (entidad.getFacultad() == null ? null : (long)entidad.getFacultad().getID());
            throw SqlErrorDetailer.wrap(e, "INSERT PROGRAMA", sql,
                entidad.getNombre(), entidad.getDuracion(), entidad.getRegistro(), facId);
        }
    }

    @Override
    public List<Programa> listar() {
        final String sql = "SELECT id, nombre, duracion, registro, facultad_id FROM PROGRAMA ORDER BY nombre";
        List<Programa> lista = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
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
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, entidad.getNombre());
            ps.setDouble(2, entidad.getDuracion());
            ps.setDate(3, new java.sql.Date(entidad.getRegistro().getTime()));

            if (entidad.getFacultad() != null) {
                ps.setLong(4, (long)entidad.getFacultad().getID());
            } else {
                ps.setNull(4, Types.BIGINT);
            }
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
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "DELETE PROGRAMA", sql, id);
        }
    }

    @Override
    public Optional<Programa> buscarPorId(Long id) {
        final String sql = "SELECT id, nombre, duracion, registro, facultad_id FROM PROGRAMA WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "SELECT PROGRAMA BY ID", sql, id);
        }
        return Optional.empty();
    }

    private Programa mapRow(ResultSet rs) throws SQLException {
        Programa p = new Programa();
        p.setID(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        p.setDuracion(rs.getDouble("duracion"));

        java.sql.Date reg = rs.getDate("registro");
        if (reg != null) p.setRegistro(new java.util.Date(reg.getTime()));

        long facId = rs.getLong("facultad_id");
        if (!rs.wasNull()) {
            Facultad f = new Facultad();
            f.setID(facId);
            p.setFacultad(f);
        }
        return p;
    }
}
