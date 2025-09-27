package com.ejemplo.Utils.Config;

import java.io.FileInputStream;
import java.util.Properties;

import javax.sql.DataSource;

import com.ejemplo.Fabricas.FabricaInterna.FabricaControladores;
import com.ejemplo.Fabricas.FabricaInterna.FabricaDAO;
import com.ejemplo.Utils.DB.ConexionDB;

public class AppConfig {
    
    public final String vendor;

    public AppConfig() {
        Properties properties = new Properties();
        try (FileInputStream configFile = new FileInputStream("config.properties")) {
            properties.load(configFile);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo cargar el archivo de configuración", e);
        }
        this.vendor = properties.getProperty("db.vendor").toUpperCase();
    }

    private DataSource crearDataSource(){
        ConexionDB conexionDB = new ConexionDB(vendor);
        return conexionDB.crearConexion();
    }

    private FabricaDAO crearFabricaDAO(){
        return FabricaDAO.of(vendor, crearDataSource());
    }

    public FabricaControladores crearFabricaControladores() {
        return new FabricaControladores(crearFabricaDAO());
    }
}
