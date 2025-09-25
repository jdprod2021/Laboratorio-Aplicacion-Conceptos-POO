package com.ejemplo.Repositorios;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.h2.tools.RunScript;

public final class DB {
  // H2 embebida en archivo (persistente)
  private static final String URL  = "jdbc:h2:file:./data/sisacad;AUTO_SERVER=TRUE";
  private static final String USER = "sa";
  private static final String PASS = "";

  private DB() {}

  /** Ejecuta tu schema una vez al arranque (usa CREATE TABLE IF NOT EXISTS en tu SQL). */
  public static void initSchema() {
    String rutaAbs = Path.of("DB", "esquema.sql").toAbsolutePath().toString();
    try {
      // H2 ejecuta el archivo completo (idempotente si tu SQL lo es)
      RunScript.execute(URL, USER, PASS, rutaAbs, StandardCharsets.UTF_8, false);
      System.out.println("Esquema verificado: " + rutaAbs);
    } catch (SQLException e) {
      System.err.println("Aviso al ejecutar esquema.sql: " + e.getMessage());
    }
  }

  /** Devuelve una Connection lista para usar (cierra donde la uses). */
  public static Connection get() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASS);
  }
}