package com.ejemplo.Utils.Config;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Properties;
import javax.sql.DataSource;

import com.ejemplo.Fabricas.FabricaInterna.FabricaControladores;
import com.ejemplo.Fabricas.FabricaInterna.FabricaDAO;
import com.ejemplo.Utils.DB.ConexionDB;

public class AppConfig {
  private final Properties props = loadProps();
  public final String vendor = props.getProperty("db.vendor", "H2").trim().toUpperCase();

  private Properties loadProps() {
    try {
      Properties p = new Properties();
      String cli = System.getProperty("app.config");
      if (cli != null && Files.exists(Path.of(cli)))
        try (Reader r = Files.newBufferedReader(Path.of(cli), StandardCharsets.UTF_8)) { p.load(r); return p; }
      Path local = Path.of("config.properties");
      if (Files.exists(local))
        try (Reader r = Files.newBufferedReader(local, StandardCharsets.UTF_8)) { p.load(r); return p; }
      try (InputStream in = Thread.currentThread().getContextClassLoader()
          .getResourceAsStream("config.properties.default")) {
        if (in != null) { p.load(new InputStreamReader(in, StandardCharsets.UTF_8)); return p; }
      }
      throw new IllegalStateException("Sin config (externa ni default).");
    } catch (IOException e) { throw new RuntimeException("Error leyendo config", e); }
  }

  private DataSource crearDataSource() { return new ConexionDB(vendor, props).crearConexion(); }
  private FabricaDAO crearFabricaDAO() { return FabricaDAO.of(vendor, crearDataSource()); }
  public FabricaControladores crearFabricaControladores() { return new FabricaControladores(crearFabricaDAO()); }
}
