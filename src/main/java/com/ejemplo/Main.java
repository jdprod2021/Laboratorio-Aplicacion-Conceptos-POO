package com.ejemplo;

import java.sql.Connection;
import java.sql.SQLException;

import com.ejemplo.Utils.DB.ConexionDB;

public class Main {
    public static void main(String[] args) {
        String vendor = "H2"; // "MYSQL" o "ORACLE" si cambias
        ConexionDB conexionDB = new ConexionDB(vendor);

        try (Connection conn = conexionDB.crearConexion().getConnection()) {
            System.out.println("✅ Conexión exitosa a " + vendor + "!");
            // Ejecuta el smoke test (usa un DS nuevo para que pueda abrir más conexiones si hace falta)
            DbSmokeTest.run(conexionDB.crearConexion(), vendor);
            System.out.println("🔒 Conexión cerrada.");
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Falla en smoke test: " + e.getMessage());
        }
    }
}
