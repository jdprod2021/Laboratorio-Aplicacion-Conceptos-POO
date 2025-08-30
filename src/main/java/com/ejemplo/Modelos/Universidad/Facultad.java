package com.ejemplo.Modelos.Universidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ejemplo.Modelos.Personas.Persona;

public class Facultad {

    private double id;
    private String nombre;
    private Persona decano;

    public Facultad(){}
    
    public Facultad(double id, String nombre, Persona decano) {
        this.id = id;
        this.nombre = nombre;
        this.decano = decano;
    }

    @Override
    public String toString() {
        return "Facultad{" + "id=" + id + ", nombre=" + nombre + ", decano=" + decano.toString() + '}';
    }

    public double getID() {
        return id;
    }

    public void guardar(Connection conn){

        decano.guardar(conn);

        String sql = "MERGE INTO FACULTAD (id, nombre, decano_id) KEY(id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, this.id);
            ps.setString(2, this.nombre);
            ps.setLong(3, ((long)this.decano.getId()));
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.err.println("Error al guardar FACULTAD: " + e.getMessage());
        }
    }

    public void cargar(Connection conn, int id){

        String sql = "SELECT * FROM FACULTAD WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.id = rs.getDouble("id");
                this.nombre = rs.getString("nombre");

                decano = new Persona();
                decano.cargar(conn, rs.getInt("decano_id"));
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error al cargar FACULTAD: " + e.getMessage());
        }
    }

}
