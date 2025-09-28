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

        boolean conexionExitosa = probarConexionBaseDatos();

        if (!conexionExitosa) {
            System.err.println("❌ No se puede continuar sin conexión a BD.");
            System.err.println("   Revisa el archivo config.properties");
            System.exit(1);
        }

        System.out.println("✅ Conexión a base de datos verificada!");
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
    private static boolean probarConexionBaseDatos() {

        // Usar H2 por defecto para development
        String vendor = "H2"; // Cambia a "MYSQL" o "ORACLE" según tu config

        try {
            ConexionDB conexionDB = new ConexionDB(vendor);

            try (Connection conn = conexionDB.crearConexion().getConnection()) {
                System.out.println("   ✅ Conexión exitosa a " + vendor + "!");
                System.out.println("   🔒 Conexión cerrada.");
                return true;

            } catch (SQLException e) {
                System.err.println("   ❌ Error SQL: " + e.getMessage());
                return false;
            }

        } catch (Exception e) {
            System.err.println("   ❌ Error al intentar conectar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

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
        System.out.println("║  │  🔗 Verificación de Conexión BD                        │   ║");
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
