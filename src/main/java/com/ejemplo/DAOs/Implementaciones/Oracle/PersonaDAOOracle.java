package com.ejemplo.DAOs.Implementaciones.Oracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.PersonaDAO;
import com.ejemplo.Modelos.Persona;
import com.ejemplo.Utils.Erros.SqlErrorDetailer;

public class PersonaDAOOracle implements PersonaDAO {

    private final DataSource dataSource;
    private static PersonaDAOOracle personaDAOOracle;

    private PersonaDAOOracle(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static PersonaDAOOracle crearPersonaDAOOracle(DataSource dataSource){
        if(personaDAOOracle == null){
            synchronized (PersonaDAOOracle.class){
                if(personaDAOOracle == null){
                    personaDAOOracle = new PersonaDAOOracle(dataSource);
                }
            }
        }
        return personaDAOOracle;
    }

    @Override
    public Persona guardar(Persona entidad) {
        final String insertSql =
            "INSERT INTO PERSONA (nombres, apellidos, email) VALUES (?, ?, ?)";

        try (Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(
                insertSql,
                new String[] { "ID" } // pide la PK generada
            )) {

            ps.setString(1, entidad.getNombres());
            ps.setString(2, entidad.getApellidos());
            ps.setString(3, entidad.getEmail());
            int updated = ps.executeUpdate();
            if (updated != 1) throw new SQLException("No se insertó PERSONA (updated=" + updated + ")");

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) entidad.setId(keys.getLong(1));
            }
            return entidad;

        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "INSERT PERSONA (ORACLE)", insertSql,
                entidad.getNombres(), entidad.getApellidos(), entidad.getEmail());
        }
    }

    @Override
    public List<Persona> listar() {
        final String sql = "SELECT id, nombres, apellidos, email FROM PERSONA ORDER BY apellidos, nombres";
        List<Persona> lista = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Persona p = new Persona();
                p.setId(rs.getLong("id"));
                p.setNombres(rs.getString("nombres"));
                p.setApellidos(rs.getString("apellidos"));
                p.setEmail(rs.getString("email"));
                lista.add(p);
            }
            return lista;

        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "SELECT PERSONAS (ORACLE)", sql);
        }
    }

    @Override
    public void actualizar(Persona entidad) {
        final String sql = "UPDATE PERSONA SET nombres=?, apellidos=?, email=? WHERE id=?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, entidad.getNombres());
            ps.setString(2, entidad.getApellidos());
            ps.setString(3, entidad.getEmail());
            ps.setLong(4, (long)entidad.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "UPDATE PERSONA (ORACLE)", sql,
                entidad.getNombres(), entidad.getApellidos(), entidad.getEmail(), entidad.getId());
        }
    }

    @Override
    public void eliminar(Long id) {
        final String sql = "DELETE FROM PERSONA WHERE id=?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "DELETE PERSONA (ORACLE)", sql, id);
        }
    }

    @Override
    public Optional<Persona> buscarPorCorreo(String correo) {
        final String sql = "SELECT id, nombres, apellidos, email FROM PERSONA WHERE email = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Persona p = new Persona();
                    p.setId(rs.getLong("id"));
                    p.setNombres(rs.getString("nombres"));
                    p.setApellidos(rs.getString("apellidos"));
                    p.setEmail(rs.getString("email"));
                    return Optional.of(p);
                }
            }
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "SELECT PERSONA BY EMAIL (ORACLE)", sql, correo);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Persona> buscarPorId(Long id) {
        final String sql = "SELECT id, nombres, apellidos, email FROM PERSONA WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Persona p = new Persona();
                    p.setId(rs.getLong("id"));
                    p.setNombres(rs.getString("nombres"));
                    p.setApellidos(rs.getString("apellidos"));
                    p.setEmail(rs.getString("email"));
                    return Optional.of(p);
                }
            }
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "SELECT PERSONA BY ID (ORACLE)", sql, id);
        }
        return Optional.empty();
    }
}
