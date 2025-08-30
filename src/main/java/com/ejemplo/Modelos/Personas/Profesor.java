package com.ejemplo.Modelos.Personas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Profesor extends Persona{

    private String TipoContrato;

    public Profesor() {}

    public Profesor (double id, String nombre, String apellido, String email, String TipoContrato) {
        super(id,nombre, apellido, email);
        this.TipoContrato = TipoContrato;
    }

    @Override
    public String toString(){
        return super.toString() + " TipoContrato: " + this.TipoContrato;
    }


    @Override
    public void guardar(Connection conn) {
        super.guardar(conn);

        String sql = "MERGE INTO PROFESOR (id, tipo_contrato) KEY(id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, this.getId());         
            ps.setString(2, this.TipoContrato);   
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.err.println("Error guardando PROFESOR: " + e.getMessage());
        }
    }

    @Override
    public void cargar(Connection conn, int id) {
        super.cargar(conn, id);

        String sql = "SELECT  * FROM PROFESOR WHERE id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    this.TipoContrato = rs.getString("tipo_contrato");
                } else {
                    System.err.println("No se encontró un profesor con ID: " + id);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error cargando PROFESOR: " + e.getMessage());
        }
    }

    public String getTipoContrato() {
        return TipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        TipoContrato = tipoContrato;
    }
}
