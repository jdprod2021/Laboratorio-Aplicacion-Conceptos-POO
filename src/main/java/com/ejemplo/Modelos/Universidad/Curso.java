package com.ejemplo.Modelos.Universidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Curso {

    private int ID;
    private String nombre;
    private Programa programa;
    private boolean activo;

    public Curso() {}

    public Curso(int ID, String nombre, Programa programa, boolean activo) {
        this.ID = ID;
        this.nombre = nombre;
        this.programa = programa;
        this.activo = activo;
    }
    
    @Override
    public String toString() {
        return "ID: " + this.ID +  ", Nombre: " + this.nombre + ", Activo: " + this.activo + ", Programa: " + this.programa.toString();
    }

    public void guardar(Connection conn){

        programa.guardar(conn);

        String sql = "MERGE INTO CURSO (id, nombre, programa_id, activo) KEY(id) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, this.ID);        
            ps.setString(2, this.nombre);
            ps.setLong(3, ((long)this.programa.getID()));
            ps.setBoolean(4, this.activo);
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.err.println("Error al guardar CURSO: " + e.getMessage());
        }
    }

    public int getID() {
        return ID;
    }

    public void cargar(Connection conn, int id){

        String sql = "SELECT * FROM CURSO WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.ID = rs.getInt("id");
                this.nombre = rs.getString("nombre");
                this.activo = rs.getBoolean("activo");

                programa = new Programa();
                programa.cargar(conn, rs.getInt("programa_id"));
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error al cargar CURSO: " + e.getMessage());
        }
    }

}
