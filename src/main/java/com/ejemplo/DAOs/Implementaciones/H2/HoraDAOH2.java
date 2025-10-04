package com.ejemplo.DAOs.Implementaciones.H2;

import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.HoraDAO;

public class HoraDAOH2 implements HoraDAO {

    private final DataSource dataSource;
    private static HoraDAOH2 horaDAOH2;

    private HoraDAOH2(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static HoraDAOH2 crearHoraDAOH2(DataSource dataSource){
        if(horaDAOH2 == null){
            synchronized (HoraDAOH2.class){
                if(horaDAOH2 == null){
                    horaDAOH2 = new HoraDAOH2(dataSource);
                }
            }
        }

        return horaDAOH2;
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
