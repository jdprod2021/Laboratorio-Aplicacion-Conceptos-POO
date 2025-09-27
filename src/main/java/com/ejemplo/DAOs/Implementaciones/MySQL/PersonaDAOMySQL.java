package com.ejemplo.DAOs.Implementaciones.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.PersonaDAO;
import com.ejemplo.Modelos.Persona;

public class PersonaDAOMySQL implements PersonaDAO {
    private final DataSource dataSource;

    public PersonaDAOMySQL(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Persona guardar(Persona entidad) {
        String sql = "INSERT INTO PERSONA (nombres, apellidos, email) VALUES (?, ?, ?)";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql, 
                           PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entidad.getNombres());
            ps.setString(2, entidad.getApellidos());
            ps.setString(3, entidad.getEmail());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entidad.setId(generatedKeys.getLong(1)); // ID generado automáticamente
                }
            }

            return entidad;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar PERSONA", e);
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
            throw new RuntimeException("Error al listar PERSONAS", e);
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
            throw new RuntimeException("Error al actualizar PERSONA", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM PERSONA WHERE id=?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar PERSONA", e);
        }
    }

    @Override
    public Optional<Persona> buscarPorCorreo(String correo) {
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
            throw new RuntimeException("Error al buscar PERSONA por correo", e);
        }
    }

    @Override
    public Optional<Persona> buscarPorId(Long id) {
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
            throw new RuntimeException("Error al buscar PERSONA por ID", e);
        }
    }
}
