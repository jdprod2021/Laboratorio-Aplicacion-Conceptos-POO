package com.ejemplo;

import java.sql.Connection;
import java.sql.SQLException;

import com.ejemplo.Fabricas.FabricaExterna.FactoryExterna;
import com.ejemplo.Utils.DB.ConexionDB;
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

        mostrarBannerInicial();

        // ========================================
        // PASO 1: PROBAR CONEXIÓN A BASE DE DATOS
        // ========================================

        System.out.println("🔗 PASO 1: Verificando conexión a base de datos...");
        System.out.println();



        // ========================================
        // PASO 2: INICIALIZAR APLICACIÓN CON FACTORY EXTERNA
        // ========================================

        System.out.println("🏭 PASO 2: Inicializando Factory Externa...");
        System.out.println();

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
     * Prueba la conexión a base de datos (código del backend)
     */


    /**
     * Banner inicial del sistema integrado
     */
    private static void mostrarBannerInicial() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                  ║");
        System.out.println("║        🎓 SISTEMA DE GESTIÓN ACADÉMICA - VERSIÓN COMPLETA 🎓    ║");
        System.out.println("║                                                                  ║");
        System.out.println("║  ┌──────────────────────────────────────────────────────────┐   ║");
        System.out.println("║  │  🔗 Prueba de Conexión BD (Backend)                    │   ║");
        System.out.println("║  │  🏭 Factory Externa (Tu implementación)                │   ║");
        System.out.println("║  │  🎨 Selección de Interfaz (Consola/GUI)               │   ║");
        System.out.println("║  │  🎛️  Sistema Completo Integrado                        │   ║");
        System.out.println("║  └──────────────────────────────────────────────────────────┘   ║");
        System.out.println("║                                                                  ║");
        System.out.println("║           ⚡ Inicialización en 3 pasos ⚡                       ║");
        System.out.println("║                                                                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        System.out.println();
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