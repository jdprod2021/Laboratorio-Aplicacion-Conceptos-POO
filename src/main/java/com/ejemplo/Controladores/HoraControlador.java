package com.ejemplo.Controladores;

import com.ejemplo.DAOs.Interfaces.HoraDAO;

public class HoraControlador {
    
    private final HoraDAO horaDAO;
    private static HoraControlador horaControlador;


    private HoraControlador(HoraDAO horaDAO){

        this.horaDAO = horaDAO;
    }

    public static HoraControlador crearHoraControlador(HoraDAO horaDAO){
        if(horaControlador == null){
            synchronized (HoraControlador.class) {
                if(horaControlador == null){
                    horaControlador = new HoraControlador(horaDAO);
                }
            }
        }
        return horaControlador;
    }

    public String obtenerHoraServidor(){
        return horaDAO.obtenerHoraServidor();
    }

}
