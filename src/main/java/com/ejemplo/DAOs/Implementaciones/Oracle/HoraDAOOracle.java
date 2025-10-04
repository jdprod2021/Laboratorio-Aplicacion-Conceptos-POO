package com.ejemplo.DAOs.Implementaciones.Oracle;

import javax.sql.DataSource;

import com.ejemplo.DAOs.Interfaces.HoraDAO;

public class HoraDAOOracle implements HoraDAO{

    private final DataSource dataSource;
    private static HoraDAOOracle horaDAOOracle;

    private HoraDAOOracle(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static HoraDAOOracle crearHoraDAOOracle(DataSource dataSource){
        if(horaDAOOracle == null){
            synchronized (HoraDAOOracle.class){
                if(horaDAOOracle == null){
                    horaDAOOracle = new HoraDAOOracle(dataSource);
                }
            }
        }

        return horaDAOOracle;
    }

    @Override
    public String obtenerHoraServidor() {
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery("SELECT TO_CHAR(SYSDATE, 'HH24:MI:SS') AS hora FROM dual")) {

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
