package Modelos.Personas;

import java.sql.Connection;
import java.sql.SQLException;

public class Persona {

    private double id;
    private String nombre;
    private String apellido;
    private String email;

    public Persona() {}

    public Persona(double id, String nombre, String apellido, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;

    }

    @Override
    public String toString(){
        return "ID:" + this.id + " " + " Nombre: " + this.nombre + " Apellido: " + this.apellido + " Email: " + this.email;
    }

    public void guardar(Connection conn) {
        
        String sql = "MERGE INTO PERSONA (id, nombres, apellidos, email) KEY(id) VALUES (?, ?, ?, ?)";
        
        try (var ps = conn.prepareStatement(sql)) {
            // Si tu columna id es BIGINT en la BD, usa setLong:
            ps.setLong(1, (long) this.id);        // o ps.setDouble(1, this.id) si realmente es DOUBLE
            ps.setString(2, this.nombre);
            ps.setString(3, this.apellido);
            ps.setString(4, this.email);
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.err.println("Error al guardar PERSONA: " + e.getMessage());
        }
    }

    public void cargar(Connection conn, int id){

        String sql = "SELECT * FROM PERSONA WHERE id = ?";
        try (var ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            var rs = ps.executeQuery();
            if (rs.next()) {
                this.id = rs.getDouble("id");
                this.nombre = rs.getString("nombres");
                this.apellido = rs.getString("apellidos");
                this.email = rs.getString("email");
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar PERSONA: " + e.getMessage());
        }
    }

    public double getId() {
        return id;
    }
}

