package com.ejemplo.Fabricas.FabricaExterna;

import com.ejemplo.Utils.Config.AppConfig;
import com.ejemplo.Vistas.Implementaciones.VistaConsola;
import com.ejemplo.Vistas.Implementaciones.VistaSwing;
import com.ejemplo.Vistas.Interface.Vista;

/**
 * Factory Externa - Patrón Factory para crear vistas
 * Responsable de desacoplar la creación de vistas del resto del sistema
 */
public class FactoryExterna {

    private static FactoryExterna factoryExterna;
    private FabricaControladores fabricaControladores;


    private FactoryExterna(){
        AppConfig appConfig = new AppConfig();
        fabricaControladores = appConfig.crearFabricaControladores();
    }

    public static FactoryExterna crearFactoryExterna(){
        if(factoryExterna == null){
            factoryExterna = new FactoryExterna();
        }

        return factoryExterna;
    } 

    public Vista crearVista(String tipo) {
        Vista vista;

        switch (tipo.toUpperCase()) {
            case "CONSOLA":
                System.out.println("🏭 Factory Externa: Creando Vista de Consola...");
                vista = new VistaConsola(fabricaControladores);
                break;

            case "GUI":
                System.out.println("🏭 Factory Externa: Creando Vista GUI...");
                vista = new VistaSwing(fabricaControladores);
                break;

            default:
                throw new IllegalArgumentException("Tipo de vista no soportado: " + tipo);
        }

        return vista;
    }
}