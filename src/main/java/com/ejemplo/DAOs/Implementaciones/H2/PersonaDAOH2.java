package com.ejemplo.DAOs.Implementaciones.H2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.PersonaDAO;
import com.ejemplo.Modelos.Persona;
import com.ejemplo.infra.SqlErrorDetailer;

public class PersonaDAOH2 implements PersonaDAO{
    private final DataSource dataSource;
    
    public PersonaDAOH2(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Persona guardar(Persona entidad) {
        String sql = "INSERT INTO PERSONA (nombres, apellidos, email) " +
                     "VALUES (?, ?, ?)";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, entidad.getNombres());
            ps.setString(2, entidad.getApellidos());
            ps.setString(3, entidad.getEmail());
            ps.executeUpdate();

            // Obtener la clave generada
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // Asignar el id generado a la entidad
                    entidad.setId(generatedKeys.getLong(1)); // Asumiendo que el id es un Long
                }
            }

            return entidad;

        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "INSERT PERSONA", sql,
                entidad.getNombres(), entidad.getApellidos(), entidad.getEmail());
        }
    }

    @Override
    public List<Persona> listar() {
        String sql = "SELECT id, nombres, apellidos, email FROM PERSONA ORDER BY apellidos, nombres";
        List<Persona> lista = new ArrayList<>();

        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Persona persona = new Persona();
                persona.setId(rs.getLong("id"));
                persona.setNombres(rs.getString("nombres"));
                persona.setApellidos(rs.getString("apellidos"));
                persona.setEmail(rs.getString("email"));
                lista.add(persona);
            }
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "SELECT PERSONAS", sql);
        }
        return lista;
    }

    @Override
    public void actualizar(Persona entidad) {
        String sql = "UPDATE PERSONA SET nombres=?, apellidos=?, email=? WHERE id=?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, entidad.getNombres());
            ps.setString(2, entidad.getApellidos());
            ps.setString(3, entidad.getEmail());
            ps.setLong(4, (long)entidad.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "UPDATE PERSONA", sql,
                entidad.getNombres(), entidad.getApellidos(), entidad.getEmail(), entidad.getId());
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM PERSONA WHERE id=?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "DELETE PERSONA", sql, id);
        }
    }

    @Override
    public Optional<Persona> buscarPorCorreo(String correo){
        String sql = "SELECT id, nombres, apellidos, email FROM PERSONA WHERE email = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Persona persona = new Persona();
                    persona.setId(rs.getLong("id"));
                    persona.setNombres(rs.getString("nombres"));
                    persona.setApellidos(rs.getString("apellidos"));
                    persona.setEmail(rs.getString("email"));
                    return Optional.of(persona);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "SELECT PERSONA BY EMAIL", sql, correo);
        }
    }

    @Override
    public Optional<Persona> buscarPorId(Long id){
        String sql = "SELECT id, nombres, apellidos, email FROM PERSONA WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Persona persona = new Persona();
                    persona.setId(rs.getLong("id"));
                    persona.setNombres(rs.getString("nombres"));
                    persona.setApellidos(rs.getString("apellidos"));
                    persona.setEmail(rs.getString("email"));
                    return Optional.of(persona);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw SqlErrorDetailer.wrap(e, "SELECT PERSONA BY ID", sql, id);
        }
    }
}
