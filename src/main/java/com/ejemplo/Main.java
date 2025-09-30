package com.ejemplo;

import com.ejemplo.Fabricas.FabricaExterna.FactoryExterna;
import com.ejemplo.Vistas.InterfaceVista;

/**
 * Main integrado que combina:
 * 1. Prueba de conexión a BD (del backend)
 * 2. Factory Externa (tu implementación)
 *
 * Este es el punto de entrada COMPLETO del sistema
 */
public class Main {

    public static void main(String[] args) {




        try {

            // Tu implementación de Factory Externa
            InterfaceVista vistaSeleccionada = FactoryExterna.crearAplicacionCompleta();

            System.out.println("🚀 PASO 3: Ejecutando aplicación...");
            System.out.println();

            vistaSeleccionada.ejecutar();

        } catch (Exception e) {
            System.err.println("💥 ERROR en Factory Externa: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("🏁 Aplicación finalizada correctamente.");
    }


    /**
     * Método para forzar tipo de BD específico
     */
    public static void ejecutarConBD(String vendor) {
        System.out.println("🗃️ Forzando uso de base de datos: " + vendor);

        // Modificar temporalmente para pruebas
        // TODO: Integrar con config.properties

        main(new String[0]);
    }
}