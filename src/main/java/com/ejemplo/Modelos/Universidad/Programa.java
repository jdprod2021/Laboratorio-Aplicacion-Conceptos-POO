package com.ejemplo.Modelos.Universidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class Programa {
    
    private double id;
    private String nombre;
    private double duracion;
    private Date registro;
    private Facultad facultad; // ASOCIACION FACULTAD

    public Programa(){}

    public Programa(double id, String nombre, double duracion, Date registro, Facultad facultad){
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.registro = registro;
        this.facultad = facultad;
    }

    @Override
    public String toString(){
        return id + " " + nombre + " " + duracion + " " + registro + " " + facultad.toString();
    }

    public double getID() {
        return id;
    }

    public void guardar(Connection conn){

        facultad.guardar(conn);

        String sql = "MERGE INTO PROGRAMA (id, nombre, duracion, registro, facultad_id) KEY(id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, this.id);
            ps.setString(2, this.nombre);
            ps.setDouble(3, this.duracion);
            ps.setDate(4, new java.sql.Date(this.registro.getTime()));
            ps.setDouble(5, this.facultad.getID());
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.err.println("Error al guardar PROGRAMA: " + e.getMessage());
        }
    }

    public void cargar(Connection conn, int id){

        String sql = "SELECT * FROM PROGRAMA WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.id = rs.getDouble("id");
                this.nombre = rs.getString("nombre");
                this.duracion = rs.getDouble("duracion");
                this.registro = rs.getDate("registro");

                facultad = new Facultad();
                facultad.cargar(conn, rs.getInt("facultad_id"));
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error al cargar PROGRAMA: " + e.getMessage());
        }
    }

}
