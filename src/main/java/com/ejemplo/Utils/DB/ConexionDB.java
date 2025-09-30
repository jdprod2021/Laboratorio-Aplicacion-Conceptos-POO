package com.ejemplo.Utils.DB;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.Connection;
import java.util.Properties;
import javax.sql.DataSource;

import org.h2.tools.RunScript;
import org.apache.ibatis.jdbc.ScriptRunner;
import com.zaxxer.hikari.*;

public class ConexionDB {
  private final String vendor;
  private final Properties props;

  public ConexionDB(String vendor, Properties props) {
    this.vendor = vendor;
    this.props = props;
  }

  public DataSource crearConexion() {
    HikariConfig cfg = new HikariConfig();
    switch (vendor) {
      case "H2" -> {
        cfg.setDriverClassName("org.h2.Driver");
        conf(cfg, "db.h2.url", "jdbc:h2:file:./data/sisacad;AUTO_SERVER=TRUE",
                 "db.h2.user", "sa", "db.h2.pass", "");
      }
      case "MYSQL" -> {
        cfg.setDriverClassName("com.mysql.cj.jdbc.Driver");
        conf(cfg, "db.mysql.url",  "jdbc:mysql://localhost:3306/prueba?allowPublicKeyRetrieval=true&useSSL=false",
                 "db.mysql.user", "usuario", "db.mysql.pass", "1234");
      }
      case "ORACLE" -> {
        cfg.setDriverClassName("oracle.jdbc.OracleDriver");
        conf(cfg, "db.oracle.url",  "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                 "db.oracle.user", "system", "db.oracle.pass", "admin123");
      }
      default -> throw new IllegalArgumentException("Vendor no soportado: " + vendor);
    }

    HikariDataSource ds = new HikariDataSource(cfg);

    if (Boolean.parseBoolean(props.getProperty("db.init", "true"))) {
      String script = props.getProperty(
        "db.init.script",
        vendor.equals("H2") ? "DB/H2.sql" : vendor.equals("MYSQL") ? "DB/MySQL.sql" : "DB/Oracle.sql"
      );
      initSchema(ds, script);
    }
    return ds;
  }

  private void conf(HikariConfig c, String urlK, String urlDef, String userK, String userDef, String passK, String passDef) {
    c.setJdbcUrl(props.getProperty(urlK, urlDef));
    c.setUsername(props.getProperty(userK, userDef));
    c.setPassword(props.getProperty(passK, passDef));
  }

  /** classpath -> archivo; H2 usa RunScript, MySQL/Oracle usan ScriptRunner */
  private void initSchema(DataSource ds, String resourcePath) {
    try (Connection con = ds.getConnection(); Reader r = openReader(resourcePath)) {
      if ("H2".equals(vendor)) {
        RunScript.execute(con, r);
      } else if ("ORACLE".equals(vendor)) {
        ScriptRunner runner = new ScriptRunner(con);
        runner.setStopOnError(true);
        runner.setSendFullScript(false);
        runner.setDelimiter("/");           // <- Oracle: bloques PL/SQL terminan con línea "/"
        runner.setFullLineDelimiter(true);  // la línea debe ser exactamente "/"
        runner.runScript(r);
      } else { // MYSQL y demás
        ScriptRunner runner = new ScriptRunner(con);
        runner.setStopOnError(true);
        runner.setSendFullScript(false); // separa por ';'
        runner.runScript(r);
      }
      System.out.println("[DB] Esquema cargado: " + resourcePath);
    } catch (Exception e) {
      throw new RuntimeException("Error al ejecutar script: " + resourcePath, e);
    }
  }

  private Reader openReader(String resourcePath) throws IOException {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    URL url = cl.getResource(resourcePath);
    if (url == null) url = ConexionDB.class.getResource("/" + resourcePath);
    if (url != null) return new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);

    Path p = Path.of(resourcePath);
    if (!Files.exists(p)) p = Path.of(System.getProperty("user.dir")).resolve(resourcePath);
    if (!Files.exists(p)) throw new FileNotFoundException("No hallado: " + resourcePath);
    return Files.newBufferedReader(p, StandardCharsets.UTF_8);
  }
}
