// Main en el paquete por defecto (sin "package")
import java.sql.*;

public class Main {

    // Opción A: H2 EMBEBIDA EN MEMORIA (se borra al cerrar el proceso)
    //private static final String URL  = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    // Opción B: H2 EMBEBIDA EN ARCHIVO (persistente) -> descomenta y crea la carpeta 'data'
    private static final String URL  = "jdbc:h2:file:./data/demo;AUTO_SERVER=TRUE";

    private static final String USER = "sa";
    private static final String PASS = "";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            // 1) Crear tabla
            try (Statement st = conn.createStatement()) {
                st.execute("""
                    CREATE TABLE IF NOT EXISTS persona (
                      id IDENTITY PRIMARY KEY,
                      nombre VARCHAR(100) NOT NULL,
                      edad INT
                    )
                """);
            }

            // 2) Insertar datos
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO persona(nombre, edad) VALUES (?,?)")) {
                ps.setString(1, "Ana");    ps.setInt(2, 25); ps.executeUpdate();
                ps.setString(1, "Carlos"); ps.setInt(2, 31); ps.executeUpdate();
            }

            // 3) Consultar y mostrar
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT id, nombre, edad FROM persona ORDER BY id");
                 ResultSet rs = ps.executeQuery()) {

                System.out.println("== Personas ==");
                while (rs.next()) {
                    long id = rs.getLong("id");
                    String nombre = rs.getString("nombre");
                    int edad = rs.getInt("edad");
                    System.out.printf("%d | %s | %d%n", id, nombre, edad);
                }
            }

            System.out.println("\nOK: H2 embebida funcionando -> " + URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
