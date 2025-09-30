package com.ejemplo.Fabricas.FabricaExterna;

import java.util.Scanner;

import com.ejemplo.Utils.Config.AppConfig;
import com.ejemplo.Vistas.InterfaceVista;
import com.ejemplo.Vistas.TipoVista;
import com.ejemplo.Vistas.VistaConsola;
//import com.ejemplo.Vistas.VistaGUI;
import com.ejemplo.Fabricas.FabricaInterna.FabricaControladores;

/**
 * Factory Externa - Patrón Factory para crear vistas
 * Responsable de desacoplar la creación de vistas del resto del sistema
 */
public class FactoryExterna {

    private static AppConfig appConfig;
    private static FabricaControladores fabricaControladores;

    /**
     * Inicializa la configuración de la aplicación
     */
    private static void inicializarConfiguracion() {
        if (appConfig == null) {
            appConfig = new AppConfig();
            fabricaControladores = appConfig.crearFabricaControladores();
        }
    }

    /**
     * Crea una vista según el tipo especificado
     * @param tipo El tipo de vista a crear
     * @return Una instancia de la vista solicitada
     * @throws IllegalArgumentException si el tipo no es soportado
     */
    public static InterfaceVista crearVista(TipoVista tipo) {
        inicializarConfiguracion();

        InterfaceVista vista;

        switch (tipo) {
            case CONSOLA:
                System.out.println("🏭 Factory Externa: Creando Vista de Consola...");
                vista = new VistaConsola();
                break;

            case GUI:
                System.out.println("🏭 Factory Externa: Creando Vista GUI...");
                vista = new VistaConsola();
                break;

            default:
                throw new IllegalArgumentException("Tipo de vista no soportado: " + tipo);
        }

        // Configurar la vista con los controladores
        vista.setFabricaControladores(fabricaControladores);

        return vista;
    }

    /**
     * Consulta al usuario qué tipo de vista prefiere
     * @return El tipo de vista seleccionado por el usuario
     */
    public static TipoVista consultarTipoVistaAlUsuario() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("🚀 BIENVENIDO AL SISTEMA DE GESTIÓN ACADÉMICA");
        System.out.println("=".repeat(50));
        System.out.println("Por favor, seleccione el tipo de interfaz que desea usar:");
        System.out.println();

        // Mostrar opciones disponibles
        TipoVista[] tipos = TipoVista.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i]);
        }

        System.out.println();
        System.out.print("👉 Ingrese su opción (1-" + tipos.length + "): ");

        try {
            int opcion = scanner.nextInt();

            if (opcion >= 1 && opcion <= tipos.length) {
                TipoVista tipoSeleccionado = tipos[opcion - 1];
                System.out.println("✅ Ha seleccionado: " + tipoSeleccionado.getNombre());
                return tipoSeleccionado;
            } else {
                System.out.println("❌ Opción inválida. Usando Consola por defecto.");
                return TipoVista.CONSOLA;
            }

        } catch (Exception e) {
            System.out.println("❌ Entrada inválida. Usando Consola por defecto.");
            scanner.nextLine(); // Limpiar buffer
            return TipoVista.CONSOLA;
        }
    }

    /**
     * Método principal que maneja todo el flujo de creación de vista
     * Consulta al usuario, crea la vista e inicializa la aplicación
     * @return Vista completamente configurada y lista para usar
     */
    public static InterfaceVista crearAplicacionCompleta() {
        try {
            // 1. Consultar al usuario qué tipo de vista quiere
            TipoVista tipoSeleccionado = consultarTipoVistaAlUsuario();

            // 2. Crear la vista usando la factory
            InterfaceVista vista = crearVista(tipoSeleccionado);

            // 3. Inicializar la vista
            vista.inicializar();

            System.out.println("🎉 Aplicación inicializada correctamente!");

            return vista;

        } catch (Exception e) {
            System.err.println("💥 Error al inicializar la aplicación: " + e.getMessage());
            e.printStackTrace();

            // Fallback a consola en caso de error
            System.out.println("🔄 Iniciando en modo consola como respaldo...");
            InterfaceVista vistaRespaldo = crearVista(TipoVista.CONSOLA);
            vistaRespaldo.inicializar();
            return vistaRespaldo;
        }
    }

    /**
     * Obtiene la fábrica de controladores configurada
     * @return FabricaControladores configurada
     */
    public static FabricaControladores getFabricaControladores() {
        inicializarConfiguracion();
        return fabricaControladores;
    }
}