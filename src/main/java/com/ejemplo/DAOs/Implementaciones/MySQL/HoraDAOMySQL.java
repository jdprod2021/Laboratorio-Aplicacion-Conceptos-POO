package com.ejemplo.DAOs.Implementaciones.MySQL;

import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.HoraDAO;

public class HoraDAOMySQL implements HoraDAO{
    
    private final DataSource dataSource;
    private static HoraDAOMySQL horaDAOMySQL;

    private HoraDAOMySQL(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static HoraDAOMySQL crearHoraDAOMySQL(DataSource dataSource){
        if(horaDAOMySQL == null){
            synchronized (HoraDAOMySQL.class){
                if(horaDAOMySQL == null){
                    horaDAOMySQL = new HoraDAOMySQL(dataSource);
                }
            }
        }

        return horaDAOMySQL;
    }

    @Override
    public String obtenerHoraServidor() {
        
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery("SELECT CURRENT_TIME() AS hora")) {

            if (resultSet.next()) {
                return resultSet.getString("hora");
            } else {
                throw new RuntimeException("No se pudo obtener la hora del servidor.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la hora del servidor: " + e.getMessage(), e);
        }

    }

}
