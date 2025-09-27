package com.ejemplo.Utils.DB;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.jdbc.ScriptRunner;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConexionDB {

    private final String vendor;

    public ConexionDB(String vendor) {
        this.vendor = vendor;
    }

    private void crearConexionH2(HikariConfig config) {
        config.setJdbcUrl("jdbc:h2:file:./data/sisacad;AUTO_SERVER=TRUE");
        config.setUsername("sa");
        config.setPassword("");
    }

    private void crearConexionMySQL(HikariConfig config) {
        config.setJdbcUrl("jdbc:mysql://localhost:3306/prueba?allowPublicKeyRetrieval=true&useSSL=false");
        config.setUsername("usuario");
        config.setPassword("1234");
    }

    private void crearConexionOracle(HikariConfig config) {
        config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521/XEPDB1");
        config.setUsername("system");
        config.setPassword("admin123");
    }

    private void initSchema(HikariConfig config, String rutaEsquema) {
        try (HikariDataSource dataSource = new HikariDataSource(config);
            Connection con = dataSource.getConnection();
            Reader reader = Files.newBufferedReader(Paths.get("DB", rutaEsquema), StandardCharsets.UTF_8)) {

            con.setAutoCommit(false);
            ScriptRunner r = new ScriptRunner(con);
            r.setStopOnError(true);
            r.runScript(reader);
            con.commit();

            if(false){
                r.setStopOnError(true);
                r.setSendFullScript(false);        // ejecuta por sentencias
                r.setDelimiter("/");               // <-- terminador de bloque
                r.setFullLineDelimiter(true);      // <-- la línea debe ser exactamente "/"

                r.runScript(reader);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar el esquema H2", e);
        }
    }

    public DataSource crearConexion() {
        HikariConfig config = new HikariConfig();
        switch (vendor) {
            case "H2":
                crearConexionH2(config);
                initSchema(config,"H2.sql");
                break;
            case "MYSQL":
                crearConexionMySQL(config);
                initSchema(config,"MySQL.sql");
                break;
            case "ORACLE":
                crearConexionOracle(config);
                initSchema(config,"Oracle.sql");
                break;
            default:
                throw new IllegalArgumentException("Vendor no soportado: " + vendor);
        }
        return new HikariDataSource(config);
    }

}
